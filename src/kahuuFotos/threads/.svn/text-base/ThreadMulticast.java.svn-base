package kahuuFotos.threads;

import java.net.DatagramPacket;
import java.util.ArrayList;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.EstadoRecibido;
import kahuuFotos.mundo.Imagen;
import kahuuFotos.mundo.Mensaje;
import net.jxta.socket.JxtaMulticastSocket;

public class ThreadMulticast extends Thread
{
	private JxtaMulticastSocket multicast;

	private AdministradorJXTA admin;

	public ThreadMulticast(JxtaMulticastSocket multi, AdministradorJXTA admin)
	{
		this.multicast = multi;
		this.admin = admin;
	}

	public void run()
	{	
		while(true)
		{
			try 
			{
				byte[] buffer = new byte[100000];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				multicast.receive(packet);

				String mensaje = new String(packet.getData());

				if(mensaje.startsWith(AdministradorJXTA.PEDIDO))
				{
					System.out.println("PEDIDO");

					if(admin.getOcupado())
					{
						System.out.println("OCUPADO");
						buffer = AdministradorJXTA.OCUPADO.getBytes();
						packet = new DatagramPacket(buffer, buffer.length);

						multicast.send(packet);					
					}
				}
				else if(mensaje.startsWith(AdministradorJXTA.OCUPADO))
				{
					System.out.println("ESTA OCUPADO");
					admin.setOcupadoOcupado();
				}
				else if(mensaje.startsWith(AdministradorJXTA.GUARDAR_ESTADO))
				{
					try 
					{
						//Primero se ve si el peer name no es el mismo

						//Primera posicion mensaje y nombre peer
						//Segunda los estados
						//Tercera los mensajes enviados
						//Cuarta los mensajes recibidos
						String[] split=mensaje.split("-");
						//Pos1
						System.out.println(split[0]);
						//Pos2
						System.out.println(split[1]);
						//Pos3
						System.out.println(split[2]);
						//Pos4
						System.out.println(split[3]);

						//Para averiguar nombre del peer
						String[] split2=split[0].split(":");

						String nombrePeer=split2[1];

						if(!nombrePeer.equals(admin.getNombrePeer()))
						{

							//Se se sacan los valores del mensaje

							System.out.println("MENSAJE RECIBIDO PRIMERO:" + mensaje);

							String estado=split[1];
							String[] datosEstado=estado.split(":");

							//mensaje t modificas;
							EstadoRecibido er = new EstadoRecibido(nombrePeer,datosEstado[1], datosEstado[2]);
							//Guardando mensajes enviados
							String enviados=split[2];

							String[] datosEnviados=enviados.split(":");
							int numero=Integer.parseInt(datosEnviados[1]);
							for (int i = 2; i < (numero *5) -2; i+=5) 
							{

								String tipo=datosEnviados[i];
								String peerSalida=datosEnviados[i+1];
								String peerLlegada=datosEnviados[i+2];
								String infoMensaje=datosEnviados[i+3];
								String numeroPaquete=datosEnviados[i+4];

								Mensaje temp= new Mensaje(peerSalida, peerLlegada, infoMensaje, tipo);
								temp.setNumeroPaquete(Integer.parseInt(numeroPaquete));

								System.out.println("MENSAJE ENVIADOS :" + temp);

								er.agregarMensajeEnviado(temp);

							}

							//Guardando mensajes enviados
							String recibidos=split[3];

							String[] datosRecibidos=recibidos.split(":");
							numero=Integer.parseInt(datosRecibidos[1]);
							for (int i = 2; i < (numero *5) -2 ; i+=5) {

								String tipo=datosRecibidos[i];
								String peerSalida=datosRecibidos[i+1];
								String peerLlegada=datosRecibidos[i+2];
								String infoMensaje=datosRecibidos[i+3];
								String numeroPaquete=datosRecibidos[i+4];

								Mensaje temp= new Mensaje(peerSalida, peerLlegada, infoMensaje, tipo);
								temp.setNumeroPaquete(Integer.parseInt(numeroPaquete));

								System.out.println("MENSAJE REC :" + temp);

								er.agregarMensajeRecibido(temp);

							}

							//Guardar el estado en la lista estados de Administrador				

							System.out.println("MENSAJE RECIBIDO FINAL:" + er.toString());

							admin.guardarEstado( );
							admin.agregarEstadoRecibido(er);

						}
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(mensaje.startsWith(AdministradorJXTA.SIMILITUD))
				{
					System.out.println(mensaje);
					if(!(mensaje.split(":")[1]).equalsIgnoreCase(admin.getNombrePeer()))
					{


						int ancho=Integer.parseInt(new String(mensaje.split(":")[2]));

						ArrayList<Imagen> listaFotoMasParecida =new ArrayList<Imagen>();
						Imagen resu=admin.darImagenSimilar(ancho);

						if(resu!=null)
						{
							System.out.println("Enviando respuesta similitud");
							resu.setParecido(Math.random()*100);
							listaFotoMasParecida.add(resu);

							String listaParecidaEnviar = admin.SIMILITUD_RESPUESTA+(mensaje.split(":")[1])+":";

							for (int i = 0; i < listaFotoMasParecida.size() && i < 4; i++) 
							{
								Imagen im = listaFotoMasParecida.get(i);
								listaParecidaEnviar += im.getNombreFoto() + "-" + im.getParecido() + ",";
							}
							buffer = listaParecidaEnviar.getBytes();
							DatagramPacket packet3 = new DatagramPacket(buffer, buffer.length);
							multicast.send(packet3);
						}
						else
						{
							System.out.println("No se encontro imagen similar");
						}


						/*File f = new File("tmp/"+ (new Date()).getTime()+".jpg" );




		                FileOutputStream outArchivo= new FileOutputStream(f);

		                buffer = new byte[1000000];
						DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length);
						multicast.receive(packet2);

						String leido = new String(packet2.getData());
						System.out.println("TAMANO RECIBIDO "+leido);

						long tamano=Long.parseLong(leido);

						long tamBuffer=tamano-1000000;
			            int tamanoReal=1000000;
			            if(tamBuffer<0)
			              {
			            	  tamanoReal=(int) tamano;
			              }


						buffer = new byte[tamanoReal];
						packet2 = new DatagramPacket(buffer, buffer.length);
						multicast.receive(packet2);

						leido = new String(packet2.getData());
		                while(!leido.equalsIgnoreCase("FIN DE LA IMAGEN"))
		                {


		                    outArchivo.write(packet2.getData());
		                    System.out.println("RECIBIENDO BYTES");
		                    System.out.println("BYTES RESTANTES:"+tamano);
		                   // System.out.println(leido);
		                    tamBuffer=tamano-1000000;
		                    if(tamBuffer<0)
				              {
				            	  tamanoReal=(int) tamano;
				              }
				              else
				              {
				            	  tamano=tamano-1000000;
				              }
		                     buffer=new byte[tamanoReal];
		                     packet2 = new DatagramPacket(buffer, buffer.length);
							multicast.receive(packet2);
							 leido = new String(packet2.getData());

		                }
		                System.out.println("IMAGEN POR SIMILITUD RECIBIDA");
		                outArchivo.close();

		                Thread.sleep(2000);

		                ArrayList<Imagen> listaFotoMasParecida =new ArrayList<Imagen>();
		                Imagen resu=admin.darImagenSimilar(f);
		                if(resu!=null)
		                {
		                	System.out.println("Enviando respuesta similitud");
		                	resu.setParecido(Math.random()*100);
		                listaFotoMasParecida.add(resu);

						String listaParecidaEnviar = admin.SIMILITUD_RESPUESTA+(mensaje.split(":")[1])+":";

						for (int i = 0; i < listaFotoMasParecida.size() && i < 4; i++) 
						{
							Imagen im = listaFotoMasParecida.get(i);
							listaParecidaEnviar += im.getNombreFoto() + "-" + im.getParecido() + ",";
						}
						buffer = listaParecidaEnviar.getBytes();
						DatagramPacket packet3 = new DatagramPacket(buffer, buffer.length);
						multicast.send(packet3);
		                }
		                else
		                {
		                	System.out.println("No se encontro imagen similar");
		                }

						 */



					}

				}
				else if(mensaje.startsWith(AdministradorJXTA.SIMILITUD_RESPUESTA+admin.getNombrePeer()))
				{
					System.out.println(mensaje);

					ArrayList<Imagen> imagenesParecidasRecibidas = new ArrayList<Imagen>();
					String listaParecida = mensaje.split(":")[1];
					String lista[] = listaParecida.split(",");

					for (int j = 0; j < lista.length; j++) 
					{
						System.out.println(lista[j]);
						String stringImagen[] = lista[j].split("-");
						Imagen imagen = new Imagen(stringImagen[0], Double.parseDouble(stringImagen[1]));
						imagenesParecidasRecibidas.add(imagen);
					}

					//System.out.println(masParecida.split(":")[1] + ":" + masParecida.split(":")[2] );
					//Imagen imagenParecida = new Imagen(masParecida.split(":")[1], Double.parseDouble(masParecida.split(":")[2]));

					admin.agregarListaFotoParecida(imagenesParecidasRecibidas);	

					admin.notificarMasParecidas();


				}

				else if(mensaje.startsWith(AdministradorJXTA.RECUPERAR_ESTADO))
				{
					System.out.println("RECUPERAR ESTADO");
					admin.procesarEstados();
					//admin.recuperarEstadoSinEnviarNada();			
				}

			} 


			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
