/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kahuuFotos.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import kahuuFotos.mundo.AdministradorJXTA;
import kahuuFotos.mundo.ImagenTem;

/**
 *
 */
public class PanelBusqueda extends JPanel implements ActionListener, Observer, ListSelectionListener
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final static String[] ENCABEZADOS = {"Nombre", "Tags", "Tiempo"};

	private final static String BUSCAR = "Buscar";

	private final static String DESCARGAR = "Descargar";

	private final static String CONTENIDO = "Contenido";

	private final static String ESPECIFICA = "Especifica";


	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos Interfaz
	//------------------------------------------------------------------------------------------------------------------------------

	private JTable table;

	private DefaultTableModel modelo;

	private JButton btnBuscar;

	private JButton btnDescargar;

	private JComboBox comboBusqueda;

	private JTextField txtBusqueda;
	
	private JProgressBar jpProgreso;

	//------------------------------------------------------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------------------------------------------------------

	private InterfazKahuuImagenes principal;

	private ArrayList<ImagenTem> busqueda;

	private int seleccionado;

	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------

	public PanelBusqueda(InterfazKahuuImagenes interfaz) 
	{
		
		jpProgreso= new JProgressBar(0, 100);
		
		setLayout( new BorderLayout());
		principal = interfaz;

		modelo = new DefaultTableModel(ENCABEZADOS, 0);

		table = new JTable();
		table.setModel(modelo);
		// Handle the listener
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener( this );

		JScrollPane scroll = new JScrollPane(table);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);

		btnBuscar = new JButton(BUSCAR);
		btnBuscar.setActionCommand(BUSCAR);
		btnBuscar.addActionListener(this);

		btnDescargar = new JButton(DESCARGAR);
		btnDescargar.setActionCommand(DESCARGAR);
		btnDescargar.addActionListener(this);

		comboBusqueda = new JComboBox();
		comboBusqueda.addItem(CONTENIDO);
		comboBusqueda.addItem(ESPECIFICA);

		txtBusqueda = new JTextField( );

		JPanel panelArriba = new JPanel( );
		panelArriba.setLayout( new BorderLayout( ) );

		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridLayout(0,2));
		panelBotones.add(comboBusqueda);
		panelBotones.add(btnBuscar);

		panelArriba.add( txtBusqueda, BorderLayout.CENTER );
		panelArriba.add( panelBotones, BorderLayout.EAST );

		add(panelArriba, BorderLayout.NORTH);
		
		//Panel sur
		JPanel sur= new JPanel();
		sur.setLayout(new GridLayout(2,1));
		sur.add(jpProgreso);
		sur.add(btnDescargar);
		add(sur , BorderLayout.SOUTH);
	}

	//------------------------------------------------------------------------------------------------------------------------------
	// MŽtodos
	//------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String comando = e.getActionCommand();

		if( comando.equals( BUSCAR ))
		{
			String escogida = (String)comboBusqueda.getSelectedItem();

			if(escogida.equals(CONTENIDO))
			{
				principal.buscarContenido( txtBusqueda.getText( ) );
			}
			else if(escogida.equals(ESPECIFICA))
			{
				principal.buscarEspecifica( txtBusqueda.getText( ) );
			}

		}
		else if( comando.equals( DESCARGAR ) )
		{
			ImagenTem i = busqueda.get(seleccionado);
			if(i!=null)
			{
			principal.descargar(i);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna imagen");
			}
		}    
	}


	@Override
	public void update(Observable o, Object ob) 
	{
		if( o instanceof AdministradorJXTA)
		{
			@SuppressWarnings("unchecked")
			ArrayList<ImagenTem> lista = (ArrayList<ImagenTem>)ob;
			busqueda = lista;
		
			modelo = new DefaultTableModel(ENCABEZADOS, 0);

			Object[] fila;
			for(ImagenTem n: lista)
			{            	
				fila = new Object[3];
				fila[0] = n.getNombreFoto();
				fila[1] = n.getTags( );
				fila[2] = n.getTiempoCreacion();
				modelo.addRow(fila);
			}
			
			table.setModel(modelo);
		}	   

	}

	@Override
	public void valueChanged(ListSelectionEvent event) 
	{		

		if( event.getSource() == table.getSelectionModel() && event.getFirstIndex() >= 0 )
		{	
			// Get the data model for this table
			//TableModel model = table.getModel();

			seleccionado = table.getSelectedRow();

			// Determine the selected item
			//String string = (String)model.getValueAt(table.getSelectedRow(),table.getSelectedColumn() );

		}	
	}

	public void actualizarBarraProgreso(int pro)
	{
		jpProgreso.setValue(pro);
	}

}
