package cei.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

public class Encrypt {
	public static String password(String data) {
		if (data == null) return "";

		byte[] plainText = null;
		byte[] hashValue = null;
		plainText = data.getBytes();

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			hashValue = md.digest(plainText);
		}
		catch(NoSuchAlgorithmException nae) {
			nae.printStackTrace();
		}

		return new String(Base64.encodeBase64(hashValue));
	}
	
	@Test
	public void test() {
		System.out.println(password("test"));
	}
}
