package kahuuFotos.seguridad;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import javax.security.auth.x500.X500Principal;

import kahuuFotos.mundo.KahuuException;

import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V3CertificateGenerator;

public class ManejadorEncriptar implements Serializable
{

	//------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------
	
	private static final long serialVersionUID = 1L;

	private static final String IP = "localhost";
	
	private static final int PUERTO = 9999;	
	
	//------------------------------------------------------------------------------------------------------------
	// Atributos
	//------------------------------------------------------------------------------------------------------------
	
	private RSA2 rsa;

	private AES aes;

	/**
	 * La llave publica de otro nodo
	 */
	private PublicKey llavePublicaOtro;
	
	/**
	 * La llave publica del ente certificadora
	 */
	private PublicKey llavePublicaEC;

	private X509Certificate certificado;

	//------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------
	
	public ManejadorEncriptar( ) 
	{	
			rsa = new RSA2( );			
			aes = new AES();
			
			certificado = null;
	}

	//------------------------------------------------------------------------------------------------------------
	// Claves
	//------------------------------------------------------------------------------------------------------------

	public void generarClaves(String nombreCliente) throws KahuuException
	{
		try
		{
			rsa.generarClaves();
			aes.generarClaves();
			//hmac.generarClaves();

			crearCertificadoDigital(nombreCliente);
			
			//certificado = generateV3Certificate(nombreCliente);
		}
		catch (Exception e)
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}	

	}
	
	public void generarNuevaClaveSymetica() throws KahuuException
	{
		try 
		{
			aes.generarClaves();
		}
		catch (Exception e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}
	
//	public void generarNuevaClaveHMAC() throws KahuuException 
//	{
//		try 
//		{
//			hmac.generarClaves();
//		}
//		catch (Exception e) 
//		{
//			throw new KahuuException("Error:"+ e.getMessage());
//		}
//	}

	public void asignarLLavePublicaOtro(byte[] publica) throws KahuuException
	{
		try 
		{
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publica));
			llavePublicaOtro = publicKey;
		} 
		catch (InvalidKeySpecException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());		
		} 
		catch (NoSuchAlgorithmException e)
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}

	}

	public void asignarLLavePublicaPropia(byte[] publica) throws KahuuException
	{
		try 
		{
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publica));
			rsa.asignarLLavePublica(publicKey);
		} 
		catch (InvalidKeySpecException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());		
		} 
		catch (NoSuchAlgorithmException e)
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}
	
	public void asignarLLavePrivadaPropia(byte[] privada) throws KahuuException
	{
		try 
		{
			PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new X509EncodedKeySpec(privada));
			rsa.asignarLLavePrivada(privateKey);
		} 
		catch (InvalidKeySpecException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());		
		} 
		catch (NoSuchAlgorithmException e)
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}

	public byte[] darLLavePublica() 
	{
		return rsa.darLLavePublica().getEncoded();
	}

	public byte[] darLLavePrivada() 
	{
		return rsa.darLLavePrivada().getEncoded();
	}
	
	public byte[] darLLavePublicaEC() 
	{
		return llavePublicaEC.getEncoded();
	}
	
	//------------------------------------------------------------------------------------------------------------
	// Asimetrico
	//------------------------------------------------------------------------------------------------------------

	public byte[] enciptarMensajeAsymetrico(byte[] mensaje)
	{
		byte[] textoCifrado = rsa.encripta(mensaje, this.llavePublicaOtro);
		return textoCifrado;
	}

	public byte[] desencrytarMensajeAsymetrico(byte[] textoCodificado) throws KahuuException
	{
		byte[] recuperarTextoPlano = rsa.desencripta(textoCodificado);
		return recuperarTextoPlano;
	}
	
	public byte[] enciptarMensajeAsymetricoFirmado(byte[] mensaje)
	{
		byte[] textoCifrado = rsa.encripta(mensaje, rsa.darLLavePrivada());
		return textoCifrado;
	}
	
	public byte[] desencryptarMensajeFirmado(byte[] mensaje) throws KahuuException
	{
		byte[] recuperarTextoPlano = rsa.desencriptarPersonalizado(mensaje,llavePublicaOtro);
		return recuperarTextoPlano;
	}

	public byte[] mensajeFirmaDigital(byte[] mensaje) throws KahuuException
	{	
		try
		{
			Signature instance = Signature.getInstance("SHA1withRSA");
			instance.initSign(rsa.darLLavePrivada());
			instance.update(mensaje);
			byte[] signature = instance.sign();
			
			return signature;
		}
		catch (Exception e)
		{
			throw new KahuuException(e.getMessage());
		}
	}
	
	public boolean verificarFirmaDigital(byte[] mensaje, byte[] firma) throws KahuuException
	{
		try
		{
			Signature sig = Signature.getInstance("SHA1withRSA");
			sig.initVerify(llavePublicaOtro);
			sig.update(mensaje);
			
			return sig.verify(firma);
		}
		catch (Exception e)
		{
			throw new KahuuException(e.getMessage());
		}
	}


	//------------------------------------------------------------------------------------------------------------
	// Simetrico
	//------------------------------------------------------------------------------------------------------------

	public byte[] encriptarMensajeSymetrico(byte[] mensaje) throws KahuuException
	{
		try
		{
			return aes.encypt(mensaje);
		} 
		catch (Exception e) 
		{
			throw new KahuuException("Error Enciptando:"+ e.getMessage());
		}
	}

	public byte[] desencriptarMensajeSysmetrico( byte[] mensaje) throws KahuuException
	{
		try
		{
			return aes.descrypt(mensaje);
		} 
		catch (Exception e) 
		{

			throw new KahuuException("Error Desenciptando:"+ e.getMessage());
		}
	}
	

	public byte[] encriptarMensajeSymetricoOtro(byte[] mensaje) throws KahuuException
	{
		try
		{
			return aes.encyptOtro(mensaje);
		} 
		catch (Exception e) 
		{
			throw new KahuuException("Error Enciptando:"+ e.getMessage());
		}
	}

	public byte[] desencriptarMensajeSysmetricoOtro( byte[] mensaje) throws KahuuException
	{
		try
		{
			return aes.descryptOtro(mensaje);
		} 
		catch (Exception e) 
		{
			//e.printStackTrace();
			throw new KahuuException("Error Desenciptando:"+ e.getMessage());
		}
	}

	public void asignarClaveSymetrica(byte[] raw)
	{
		aes.aignarClaveSecreta(raw);
	}

	public byte[] darClaveSymetrica()
	{
		return aes.darClaveSymetrica();
	}
	
	public void asignarClaveSymetricaOtro(byte[] raw)
	{
		aes.aignarClaveSecretaOtro(raw);
	}

	public byte[] darClaveSymetricaOtro()
	{
		return aes.darClaveSymetricaOtro();
	}

	//------------------------------------------------------------------------------------------------------------
	// DIGEST MENSAJE
	//------------------------------------------------------------------------------------------------------------

	public byte[] crearDigest(byte[] mensaje) throws KahuuException
	{
		try 
		{
			MessageDigest md = MessageDigest.getInstance("SHA1");

			// Update the message digest with some more bytes
			// This can be performed multiple times before creating the hash
			md.update(mensaje);

			// Create the digest from the message
			byte[] aMessageDigest = md.digest();

			return aMessageDigest;
		}
		catch (NoSuchAlgorithmException e) 
		{
			throw new KahuuException("Error digest:"+ e.getMessage());
		}
	}

	public boolean compararDigestYMensaje(byte[] digest, byte[] mensaje) throws KahuuException
	{
		try 
		{
			return new String(crearDigest(mensaje)).equals(new String(digest));
		}
		catch (Exception e) 
		{
			throw new KahuuException("Error digest:"+ e.getMessage());
		}
	}

	//------------------------------------------------------------------------------------------------------------
	// Ceritficadps
	//------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("deprecation")
	private X509Certificate generateV3Certificate(String emailDelCertificador) throws  KahuuException 
	{
		try 
		{
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();

			certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
			certGen.setIssuerDN(new X500Principal("CN=Certificado Kahuu Foto"));
			certGen.setNotBefore(new Date(System.currentTimeMillis() - 10000));
			certGen.setNotAfter(new Date(System.currentTimeMillis() + 10000));
			certGen.setSubjectDN(new X500Principal("CN=Test Certificate"));

			certGen.setPublicKey(rsa.darLLavePublica());			
			certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

			certGen.addExtension(X509Extensions.BasicConstraints, true, new BasicConstraints(false));
			certGen.addExtension(X509Extensions.KeyUsage, true, new KeyUsage(KeyUsage.digitalSignature| KeyUsage.keyEncipherment));
			certGen.addExtension(X509Extensions.ExtendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
			certGen.addExtension(X509Extensions.SubjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.rfc822Name, emailDelCertificador)));

			
			llavePublicaEC = rsa.darLLavePublica();
			return certGen.generateX509Certificate(rsa.darLLavePrivada());
		} 
		catch (InvalidKeyException e)
		{
			throw new KahuuException("Error:"+ e.getMessage());
		} 
		catch (SecurityException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		} 
		catch (SignatureException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}

	public X509Certificate darCertificadoPlano()
	{
		return certificado;
	}

	public byte[] darCertificado() throws KahuuException
	{
		try 
		{
			return certificado.getEncoded();
		} 
		catch (CertificateEncodingException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}

	public void asignarCertificadoPropio(byte[] bytesCertificado) throws KahuuException
	{
		try 
		{
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(bytesCertificado);
			X509Certificate certEncoded = (X509Certificate)certFactory.generateCertificate(in);

			certificado = certEncoded;		
		} 
		catch (CertificateException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}

	public void asignarLLaverPorCertificadoPlano(X509Certificate certificado) 
	{	
		PublicKey publica = certificado.getPublicKey();	
		llavePublicaOtro = publica;
	}
	
	public void asignarLLavePorCertificadoEC(byte[] bytesCertificado) throws KahuuException
	{
		try 
		{
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(bytesCertificado);
			X509Certificate certEncoded = (X509Certificate)certFactory.generateCertificate(in);

			PublicKey publica = certEncoded.getPublicKey();	
			llavePublicaEC = publica;
		} 
		catch (CertificateException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}
	

	public void asignarLLaverPorCertificadoPlano(byte[] bytesCertificado) throws KahuuException 
	{		
		try 
		{
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(bytesCertificado);
			X509Certificate certEncoded = (X509Certificate)certFactory.generateCertificate(in);

			PublicKey publica = certEncoded.getPublicKey();	
			llavePublicaOtro = publica;
		} 
		catch (CertificateException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}

	}
	
	public void crearCertificadoDigital(String name) throws KahuuException
	{
		try 
		{
			Socket socket = new Socket(IP,PUERTO);
			
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
					
			//Envio de l nombre
			System.out.println("ENVIO NOMBRE:" + asHex(name.getBytes()));
			out.write(name.getBytes());
			out.flush();
		
			Thread.sleep(1000);
			
			//Envio de llave publica
			System.out.println("ENVIO LLAVE PUBLICA:" + asHex(rsa.darLLavePublica().getEncoded()));
			out.write(rsa.darLLavePublica().getEncoded());
			out.flush();
			
			byte[] mensaje = leerMensaje(in);
			System.out.println("CERTIFICADO PROPIO:" + asHex(mensaje));
			asignarCertificadoPropio(mensaje);
			
			byte[] certificadoEC = leerMensaje(in);
			System.out.println("CERTIFICADO EC:" + asHex(certificadoEC));
			asignarLLavePorCertificadoEC(certificadoEC);
			
			
			out.close();
			in.close();
			socket.close();
		} 
		catch (UnknownHostException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		} 
		catch (IOException e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public boolean verificarFirmaCertificadoDigital(byte[] encodedCertificado) 
	{		
		try
		{
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(encodedCertificado);
			X509Certificate certEncoded = (X509Certificate)certFactory.generateCertificate(in);
			
			certEncoded.verify(llavePublicaEC);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	
	}

	//------------------------------------------------------------------------------------------------------------
	// HMAC Autenticacion
	//------------------------------------------------------------------------------------------------------------

//	public byte[] encriptar(byte[] mensaje) throws KahuuException
//	{
//		try
//		{
//			byte[] mensajeRec= hmac.digestMessage(mensaje);
//			return mensajeRec;
//		} 
//		catch (Exception e) 
//		{			
//			throw new KahuuException("Error digest:"+ e.getMessage());
//		}
//	}
//
//	public byte[] darLlaveHMAC()
//	{
//		return hmac.darLLave();
//	}
//
//	public void asignarLlaveHMAC(byte[] llave) throws KahuuException
//	{
//		try 
//		{
//			hmac.assignarLLave(llave);
//		} 
//		catch (Exception e) 
//		{
//			throw new KahuuException("Error digest:"+ e.getMessage());
//		}
//	}
	
	//------------------------------------------------------------------------------------------------------------
	// Otros
	//------------------------------------------------------------------------------------------------------------

	/**
	 * Turns array of bytes into string
	 *
	 * @param buf	Array of bytes to convert to hex string
	 * @return	Generated hex string
	 */
	public static String asHex (byte buf[]) 
	{
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
	
	private byte[] leerMensaje(InputStream in) throws KahuuException
	{
		byte[] buffer = new byte[2000];
		int datos=-1;
		try 
		{
			datos=in.read(buffer);

			byte[] buffer2= new byte[datos];
			
			for (int i = 0; i < datos; i++) 
			{
				buffer2[i]=buffer[i];
			}
			
			return buffer2;			
		} 
		catch (Exception e) 
		{
			throw new KahuuException("Error:"+ e.getMessage());
		}	
	}
	

	
	//	public BigInteger darLLavePublicaInteger()
	//	{
	//		return rsa.damee();
	//	}
	//
	//	public BigInteger darLLaveCompatidaInteger()
	//	{
	//		return rsa.damen();
	//	}

	//	public PrivateKey darLLavePrivada() throws KahuuException
	//	{
	//		try 
	//		{
	//			KeyFactory kf = KeyFactory.getInstance("RSA");
	//			RSAPrivateKeySpec prvSpec = new RSAPrivateKeySpec(rsa.damen(), rsa.damed());
	//			RSAPrivateKey prvKey = (RSAPrivateKey) kf.generatePrivate(prvSpec);
	//
	//			return prvKey;
	//		} 
	//		catch (Exception e) 
	//		{
	//			throw new KahuuException("Error:"+ e.getMessage());
	//		}
	//	}

}
