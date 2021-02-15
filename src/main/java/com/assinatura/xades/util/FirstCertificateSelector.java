package com.assinatura.xades.util;

import java.security.cert.X509Certificate;
import java.util.List;

import xades4j.providers.impl.KeyStoreKeyingDataProvider.SigningCertSelector;

public class FirstCertificateSelector implements SigningCertSelector {

	 @Override
	     public X509Certificate selectCertificate(
	             List<X509Certificate> availableCertificates) {
	         return availableCertificates.get(0);
	    }

}


