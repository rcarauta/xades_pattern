package com.assinatura.xades.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assinatura.xades.service.AssinaturaXadesService;
import com.assinatura.xades.service.ValidateCertificatePatternService;
import com.assinatura.xades.util.UnzipFile;

@RestController
@RequestMapping("/sign")
public class AssinaturaXadesController {
	
	@Autowired
	private UnzipFile unzipFile;
	@Autowired
	private AssinaturaXadesService assinarutaXadesService;
	@Autowired
	private ValidateCertificatePatternService validateCertificatePatternService;
	

	@PostMapping("/xades")
	public String signXades(@RequestPart("unzipfile") MultipartFile file, 
					@RequestPart("senha")String password, @RequestPart("certificado")String certificado,
					@RequestPart("idPessoa") String idPessoa) throws Exception {
	    System.out.println("______________________");
        System.out.println("\tSign");
        System.out.println("______________________");
        System.out.println("File = "+file);
        String tempFile = unzipFile.unzip(file);
        System.out.println("tempFile = "+tempFile);
        String pathFiles = unzipFile.extractAllFiles(tempFile);
        System.out.println("pathFiles = "+pathFiles);
        File folder = new File(pathFiles);
        System.out.println("Folder ===== "+folder);
        File[] listFiles = folder.listFiles();
        System.out.println("listFiles ===== "+listFiles.length);
        File folderPerm = new File(listFiles[0].getAbsolutePath());
        //System.out.println("folderPerm ===== "+folderPerm);
       // File[] listOfFiles = folderPerm.listFiles();
        //System.out.println("listOfFiles ===== "+listOfFiles.length);
        for(int i = 0; i < listFiles.length; i++){
        	System.out.println("for =====");
            String filename = listFiles[i].getName();
            System.out.println("filenamefor =====");
            if(filename.endsWith(".xml")||filename.endsWith(".XML")) {
            	System.out.println("sigBesAntes =====");
            	assinarutaXadesService.sign(pathFiles+""+filename, i, password, certificado, Integer.parseInt(idPessoa));
            }
        }
        
        return "Retornou!!";
	}
	
	
	@GetMapping("/validate/id")
	public String validateDiploma(@RequestParam("idDiploma")Integer idDiploma) {
		this.validateCertificatePatternService.validate(null);
		return "validate";
	}
	
	@GetMapping("/validate/xml")
	public String validateDiploma(@RequestPart("diploma") MultipartFile diplomaXML) {
		this.validateCertificatePatternService.validate(diplomaXML);
		return "validate";
	}
	
	
	
}
