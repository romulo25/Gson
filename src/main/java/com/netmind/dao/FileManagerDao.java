package com.netmind.dao;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class FileManagerDao extends Thread {
	private String fileName;

	private static File file = null;

	// public static Map<String, File> fileType = new HashMap<String, File>();
	/*
	 * https://howtodoinjava.com/java/collections/hashmap/synchronize-hashmap/
	 * #:~:text=Java%20HashMap%20is%20not%20synchronized,
	 * hashmap%20and%20ConcurrentHashMap%20in%20Java
	 */
	private static ConcurrentHashMap<String, File> fileType = new ConcurrentHashMap<String, File>();

	public FileManagerDao() {

	}

	public FileManagerDao(String filename) {
		this.fileName = filename;
	}

	public static synchronized boolean createFile(String fileName)
			throws IOException {
		boolean isFileCreated = false;
		int twoDataFiles = 2;

		file = new File(fileName);

		try {
			if (file.createNewFile()) {
				System.out.println("File is created!");
				if (fileName.contains(".txt")) {
					fileType.put("txt", file);
				} else if (fileName.contains(".json")) {
					fileType.put("json", file);
				}

			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (fileType.size() < twoDataFiles) {
			if (fileName.contains(".txt")) {
				fileType.put("txt", file);
			} else if (fileName.contains(".json")) {
				fileType.put("json", file);
			}
		}

		return isFileCreated;
	}

	public static String getFileName(String type) {
		return fileType.get(type).getName();
	}

	@Override
	public void run() {
		int twoDataFiles = 2;

		try {
			Thread.sleep(5000);
			file = new File(fileName);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					throw e;
				}
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}

		if (fileType.size() < twoDataFiles) {
			if (fileName.contains(".txt")) {
				fileType.put("txt", file);
			} else if (fileName.contains(".json")) {
				fileType.put("json", file);
			}
		}
	}

}
