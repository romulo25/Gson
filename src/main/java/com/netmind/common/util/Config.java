package com.netmind.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.netmind.business.StudentBlImpl;

public class Config {

	static final Logger logger = Logger.getLogger(Config.class);
	static Properties prop = null;
	static InputStream input = null;

	static {
		prop = new Properties();
		try {
			input = StudentBlImpl.class
					.getResourceAsStream("/config.properties");
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	public static String getJsonFileName() {
		return prop.getProperty(Constants.JSON_FILE_NAME);
	}

	public static String getTxtFileName() {
		return prop.getProperty(Constants.TXT_FILE_NAME);
	}

	public static String getTextTxtFileName() {
		return prop.getProperty(Constants.TEXT_TXT_FILE_NAME);
	}

	public static String getTextJsonFileName() {
		return prop.getProperty(Constants.TEXT_JSON_FILE_NAME);
	}
}
