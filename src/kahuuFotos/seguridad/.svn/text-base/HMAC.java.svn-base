package kahuuFotos.seguridad;


import java.io.Serializable;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMAC implements Serializable
{

	private Mac mac;

	private SecretKey key;

	public HMAC() 
	{

	}

	public void generarClaves() throws Exception
	{
		// Generate a key for the HMAC-MD5 keyed-hashing algorithm; see RFC 2104
		// In practice, you would save this key.
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacMD5");
		key = keyGen.generateKey();


		// Create a MAC object using HMAC-MD5 and initialize with key
		mac = Mac.getInstance(key.getAlgorithm());
		mac.init(key);
	}

	public byte[] darLLave()
	{
		return key.getEncoded();
	}

	public void assignarLLave(byte[ ] llave) throws Exception
	{
		key = new SecretKeySpec(llave, "HmacMD5");

		mac = Mac.getInstance(key.getAlgorithm());
		mac.init(key);
	}
	
	public byte[] digestMessage(byte[] mensaje) throws Exception
	{
		byte[] utf8 = mensaje;
		byte[] digest = mac.doFinal(utf8);

		// If desired, convert the digest into a string
		//String digestB64 = new sun.misc.BASE64Encoder().encode(digest);
		return digest;
	}


}
