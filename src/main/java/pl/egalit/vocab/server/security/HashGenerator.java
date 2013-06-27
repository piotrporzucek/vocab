package pl.egalit.vocab.server.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
	private static String bytesToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				sb.append('0');
			}
			sb.append(hex);
		}
		return sb.toString();
	}

	public static String getHash(String string) {
		MessageDigest digest = null;
		String hash;
		try {
			digest = MessageDigest.getInstance("SHA-1");
			digest.update(string.getBytes());

			hash = bytesToHexString(digest.digest());

		} catch (NoSuchAlgorithmException e1) {
			hash = null;
		}
		return hash;
	}

}
