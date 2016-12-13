package kahuuFotos.seguridad;


import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import kahuuFotos.mundo.KahuuException;

public class RSA2 implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PrivateKey privateKey;
	
	private PublicKey publicKey;
	
	public void generarClaves()
	{	
		try 
		{
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(1024);  
			KeyPair key = keyPairGenerator.generateKeyPair();  
			
			privateKey = key.getPrivate();
			publicKey =  key.getPublic();
		} 
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}  

	}

	public byte[] encripta(byte[] mensaje, Key publicaOtro)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicaOtro);
			
			byte[] cipherData = cipher.doFinal(mensaje);
			//byte[] cipherData = blockCipher(mensaje,Cipher.ENCRYPT_MODE,cipher);
	
			return cipherData;
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

		
		return null;
	}
	
	
	public byte[] desencripta(byte[] mensaje) throws KahuuException
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] cipherData = cipher.doFinal(mensaje);
			//byte[] cipherData = blockCipher(mensaje,Cipher.DECRYPT_MODE,cipher);
			
			return cipherData;
		}
		catch (Exception e)
		{
			throw new KahuuException("Error Enciptando:"+ e.getMessage());
		}
	}
	
	public byte[] desencriptarPersonalizado(byte[] mensaje, Key llave) throws KahuuException
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, llave);
			
			byte[] cipherData = cipher.doFinal(mensaje);
			
			return cipherData;
		}
		catch (Exception e)
		{
			throw new KahuuException("Error Enciptando:"+ e.getMessage());
		}
	
	}
	
	private byte[] blockCipher(byte[] bytes, int mode, Cipher cipher) throws IllegalBlockSizeException, BadPaddingException
	{
		// string initialize 2 buffers.
		// scrambled will hold intermediate results
		byte[] scrambled = new byte[0];

		// toReturn will hold the total result
		byte[] toReturn = new byte[0];
		
		// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
		int length = (mode == Cipher.ENCRYPT_MODE)? 100 : 128;

		// another buffer. this one will hold the bytes that have to be modified in this step
		byte[] buffer = new byte[length];

		for (int i=0; i< bytes.length; i++){

			// if we filled our buffer array we have our block ready for de- or encryption
			if ((i > 0) && (i % length == 0))
			{
				//execute the operation
				scrambled = cipher.doFinal(buffer);
				// add the result to our total result.
				toReturn = append(toReturn,scrambled);
				// here we calculate the length of the next buffer required
				int newlength = length;

				// if newlength would be longer than remaining bytes in the bytes array we shorten it.
				if (i + length > bytes.length) {
					 newlength = bytes.length - i;
				}
				// clean the buffer array
				buffer = new byte[newlength];
			}
			// copy byte into our buffer.
			buffer[i%length] = bytes[i];
		}

		// this step is needed if we had a trailing buffer. should only happen when encrypting.
		// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
		scrambled = cipher.doFinal(buffer);

		// final step before we can return the modified data.
		toReturn = append(toReturn,scrambled);

		return toReturn;
	}
	
	private byte[] append(byte[] prefix, byte[] suffix){
		byte[] toReturn = new byte[prefix.length + suffix.length];
		for (int i=0; i< prefix.length; i++){
			toReturn[i] = prefix[i];
		}
		for (int i=0; i< suffix.length; i++){
			toReturn[i+prefix.length] = suffix[i];
		}
		return toReturn;
	}
	
	public PublicKey darLLavePublica()
	{
		return publicKey;
	}
	
	public PrivateKey darLLavePrivada()
	{
		return privateKey;
	}
	
	public void asignarLLavePublica(PublicKey publica)
	{
		publicKey = publica;
	}
	
	public void asignarLLavePrivada(PrivateKey privada)
	{
		privateKey = privada;
	}
}
