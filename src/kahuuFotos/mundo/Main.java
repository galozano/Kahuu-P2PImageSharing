package kahuuFotos.mundo;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import net.jxta.logging.Logging;
import be.ac.luc.vdbergh.ntp.NtpConnection;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.setProperty(Logging.JXTA_LOGGING_PROPERTY, Level.OFF.toString());
		
		
		try
		{
			NtpConnection ntp = new NtpConnection(InetAddress.getByName("pool.ntp.org"));			
			System.out.println(ntp.getTime().toString());
		} 
		catch (SocketException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		try 
//		{
//			String random = "" + new Random().nextInt(1000000); 
//			
//			System.out.println(random);
//			
//			AdministradorJXTA  admin = new AdministradorJXTA( );
//			
//			admin.agregarFoto("FOTO1" + random, "FOTO1", null);
//			admin.agregarFoto("FOTO2" + random, "FOTO1", null);
//			admin.agregarFoto("FOTO3" + random, "FOTO1", null);
//			admin.agregarFoto("FOTO4" + random, "FOTO1", null);
//			admin.agregarFoto("FOTO5" + random, "FOTO1", null);
//			admin.agregarFoto("FOTO6" + random, "FOTO1", null);
//			
//			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//			
//			while(true)
//			{
//				System.out.println("ESCRIBA LA FOTO:");
//				
//				String busqueda = br.readLine();
//				
//				System.out.println("BUSQUEDA:" + busqueda);
//				
//				admin.buscarFotos(busqueda);		
//				
//				Thread.sleep(10000);
//			}
//		} 
//		catch (Exception e) 
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		


	}

}
