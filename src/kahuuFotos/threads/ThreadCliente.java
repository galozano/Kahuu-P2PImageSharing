 package kahuuFotos.threads;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.Imagen;

/**
 * Thread Cliente encargado de el intercambio de archivos
 * @author gustavolozano
 *
 */
public class ThreadCliente extends Thread
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------
	
	private static final String FOTO = "FOTO:";

	private static final String RECIBO_FOTO = "RECIBO_FOTO:";

	private static final String FOTO_PARECIDA = "FOTO_PARECIDA:";

	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------------------------
	
	private BufferedReader read;

	private PrintWriter write;

	private OutputStream os;

	private InputStream io;

	private int tipo;

	private String nombreFoto;

	private AdministradorJXTA admin;

	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------
	
	public ThreadCliente(InputStream is, OutputStream os, String nombreFoto, int tipo, AdministradorJXTA admin)
	{
		this.admin = admin;
		this.tipo = tipo;
		this.nombreFoto = nombreFoto;
		inicializar(is, os);
	}

	public ThreadCliente(InputStream is, OutputStream os, AdministradorJXTA admin)
	{
		this.admin = admin;
		tipo = 0;
		inicializar(is, os);
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Metodos
	//------------------------------------------------------------------------------------------------------------------------------
	
	private void inicializar(InputStream is, OutputStream os)
	{
		this.io = is;
		this.os = os;

		write = new PrintWriter(this.os, true);
		read = new BufferedReader( new InputStreamReader(this.io));
	}

	public void run()
	{
		if(tipo == 0)
		{
			try 
			{			
				String confirmacion = read.readLine();

				if(confirmacion.startsWith(FOTO))
				{				
					enviarFoto(confirmacion.split(":")[1]);
				}			
				else if(confirmacion.startsWith(RECIBO_FOTO))
				{		
					File f = recibirFotoParecida(confirmacion.split(":")[1]);	
					
					ArrayList<Imagen> listaFotoMasParecida = admin.compararConFoto(f);
						
					String listaParecidaEnviar = FOTO_PARECIDA;
					
					for (int i = 0; i < listaFotoMasParecida.size() && i < 4; i++) 
					{
						Imagen im = listaFotoMasParecida.get(i);
						listaParecidaEnviar += im.getNombreFoto() + "-" + im.getParecido() + ",";
					}
					
					write.println(listaParecidaEnviar);
					
//					Imagen fotoMasParecida = listaFotoMasParecida.get(0);
//					
//					String nombreFotoParecida = FOTO_PARECIDA + fotoMasParecida.getNombreFoto();			
//					write.println(nombreFotoParecida + ":" + fotoMasParecida.getParecido());					
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(tipo == AdministradorJXTA.RECIBIR_FOTO)
		{
			write.println(FOTO+ nombreFoto);

			try 
			{
				recibirFoto();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}		
		else if(tipo == AdministradorJXTA.ENVIAR_FOTO)
		{
			try 
			{
				write.println(RECIBO_FOTO+nombreFoto);				
				enviarFotoParecida(nombreFoto);			

				String masParecida = read.readLine();		
					
				ArrayList<Imagen> imagenesParecidasRecibidas = new ArrayList<Imagen>();
				String listaParecida = masParecida.split(":")[1];
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
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		cerrarTodo();
	}

	private void enviarFoto(String nombreFoto) throws IOException
	{	
		
		FileInputStream fis = new FileInputStream(AdministradorJXTA.RUTA_FOTO +admin.getNombrePeer()+"/" +nombreFoto);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		ObjectOutputStream oos = new ObjectOutputStream(this.os) ;
		oos.writeObject(buffer); 
		
		oos.flush();
		
		oos.close();
		fis.close();
		
		System.out.println("ENVIADA");

	}

	private void recibirFoto() throws IOException, ClassNotFoundException
	{	
		ObjectInputStream ois = new ObjectInputStream(this.io);
		byte[] buffer = (byte[])ois.readObject();
		FileOutputStream fos = new FileOutputStream(AdministradorJXTA.RUTA_DESCARGAS+admin.getNombrePeer()+"/"  + nombreFoto);
		fos.write(buffer);
		
		fos.flush();
		ois.close();	
		fos.close();

		System.out.println("RECIBIDA");
	}

	private File recibirFotoParecida(String nombre) throws IOException, ClassNotFoundException
	{	
		File f = new File("tmp/"+ nombre.substring(nombre.lastIndexOf("/") + 1) );
		
		ObjectInputStream ois = new ObjectInputStream(this.io);
		byte[] buffer = (byte[])ois.readObject();
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(buffer);
		
		fos.flush();
		ois.close();	
		fos.close();
		
		return f;
	}

	private void enviarFotoParecida(String rutaParecida) throws IOException
	{		
		File myFile = new File(rutaParecida);
		
		FileInputStream fis = new FileInputStream(myFile);
		byte[] buffer = new byte[fis.available()];
		fis.read(buffer);
		ObjectOutputStream oos = new ObjectOutputStream(this.os) ;
		oos.writeObject(buffer); 
		
		oos.flush();
		
		oos.close();
		fis.close();     
	}

	public void cerrarTodo()
	{
		try 
		{
			this.io.close();
			this.os.close();
			this.read.close();
			this.write.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
