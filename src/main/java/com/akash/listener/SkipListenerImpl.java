package com.akash.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.akash.postgresql.entities.Student;

@Component
public class SkipListenerImpl implements SkipListener<Student, com.akash.mysql.entities.Student> {

	@Override
	public void onSkipInRead(Throwable th) {
		if (th instanceof FlatFileParseException) {
			String filePath = "D:\\Spring\\SpringBatch Udemy\\CourceMine\\DataMigrationNewVersion\\Chunk Job\\Chunk Step\\reader\\SkipInRead.txt";
			createFile(filePath, ((FlatFileParseException) th).getInput());
		}
	}

	@Override
	public void onSkipInWrite(com.akash.mysql.entities.Student item, Throwable th) {
		if (th instanceof NullPointerException) {
			String filePath = "D:\\Spring\\SpringBatch Udemy\\CourceMine\\DataMigrationNewVersion\\Chunk Job\\Chunk Step\\writer\\SkipInWriter.txt";
			createFile(filePath, item.toString());
		}
	}

	@Override
	public void onSkipInProcess(Student item, Throwable th) {
		if (th instanceof NullPointerException) {

			String filePath = "D:\\Spring\\SpringBatch Udemy\\CourceMine\\DataMigrationNewVersion\\Chunk Job\\Chunk Step\\processor\\SkipInProcess.txt";
			createFile(filePath, item.toString());
		}
	}

	public void createFile(String filePath, String data) {
		try (FileWriter fileWriter = new FileWriter(new File(filePath), true)) {
			fileWriter.write(data + " ," + new Date() + "\n");
		} catch (Exception e) {
			System.out.println("Exception occured while creating file :" + e);
		}
	}

}
