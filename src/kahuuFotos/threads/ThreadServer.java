package kahuuFotos.threads;

import java.io.IOException;
import java.net.Socket;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.KahuuException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaServerSocket;

/**
 * 
 * @author gustavolozano
 *
 */
public class ThreadServer extends Thread
{
	
	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	private JxtaServerSocket serverSocket;
	
	private AdministradorJXTA admin;
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------
	
	public ThreadServer(PeerGroup netPeerGroup, PipeAdvertisement pipeAdv, AdministradorJXTA admin) throws KahuuException
	{
		this.admin = admin;
		try 
		{
			serverSocket = new JxtaServerSocket(netPeerGroup, pipeAdv);
		}
		catch (IOException e)
		{
			throw new KahuuException("Error comenzando el servidor:" + e.getMessage());
		}
	}
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Metodos
	//------------------------------------------------------------------------------------------------------------------------------
	
	
	public void run()
	{	
		while(true)
		{
			try 
			{
				Socket socket = serverSocket.accept();

				if(socket != null)
				{				
					//Therad donde se envia la foto
					ThreadCliente cliente = new ThreadCliente(socket.getInputStream(), socket.getOutputStream(),admin );
					cliente.start();
				}				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}			
		}
	}

}
