package kahuuFotos.seguridad;


import java.io.Serializable;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES implements Serializable
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SecretKeySpec skeySpec;
	
	private SecretKeySpec otroSkeySpec;


	public AES() 
	{

	}
	
	public void generarClaves() throws Exception
	{
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		kgen.init(128);

		SecretKey skey = kgen.generateKey();
		byte[] raw = skey.getEncoded();

		skeySpec = new SecretKeySpec(raw, "AES");	
	}
	
	public void aignarClaveSecreta(byte[] raw)
	{
		skeySpec = new SecretKeySpec(raw, "AES");	
	}
	
	public byte[] darClaveSymetrica()
	{
		return skeySpec.getEncoded();
	}

	public byte[] encypt(byte[] message) throws Exception
	{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(message);
		
		return encrypted;
	}
	
	public byte[] descrypt(byte[] message) throws Exception
	{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		
		byte[] original = cipher.doFinal(message);
		
		return original;
	}
	
	public void aignarClaveSecretaOtro(byte[] raw)
	{
		otroSkeySpec = new SecretKeySpec(raw, "AES");	
	}
	
	public byte[] darClaveSymetricaOtro()
	{
		return otroSkeySpec.getEncoded();
	}

	public byte[] encyptOtro(byte[] message) throws Exception
	{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, otroSkeySpec);
		byte[] encrypted = cipher.doFinal(message);
		
		return encrypted;
	}
	
	public byte[] descryptOtro(byte[] message) throws Exception
	{
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, otroSkeySpec);
		
		byte[] original = cipher.doFinal(message);
		
		return original;
	}

}
