package com.akash.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.akash.postgresql.entities.SubjectsLearning;

@Component("SkipListenerImpl1")
public class SkipListenerImpl1 implements SkipListener<SubjectsLearning, com.akash.mysql.entities.SubjectsLearning> {

	@Override
	public void onSkipInRead(Throwable th) {
		if (th instanceof FlatFileParseException) {
			String filePath = "D:\\Spring\\SpringBatch Udemy\\CourceMine\\DataMigrationNewVersion\\Chunk Job\\Chunk Step\\reader\\SkipInReadSubj.txt";
			createFile(filePath, ((FlatFileParseException) th).getInput());
		}
	}

	@Override
	public void onSkipInWrite(com.akash.mysql.entities.SubjectsLearning item, Throwable th) {
		if (th instanceof NullPointerException) {
			String filePath = "D:\\Spring\\SpringBatch Udemy\\CourceMine\\DataMigrationNewVersion\\Chunk Job\\Chunk Step\\writer\\SkipInWriterSubj.txt";
			createFile(filePath, item.toString());
		}
	}

	@Override
	public void onSkipInProcess(SubjectsLearning item, Throwable th) {
		if (th instanceof NullPointerException) {

			String filePath = "D:\\Spring\\SpringBatch Udemy\\CourceMine\\DataMigrationNewVersion\\Chunk Job\\Chunk Step\\processor\\SkipInProcessSubj.txt";
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
