/*
   9 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kahuuFotos.mundo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Observable;

import javax.swing.ImageIcon;

import kahuuFotos.adv.AdvertisementPeer;
import kahuuFotos.adv.AdvertisementRecurso;
import kahuuFotos.adv.MyAdvertisementPeers;
import kahuuFotos.adv.MyAdvertisementRecurso;
import kahuuFotos.imagens.NaiveSimilarityFinder;
import kahuuFotos.interfaz.InterfazKahuuImagenes;
import kahuuFotos.seguridad.ManejadorEncriptar;
import kahuuFotos.threads.ThreadFotoParecida;
import kahuuFotos.threads.ThreadMulticast;
import kahuuFotos.threads2.ThreadClienteDescargas;
import kahuuFotos.threads2.ThreadClienteDescargasIncompletas;
import kahuuFotos.threads2.ThreadServidorDescargaImagen;
import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.ID;
import net.jxta.id.IDFactory;
import net.jxta.impl.id.UUID.PipeID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.PipeService;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaMulticastSocket;
import be.ac.luc.vdbergh.ntp.NtpConnection;

/**
 * El administrador general de JXTA
 * @author gustavolozano
 *
 */
public class AdministradorJXTA extends Observable implements DiscoveryListener
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------

	public final static String SOCKETIDSTR = "urn:jxta:uuid-59616261646162614E5047205032503393B5C2F6CA7A41FDB0F890173088E79404";

	public final static String PEER = "PEER";

	public final static String RUTA_FOTO = "misFotos/";
	
	public final static String RUTA_FIRMAS = "firmas/";

	public final static String RUTA_DESCARGAS = "descargas/";

	private final static String RUTA_CACHE = "data/cache";

	public final static int RECIBIR_FOTO = 1;

	public final static int ENVIAR_FOTO = 2;

	public  String PATH_ESTADO="data/save/";

	public final static String PEDIDO = "PEDIDO";

	public final static String OCUPADO = "OCUPADO";

	public final static String SIMILITUD = "PEDIR_SIMILITUD";

	public final static String SIMILITUD_RESPUESTA = "SIMILITUD_RESPUESTA";

	public final static String DESCARGAR_FOTO = "DESCARGAR FOTO:";

	public final static String DESCARGAR_FOTO_DESDE_PAQUETE = "DESCARGAR FOTO DESDE PAQUETE:";

	//ESTADOS
	//Mensajes de Estado

	public final static String GUARDAR_ESTADO = "GUARDAR_ESTADO:";

	public static final String RECUPERAR_ESTADO = "RECUPERAR ESTADO";

	//------------------------------------------------------------------------------------------------------------------------------
	// Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	private NetworkManager manager;

	private PeerGroup netPeerGroup;

	private DiscoveryService discovery;

	private PipeAdvertisement pipeAdv;

	//private PipeService pipe_service;

	private ArrayList<Imagen> misFotos;

	private ArrayList<Imagen> misDescargas;

	private ArrayList<Imagen> fotosParecidas;

	private ArrayList<Descarga> descargas;

	private File archivoComprar;

	private InterfazKahuuImagenes interfaz;

	private ThreadFotoParecida threadFotoParecida;

	private String peer_name;

	private boolean estaOcupado;

	private JxtaMulticastSocket multicastSocket;

	//Estados por los que ha pasado el peer
	private ArrayList<Estado> estadosGuardados;

	//Lista de mensajes enviados
	private ArrayList<Mensaje> mensajesEnviados;

	//Lista de mensajes recibidos
	private ArrayList<Mensaje> mensajesRecibidos;

	private ArrayList<EstadoRecibido> estadosRecibidos;

	private boolean estadoGuardado;

	private ThreadServidorDescargaImagen threaServidorDescarga;

	private ThreadMulticast multiThread;

	private ManejadorEncriptar manejarEncriptacion;

	//------------------------------------------------------------------------------------------------------------------------------
	// Constructores
	//------------------------------------------------------------------------------------------------------------------------------

	public AdministradorJXTA(String s ) throws KahuuException
	{
		try 
		{	
			//estadoActual = new Estado(NADA, new ArrayList<String>(), s);
			leerClaves(s);
			
			descargas=new ArrayList<Descarga>();

			estaOcupado = false;
			estadoGuardado = false;    
			//String peer_name = "Peer " + new Random().nextInt(1000000); 
			peer_name = s; 
			manager = new NetworkManager(NetworkManager.ConfigMode.ADHOC, peer_name, new File(RUTA_CACHE, peer_name).toURI());
			manager.startNetwork();

			estadosRecibidos=new ArrayList<EstadoRecibido>();
			estadosGuardados = new ArrayList<Estado>();
			mensajesRecibidos=new ArrayList<Mensaje>();
			mensajesEnviados=new ArrayList<Mensaje>();	

			//Registrar advertisements
			AdvertisementFactory.registerAdvertisementInstance(AdvertisementRecurso.getAdvertisementType(), new MyAdvertisementRecurso.Instantiator());
			AdvertisementFactory.registerAdvertisementInstance(AdvertisementPeer.getAdvertisementType(), new MyAdvertisementPeers.Instantiator());

			netPeerGroup = manager.getNetPeerGroup();
			PeerGroupID id = netPeerGroup.getPeerGroupID();

			PipeID idPipe = crearPipeID(id);    
			pipeAdv = crearPipeAdvertisement(idPipe);

			discovery = netPeerGroup.getDiscoveryService();
			discovery.addDiscoveryListener(this);

			MyAdvertisementPeers peer = new MyAdvertisementPeers();
			peer.setNombrePeer(PEER);
			peer.setPipeAdv(pipeAdv);

			discovery.publish(peer);

			misDescargas = new ArrayList<Imagen>();
			misFotos = new ArrayList<Imagen>();

			fotosParecidas = new ArrayList<Imagen>();

			File file2=new File(PATH_ESTADO+peer_name+"/");
			if(!file2.exists())
			{
				file2.mkdirs();
			}
			
			File file3=new File(RUTA_FIRMAS+peer_name+"/");
			if(!file3.exists())
			{
				file3.mkdirs();
			}


			//Cargando el estado salvado del programa

			//Cambio de prueba aqui!!!!
			//Inicio de el servidor local para oir consultas
			//ThreadServer server = new ThreadServer(netPeerGroup, pipeAdv, this);
			//server.start();

			threaServidorDescarga=new ThreadServidorDescargaImagen(this, netPeerGroup,pipeAdv);
			threaServidorDescarga.start();


			//Inicio de el servidor multicast para oir consultas
			multicastSocket= new JxtaMulticastSocket(manager.getNetPeerGroup(), getSocketAdvertisementPropagate());

			multiThread = new ThreadMulticast(multicastSocket, this);
			multiThread.start();

			cargarEstado();

			cargarEstadoDescargas();


		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new KahuuException("No fue posible conectarse a la red: \n" + e.getMessage());
		}    
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Iniciar
	//------------------------------------------------------------------------------------------------------------------------------

	private static PipeID crearPipeID(PeerGroupID pgID) throws KahuuException 
	{
		PipeID socketID = null;
		try 
		{
			socketID = (PipeID) IDFactory.newPipeID(pgID);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			throw new KahuuException("No se pudo publicar la informaci—n del nodo en la red: \n" + e.getMessage());
		}
		return socketID;
	}

	public PipeAdvertisement crearPipeAdvertisement(ID pipeId)
	{
		PipeAdvertisement advertisement = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());

		advertisement.setPipeID(pipeId);
		advertisement.setType(PipeService.UnicastType);
		advertisement.setName("Pipe distribuidos");
		return advertisement;
	}

	public static PipeAdvertisement getSocketAdvertisementPropagate() 
	{
		PipeID socketID = null;
		try 
		{
			socketID = (PipeID) IDFactory.fromURI(new URI(SOCKETIDSTR));
		} 
		catch (URISyntaxException use) 
		{
			use.printStackTrace();
		}
		PipeAdvertisement advertisement = (PipeAdvertisement)AdvertisementFactory.newAdvertisement(PipeAdvertisement.getAdvertisementType());
		advertisement.setPipeID(socketID);
		// set to type to propagate
		advertisement.setType(PipeService.PropagateType);
		advertisement.setName("Socket tutorial");
		return advertisement;
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Busquedas
	//------------------------------------------------------------------------------------------------------------------------------

	public void buscarTodosLosPeers()
	{
		discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, MyAdvertisementPeers.NOMBRE_PEER, PEER, 50, this);  
	}

	public void buscarFotos(String nombreFoto) throws KahuuException
	{
		if(!pedirPermidoAPeers())
		{
			//estadoActual = BUSCANDO_FOTO+ nombreFoto;
			Estado estadoActual=new Estado();
			estadoActual.setTipoEstado(Estado.BUSCANDO_FOTO);
			estadoActual.setMensajeSolicitado(nombreFoto);
			estadosGuardados.add(estadoActual);

			discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, MyAdvertisementRecurso.NOMBRE, nombreFoto, 10, this);

			//Para los estados
			Mensaje m= new Mensaje();
			m.setMensaje(nombreFoto);
			m.setTipoMensaje(Mensaje.MENSAJE_ENVIO_BUSQUEDA);
			m.setMensaje(nombreFoto);
			mensajesEnviados.add(m);
			interfaz.actualizarPanelEstados();
		}
		else
		{
			throw new KahuuException("Sistema está ocupado.");
		}		     
	}

	public void busquedaTags(String tags) throws KahuuException
	{
		if(!pedirPermidoAPeers())
		{
			//estadoActual = BUSCANDO_TAGS + tags;
			Estado estadoActual=new Estado();
			estadoActual.setTipoEstado(Estado.BUSCANDO_TAGS);
			estadoActual.setMensajeSolicitado(tags);
			estadosGuardados.add(estadoActual);

			discovery.getRemoteAdvertisements(null, DiscoveryService.ADV, MyAdvertisementRecurso.TAGS, "*"+tags+"*", 10, this);

			Mensaje m= new Mensaje();
			m.setTipoMensaje(Mensaje.MENSAJE_ENVIO_BUSQUEDA);
			m.setMensaje(tags);
			mensajesEnviados.add(m);
			interfaz.actualizarPanelEstados();
		}
		else
		{
			throw new KahuuException("Sistema est‡ ocupado.");
		}
	}


	public void discoveryEvent(DiscoveryEvent de) 
	{
		System.out.println("Metodo Disvovery");

		Enumeration<Advertisement> respuestas = de.getSearchResults();
		ArrayList<ImagenTem> listaImgenes = new ArrayList<ImagenTem>();   
		ArrayList<Peer> listaPeers = new ArrayList<Peer>();


		while (respuestas.hasMoreElements())
		{
			Object advertisement = respuestas.nextElement();

			if(advertisement instanceof MyAdvertisementRecurso)
			{
				MyAdvertisementRecurso ad = (MyAdvertisementRecurso)advertisement;                             
				listaImgenes.add(new ImagenTem(ad.getNombreFoto(), ad.getTags(),"",ad.getTiempoCreacion(),ad.getPipeAdv(),ad.getPeerAgrego(),Boolean.parseBoolean(ad.isSensible())));
			}
			else if(advertisement instanceof MyAdvertisementPeers)
			{
				MyAdvertisementPeers ad = (MyAdvertisementPeers)advertisement;                                       
				Peer p = new Peer();
				p.setPiveAdv(ad.getPipeAdv());
				listaPeers.add(p);               
			}
		}

		if(listaImgenes.size() > 0)
		{
			System.out.println("Lista Imagenes:" + listaImgenes.size());
			//Se le notifica a el panel de busquedas
			setChanged();
			notifyObservers(listaImgenes);
		}
		else if(listaPeers.size() > 0)
		{     
			if(threadFotoParecida == null || !threadFotoParecida.isAlive())
			{        	
				threadFotoParecida = new ThreadFotoParecida(listaPeers, netPeerGroup, archivoComprar, this);
				threadFotoParecida.start();
			}
		}
	}

	
	//------------------------------------------------------------------------------------------------------------------------------
	// Agregar Fotos
	//------------------------------------------------------------------------------------------------------------------------------
	
	public void agregarFoto(String nombreFoto, String tags, File fotoAgregar, boolean b) throws KahuuException
	{
		int posPunto = fotoAgregar.getName().lastIndexOf(".");
		String ext = fotoAgregar.getName().substring(posPunto);
		String nombreCompleto = nombreFoto + ext;

		if(getImagenPorId(nombreCompleto)==null && !pedirPermidoAPeers())
		{
			//Copia el archivo agregado por e l usuario a la carpeta de misFotos
			copyFile(fotoAgregar, new File(RUTA_FOTO+peer_name+"/" +nombreCompleto));
			
			
			try 
			{
				crearFirmaImagen(new File(RUTA_FOTO+peer_name+"/" +nombreCompleto), RUTA_FIRMAS+peer_name+"/"+nombreFoto+"Firma.firm");

				MyAdvertisementRecurso adv = new MyAdvertisementRecurso();
				adv.setNombreAdvertisement(nombreFoto);

				String tc = getActualTime();

				adv.setNombreFoto( nombreCompleto);
				adv.setTags(tags);
				adv.setTiempoCreacion(tc);
				adv.setPipeAdv(pipeAdv);
				adv.setPeerAgrego(peer_name);
				adv.setSensible(""+b);

				// Se publica el archivo a la red JXTA
				discovery.publish(adv);

				this.misFotos.add(new Imagen(nombreCompleto, tags,"",tc,peer_name,b));

				Estado estadoActual=new Estado();
				estadoActual.setTipoEstado(Estado.AGREGANDO_FOTO);
				estadoActual.setMensajeSolicitado(nombreCompleto);
				estadosGuardados.add(estadoActual);

				interfaz.actualizarPanelEstados();

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				throw new KahuuException("No se pudo publicar la información del nodo en la red: \n" + e.getMessage());
			}
		}
		else
		{
			throw new KahuuException("La imagen ya existe en el sistema o el sistema esta ocupado");
		}

	}
	
	public void crearFirmaImagen(File file, String string) throws IOException, KahuuException {
		// TODO Auto-generated method stub
		FileInputStream in;
		
			in = new FileInputStream(file);
		
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = in.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();
		in.close();
		
		byte[] firma=manejarEncriptacion.mensajeFirmaDigital(buffer.toByteArray());
		
		
		FileOutputStream out=new FileOutputStream(new File(string));
		out.write(firma);
		out.close();
		
		
	}

	public  void copyFile(File sourceFile, File destFile) throws  KahuuException
	{
		//FileChannel source = null;
		//FileChannel destination = null;
		try 
		{
			if(!destFile.exists()) 
			{
				destFile.createNewFile();
			}
			//Encripto con simetrica
			//Firmo usado signutre


			/*
			source = new FileInputStream(sourceFile).getChannel();
			destination = new FileOutputStream(destFile).getChannel();

			// previous code: destination.transferFrom(source, 0, source.size());
			// to avoid infinite loops, should be:
			long count = 0;
			long size = source.size();              
			while((count += destination.transferFrom(source, 0, size-count))<size);

			if(source != null) 
			{
				source.close();
			}

			if(destination != null)
			{
				destination.close();
			}*/

			FileInputStream in=new FileInputStream(sourceFile);
			FileOutputStream out=new FileOutputStream(destFile);

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = in.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			in.close();

			byte[] encriptado=	manejarEncriptacion.encriptarMensajeSymetrico(buffer.toByteArray());

			out.write(encriptado);
			out.close();


		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new KahuuException("No fue posible copiar el archivo: \n" + e.getMessage());
		}
	}


	//------------------------------------------------------------------------------------------------------------------------------
	// Manejo de Descarga
	//------------------------------------------------------------------------------------------------------------------------------

	public void descargar(ImagenTem imagen) throws KahuuException
	{
		try 
		{    	
			/* Cambio de prueba aqui!!!
			 * JxtaSocket socket = new JxtaSocket();
			//Se conecta usando el Adversitement
			socket.connect(this.netPeerGroup, imagen.getPiveAdv());

			//Se crea un Thread para manejar la llegada del archivo 
			ThreadCliente cliente = new ThreadCliente(socket.getInputStream(), socket.getOutputStream(), imagen.getNombreFoto(), RECIBIR_FOTO, this);
			cliente.start();

			imagen.setTiempoDescarga(getActualTime());
			this.misDescargas.add(imagen);	
			interfaz.actualizarVisores();*/

			Estado estadoActual=new Estado();
			estadoActual.setTipoEstado(Estado.SOLICITANDO_DESCARGAR_FOTO);
			estadoActual.setMensajeSolicitado(imagen.getNombreFoto());
			estadosGuardados.add(estadoActual);


			ThreadClienteDescargas descargas= new ThreadClienteDescargas(imagen, this, netPeerGroup, interfaz);		
			descargas.start();

			interfaz.actualizarPanelEstados();

		}  
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new KahuuException("No se pudo descargar la imagen: \n" + e.getMessage());		
		} 	    	
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Manejo de Tiempo
	//------------------------------------------------------------------------------------------------------------------------------
	
	public String getActualTime() throws KahuuException
	{
		try
		{
			NtpConnection ntp = new NtpConnection(InetAddress.getByName("pool.ntp.org"));			
			return ntp.getTime().toString();
		} 
		catch (SocketException e) 
		{
			throw new KahuuException("No se pudo determinar el tiempo: \n" + e.getMessage());	
		}
		catch (UnknownHostException e) 
		{
			throw new KahuuException("No se pudo determinar el tiempo: \n" + e.getMessage());	
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Manejo de Imagenes
	//------------------------------------------------------------------------------------------------------------------------------
	
	public Imagen getImagenPorId(String imagen) 
	{
		for (int i = 0; i < misDescargas.size(); i++) {
			Imagen tem=misDescargas.get(i);
			if(tem.getNombreFoto().equalsIgnoreCase(imagen))
			{
				return tem;
			}
		}

		for (int i = 0; i < misFotos.size(); i++) {
			Imagen tem=misFotos.get(i);
			if(tem.getNombreFoto().equalsIgnoreCase(imagen))
			{
				return tem;
			}
		}

		return null;
	}
	
	
	public Imagen getImagenPorIdSensible(String imagen, String nombrePeer) 
	{
		for (int i = 0; i < misDescargas.size(); i++) {
			Imagen tem=misDescargas.get(i);
			if(tem.getNombreFoto().equalsIgnoreCase(imagen))
			{
				
				if(tem.isSensible())
				{
					tem.agregarPersonaConsulto(nombrePeer);
				}
				return tem;
			}
		}

		for (int i = 0; i < misFotos.size(); i++) {
			Imagen tem=misFotos.get(i);
			if(tem.getNombreFoto().equalsIgnoreCase(imagen))
			{
				if(tem.isSensible())
				{
					tem.agregarPersonaConsulto(nombrePeer);
				}
				return tem;
			}
		}
		
		interfaz.actualizarVisores();

		return null;
	}

	public void guardarInfoImagenes() throws Exception
	{
		try 
		{
			System.out.println("GUARDANDO "+misFotos.size());
			FileOutputStream fileOut=new FileOutputStream(PATH_ESTADO+peer_name+"/salvado.obj");
			ObjectOutputStream salida=new ObjectOutputStream(fileOut);
			salida.writeObject(misFotos);
			salida.writeObject(misDescargas);
			salida.writeObject(estadosRecibidos);
			salida.writeObject(mensajesEnviados);
			salida.writeObject(mensajesRecibidos);
			salida.writeObject(estadosGuardados);

			salida.close();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			throw new Exception("Imposible guargar la información de las imágenes");
		}

	}

	public void guardarInfoDescargas() throws Exception
	{
		try 
		{
			System.out.println("GUARDANDO PROGRESO DESCARGA");
			FileOutputStream fileOut=new FileOutputStream(PATH_ESTADO+peer_name+"/descargas.obj");
			ObjectOutputStream salida=new ObjectOutputStream(fileOut);
			salida.writeObject(descargas);
			salida.close();
		} 
		catch (IOException ex) 
		{
			ex.printStackTrace();
			throw new Exception("Imposible guargar la información de las imágenes");
		}
	}

	
	//------------------------------------------------------------------------------------------------------------------------------
	// Exclusion Mutua
	//------------------------------------------------------------------------------------------------------------------------------
	
	public boolean pedirPermidoAPeers() throws KahuuException
	{
		estaOcupado = false;

		try 
		{
			byte[] buffer = PEDIDO.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			multicastSocket.send(packet);

			Thread.sleep(100);

			return estaOcupado;
		} 
		catch (IOException e) 
		{
			throw new KahuuException(e.getMessage());
		}
		catch (InterruptedException e)
		{
			throw new KahuuException(e.getMessage());
		}
	}
	
	public boolean getOcupado()
	{
		return estaOcupado;
	}

	public void setOcupadoOcupado()
	{
		estaOcupado = true;
	}

	
	//------------------------------------------------------------------------------------------------------------------------------
	// Similares
	//------------------------------------------------------------------------------------------------------------------------------
	
	public ArrayList<Imagen> compararConFoto(File fotoRecibida) throws IOException
	{
		NaiveSimilarityFinder similar = new NaiveSimilarityFinder(fotoRecibida,this);
		ArrayList<Imagen> imagenesParecidas = similar.darImagenes();

		return imagenesParecidas;
	}
	
	public Imagen darImagenSimilar(int ancho)
	{

		for (int i = 0; i < misFotos.size(); i++) {

			Imagen im=misFotos.get(i);

			System.out.println("COMPARANDO CON IMAGEN:"+AdministradorJXTA.RUTA_FOTO+peer_name+"/"+im.getNombreFoto());

			ImageIcon a2 = new ImageIcon(AdministradorJXTA.RUTA_FOTO+peer_name+"/"+im.getNombreFoto());
			System.out.println(+a2.getIconWidth()+"-"+ancho);
			if(a2.getIconWidth()==ancho)
			{
				System.out.println("DEVOLVIENDO IMAGEN");
				return im;
			}

		}

		System.out.println("NO SE ENCONTRO IMAGEN SIMILAR");
		return null;
	}
	
	public void buscarFotosSimilares(File foto) throws KahuuException
	{	
		//Aqui prueba cambio
		if(!pedirPermidoAPeers())
		{
			estaOcupado = true;
			//fotosParecidas.clear();
			//this.archivoComprar = foto;
			//buscarTodosLosPeers();


			fotosParecidas.clear();
			try {

				//FileInputStream fis = new FileInputStream();
				ImageIcon ima=new ImageIcon(foto.getPath());
				String ancho=""+ima.getIconWidth();

				byte[] buffer = (SIMILITUD+":"+peer_name+":"+ancho).getBytes();
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				multicastSocket.send(packet);

				/*	int lectura=1;
			Long tamano=foto.length();
			long tamBuffer=tamano-2000;
			int tamanoReal=2000;
			String tama=""+tamano.toString();
			System.out.println("TAMANO ARCHIVO: "+tama);

			buffer = (tama).getBytes();
			packet = new DatagramPacket(buffer, buffer.length);
			multicastSocket.send(packet);

			Thread.sleep(5000);

			if(tamBuffer<0)
			{
				tamanoReal=fis.available();
			}
			else
			{
				tamano=tamano-2000;
			}


			buffer = new byte[tamanoReal];
			lectura= fis.read(buffer);

			boolean termino=false;

			while(lectura!=-1&&!termino)
			{
				System.out.println("ENVIANDO BYTES IMAGEN PARECIDA");
				System.out.println("BYTES RESTANTES:"+fis.available());
				packet = new DatagramPacket(buffer, buffer.length);
				multicastSocket.send(packet);
				//String st=new String(buffer);
				//System.out.println(st);
				Thread.sleep(3000);

				tamBuffer=tamano-2000;

				if(tamBuffer<0)
				{
					tamanoReal=fis.available();
				}
				else
				{
					tamano=tamano-2000;
				}
				if(fis.available()==0)
				{
					termino=true;
				}

				buffer=new byte[tamanoReal];
				lectura= fis.read(buffer);

			}

			fis.close();*/
				//estaOcupado = false;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{

			throw new KahuuException("Sistema está ocupado.");
		}

	}
	
	public void agregarFotoParecida(Imagen i)
	{
		fotosParecidas.add(i);
	}
	
	public void agregarListaFotoParecida(ArrayList<Imagen> lista)
	{
		fotosParecidas.addAll(lista);
	}
	
	public void clearFotosParecidas()
	{
		fotosParecidas.clear();
	}
	
	public void notificarMasParecidas()
	{   	
		for (int i = 0; i < fotosParecidas.size() -1; i++) 
		{   		
			Imagen menor = fotosParecidas.get(i);
			int cual = i;

			for (int j = i + 1; j < fotosParecidas.size(); j++) 
			{
				Imagen b = fotosParecidas.get(j);

				if(menor.getParecido() > b.getParecido())
				{
					menor = b;
					cual = j;
				}
			}

			fotosParecidas.set(cual, fotosParecidas.get(i));
			fotosParecidas.set(i, menor);	
		}

		interfaz.refrescarMasParecidas(fotosParecidas);
		estaOcupado = false;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Estados
	//------------------------------------------------------------------------------------------------------------------------------
	
	public boolean guardarEstado( ) throws KahuuException
	{
		try 
		{		
			if(estadoGuardado!=true)
			{
				estadoGuardado = true;

				//Estado es = new Estado(estado, historial, peer_name);

				//Se manda el Estado y el historial del canal

				//guardarInfoImagenes();
				interfaz.vaciarEstadosRecibidos();

				estadosRecibidos.clear();

				int index=estadosGuardados.size()-1;

				if(index>=0)
				{
					Estado ultimo=estadosGuardados.get(index);

					EstadoRecibido miMensaje=new EstadoRecibido(peer_name, ultimo.getTipoEstado(), ultimo.getMensajeSolicitado());

					String mensaje = GUARDAR_ESTADO + peer_name+"-"+"ESTADO:"+
							ultimo.getTipoEstado()+":"+ultimo.getMensajeSolicitado()+"-MENSAJES ENVIADOS:"
							+mensajesEnviados.size();

					for (int i = 0; i < mensajesEnviados.size(); i++)
					{
						Mensaje m=mensajesEnviados.get(i);
						mensaje+=":"+m.getTipoMensaje()+":"+m.getPeerSalida()+":"+m.getPeerLlegada()+":"+m.getMensaje()+":"+m.getNumeroPaquete();

						miMensaje.agregarMensajeEnviado(m);
					}

					mensaje+="-MENSAJES RECIBIDOS:"+mensajesRecibidos.size();

					for (int i = 0; i < mensajesRecibidos.size(); i++) 
					{

						Mensaje m=mensajesRecibidos.get(i);
						mensaje+=":"+m.getTipoMensaje()+":"+m.getPeerSalida()+":"+m.getPeerLlegada()+":"+m.getMensaje()+":"+m.getNumeroPaquete();
						miMensaje.agregarMensajeRecibido(m);
					}

					estadosRecibidos.add(miMensaje);
					interfaz.agregarEstadoRecibido(miMensaje);
					//String mensaje  ="";

					System.out.println("MENSAJE A ENVIAR:" + mensaje);
					System.out.println("NUMERO BYTES" + mensaje.getBytes().length);

					byte[] buffer = mensaje.getBytes();
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

					multicastSocket.send(packet);

					Thread.sleep(1000);
				}
				else
				{
					String mens= GUARDAR_ESTADO + peer_name+"-"+"ESTADO:NULL:NULL-MENSAJES ENVIADOS:0-MENSAJES RECIBIDOS:0";
					EstadoRecibido miMensaje=new EstadoRecibido(peer_name, "NULL","NULL");

					byte[] buffer = mens.getBytes();
					DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

					multicastSocket.send(packet);
					estadosRecibidos.add(miMensaje);
					//estadoGuardado=true;
					//interfaz.mostrarMensaje("No ha realizado ninguna operación, su estado es el estado inicial del programa.");
				}
			}

			return estadoGuardado;
		} 
		catch (IOException e) 
		{
			throw new KahuuException(e.getMessage());
		}
		catch (Exception e)
		{
			throw new KahuuException(e.getMessage());
		}
	}

	public void procesarEstados( )
	{
		System.out.println("RECIBIDOS ESTADOS:" + estadosRecibidos.size());
		estadoGuardado = false;


		if(estadosRecibidos.size() == 3)
		{
			System.out.println("Listo Para Procesar Estados!");
			//Compara los 3 historiales de la lista estados y deja las parejas que no son iguales

			//estadoGuardado = false;
			//Comparando datos recibidos
			System.out.println("ESTADO OBTENIDOS ANTES:" +estadosRecibidos.toString());

			for (int i = 0; i <estadosRecibidos.size(); i++) {
				EstadoRecibido e1=estadosRecibidos.get(i);

				for (int j = 0; j < estadosRecibidos.size(); j++) {

					EstadoRecibido e2 =estadosRecibidos.get(j);
					if(j!=i)
					{
						e1.compararMensajes(e2);
					}				
				}
			}

			System.out.println("ESTADO OBTENIDOS:" +estadosRecibidos.toString());

			try 
			{				
				guardarInfoImagenes();			
			} 
			catch (Exception e)
			{
				e.printStackTrace();

			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void cargarEstado() throws Exception 
	{
		try 
		{
			File fl=new File(PATH_ESTADO+peer_name+"/salvado.obj");
			if(fl.exists())
			{
				System.out.println("CARGANDO");
				FileInputStream fileIn = new FileInputStream(fl);
				ObjectInputStream entrada=new ObjectInputStream(fileIn);
				misFotos=(ArrayList<Imagen>) entrada.readObject();
				misDescargas=(ArrayList<Imagen>) entrada.readObject();
				estadosRecibidos=(ArrayList<EstadoRecibido>)entrada.readObject();
				mensajesEnviados=(ArrayList<Mensaje>)entrada.readObject();
				mensajesRecibidos=(ArrayList<Mensaje>)entrada.readObject();
				estadosGuardados=(ArrayList<Estado>)entrada.readObject();
				entrada.close();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();

			misDescargas = new ArrayList<Imagen>();
			misFotos = new ArrayList<Imagen>();
			estadosRecibidos=new ArrayList<EstadoRecibido>();

			mensajesEnviados=new ArrayList<Mensaje>();

			mensajesRecibidos=new ArrayList<Mensaje>();
			estadosGuardados=new ArrayList<Estado>();
		}
	}

	@SuppressWarnings("unchecked")
	public void cargarEstadoDescargas() throws Exception 
	{
		try 
		{
			File fl=new File(PATH_ESTADO+peer_name+"/descargas.obj");
			if(fl.exists())
			{
				System.out.println("CARGANDO descargas");
				FileInputStream fileIn = new FileInputStream(fl);
				ObjectInputStream entrada=new ObjectInputStream(fileIn);
				descargas=(ArrayList<Descarga>) entrada.readObject();

				entrada.close();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();


			descargas = new ArrayList<Descarga>();
		}
	}
	
	public void agregarImagenDescargada(ImagenTem imagen) {
		try {

			this.misDescargas.add(new Imagen(imagen.getNombreFoto(), imagen.getTags(), getActualTime(), imagen.getTiempoCreacion(), imagen.getPeerAgrego(),imagen.isSensible()));	

			interfaz.actualizarVisores();

			Estado estadoActual=new Estado();
			estadoActual.setTipoEstado(Estado.AGREGANDO_FOTO_DESCARGADA);
			estadoActual.setMensajeSolicitado(imagen.getNombreFoto());

			estadosGuardados.add(estadoActual);

		} catch (KahuuException e) {
		
			interfaz.mostrarMensaje("No se pudo solicitar el tiempo de descarga");
		}
	}

	public boolean getEstadoGuardado()
	{
		return estadoGuardado;
	}
	
	public void agregarMensajeEnviado(Mensaje m) 
	{
		mensajesEnviados.add(m);
	}

	public void agregarMensajeRecibido(Mensaje m)
	{
		mensajesRecibidos.add(m);
	}

	public ArrayList<Estado> darEstados() 
	{
		return estadosGuardados;
	}

	public ArrayList<Mensaje> darMensajesEnviados()
	{
		return mensajesEnviados;
	}

	public ArrayList<Mensaje> darMensajesRecibidos()
	{
		return mensajesRecibidos;
	}

	public void actualizarPanelEstados() {
		
		interfaz.actualizarPanelEstados();
	}

	public void agregarEstadoRecibido(EstadoRecibido er) {
		estadosRecibidos.add(er);
		interfaz.agregarEstadoRecibido(er);

	}

	public void recuperarEstado() {
		try 
		{
			enviarMensajeMulticast(RECUPERAR_ESTADO);

		} catch (Exception e) {
			e.printStackTrace();
			interfaz.mostrarMensaje("Imposible cargar el estado");
		}
	}
	
	public void recuperarEstadoSinEnviarNada() {
		
		try 
		{
			cargarEstado();
			buscarDescargasIncompletas();

		} catch (Exception e) {
			
			e.printStackTrace();
			interfaz.mostrarMensaje("Imposible cargar el estado");
		}
	}

	private void buscarDescargasIncompletas() {
		
		EstadoRecibido miEstado=null;
		for (int i = 0; i <estadosRecibidos.size(); i++) {
			EstadoRecibido e1=estadosRecibidos.get(i);
			if(e1.getNombrePeer().equals(peer_name))
			{
				miEstado=e1;
			}
		}

		if(miEstado!=null)
		{
			Mensaje m1=miEstado.buscarDescargaIncompleta();

			if(m1!=null)
			{
				String mensaje=m1.getMensaje();

				System.out.println("MENSAJE DESCARGA INCOMPLETA:"+mensaje);

				try 
				{
					buscarFotos(mensaje.split(".")[0]);
					interfaz.mostrarMensaje("Se ha buscado automaticamente la foto '"+mensaje+"' que usted no pudo descargar." +
							"\n Si desea descargar la imagen por favor dirijase al panel de busqueda y descarguela nuevamente.");

				} catch (KahuuException e) {
			
					interfaz.mostrarMensaje("Usted tenia una descarga pendiente, pero la busqueda automatica no se pudo realizar.");
				}
			}
		}
	}

	public void agregarDescarga(Descarga descarga)
	{
		descargas.add(descarga);
	}

	public ArrayList<Descarga> darDescargasEnCurso() {
		return descargas;
	}

	public void eliminarDescarga(Descarga descar) {
		descargas.remove(descar);

	}

	public void continuarDescarga(Descarga descar) {

		if(descar.aunNoEstaTerminada())
		{
			ThreadClienteDescargasIncompletas thread= new ThreadClienteDescargasIncompletas(this, netPeerGroup, interfaz, descar);
			thread.start();
		}
		else
		{
			interfaz.mostrarMensaje("La descarga ya esta terminada");
		}


	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Seguridad
	//------------------------------------------------------------------------------------------------------------------------------
	
	public ManejadorEncriptar darManejadorEncriptacion()
	{
		return manejarEncriptacion;

	}

	public byte[] desencriptarFoto(byte[] byteArray) throws KahuuException 
	{
		return manejarEncriptacion.desencriptarMensajeSysmetrico(byteArray);
	}

	
	public void guardarClaves( ) throws KahuuException
	{
		try
		{		
			FileOutputStream fileOut=new FileOutputStream("data/claves/"+this.peer_name+".obj");
			ObjectOutputStream salida=new ObjectOutputStream(fileOut);
			
			salida.writeObject(manejarEncriptacion);
			
			salida.flush();
			salida.close();
			fileOut.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			throw new KahuuException("Error:"+ e.getMessage());
		}
	}
	
	public void leerClaves(String peer) throws KahuuException
	{
		try
		{
			FileInputStream fileIn = new FileInputStream("data/claves/"+peer+".obj");
			ObjectInputStream entrada=new ObjectInputStream(fileIn);
		
			manejarEncriptacion = (ManejadorEncriptar) entrada.readObject();
			
			entrada.close();
			fileIn.close();
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			manejarEncriptacion = new ManejadorEncriptar( );
			manejarEncriptacion.generarClaves(peer);
			manejarEncriptacion.asignarLLavePublicaOtro(manejarEncriptacion.darLLavePublica());
		}
	}
	//------------------------------------------------------------------------------------------------------------------------------
	// Soporte
	//------------------------------------------------------------------------------------------------------------------------------
	
	public void enviarMensajeMulticast(String mensaje)
	{
		byte[] buffer = mensaje.getBytes();
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

		try 
		{
			multicastSocket.send(packet);		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getNombrePeer() 
	{
		return peer_name;
	}
	
	public void setInterfaz(InterfazKahuuImagenes interfaz)
	{
		this.interfaz = interfaz;
	}

	public void deneterJXTA()
	{
		manager.stopNetwork();

	}
	
	public ArrayList<Imagen> getMisFotos() 
	{
		return misFotos;
	}

	public ArrayList<Imagen> getMisDescargas() 
	{
		return misDescargas;
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Pipes
	//------------------------------------------------------------------------------------------------------------------------------

	//	private void send_to_peer(String message, PipeAdvertisement pipe) 
	//	{
	//		try 
	//		{
	//			OutputPipe sender = pipe_service.createOutputPipe(pipe, 1000);
	//					
	//			Message msg = new Message();	
	//			MessageElement nombreElem = null;
	//			MessageElement esElem = null;
	//			
	//			String es = "NOMBRE";			
	//			esElem = new ByteArrayMessageElement("ES", null, es.getBytes("ISO-8859-1"), null);
	//			nombreElem = new ByteArrayMessageElement("NombreFoto", null, message.getBytes("ISO-8859-1"), null);
	//			
	//			msg.addMessageElement(esElem);
	//			msg.addMessageElement(nombreElem);
	//			
	//			sender.send(msg);		
	//			
	//		} 
	//		catch (UnsupportedEncodingException e) 
	//		{
	//			// Yepp, you want to spell ISO-8859-1 correctly
	//			e.printStackTrace();
	//		} 
	//		catch (IOException e)
	//		{
	//			e.printStackTrace();
	//		}
	//	}
	//
	//	@Override
	//	public void pipeMsgEvent(PipeMsgEvent event)
	//	{
	//		try 
	//		{
	//			OutputPipe mandar = pipe_service.createOutputPipe(crearPipeAdvertisement(event.getPipeID()), 1000);		
	//			
	//			Message msg = event.getMessage();
	//					
	//			byte[] esBytes = msg.getMessageElement("ES").getBytes(true); 
	//			String infoMensaje = new String(esBytes);
	//	
	//			
	//			if(infoMensaje.startsWith("NOMBRE"))
	//			{
	//				
	//				byte[] nombreBytes = msg.getMessageElement("NombreFoto").getBytes(true); 
	//				String nombreFoto = new String(nombreBytes);
	//				
	//				System.out.println(nombreFoto);
	//				//Enviar Foto
	//					
	//			}
	//			else 
	//			{
	//				InputStream input =  msg.getMessageElement("FOTO").getStream();
	//			}
	//		}
	//		catch (Exception e)
	//		{
	//			// You will notice that JXTA is not very specific with exceptions...
	//			e.printStackTrace();
	//		}	
	//	}

	//  public void mandarAtodosLosPeers(ArrayList<Peer> listaPeers) throws IOException
	//  {  		
	//  	for (int i = 0; i < listaPeers.size(); i++)
	//  	{
	//  		JxtaSocket socket = new JxtaSocket();
	//  		socket.connect(this.netPeerGroup, listaPeers.get(i).getPiveAdv());
	//  		
	//  		//Se le manda la foto
	//			ThreadCliente cliente = new ThreadCliente(socket.getInputStream(), socket.getOutputStream(), archivoComprar.getAbsolutePath(), ENVIAR_FOTO, this);
	//			cliente.start();				
	//		}
	//  }
	//	




}
