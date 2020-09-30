package com.assinatura.xades.assinatura;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import xades4j.algorithms.EnvelopedSignatureTransform;
import xades4j.production.DataObjectReference;
import xades4j.production.SignedDataObjects;
import xades4j.production.XadesBesSigningProfile;
import xades4j.production.XadesSigner;
import xades4j.properties.DataObjectDesc;
import xades4j.providers.KeyingDataProvider;
import xades4j.providers.impl.FileSystemKeyStoreKeyingDataProvider;
import xades4j.utils.DOMHelper;

@RestController
@RequestMapping("/sign")
public class AssinaturaXades {
	
    private static final String CERT_FOLDER = "/home/renato/Downloads/desenvolvimento/cpd/git/certificado/certificado_auto_assinado/";
    private static final String CERT        = "mycert.pfx";
    private static final String PASS        = "123456"; 

    private static final String TSA_URL     = "http://XXX.XXX.XXX/ts.inx";
    private static final String TSA_USER    = "XXXXXXXX";
    private static final String TSA_PASS    = "XXXXXXXX";

    private static final String UNSIGNED    = "/home/renato/Downloads/desenvolvimento/cpd/git/certificado/certificados_teste_xades_java/original.xml";
    private static final String SIGNED      = "/home/renato/Downloads/desenvolvimento/cpd/git/certificado/certificados_teste_xades_java/sing_bes_java.xml";
    private static final String SIGNEDT     = "C:/Test/sign-verify/signed-t-bes.xml";    
    private static final String DOCUMENT    = "/home/renato/Downloads/desenvolvimento/cpd/git/certificado/certificados_teste_xades_java/original.xml";

	
	@GetMapping("/xades")
	public void signXades() throws Exception {
	    System.out.println("______________________");
        System.out.println("\tSign");
        System.out.println("______________________");
        signBes();
	}
	
	
	private void signBes() throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(readFile(UNSIGNED))));
		
        Element elem = doc.getDocumentElement();

        System.out.println(elem);

        KeyingDataProvider kdp = new FileSystemKeyStoreKeyingDataProvider(
                "pkcs12",
                CERT_FOLDER + CERT,
                new FirstCertificateSelector(),
                new DirectPasswordProvider(PASS),
                new DirectPasswordProvider(PASS),
                true);
        
        elem.setIdAttribute("ID", true);
        DataObjectDesc obj = new DataObjectReference("#" + elem.getAttribute("ID"))
                .withTransform(new EnvelopedSignatureTransform());
        SignedDataObjects dataObjs = new SignedDataObjects().withSignedDataObject(obj);

        XadesSigner signer = new XadesBesSigningProfile(kdp).newSigner();
        signer.sign(dataObjs, elem);

        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer transformer = tFactory.newTransformer();
        DOMSource source = new DOMSource(doc);        
        StreamResult result = new StreamResult(new File(SIGNED));
        transformer.transform(source, result);
    }
	
	private String readFile(String file) throws IOException {
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
