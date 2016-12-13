/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kahuuFotos.threads2;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.Imagen;
import kahuuFotos.mundo.Mensaje;
import kahuuFotos.seguridad.ManejadorEncriptar;

/**
 *
 * @author Kevin
 */
public class ThreadManejoDescarga extends Thread
{
	private AdministradorJXTA principal;

	private Socket socketCliente;

	private ManejadorEncriptar manager;
	private OutputStream out;
	private InputStream in;

	public ThreadManejoDescarga(AdministradorJXTA ejemp, Socket cliente)
	{   

		principal=ejemp;
		socketCliente=cliente;


	}

	private void sendAndReceiveData() {
		try {
			long start = System.currentTimeMillis();

			// get the socket output stream
			out = socketCliente.getOutputStream();
			//DataOutput dos = new DataOutputStream(out);
			// get the socket input stream
			in = socketCliente.getInputStream();
			//DataInput dis = new DataInputStream(in);



			//--------------------------------------------------------------------------
			// SEGURIDAD: SE RECIBE CERTIFICADO DIGITAL DEL PRESENTE USUARIO
			//--------------------------------------------------------------------------
			manager=principal.darManejadorEncriptacion();
			byte[] buffer=new byte[10000];
			int datos=leerMensajeSinEncriptar(buffer);
			manager.asignarLLaverPorCertificadoPlano(buffer);



			//--------------------------------------------------------------------------
			// SEGURIDAD: SE ENVIA CERTIFICADO DIGITAL AL USUARIO SOLICITANTE
			//--------------------------------------------------------------------------
			enviarSinEncriptar(manager.darCertificado());
			//--------------------------------------------------------------------------
			// SEGURIDAD: SE ENVÍA LLAVE SIMETRICA
			//--------------------------------------------------------------------------

			byte[] llaveSimetrica = manager.darClaveSymetrica();
			System.out.println("LLave Simetrica:" + ManejadorEncriptar.asHex(llaveSimetrica));

			byte[] llaveSimetricaEncriptada = manager.enciptarMensajeAsymetrico(llaveSimetrica);
			System.out.println("LLave Simetrica Encriptada:" + ManejadorEncriptar.asHex(llaveSimetricaEncriptada) );
			System.out.println("LLave Simetrica Encriptada tamanio:" + llaveSimetricaEncriptada.length );

			enviarSinEncriptar(llaveSimetricaEncriptada);

			buffer=new byte[1000];
			byte[] buffer2=leerMensaje(buffer);
			
			System.out.println("Mensaje recibido despues de: "+ManejadorEncriptar.asHex(buffer2));
			String protocolo=new String(buffer2);
			System.out.println("Recibido"+protocolo);

			if(protocolo.startsWith(AdministradorJXTA.DESCARGAR_FOTO))
			{

				String[] mensajesr=protocolo.split(":");
				String nombrePeer=mensajesr[1];
				buffer=new byte[1024];
				
				buffer2=leerMensaje(buffer);
				
				String recibido=new String(buffer2);
				System.out.println(recibido);

				Mensaje m= new Mensaje();
				m.setMensaje(recibido);
				m.setTipoMensaje(Mensaje.MENSAJE_RECEPCION_SOLICITUD);
				m.setPeerLlegada(principal.getNombrePeer());
				m.setPeerSalida(nombrePeer);

				principal.agregarMensajeRecibido(m);
				principal.actualizarPanelEstados();

				Imagen im=principal.getImagenPorIdSensible(recibido,nombrePeer);
				
				if(im!=null)
				{
					File arch=new File(AdministradorJXTA.RUTA_FOTO +principal.getNombrePeer()+"/" +im.getNombreFoto());

					enviarMensaje(("ENVIANDO ARCHIVO:"+arch.getName()).getBytes());
					String tamano=""+arch.length();

					enviarMensaje(tamano.getBytes());


					FileInputStream stream= new FileInputStream(arch);
					int lectura=1;
					buffer=new byte[10000];
					lectura= stream.read(buffer);

					int numero = 0;

					while(lectura!=-1)
					{
						System.out.println("ENVIANDO BYTES");

						Mensaje m3= new Mensaje();
						m3.setMensaje(recibido);
						m3.setTipoMensaje(Mensaje.MENSAJE_ENVIO_FOTO);
						m3.setPeerSalida(principal.getNombrePeer());
						m3.setPeerLlegada(nombrePeer);
						m3.setNumeroPaquete(numero);
						principal.agregarMensajeEnviado(m3);
						principal.actualizarPanelEstados();

						numero++;
												
						byte[] buffer3=new byte[lectura];
						
						for (int i = 0; i < lectura; i++) {
							buffer3[i]=buffer[i];
						}
						
						enviarSinEncriptar(buffer3);

						buffer=new byte[10000];
						lectura= stream.read(buffer);
					}

					stream.close();


					enviarMensaje("FIN DEL ARCHIVO".getBytes());
					
					
					int posPunto = im.getNombreFoto().lastIndexOf(".");
					String ext = im.getNombreFoto().substring(0,posPunto);
					
					
					//-------------------------------------------------------------
					// CARGANDO Y ENVIANDO FIRMA PARA VERIFICACION
					//-------------------------------------------------------------
				FileInputStream in;
								
					in = new FileInputStream(new File(AdministradorJXTA.RUTA_FIRMAS+principal.getNombrePeer()+"/"+ext+"Firma.firm"));
				
				
				ByteArrayOutputStream byteBufferStream = new ByteArrayOutputStream();

				int nRead;
				byte[] data = new byte[128];

				while ((nRead = in.read(data, 0, data.length)) != -1) {
					byteBufferStream.write(data, 0, nRead);
				}

				byteBufferStream.flush();
				in.close();
					
					System.out.println("Firma"+ManejadorEncriptar.asHex(byteBufferStream.toByteArray()));
					
					enviarSinEncriptar(byteBufferStream.toByteArray());

				}
				else
				{
					enviarMensaje("NO IMAGEN".getBytes());

				}
				/*String datos=dis.readUTF();
                System.out.println(datos);
                dos.writeUTF("ENVIANDO PERSONAS");
                dos.writeInt(2);
                dos.writeUTF("kevin");
                dos.writeUTF("Marce");*/


				principal.actualizarPanelEstados();


				buffer=new byte[10000];
				
				buffer2=leerMensaje(buffer);
				
				recibido=new String(buffer2);
				System.out.println(recibido);


				out.close();
				in.close();


				socketCliente.close();

				System.out.println("Connection closed");

			}
			else if(protocolo.startsWith(AdministradorJXTA.DESCARGAR_FOTO_DESDE_PAQUETE))
			{

				String[] mensajesr=protocolo.split(":"); 

				String nombreImagen=mensajesr[2];
				int paquete=Integer.parseInt(mensajesr[3]);

				String nombrePeer=mensajesr[1];

				Imagen im=principal.getImagenPorId(nombreImagen);
				if(im!=null)
				{
					enviarMensaje("ENVIANDO ARCHIVO".getBytes());

					File arch=new File(AdministradorJXTA.RUTA_FOTO +principal.getNombrePeer()+"/" +im.getNombreFoto());
					FileInputStream stream= new FileInputStream(arch);
					int lectura=1;

					int numero = paquete;

					//lectura= stream.read(buffer, (numero)*10000, 10000)	
					for (int i = 0; i <= paquete; i++) 
					{
						buffer=new byte[10000];
						lectura= stream.read(buffer);	
					}

					buffer=new byte[10000];
					lectura= stream.read(buffer);	
					//lectura= stream.read(buffer, (numero)*10000, 10000);

					while(lectura!=-1)
					{
						//System.out.println("ENVIANDO BYTES");
						Mensaje m3= new Mensaje();
						m3.setMensaje(nombreImagen);
						m3.setTipoMensaje(Mensaje.MENSAJE_ENVIO_FOTO);
						m3.setPeerSalida(principal.getNombrePeer());
						m3.setPeerLlegada(nombrePeer);
						m3.setNumeroPaquete(numero);
						principal.agregarMensajeEnviado(m3);
						principal.actualizarPanelEstados();

						numero++;
						enviarMensaje(buffer);

						buffer=new byte[10000];
						lectura= stream.read(buffer);
						//lectura= stream.read(buffer, (numero)*10000, 10000);
					}

					stream.close();
					enviarMensaje("FIN DEL ARCHIVO".getBytes());
				}
				else
				{
					enviarMensaje("NO IMAGEN".getBytes());

				}

				buffer=new byte[1024];			
				buffer2=leerMensaje(buffer);
				
				String recibido=new String(buffer, 0, datos);
			}
		} catch (Exception ie) {
			ie.printStackTrace();
		}
	}

	public void run() {
		sendAndReceiveData();
	}

	public void enviarMensaje(byte[] buffer)
	{
		try
		{	
			// Desencriptar con tu clave simetrica
			// encriptar con llave simetrica recibida y enviar
			out.write(manager.encriptarMensajeSymetrico(buffer));
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
			datos=in.read(buffer);

			byte[] buffer2=new byte[datos];
			
			for (int i = 0; i < datos; i++) {
				buffer2[i]=buffer[i];
			}
			System.out.println("Mensaje recibido sin desencriptar:"+ManejadorEncriptar.asHex(buffer2));
			
			miBuffer=manager.desencriptarMensajeSysmetrico(buffer2);
			
			System.out.println("Mensaje recibido desencriptado:"+ManejadorEncriptar.asHex(miBuffer));
			
		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return miBuffer;
	}
	
	public void enviarSinEncriptar(byte[] buffer)
	{
		try
		{	
			out.write(buffer);
			out.flush(); 
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
}

