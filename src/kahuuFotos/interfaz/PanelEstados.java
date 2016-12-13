package kahuuFotos.interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kahuuFotos.mundo.EstadoRecibido;
import kahuuFotos.mundo.Mensaje;

public class PanelEstados extends JPanel implements ActionListener, ListSelectionListener
{
	//--------------------------------------------------
	// CONSTANTES
	//--------------------------------------------------
	public static final String GUARDAR="GUARDAR";
	public static final String RECUPERAR="RECUPERAR";
	
	//--------------------------------------------------
	// ATRIBUTOS
	//--------------------------------------------------
	
	private JButton btGuardarEstado;
	private JButton btRecuperarEstado;
	private InterfazKahuuImagenes principal;
	
	private JList listaEstados;
	private JList listaMensajesEnviados;
	private JList listaMensajesRecibidos;
	
	
	private JList listaEstadosRecibidos;
	private JList listaMensajesEnviadosRecibidos;
	private JList listaMensajesRecibidosRecibidos;
	
	
	private ArrayList<EstadoRecibido> estadosRecibidos; 
	
	//--------------------------------------------------
	// CONSTRUCTOR
	//--------------------------------------------------
	
	public PanelEstados(InterfazKahuuImagenes interfaz)
	{
		this.setLayout(new GridLayout(1,2));
		
		estadosRecibidos=new ArrayList<EstadoRecibido>();
		
		listaEstados= new JList();
		JScrollPane panel=new JScrollPane(listaEstados); 
		panel.setBorder(BorderFactory.createTitledBorder("Estados"));
		listaMensajesEnviados= new JList();
		JScrollPane panel2=new JScrollPane(listaMensajesEnviados); 
		panel2.setBorder(BorderFactory.createTitledBorder("Mensajes enviados"));
		listaMensajesRecibidos= new JList();
		JScrollPane panel3=new JScrollPane(listaMensajesRecibidos); 
		panel3.setBorder(BorderFactory.createTitledBorder("Mensajes Recibidos"));
		JPanel centro= new JPanel();
		centro.setLayout(new GridLayout(3,1));
		centro.add(panel);
		centro.add(panel2);
		centro.add(panel3);
		
		listaEstadosRecibidos=new JList();
		listaEstadosRecibidos.addListSelectionListener(this);
		JScrollPane panel4=new JScrollPane(listaEstadosRecibidos); 
		panel4.setBorder(BorderFactory.createTitledBorder("Estados Recibidos"));
		listaMensajesEnviadosRecibidos=new JList();
		JScrollPane panel5=new JScrollPane(listaMensajesEnviadosRecibidos); 
		panel5.setBorder(BorderFactory.createTitledBorder("Mensajes Enviados Recibidos "));
		listaMensajesRecibidosRecibidos=new JList();
		JScrollPane panel6=new JScrollPane(listaMensajesRecibidosRecibidos); 
		panel6.setBorder(BorderFactory.createTitledBorder("Mensajes Recibidos Recibidos"));
		
		
		JPanel oriente= new JPanel();
		
		oriente.setLayout(new GridLayout(3,1));
		oriente.add(panel4);
		oriente.add(panel5);
		oriente.add(panel6);
		
		
		
		
		
		this.add(centro);
		this.add(oriente);
		
		
		
		principal=interfaz;
		
		btGuardarEstado=new JButton("GUARDAR EL ESTADO");
		btGuardarEstado.setActionCommand(GUARDAR);
		btGuardarEstado.addActionListener(this);
		
		btRecuperarEstado= new JButton("RECUPERAR EL ESTADO");
		btRecuperarEstado.setActionCommand(RECUPERAR);
		btRecuperarEstado.addActionListener(this);
		//Panel de botones ubicado en el sur
		JPanel sur= new JPanel();
		sur.setLayout(new GridLayout(1,2));
		sur.add(btGuardarEstado);
		sur.add(btRecuperarEstado);
		
		
		//Agregando al panel principal
		this.add(sur, BorderLayout.SOUTH);
		
	}

	
	//--------------------------------------------------
	// METODOS
	//--------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String comando=arg0.getActionCommand();
		
		if(comando.equals(GUARDAR))
		{
			principal.guardarEstado();
		}
		else if(comando.equals(RECUPERAR))
		{
			principal.recuperarEstado();
		}
	}
	
	public void actualizarPanel()
	{
		
		listaEstados.setListData(principal.darEstados().toArray());
		listaEstados.setSelectedIndex(0); 
	      
	      
	      listaMensajesEnviados.setListData(principal.darMensajesEnviados().toArray());
	      listaMensajesEnviados.setSelectedIndex(0); 
	      
	      
	      listaMensajesRecibidos.setListData(principal.darMensajesRecibidos().toArray());
	      listaMensajesRecibidos.setSelectedIndex(0); 
		
	}
	
	
	public void agregarRecibidos(EstadoRecibido estado)
	{
		estadosRecibidos.add(estado);
		
		listaEstadosRecibidos.setListData(estadosRecibidos.toArray());
		listaEstadosRecibidos.setSelectedIndex(0);
	}
	
	public void limpiarLista()
	{
		estadosRecibidos=new ArrayList<EstadoRecibido>();
	}


	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
		
		if( listaEstadosRecibidos.getSelectedValue( ) != null )
        {
            EstadoRecibido er = (EstadoRecibido)listaEstadosRecibidos.getSelectedValue( );
            
           ArrayList<Mensaje> enviado= er.getListaMensajesEnviados();
           ArrayList<Mensaje> recibido= er.getListaMensajesRecibidos();
           
           listaMensajesEnviadosRecibidos.setListData( enviado.toArray() );
           listaMensajesEnviadosRecibidos.setSelectedIndex( 0 );
           
           listaMensajesRecibidosRecibidos.setListData( recibido.toArray());
           listaMensajesRecibidosRecibidos.setSelectedIndex( 0 );
           
            
        }
		
	}
	
	
}
