package kahuuFotos.interfaz;

import java.awt.BorderLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import kahuuFotos.interfaz2.PanelAgregarImagen;
import kahuuFotos.interfaz2.PanelDescargasEnCurso;
import kahuuFotos.interfaz2.PanelVisorImagenes;
import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.Descarga;
import kahuuFotos.mundo.Estado;
import kahuuFotos.mundo.EstadoRecibido;
import kahuuFotos.mundo.Imagen;
import kahuuFotos.mundo.ImagenTem;
import kahuuFotos.mundo.KahuuException;
import kahuuFotos.mundo.Mensaje;
import net.jxta.logging.Logging;
import de.javasoft.plaf.synthetica.SyntheticaBlackEyeLookAndFeel;

/**
 * 
 * @author gustavolozano
 *
 */
public class InterfazKahuuImagenes extends JFrame
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTabbedPane tabPanel;

	private PanelBusqueda panelBusqueda;

	private PanelDescargas panelDescargas;

	private PanelMisFotos panelMisFotos;
	
	private PanelSimilares panelSimilares;
	
	private PanelVisorImagenes panelVisorImagenesAgregadas;
	
	private PanelVisorImagenes panelVisorImagenesDescargadas;
	
	private PanelAgregarImagen panelAgregarImagen;
	
	private PanelEstados panelEstados;
	
	private PanelDescargasEnCurso panelDescargasEnCurso;

	private PanelMenu menuBar;

	private AdministradorJXTA admin;
	
	private String nombreNodo;

	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------

	public InterfazKahuuImagenes( AdministradorJXTA admin, String s )
	{
		setSize(700, 500);
		setLayout(new BorderLayout());
		setTitle("Kahuu Fotos-"+s);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		nombreNodo=s;
		this.admin = admin;
		tabPanel = new JTabbedPane( );
		
		ImageIcon iconBuscar = new ImageIcon("data/imagenes/buscar.png");
		ImageIcon iconVisor = new ImageIcon("data/imagenes/biblioteca.png");
		ImageIcon iconBiblioteca = new ImageIcon("data/imagenes/visor.png");
		ImageIcon iconAdd = new ImageIcon("data/imagenes/add.png");
		ImageIcon iconbalance = new ImageIcon("data/imagenes/balance.png");
		ImageIcon iconEstado = new ImageIcon("data/imagenes/battery.png");
		

		panelBusqueda = new PanelBusqueda( this );
		
		tabPanel.addTab( "Busqueda", iconBuscar, panelBusqueda, "Buscar imagenes" );

		panelSimilares = new PanelSimilares(this);
		tabPanel.addTab("Similares",iconbalance, panelSimilares,"Buscar por imagenes similares");	
		
		panelAgregarImagen = new PanelAgregarImagen(this);
		tabPanel.addTab( "Agregar imagen", iconAdd, panelAgregarImagen, "Agregar imagen" );
		
		//panelMisFotos = new PanelMisFotos( );
		//tabPanel.addTab( "Mis Fotos", panelMisFotos );

		//panelDescargas = new PanelDescargas();
		//tabPanel.addTab("Descargas", panelDescargas);
		
		panelVisorImagenesAgregadas = new PanelVisorImagenes(this, admin.RUTA_FOTO+nombreNodo);
		tabPanel.addTab("Visor de imagenes agregadas", iconVisor, panelVisorImagenesAgregadas, "Visor de imagenes agregadas");
		
		panelVisorImagenesDescargadas = new PanelVisorImagenes(this, admin.RUTA_DESCARGAS+nombreNodo);
		tabPanel.addTab("Visor de imagenes descargadas", iconVisor, panelVisorImagenesDescargadas, "Visor de imagenesdescargadas");
		
		
		panelEstados= new PanelEstados(this);
		tabPanel.addTab("Estados", iconEstado, panelEstados, "Estados guardados");
		
		panelDescargasEnCurso= new PanelDescargasEnCurso(this);
		tabPanel.addTab("Descargas en curso", iconEstado, panelDescargasEnCurso, "Descargas en curso");
		
		menuBar = new PanelMenu( this );   
		setJMenuBar( menuBar );
		
		actualizarPanelDescargasEnCurso();
		actualizarPanelEstados();
		

		admin.addObserver( panelBusqueda );  
		add(tabPanel, BorderLayout.CENTER);  

		setLocationRelativeTo(null); 
		
		try {
			admin.cargarEstado();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(this,e.getMessage());
		}
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Metodos
	//------------------------------------------------------------------------------------------------------------------------------

	public void refescar()
	{
		//panelDescargas.refrescar(admin.getMisDescargas());
		//panelMisFotos.refrescar(admin.getMisFotos());
	}
	
	public void refrescarMasParecidas(ArrayList<Imagen> parecidas)
	{
		panelSimilares.refresh(parecidas);
	}

	public void descargar(ImagenTem imagen)
	{
		try 
		{
			admin.descargar(imagen);
			refescar();
		} 
		catch (KahuuException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void buscarFotosSimilares(File foto)
	{
		try {
			admin.buscarFotosSimilares(foto);
		} catch (KahuuException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void buscarContenido(String nombreFoto) 
	{
		try 
		{
			admin.busquedaTags(nombreFoto);
		} catch (KahuuException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void buscarEspecifica(String nombreFoto) 
	{
		try {
			admin.buscarFotos(nombreFoto);
		} catch (KahuuException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void agregarFoto(String nombreFoto, String tags, File foto, boolean b)
	{
		try
		{
			admin.agregarFoto(nombreFoto, tags, foto,b);
			refescar();
			panelVisorImagenesAgregadas.actualizar();
		}
		catch( Exception e )
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void buscarSimilar(String nombre)
	{
		tabPanel.setSelectedIndex(0);
		this.buscarEspecifica(nombre);		
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Imagen> getMisFotos() 
	{
		return admin.getMisFotos();
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Imagen> getMisDescargas() 
	{
		return admin.getMisDescargas();
	}
	
	public void dialogoAgregarFoto()
	{
		DialogoAgregarFoto dialogo = new DialogoAgregarFoto( this );
		dialogo.setVisible( true );
	}

	@Override
	public void dispose()
	{
		try {
		admin.guardarInfoImagenes();
		admin.guardarClaves();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this,"Imposible guardar el estado del programa");
		}
		admin.deneterJXTA( );        
		super.dispose();
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// Main
	//------------------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args)
	{
		//String s = JOptionPane.showInputDialog(null, "Ingrese el nombre del nodo", "Nombre", JOptionPane.QUESTION_MESSAGE);
		System.setProperty(Logging.JXTA_LOGGING_PROPERTY, Level.OFF.toString());
		
		 try{
	            UIManager.setLookAndFeel(new SyntheticaBlackEyeLookAndFeel());
	            
	        }
	        catch(Exception e){
	            
	            
	        }
	        
	        java.awt.EventQueue.invokeLater(new Runnable() {

	            public void run() {
	               
	                 String s = JOptionPane.showInputDialog(null, "Ingrese el nombre del nodo", "Nombre", JOptionPane.QUESTION_MESSAGE);
	        try {
	        	if(s!=null&&!s.equals(""))
	        	{
	        		AdministradorJXTA admin = new AdministradorJXTA( s );
					InterfazKahuuImagenes i = new InterfazKahuuImagenes( admin,s );
					i.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					admin.setInterfaz(i);
				
					i.setVisible(true);
	        	}
	        	else
	        	{
	        		throw new Exception("Nombre invalido para el peer");
	        	}
	        	
	        } catch (Exception e) {
	        	e.printStackTrace();
	            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	                
	                
	                
	            }
	        });	
	}

	public Imagen getImagenPorId(String imagen) {
		// TODO Auto-generated method stub
		return admin.getImagenPorId(imagen);
	}

	public void actualizarVisores() {
		// TODO Auto-generated method stub
		panelVisorImagenesAgregadas.actualizar();
		panelVisorImagenesDescargadas.actualizar();
	}

	public void guardarEstado() {
		// TODO Auto-generated method stub
		try {
			admin.guardarEstado();
		} catch (KahuuException e) {
			JOptionPane.showMessageDialog(this,"Ocurrio un error al intentar guardar el estado.");
		}
		
	}

	public void recuperarEstado() {
		// TODO Auto-generated method stub
		admin.recuperarEstado();
		
	}

	public void actualizarBarraDeProgreso(int pro) {
		panelBusqueda.actualizarBarraProgreso(pro);
		
	}

	public void mostrarMensaje(String string) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(this,string);
	}
	
	public ArrayList<Estado> darEstados()
	{
		return admin.darEstados();
	}
	
	public ArrayList<Mensaje> darMensajesEnviados()
	{
		return admin.darMensajesEnviados();
	}
	
	public ArrayList<Mensaje> darMensajesRecibidos()
	{
		return admin.darMensajesRecibidos();
	}
	
	public void actualizarPanelEstados()
	{
		panelEstados.actualizarPanel();
	}

	public void agregarEstadoRecibido(EstadoRecibido er) {
		// TODO Auto-generated method stub
		panelEstados.agregarRecibidos(er);
		
	}

	public void vaciarEstadosRecibidos() {
		// TODO Auto-generated method stub
		panelEstados.limpiarLista();
	}

	public ArrayList<Descarga> darDescargasEnCurso() {
		// TODO Auto-generated method stub
		return admin.darDescargasEnCurso();
	}
	
	
	public void actualizarPanelDescargasEnCurso()
	{
		panelDescargasEnCurso.actualizarPanelDescargaEnCurso();
	}

	public void eliminarDescarga(Descarga descar) {
		// TODO Auto-generated method stub
		admin.eliminarDescarga(descar);
		actualizarPanelDescargasEnCurso();
	}

	public void continuarDescarga(Descarga descar) {
		// TODO Auto-generated method stub
		admin.continuarDescarga(descar);
	}

	public byte[] desencriptarFoto(byte[] byteArray) throws KahuuException {
		// TODO Auto-generated method stub
		return admin.desencriptarFoto(byteArray);
	}
}