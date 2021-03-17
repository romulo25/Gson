package com.netmind.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.netmind.common.model.Student;
import com.netmind.common.util.Config;
import com.netmind.dao.contracts.StudentDao;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class StudentDaoImplIntegrationTest {

	static StudentDao studentDao = new StudentDaoImpl();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		FileManagerDao.createFile(Config.getTxtFileName());
		FileManagerDao.createFile(Config.getJsonFileName());
		/*
		 * You can do it multithreading FileManagerDao fileManagerDaoTxtThread =
		 * new FileManagerDao( Config.getTxtFileName()); FileManagerDao
		 * fileManagerDaoJsonThread = new FileManagerDao(
		 * Config.getJsonFileName());
		 * 
		 * try { fileManagerDaoTxtThread.start();
		 * fileManagerDaoTxtThread.join(); fileManagerDaoJsonThread.start();
		 * fileManagerDaoJsonThread.join(); } catch (InterruptedException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		Student student = new Student();
		student.setIdStudent(1);
		student.setName("jordi");
		student.setSurname("ferrer");
		student.setAge(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);

		Student student1 = new Student();
		student1.setIdStudent(2);
		student1.setName("pepe");
		student1.setSurname("martinez");
		student1.setAge(20);
		DateTimeFormatter formatter1 = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth2 = LocalDate.parse("21-02-1999", formatter1);
		student.setDateOfBirth(dateOfBirth2);

		studentDao.addToJsonFile(student);
		studentDao.addToJsonFile(student1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		/*
		 * File txtFile = new File(Config.getTxtFileName()); txtFile.delete();
		 * 
		 * File jsonFile = new File(Config.getJsonFileName());
		 * jsonFile.delete();
		 */
	}

	/*
	 * @Before public void setUp() throws Exception { }
	 * 
	 * @After public void tearDown() throws Exception { }
	 */

	@Test
	@Parameters({ "3, pepe, soto, 21, 26-02-2000",
			"4, Mar, Biel, 21, 26-02-2000",
			"5, Juan, Fernando, 21, 26-02-2000" })
	public void testAddToJsonFile(Integer idStudent, String name,
			String surname, Integer age, String date) throws IOException {

		Student student = new Student();
		student.setIdStudent(idStudent);
		student.setName(name);
		student.setSurname(surname);
		student.setAge(age);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse(date, formatter);
		student.setDateOfBirth(dateOfBirth);

		assertTrue(studentDao.addToJsonFile(student) == true);
	}

	@Test
	public void testGetAllFromJson() throws IOException {
		assertTrue(studentDao.getAllFromJson().size() > 0);
	}

	@Test
	public void testRemoveFromJsonFile() throws IOException {
		assertTrue(studentDao.removeFromJsonFile(2) == true);
	}

	@Test
	public void testUpdateFromJson() throws IOException {
		Student student = new Student();
		student.setIdStudent(1);
		student.setName("ACTUALIZADO");
		student.setSurname("ACTUALIZADO");
		student.setAge(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);
		assertTrue(studentDao.updateToJsonFile(student) == true);
	}

}