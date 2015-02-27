package rest.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
	public static String md5(String str) {
		
	        MessageDigest m = null;
			try {
				m = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        m.update(str.getBytes(),0,str.length());
	        //System.out.println("MD5: "+new BigInteger(1,m.digest()).toString(16));
	        return new BigInteger(1,m.digest()).toString(16);
	}
	
}
