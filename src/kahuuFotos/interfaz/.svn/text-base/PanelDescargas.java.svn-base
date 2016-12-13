package kahuuFotos.interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.Imagen;

/**
 * 
 * @author gustavolozano
 *
 */
public class PanelDescargas extends JPanel implements ListSelectionListener, ActionListener
{ 
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Constantes
    //------------------------------------------------------------------------------------------------------------------------------
    
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final static String[] ENCABEZADOS = {"Nombre", "Tags", "Tiempo", "Tiempo Descarga"};
    
    private static final String VER_FOTO = "Ver Foto";
    
    
    //------------------------------------------------------------------------------------------------------------------------------
    //Atributos Interfaz
    //------------------------------------------------------------------------------------------------------------------------------
    
    private JButton btnVerFoto;
    
    private JTable tabla;

    private DefaultTableModel modelo;
    
    //------------------------------------------------------------------------------------------------------------------------------
    //Atributos
    //------------------------------------------------------------------------------------------------------------------------------
    
    private int seleccionado;

    private ArrayList<Imagen> imagenes;
   
    //------------------------------------------------------------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------------------------------------------------------------
    
    public PanelDescargas()
    {
        TitledBorder title = BorderFactory.createTitledBorder("Descargas");       
        setBorder( title );
        
        setLayout(new BorderLayout());
        setSize(10000, 10000);
        
        seleccionado = -1;
        modelo = new DefaultTableModel(ENCABEZADOS, 0);
        tabla = new JTable();
        tabla.setModel(modelo);
        
        // Handle the listener
        ListSelectionModel selectionModel = tabla.getSelectionModel();
        selectionModel.addListSelectionListener( this );
        
        btnVerFoto = new JButton(VER_FOTO);
        btnVerFoto.addActionListener(this);
        btnVerFoto.setActionCommand(VER_FOTO);

        imagenes = new ArrayList<Imagen>();
        
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scroll, BorderLayout.CENTER);    
        
        add(btnVerFoto, BorderLayout.SOUTH);
    }
    
    //------------------------------------------------------------------------------------------------------------------------------
    // MŽtodos
    //------------------------------------------------------------------------------------------------------------------------------
    
    public void refrescar(ArrayList<Imagen> imagenes)
	{
		 modelo = new DefaultTableModel(ENCABEZADOS, 0);
         
		 this.imagenes = imagenes;
		 
         String[] fila;
         for(Imagen n: imagenes)
         {
             fila = new String[4];
             fila[0] = n.getNombreFoto( );
             fila[1] = n.getTags( );
             fila[2] = n.getTiempoCreacion();
             fila[3] = n.getTiempoDescarga();
             modelo.addRow(fila);
         }
         
         tabla.setModel(modelo);	
	}

	@Override
	public void valueChanged(ListSelectionEvent event)
	{
		if( event.getSource() == tabla.getSelectionModel() && event.getFirstIndex() >= 0 )
		{	
			// Get the data model for this table
			//TableModel model = table.getModel();
	
			seleccionado = tabla.getSelectedRow();

			// Determine the selected item
			//String string = (String)model.getValueAt(table.getSelectedRow(),table.getSelectedColumn() );
			
		}	
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String comando = e.getActionCommand();

		if( comando.equals( VER_FOTO ))
		{
			if(seleccionado >=0)
			{
				String path = AdministradorJXTA.RUTA_DESCARGAS +imagenes.get(seleccionado).getNombreFoto();
				
				DialogoVerFoto dialogo = new DialogoVerFoto(path);
				dialogo.setVisible(true);
			}
		}	
	}
    
}
