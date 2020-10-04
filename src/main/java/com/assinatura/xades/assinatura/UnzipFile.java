package com.assinatura.xades.assinatura;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

@Component
public class UnzipFile {

	@Autowired
	private ServletContext servletContext;


	public String unzip(MultipartFile file) throws IOException {
		String path = servletContext.getRealPath("")+"lote_documento.zip";
		File tempFile = new File(path);
		tempFile.createNewFile();
	    file.transferTo(tempFile);
	    return path;
	}


	public String extractAllFiles(String tempFile) {
		try {
	         ZipFile zipFile = new ZipFile(tempFile);
	         String pathFiles = tempFile.replaceAll("lote_documento.zip","");
	         zipFile.extractAll(pathFiles);
	         return pathFiles;
	    } catch (ZipException e) {
	       throw new RuntimeException();
	    }
		
	}



}
