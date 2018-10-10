package com.anglo.controleescolar.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class AngloIO {
	@Autowired
	protected MessageSource messageSource;
	
	protected final String FILES_FOLDER = "/WEB-INF/files";
	protected final String USER_FILES_FOLDER = "/WEB-INF/files/user-files";
	protected final String FILE_NAME = "file";
	protected final String TEMP_FILE_NAME = "file.tmp";
	
	protected byte[] createZipFile(Map<String, byte[]> files) throws IOException {
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
		for (Entry<String, byte[]> item : files.entrySet()) {
			ZipEntry zipEntry = new ZipEntry(item.getKey());
			zipOutputStream.putNextEntry(zipEntry);
			byte[] bytes = item.getValue();
			int length = bytes.length;
			zipOutputStream.write(bytes, 0, length);
			zipOutputStream.closeEntry();
		}
		zipOutputStream.close();
		byte[] result = fileOutputStream.toByteArray();
		fileOutputStream.close();
		return result;
	}
	
	protected byte[] createZipFromSavedFiles(Map<String, String> files) throws IOException {
		ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
		for (Entry<String, String> item : files.entrySet()) {
			ZipEntry zipEntry = new ZipEntry(item.getKey());
			zipOutputStream.putNextEntry(zipEntry);
			byte[] bytes = getFile(new File(item.getValue()));
			int length = bytes.length;
			zipOutputStream.write(bytes, 0, length);
			zipOutputStream.closeEntry();
		}
		zipOutputStream.close();
		byte[] result = fileOutputStream.toByteArray();
		fileOutputStream.close();
		return result;
	}
	
	protected byte[] getFile(File file) throws IOException {
		if (file.exists()) { 
			FileInputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream fileOutputStream = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
            	fileOutputStream.write(buf, 0, readNum);
            }
            fileInputStream.close();
            return fileOutputStream.toByteArray();
		}
		return null;
	}
	
	protected void saveFile(byte[] file, String path, boolean overwrite) throws IOException {
		File targetFile = new File(path);
		File dir = new File(targetFile.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		} else if (targetFile.exists()) {
			if (overwrite) {
				targetFile.delete();
			} else {
				throw new IOException(messageSource.getMessage("exception.the.existing.file.must.be.deleted.or.overwritten", null, null));
			}
		}
		FileOutputStream stream = new FileOutputStream(targetFile);
		stream.write(file);
		stream.flush();
		stream.close();
	}
}
