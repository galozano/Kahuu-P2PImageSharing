/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kahuuFotos.threads2;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import kahuuFotos.interfaz.InterfazKahuuImagenes;
import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.Descarga;
import kahuuFotos.mundo.ImagenTem;
import kahuuFotos.mundo.Mensaje;
import kahuuFotos.seguridad.ManejadorEncriptar;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaSocket;

/**
 *
 * @author Kevin
 */
public class ThreadClienteDescargas extends Thread
{
	public final static String SOCKETIDSTR = "urn:jxta:uuid-59616261646162614E5047205032503393B5C2F6CA7A41FBB0F890173088E79404";

	private ImagenTem nRemotoImagen;

	private InterfazKahuuImagenes interfazPrincipal;
	private AdministradorJXTA principal;

	private final PeerGroup netPeerGroup;
	
	private OutputStream out;
	private InputStream in;
	private ManejadorEncriptar manager;
	
	public ThreadClienteDescargas(ImagenTem dato,AdministradorJXTA ejempl,PeerGroup nPeerGroup,InterfazKahuuImagenes interfaz)
	{
		nRemotoImagen=dato;
		principal=ejempl;
		netPeerGroup=nPeerGroup;
		interfazPrincipal=interfaz;

	}

	public void run(){

		System.out.println("Connecting to the server");
		try {
			JxtaSocket socket = new JxtaSocket(netPeerGroup, nRemotoImagen.getPiveAdv());

			//System.out.println( nRemotoImagen.getPiveAdv().getID());;
			//System.out.println(nRemotoImagen.getPiveAdv().getName());
			//System.out.println(nRemotoImagen.getPiveAdv().getPipeID());


			Mensaje m= new Mensaje();
			m.setMensaje(nRemotoImagen.getNombreFoto());
			m.setTipoMensaje(Mensaje.MENSAJE_ENVIO_SOLICITUD_FOTO);
			m.setPeerSalida(principal.getNombrePeer());
			m.setPeerLlegada(nRemotoImagen.getPeerAgrego());

			principal.agregarMensajeEnviado(m);
			principal.actualizarPanelEstados();
			// get the socket output stream
			out = socket.getOutputStream();
			//DataOutput dos = new DataOutputStream(out);

			// get the socket input stream
			in = socket.getInputStream();
			//DataInput dis = new DataInputStream(in);
			
			
			//--------------------------------------------------------------------------
			// SEGURIDAD: SE ENVIA CERTIFICADO DIGITAL DEL PRESENTE USUARIO
			//--------------------------------------------------------------------------
			manager=principal.darManejadorEncriptacion();
			enviarSinEncriptar(manager.darCertificado());
			//--------------------------------------------------------------------------
			// SEGURIDAD: SE RECIBE CERTIFICADO DIGITAL DEL USUARIO QUE TIENE LA IMAGEN
			//--------------------------------------------------------------------------
			byte[] buffer=new byte[10000];
			int datos=leerMensajeSinEncriptar(buffer);
			manager.asignarLLaverPorCertificadoPlano(buffer);
			
			//--------------------------------------------------------------------------
			// SEGURIDAD: SE RECIBE LLAVE SIMETRICA DEL USUARIO QUE TIENE LA IMAGEN
			//--------------------------------------------------------------------------
			
			//Tiene que ser de 128 por el restriccion de el metodo asimetrico
			buffer=new byte[128];
			datos=leerMensajeSinEncriptar(buffer);
			
			System.out.println("BUFFER RECIBIDO:" + ManejadorEncriptar.asHex(buffer));	
			byte[] llaveSimetrica = manager.desencrytarMensajeAsymetrico(buffer);
			System.out.println("LLAVE SIMETRICA:"+ManejadorEncriptar.asHex(llaveSimetrica));
			
			manager.asignarClaveSymetricaOtro(llaveSimetrica);

			enviarMensaje((AdministradorJXTA.DESCARGAR_FOTO+principal.getNombrePeer()).getBytes());
			enviarMensaje(nRemotoImagen.getNombreFoto().getBytes());
			
			System.out.println("Envio mensajes de comando y nombre de la imagen");
			buffer=new byte[10000];

			byte [] buffer2=leerMensaje(buffer);
			
			String recibido=new String(buffer2);
			System.out.println("Respuesta recibida"+recibido);

			if(!recibido.equalsIgnoreCase("NO IMAGEN"))
			{
				buffer=new byte[10000];
				buffer2=leerMensaje(buffer);
				String tamano=new String(buffer2);
				System.out.println(tamano);
				long tamLon=Long.parseLong(tamano);

				String[]spli=recibido.split(":");
				System.out.println("Nombre archivo:"+spli[1]);

				buffer=new byte[10000];
				datos=leerMensajeSinEncriptar(buffer);
				
				String leido=new String(buffer);
				File arch=new File(AdministradorJXTA.RUTA_DESCARGAS+principal.getNombrePeer()+"/"+spli[1]);
				
				Descarga des= new Descarga(0, tamLon, nRemotoImagen.getNombreFoto(), arch,nRemotoImagen.getPiveAdv().getID(),nRemotoImagen.getPeerAgrego());
				des.setTagPersonas(nRemotoImagen.getTags());
				
				principal.agregarDescarga(des);

				principal.agregarImagenDescargada(nRemotoImagen);
				principal.guardarInfoImagenes();
				
				FileOutputStream outArchivo= new FileOutputStream(arch);
				long recibidos=0;
				
				int numeroFoto = 0;
				System.out.println("RECIBIENDO BYTES");
				
				buffer2=buffer.clone();
				byte[] buffer3=new byte[datos];
				
				for (int i = 0; i < datos; i++) {
					buffer3[i]=buffer2[i];
					}
				buffer=buffer3;
				
				while(!leido.equalsIgnoreCase("FIN DEL ARCHIVO"))
				{
					recibidos+=10000;
					
					
					
					outArchivo.write(buffer);
					//System.out.println("RECIBIENDO BYTES");
					
					Mensaje m3= new Mensaje();
					m3.setMensaje(nRemotoImagen.getNombreFoto());
					m3.setTipoMensaje(Mensaje.MENSAJE_RECEPCION_FOTO);
					m3.setPeerSalida(nRemotoImagen.getPeerAgrego());
					m3.setPeerLlegada(principal.getNombrePeer());
					m3.setNumeroPaquete(numeroFoto);
					principal.agregarMensajeRecibido(m3);
					principal.actualizarPanelEstados();
					
					numeroFoto++;
					des.setPaquete(numeroFoto);
					principal.guardarInfoDescargas();
					interfazPrincipal.actualizarPanelDescargasEnCurso();
					
					buffer=new byte[10000];
					datos=leerMensajeSinEncriptar(buffer);
					
					buffer2=buffer.clone();
					buffer3=new byte[datos];
					
					for (int i = 0; i < datos; i++) {
						buffer3[i]=buffer2[i];
						}
					buffer=buffer3;
					
					try
					{
					
					leido=new String(manager.desencriptarMensajeSysmetricoOtro(buffer3));
					}
					catch(Exception e)
					{
						//No pudo desencriptar por que es un fragmento de la fila
						
					}
					
					
					if(recibidos<=tamLon)
					{
						int por=(int) ((recibidos*100)/tamLon);
						interfazPrincipal.actualizarBarraDeProgreso(por);
					}
					else
					{
						interfazPrincipal.actualizarBarraDeProgreso(0);


					}
				}
				outArchivo.close();
				System.out.println("FIN DEL ARCHIVO");
				
				
				
				
				FileInputStream entrada=new FileInputStream(new File(AdministradorJXTA.RUTA_DESCARGAS+principal.getNombrePeer()+"/"+spli[1]));
				ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[16384];

				while ((nRead = entrada.read(data, 0, data.length)) != -1) {
					byteStream.write(data, 0, nRead);
				}

				byteStream.flush();
				entrada.close();
				
				
				//-------------------------------------------------------------
				// RECIBIENDO FIRMA Y VERIFICANDO VALIDES FIRMA PARA VERIFICACION
				//-------------------------------------------------------------
				buffer=new byte[100000];
				datos=leerMensajeSinEncriptar(buffer);
				
				
			
					
					
					buffer3=new byte[datos];
					
					for (int i = 0; i < datos; i++) {
						buffer3[i]=buffer[i];
						}
					
					System.out.println("Firma"+ManejadorEncriptar.asHex(buffer3));
					
					
					boolean veri=manager.verificarFirmaDigital(byteStream.toByteArray(),buffer3 );
				if(veri){
					interfazPrincipal.mostrarMensaje("Firma validada con exito.");
					
				}
				else
				{
					interfazPrincipal.mostrarMensaje("Error al validar firma, firma no valida.");
				}
				
				byte[] resultado=manager.desencriptarMensajeSysmetricoOtro(byteStream.toByteArray());
				byte[]resulEncriptado=manager.encriptarMensajeSymetrico(resultado);
				
				FileOutputStream outArchivo2= new FileOutputStream(arch);
				outArchivo2.write(resulEncriptado);
				outArchivo2.close();
				
				
				int posPunto = nRemotoImagen.getNombreFoto().lastIndexOf(".");
				String ext = nRemotoImagen.getNombreFoto().substring(0,posPunto);
				
				principal.crearFirmaImagen(arch, AdministradorJXTA.RUTA_FIRMAS+principal.getNombrePeer()+"/"+ext+"Firma.firm");
				
				
				System.out.println(leido);

		
				principal.actualizarPanelEstados();


				enviarMensaje("ARCHIVO RECIBIDO".getBytes());
				
				//String resp=dis.readUTF();
				//System.out.println(resp);
				/*int numero=dis.readInt();
            for (int i = 0; i < numero; i++) {
                String respo=dis.readUTF();
           System.out.println(respo);
            }*/


				interfazPrincipal.mostrarMensaje("Imagen agregada con exito");
				interfazPrincipal.actualizarVisores();
			}
			else
			{

				System.out.println("RESPUESTA NEGATIVA");
				enviarMensaje("RESPUESTA NEGATIVA ACEPTADA".getBytes());
				
			}
			out.close();
			in.close();

			socket.close();
			System.out.println("Socket connection closed");



		}  catch (IOException ex) {
			Logger.getLogger(ThreadClienteDescargas.class.getName()).log(Level.SEVERE, null, ex);
			interfazPrincipal.mostrarMensaje("La imagen no ha podido ser agregada correctamente");
		}
		catch (Exception ex) {
			Logger.getLogger(ThreadClienteDescargas.class.getName()).log(Level.SEVERE, null, ex);
			interfazPrincipal.mostrarMensaje("La imagen no ha podido ser agregada correctamente");
		}

	}
	
