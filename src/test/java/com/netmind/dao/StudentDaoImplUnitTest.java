package com.netmind.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.netmind.common.model.Student;
import com.netmind.dao.contracts.StudentDao;

@RunWith(MockitoJUnitRunner.class)
public class StudentDaoImplUnitTest {

	@Mock
	private StudentDao studentDao;

	// Lista de estudiantes, para pruebas
	public ArrayList<Student> studentList = new ArrayList<Student>();

	Student student;
	Student student1;

	// https://howtodoinjava.com/mockito/junit-mockito-example/
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		student = new Student();
		student.setIdStudent(1);
		student.setName("jordi");
		student.setSurname("ferrer");
		student.setAge(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);

		student1 = new Student();
		student1.setIdStudent(2);
		student1.setName("pepe");
		student1.setSurname("martinez");
		student1.setAge(20);
		DateTimeFormatter formatter1 = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth2 = LocalDate.parse("21-02-1999", formatter1);
		student1.setDateOfBirth(dateOfBirth2);

		studentList.add(student);
		studentList.add(student1);

		when(studentDao.getAllFromJson()).thenReturn(studentList);
	}

	@Test
	public void testGetAllFromJson() throws IOException {
		List<Student> studentList = studentDao.getAllFromJson();

		verify(studentDao, never()).add(student);
		verify(studentDao, never()).add(student1);
		assertTrue("El tamaño de la lista es diferente que 2",
				studentList.size() == 2);
	}
}