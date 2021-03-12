package com.netmind.business.contracts;

import java.io.IOException;

import com.netmind.common.model.Student;

public interface StudentBl {
	public boolean addToJsonFile(Student student) throws IOException;

	public boolean add(Student student) throws IOException;
}
