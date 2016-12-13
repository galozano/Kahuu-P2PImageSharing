/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kahuuFotos.threads2;


import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import kahuuFotos.mundo.AdministradorJXTA;
import net.jxta.document.AdvertisementFactory;
import net.jxta.id.IDFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.pipe.PipeID;
import net.jxta.pipe.PipeService;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.socket.JxtaServerSocket;

/**
 *
 * @author Kevin
 */
public class ThreadServidorDescargaImagen extends Thread
{
    private AdministradorJXTA principal;
    private  JxtaServerSocket serverSocket;
    public static final int PUERTO=7777;
    public final static String SOCKETIDSTR = "urn:jxta:uuid-59616261646162614E5047205032503393B5C2F6CA7A41FBB0F890173088E79404";

    public ThreadServidorDescargaImagen(AdministradorJXTA main,PeerGroup netPeerGroup, PipeAdvertisement pipeAdv) throws Exception
    {
        principal=main;

        try 
        {
           serverSocket = new JxtaServerSocket(netPeerGroup, pipeAdv, 10);
            serverSocket.setSoTimeout(0);
        } 
        catch (IOException ex) 
        {
           ex.printStackTrace();
        }
        
    }
    
    public void run()
    {
        while(true)
        {
            try {
                Socket socket=serverSocket.accept();
                if(socket!=null)
                {
                ThreadManejoDescarga man= new ThreadManejoDescarga(principal, socket);
                man.start();
                }
            
            } 
            
            catch (IOException ex) {
                Logger.getLogger(ThreadServidorDescargaImagen.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        
    }


     public static PipeAdvertisement createSocketAdvertisement() {
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
