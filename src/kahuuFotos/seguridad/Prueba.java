package kahuuFotos.seguridad;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;

import javax.swing.JFrame;

import kahuuFotos.mundo.KahuuException;

public class Prueba extends JFrame
{

	public Prueba() throws KahuuException, IOException, CertificateEncodingException
	{

//		File file = null; 
//		JFileChooser fc = new JFileChooser();
//
//		int returnVal = fc.showOpenDialog(this);
//
//		if (returnVal == JFileChooser.APPROVE_OPTION)
//		{
//			file = fc.getSelectedFile();
//		} 
//
//
//		FileInputStream in=new FileInputStream(file);
//		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//
//		int nRead;
//		byte[] data = new byte[1024];
//
//		while ((nRead = in.read(data, 0, data.length)) != -1)
//		{
//			buffer.write(data, 0, nRead);
//		}
//
//		buffer.flush();
//		in.close();


		ManejadorEncriptar peer1 = new ManejadorEncriptar();
		ManejadorEncriptar peer2 = new ManejadorEncriptar();

		peer1.generarClaves("Peer1");
		peer2.generarClaves("Peer2");
		

			
		System.out.println(peer1.verificarFirmaCertificadoDigital(peer1.darCertificadoPlano().getEncoded()));

		
		//peer2.generarClaves("Peer2");

		byte[] mensaje;;
		byte[] mensajeDEs = new byte[128];

		
		//------------------------------------------------------------------------------------------------
		// mandar Una foto
		//------------------------------------------------------------------------------------------------
		
		//PEER ENVIO
		
		//Lo que se va a mandar
		//byte[] prueba = buffer.toByteArray();
		byte[] prueba = "xfjklbvdfghsdrukghsdilfhgsdifugysdfghsdkulghsdkgfsdyjhsdlg".getBytes();
		
//		//Se firma
//		byte[] firma = peer1.mensajeFirmaDigital(prueba);	
//		System.out.println(ManejadorEncriptar.asHex(firma));
		
		//Se encipta simetricamente
		//byte[] encriptadoSimetrico = peer1.encriptarMensajeSymetrico(prueba);
	
		
		//PEER RECEPTOR
//		byte[] mensajeRecidido = peer2.desencriptarMensajeSysmetrico(encriptadoSimetrico);
//		System.out.println(peer2.verificarFirmaDigital(mensajeRecidido, firma));
		
		//------------------------------------------------------------------------------------------------
		// Ecriptar Foto para guardar
		//------------------------------------------------------------------------------------------------

//		byte[] clavesimetrica =peer1.darClaveSymetrica();
//		System.out.println(ManejadorEncriptar.asHex(clavesimetrica));
//		System.out.println(clavesimetrica.length);
//		
//		mensaje = peer1.enciptarMensajeAsymetrico(peer1.darClaveSymetrica());	
//		System.out.println(ManejadorEncriptar.asHex(mensaje));
//		System.out.println(mensaje.length);
//		
//		mensajeDEs = peer2.desencrytarMensajeAsymetrico(mensaje);	
//		System.out.println(mensajeDEs.length);
//		System.out.println(ManejadorEncriptar.asHex(mensajeDEs));
//		
//		peer2.asignarClaveSymetricaOtro(mensajeDEs);
//		
//		byte[] encriptadoSYM = peer1.encriptarMensajeSymetrico(prueba);
//		
//		byte[] dec = peer2.desencriptarMensajeSysmetricoOtro(encriptadoSYM);
//		System.out.println(new String(dec));

//		mensaje = peer1.enciptarMensajeAsymetrico("MENSAJE ASYMETRICO 2".getBytes());	
//		System.out.println(ManejadorEncriptar.asHex(mensaje));
//
//		mensajeDEs = peer2.desencrytarMensajeAsymetrico(mensaje);	
//		System.out.println(new String(mensajeDEs));
////
//		peer2.asignarClaveSymetrica(peer1.darClaveSymetrica());
////
//		mensaje = peer1.encriptarMensajeSymetrico("MENSAJE ASYMETRICO 3".getBytes());	
//		System.out.println(ManejadorEncriptar.asHex(mensaje));
////
//		mensajeDEs = peer2.desencriptarMensajeSysmetrico(mensaje);
//		System.out.println(new String(mensajeDEs));
//
//		System.out.println("SEGUNDO");
//
//		mensaje = peer1.encriptarMensajeSymetrico("MENSAJE ASYMETRICO 4".getBytes());	
//		System.out.println(ManejadorEncriptar.asHex(mensaje));
//
//		mensajeDEs = peer2.desencriptarMensajeSysmetrico(mensaje);
//		System.out.println(new String(mensajeDEs));
//
//		String mensajeCOmun =  "MENSAJE ASYMETRICO 5";
//
//		byte[] digest = peer1.crearDigest(mensajeCOmun.getBytes());	
//		System.out.println(ManejadorEncriptar.asHex(digest));
//
//		System.out.println(peer2.compararDigestYMensaje(digest, mensajeCOmun.getBytes()));
		
		
		System.out.println("");

	}


	/**
	 * @param args
	 * @throws Exception 
	 * @throws SignatureException 
	 * @throws NoSuchProviderException 
	 * @throws InvalidKeyException 
	 */
	public static void main(String[] args) throws InvalidKeyException, NoSuchProviderException, SignatureException, Exception
	{

		Prueba p = new Prueba();
		p.setVisible(true);
	}

}
