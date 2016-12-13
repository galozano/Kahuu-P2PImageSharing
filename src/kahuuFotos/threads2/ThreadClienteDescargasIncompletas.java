/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kahuuFotos.threads2;


import java.io.File;
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
import kahuuFotos.mundo.Mensaje;
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
public class ThreadClienteDescargasIncompletas extends Thread
{
	public final static String SOCKETIDSTR = "urn:jxta:uuid-59616261646162614E5047205032503393B5C2F6CA7A41FBB0F890173088E79404";

	

	private InterfazKahuuImagenes interfazPrincipal;
	private AdministradorJXTA principal;

	private final PeerGroup netPeerGroup;
	
	private Descarga descarga;
	
	public ThreadClienteDescargasIncompletas(AdministradorJXTA ejempl,PeerGroup nPeerGroup,InterfazKahuuImagenes interfaz, Descarga miDescarga)
	{
		
		principal=ejempl;
		netPeerGroup=nPeerGroup;
		interfazPrincipal=interfaz;
		descarga=miDescarga;

	}

	public void run(){

		System.out.println("Connecting to the server");
		try {
			
			JxtaSocket socket = new JxtaSocket(netPeerGroup,principal.crearPipeAdvertisement(descarga.getPipeID()));

//			System.out.println( nRemotoImagen.getPiveAdv().getID());;

//			System.out.println(nRemotoImagen.getPiveAdv().getName());

//			System.out.println(nRemotoImagen.getPiveAdv().getPipeID());


			Mensaje m= new Mensaje();
			m.setMensaje(descarga.getNombreImagen());
			m.setTipoMensaje(Mensaje.MENSAJE_ENVIO_SOLICITUD_FOTO);
			m.setPeerSalida(principal.getNombrePeer());
			m.setPeerLlegada(descarga.getPeerAgrego());

			principal.agregarMensajeEnviado(m);
			principal.actualizarPanelEstados();
			// get the socket output stream
			OutputStream out = socket.getOutputStream();
			//DataOutput dos = new DataOutputStream(out);

			// get the socket input stream
			InputStream in = socket.getInputStream();
			//DataInput dis = new DataInputStream(in);

			out.write((AdministradorJXTA.DESCARGAR_FOTO_DESDE_PAQUETE+principal.getNombrePeer()+":"+descarga.getNombreImagen()+":"+descarga.getPaquete()).getBytes());
			out.flush(); 

			

			byte[] buffer=new byte[10000];
			int datos=in.read(buffer);
			String recibido=new String(buffer, 0, datos);
			System.out.println(recibido);

			if(!recibido.equalsIgnoreCase("NO IMAGEN"))
			{
				

				

			
				File arch=new File(descarga.getArchivoTemporal().getPath());
				FileOutputStream outArchivo= new FileOutputStream(arch,true);
				
				
				int numeroFoto = descarga.getPaquete();
				
				buffer=new byte[10000];
				datos=in.read(buffer);
				String leido=new String(buffer, 0, datos);
				
				while(!leido.equalsIgnoreCase("FIN DEL ARCHIVO"))
				{
					
					outArchivo.write(buffer);
					System.out.println("RECIBIENDO BYTES");
					descarga.setPaquete(numeroFoto+1);
					Mensaje m3= new Mensaje();
					m3.setMensaje(recibido);
					m3.setTipoMensaje(Mensaje.MENSAJE_RECEPCION_FOTO);
					m3.setPeerSalida(descarga.getPeerAgrego());
					m3.setPeerLlegada(principal.getNombrePeer());
					m3.setNumeroPaquete(numeroFoto);
					principal.agregarMensajeRecibido(m3);
					principal.actualizarPanelEstados();
					
					numeroFoto++;
					descarga.setPaquete(numeroFoto);
					principal.guardarInfoDescargas();
					interfazPrincipal.actualizarPanelDescargasEnCurso();
					
					buffer=new byte[10000];
					datos=in.read(buffer);
					leido=new String(buffer, 0, datos);

					
					
					
				}
				outArchivo.close();
				System.out.println(leido);

		
				principal.actualizarPanelEstados();


				out.write("ARCHIVO RECIBIDO".getBytes());
				out.flush();
				//String resp=dis.readUTF();
				//System.out.println(resp);
				/*int numero=dis.readInt();
            for (int i = 0; i < numero; i++) {
                String respo=dis.readUTF();
           System.out.println(respo);
            }*/


				//principal.agregarImagenDescargada(nRemotoImagen);

				interfazPrincipal.mostrarMensaje("Imagen descargada con exito");
				interfazPrincipal.actualizarVisores();
			}
			else
			{

				System.out.println("RESPUESTA NEGATIVA");
				out.write("RESPUESTA NEGATIVA ACEPTADA".getBytes());
				out.flush();
			}
			out.close();
			in.close();

			socket.close();
			System.out.println("Socket connection closed");



		}  catch (IOException ex) {
			Logger.getLogger(ThreadClienteDescargasIncompletas.class.getName()).log(Level.SEVERE, null, ex);
			interfazPrincipal.mostrarMensaje("La imagen no ha podido ser agregada correctamente");
		}
		catch (Exception ex) {
			Logger.getLogger(ThreadClienteDescargasIncompletas.class.getName()).log(Level.SEVERE, null, ex);
			interfazPrincipal.mostrarMensaje("La imagen no ha podido ser agregada correctamente");
		}

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
