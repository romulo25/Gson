package com.netmind.dao;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.netmind.common.model.LocalDateSerializer;
import com.netmind.common.model.Student;
import com.netmind.dao.contracts.StudentDao;

public class StudentDaoImpl implements StudentDao {

	private static ArrayList<Student> studentList = null;
	static final Logger logger = Logger.getLogger(StudentDaoImpl.class);

	static {
		studentList = new ArrayList<Student>();
	}

	@Override
	public boolean add(Student student) {
		logger.info("add method called");
		studentList.add(student);
		return true;
	}

	@Override
	public boolean addStudentToFile(Student student) throws IOException {
		logger.info("addStudentToFile method called");
		try (FileWriter fileWriter = new FileWriter(
				FileManagerDao.getFileName("txt"), true);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
			bufferWriter.write(student.toTxtFile());
			bufferWriter.write(System.lineSeparator());
		} catch (IOException e) {
			logger.error(e.getMessage() + student.toString());
			throw e;
		}

		return true;
	}

	@Override
	public boolean addToJsonFile(Student student) throws IOException {
		// TODO Auto-generated method stub
		List<Student> studentList = getAllFromJson();
		studentList.add(student);

		writeDataToJsonFile(studentList);

		return true;
	}

	@Override
	public ArrayList<Student> getAllFromJson() {
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class,
				new JsonDeserializer<LocalDate>() {

					@Override
					public LocalDate deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {

						DateTimeFormatter formatter = DateTimeFormatter
								.ofPattern("dd-MM-yyyy");

						// convert String to LocalDate
						LocalDate localDate = LocalDate
								.parse(json.getAsString(), formatter);
						// TODO Auto-generated method stub
						return localDate;
					}

				}).create();
		ArrayList<Student> studentList = null;
		try (Reader reader = new FileReader(
				FileManagerDao.getFileName("json"))) {
			studentList = gson.fromJson(reader, new TypeToken<List<Student>>() {
			}.getType());
			if (studentList == null) {
				studentList = new ArrayList<Student>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public boolean updateToJsonFile(Student student) throws IOException {
		List<Student> studentList = getAllFromJson();

		Student studentFiltered = studentList
				.stream().filter(studentLambda -> studentLambda
						.getIdStudent() == student.getIdStudent())
				.findFirst().get();

		studentFiltered.setIdStudent(student.getIdStudent());
		studentFiltered.setName(student.getName());
		studentFiltered.setSurname(student.getSurname());
		studentFiltered.setAge(student.getAge());
		studentFiltered.setDateOfBirth(student.getDateOfBirth());

		writeDataToJsonFile(studentList);

		return true;
	}

	@Override
	public boolean removeFromJsonFile(Integer id) throws IOException {
		List<Student> studentJsonList = getAllFromJson();
		Student removedStudent = null;

		removedStudent = studentJsonList.stream()
				.filter(student -> student.getIdStudent().equals(id))
				.findFirst().get();

		if (removedStudent != null) {
			studentJsonList.remove(removedStudent);
			return writeDataToJsonFile(studentJsonList);
		} else {
			return false;
		}
	}

	private boolean writeDataToJsonFile(List<Student> studentJsonList)
			throws IOException {
		try (Writer writer = new FileWriter(FileManagerDao.getFileName("json"),
				false)) {

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(LocalDate.class,
					new LocalDateSerializer());

			Gson gson = gsonBuilder.setPrettyPrinting().create();
			gson.toJson(studentJsonList.toArray(), writer);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}

		return true;
	}

}
