package kahuuFotos.threads;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.Peer;
import net.jxta.peergroup.PeerGroup;
import net.jxta.socket.JxtaSocket;

/**
 * Thread encargado de hacer el metodo de fotos parecidas 
 * @author gustavolozano
 *
 */
public class ThreadFotoParecida extends Thread
{

	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------------------------
	
	private ArrayList<Peer> listaPeers;
	
	private PeerGroup netPeerGroup;
	
	private File archivoComparar;
	
	private AdministradorJXTA admin;
	
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------
	
	public ThreadFotoParecida(ArrayList<Peer> listaPeers, PeerGroup netPeerGroup, File archivo, AdministradorJXTA admin)
	{
		this.listaPeers = listaPeers;
		this.netPeerGroup = netPeerGroup;
		this.archivoComparar = archivo;
		this.admin = admin;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Metodos
	//------------------------------------------------------------------------------------------------------------------------------
	
	
	public void run()
	{
		ArrayList<ThreadCliente> threads = new ArrayList<ThreadCliente>();
		
		try
		{
			System.out.println("LISTA PEERS THREAD:" + listaPeers.size());
			
			for (int i = 0; i < listaPeers.size(); i++)
	    	{
	    		JxtaSocket socket = new JxtaSocket();
	    		socket.connect(this.netPeerGroup, listaPeers.get(i).getPiveAdv());
	    		
	    		//Se le manda la foto
				ThreadCliente cliente = new ThreadCliente(socket.getInputStream(), socket.getOutputStream(), archivoComparar.getAbsolutePath(), AdministradorJXTA.ENVIAR_FOTO, admin);
				cliente.start();	
				
				threads.add(cliente);
			}
	    	
	    	boolean finalizo = false;
	    	
	    	while(!finalizo)
	    	{
	    		finalizo = true;
	    		
	        	for (ThreadCliente th : threads)
	        	{
	        		if(th.isAlive())
	        			finalizo = false;
	        	}
	    	}
	    	
	    	admin.notificarMasParecidas();
	    	
	    	System.out.println("FINALIZARON TODOS LOS THREADS");

		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
}
