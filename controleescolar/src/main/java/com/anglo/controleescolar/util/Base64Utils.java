package com.anglo.controleescolar.util;

import java.nio.charset.Charset;

public abstract class Base64Utils {
	public static String getBase64DecodedString(String encodedText) {
		byte[] decoded = org.springframework.util.Base64Utils.decodeFromString(encodedText);
		String result = new String(decoded, Charset.defaultCharset());
		return result;
	}
	
	public static String getBase64EncodedString(String text) {
		String result = org.springframework.util.Base64Utils.encodeToString(text.getBytes(Charset.defaultCharset()));
		return result;
	}
}
