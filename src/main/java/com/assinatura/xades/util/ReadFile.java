package com.assinatura.xades.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class ReadFile {
	
	public String readFile(String file) throws IOException {
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("\n");

	    try {
	        while((line = reader.readLine()) != null) {
	            stringBuilder.append(line);
	            //stringBuilder.append(ls);
	        }

	        return stringBuilder.toString();
	    } finally {
	        reader.close();
	    }
	}

}
