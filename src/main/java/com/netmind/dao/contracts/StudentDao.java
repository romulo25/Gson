package com.netmind.dao.contracts;

import java.io.IOException;
import java.util.List;

import com.netmind.common.model.Student;

public interface StudentDao {
	boolean add(Student student) throws IOException;

	boolean addToJsonFile(Student student) throws IOException;

	boolean updateToJsonFile(Student student) throws IOException;

	boolean addStudentToFile(Student student) throws IOException;

	List<Student> getAllFromJson();

	boolean removeFromJsonFile(Integer id) throws IOException;
}
