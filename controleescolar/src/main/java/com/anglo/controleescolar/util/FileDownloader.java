package com.anglo.controleescolar.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

public abstract class FileDownloader extends AngloIO {
	protected void downloadFile(String filePath, String fileName, HttpServletResponse response, String applicationPath) throws IOException {
		byte[] file = getFile(new File(filePath));
		String mimeType= URLConnection.guessContentTypeFromName(fileName);
		if(mimeType == null){
			mimeType = "application/octet-stream";
		}
        response.setContentType(mimeType);
        String encodedFileName = URLEncoder.encode(fileName, "UTF-8")
			.replaceAll("\\+", "%20")
	        .replaceAll("\\%21", "!")
	        .replaceAll("\\%27", "'")
	        .replaceAll("\\%28", "(")
	        .replaceAll("\\%29", ")")
	        .replaceAll("\\%7E", "~");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        response.setContentLength(file.length);
		InputStream inputStream = new ByteArrayInputStream(file);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
	
	protected void downloadZip(Map<String, String> files, String fileName, HttpServletResponse response, String applicationPath) throws IOException {
		byte[] file = createZipFromSavedFiles(files);
		String zipFileName = String.format("%s.zip", fileName);
		String mimeType= URLConnection.guessContentTypeFromName(zipFileName);
		if(mimeType == null){
			mimeType = "application/octet-stream";
		}
        response.setContentType(mimeType);
        String encodedFileName = URLEncoder.encode(zipFileName, "UTF-8")
			.replaceAll("\\+", "%20")
	        .replaceAll("\\%21", "!")
	        .replaceAll("\\%27", "'")
	        .replaceAll("\\%28", "(")
	        .replaceAll("\\%29", ")")
	        .replaceAll("\\%7E", "~");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFileName);
        response.setContentLength(file.length);
		InputStream inputStream = new ByteArrayInputStream(file);
        FileCopyUtils.copy(inputStream, response.getOutputStream());
	}
}
