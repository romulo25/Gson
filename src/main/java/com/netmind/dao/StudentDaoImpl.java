package com.netmind.dao;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

		try (Writer writer = new FileWriter(
				FileManagerDao.getFileName("json"))) {

			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(LocalDate.class,
					new LocalDateSerializer());

			Gson gson = gsonBuilder.setPrettyPrinting().create();
			gson.toJson(studentList.toArray(), writer);
		} catch (IOException e) {
			logger.error(e.getMessage() + student.toString());
			throw e;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Student> getAllFromJson() {
		// TODO Auto-generated method stub
		ArrayList<Student> studentList = null;
		try (Reader reader = new FileReader(
				FileManagerDao.getFileName("json"))) {
			studentList = new Gson().fromJson(reader, ArrayList.class);
			if (studentList == null) {
				studentList = new ArrayList<Student>();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public boolean updateJsonFile(Student student) throws IOException {
		return false;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean removeJsonFile(Student student) throws IOException {
		return false;
		// TODO Auto-generated method stub

	}

}
