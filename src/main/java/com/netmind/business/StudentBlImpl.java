package com.netmind.business;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import org.apache.log4j.Logger;

import com.netmind.business.contracts.StudentBl;
import com.netmind.common.model.Student;
import com.netmind.common.util.Config;
import com.netmind.dao.FileManagerDao;
import com.netmind.dao.StudentDaoImpl;
import com.netmind.dao.contracts.StudentDao;

public class StudentBlImpl implements StudentBl {

	static final Logger logger = Logger.getLogger(StudentBlImpl.class);

	@Override
	public boolean add(Student student) throws IOException {
		StudentDao studentDao = new StudentDaoImpl();

		student.setAge(calculateAge(student.getDateOfBirth()));

		FileManagerDao fileManagerDaoTxtThread = new FileManagerDao(
				Config.getTxtFileName());
		FileManagerDao fileManagerDaoJsonThread = new FileManagerDao(
				Config.getJsonFileName());

		try {
			fileManagerDaoTxtThread.start();
			fileManagerDaoTxtThread.join();
			fileManagerDaoJsonThread.start();
			fileManagerDaoJsonThread.join();
		} catch (InterruptedException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		// FileManagerDao.createFile(Config.getJsonFileName());
		// FileManagerDao.createFile(Config.getTxtFileName());
		logger.info(Config.getTextTxtFileName());
		logger.info(Config.getJsonFileName());

		return studentDao.addStudentToFile(student);
	}

	private int calculateAge(LocalDate dateOfBirth) {
		Period edad = Period.between(dateOfBirth, LocalDate.now());
		return edad.getYears();
	}

	@Override
	public boolean addToJsonFile(Student student) throws IOException {
		StudentDao studentDao = new StudentDaoImpl();
		// TODO Auto-generated method stub
		return studentDao.addToJsonFile(student);
	}

}
