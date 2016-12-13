package kahuuFotos.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import kahuuFotos.imagens.JPEGImageFileFilter;
import kahuuFotos.mundo.Imagen;

/**
 * 
 * @author gustavolozano
 *
 */
public class PanelSimilares extends JPanel implements ActionListener, ListSelectionListener
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String[] ENCABEZADOS = {"Nombre", "Similitud"};
	
	private final static String BUSCAR = "Buscar";
	
	private final static String COMPARAR_FOTO = "Comparar";

	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos Interfaz
	//------------------------------------------------------------------------------------------------------------------------------

	private JTable table;

	private DefaultTableModel modelo;

	private JButton btnBuscar;
	
	private JButton btnComparar;

	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	private InterfazKahuuImagenes principal;

	private int seleccionado;
	
	private ArrayList<Imagen> listaSimilares;
	
	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------
	
	public PanelSimilares(InterfazKahuuImagenes interfaz)
	{
		
		setLayout( new BorderLayout());
		this.principal = interfaz;
		seleccionado = -1;
		modelo = new DefaultTableModel(ENCABEZADOS, 0);

		listaSimilares = new ArrayList<Imagen>();
		
		table = new JTable();
		table.setModel(modelo);
		
		// Handle the listener
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener( this );

		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		btnBuscar = new JButton(BUSCAR);
		btnBuscar.setActionCommand(BUSCAR);
		btnBuscar.addActionListener(this);
		
		btnComparar = new JButton(COMPARAR_FOTO);
		btnComparar.setActionCommand(COMPARAR_FOTO);
		btnComparar.addActionListener(this);

		JPanel panelAbajo = new JPanel( );
		panelAbajo.setLayout( new GridLayout(0,2) );
		panelAbajo.add(btnBuscar);
		panelAbajo.add(btnComparar);
		
		add(scroll, BorderLayout.CENTER);
		add(panelAbajo , BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String comando = e.getActionCommand();

		if( comando.equals( BUSCAR ))
		{
			if(seleccionado >=0)
			{
				String nombreFoto = listaSimilares.get(seleccionado).getNombreFoto();
				String sinExt = nombreFoto.substring(0, nombreFoto.lastIndexOf("."));
				
				System.out.println(sinExt);
				
				principal.buscarSimilar(sinExt);
			}
		}
		else if( comando.equals( COMPARAR_FOTO ) )
		{
			JFileChooser chooser = new JFileChooser("./");

			chooser.setFileFilter(new JPEGImageFileFilter());

			int returnVal = chooser.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) 
			{
				File file = chooser.getSelectedFile();
				principal.buscarFotosSimilares(file);
			}
		}
		
	}

	@Override
	public void valueChanged(ListSelectionEvent event) 
	{
		if( event.getSource() == table.getSelectionModel() && event.getFirstIndex() >= 0 )
		{	
			seleccionado = table.getSelectedRow();	
		}			
	}
	
	public void refresh(ArrayList<Imagen> listaImagenes)
	{
		this.listaSimilares = listaImagenes;
		modelo = new DefaultTableModel(ENCABEZADOS, 0);

		Object[] fila;
		for(Imagen n: listaImagenes)
		{            	
			fila = new Object[2];
			fila[0] = n.getNombreFoto();
			fila[1] = n.getParecido();
			modelo.addRow(fila);
		}
		table.setModel(modelo);
	}
}