	public void enviarMensaje(byte[] buffer)
	{
		try
		{	
			
		
			// encriptar con llave simetrica recibida y enviar
			
			System.out.println("Mensaje enviado sin encriptar:"+ManejadorEncriptar.asHex(buffer));
			byte[] buffer2=manager.encriptarMensajeSymetricoOtro(buffer);
			System.out.println("Mensaje enviado encriptado:"+ManejadorEncriptar.asHex(buffer2));
			out.write(buffer2);

			out.flush(); 
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void enviarSinEncriptar(byte[] buffer)
	{
		try
		{	
			
			// Desencriptar con tu clave simetrica
			// encriptar con llave simetrica recibida y enviar
			out.write(buffer);
			out.flush(); 
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	public byte[] leerMensaje(byte[] buffer)
	{
		byte[] miBuffer = null;
		int datos=-1;
		try
		{
			System.out.println("MENSAJE RECIBIDO:" + buffer);
			datos=in.read(buffer);
			byte[] buffer2=new byte[datos];
			
			for (int i = 0; i < datos; i++) {
				buffer2[i]=buffer[i];
			}
			
			System.out.println("Mensaje recibido sin desencriptar:"+ManejadorEncriptar.asHex(buffer2));
			
			miBuffer=manager.desencriptarMensajeSysmetricoOtro(buffer2);
			
			System.out.println("Mensaje recibido desencriptado:"+ManejadorEncriptar.asHex(miBuffer));
			
			System.out.println("MENSAJE RECIBIDO DESENCRYPTADO:" + buffer);
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return miBuffer;

	}
	
	public int leerMensajeSinEncriptar(byte[] buffer)
	{
		
		int datos=-1;
		try {
			datos=in.read(buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return datos;
		
	}

	public static PipeAdvertisement createSocketAdvertisement() 
	{
		PipeID socketID = null;

		try {
			socketID = (PipeID) IDFactory.fromURI(new URI(SOCKETIDSTR));
		} catch (URISyntaxException use) {
			use.printStackTrace();
		}
		PipeAdvertisement advertisement = (PipeAdvertisement)
		AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());

		advertisement.setPipeID(socketID);
		advertisement.setType(PipeService.UnicastType);
		advertisement.setName("Socket tutorial");
		return advertisement;
	}
}
