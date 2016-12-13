package kahuuFotos.interfaz2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import kahuuFotos.interfaz.InterfazKahuuImagenes;
import kahuuFotos.mundo.Descarga;

public class PanelDescargasEnCurso extends JPanel implements ActionListener
{
	public final static String CONTINUAR = "CONTINUAR";
	public final static String ELIMINAR = "ELIMINAR";
	
	
	private JPanel panelContenedor;
	
	
	private InterfazKahuuImagenes principal;
	
	private JComboBox jcDescarga;
	
	private JButton btEliminarDescarga;
	private JButton btContinuarDescarga;
	
	public PanelDescargasEnCurso(InterfazKahuuImagenes interfaz)
	{
		this.setLayout(new BorderLayout());
		
		principal=interfaz;
		panelContenedor=new JPanel();
		panelContenedor.setLayout(new FlowLayout());
		JScrollPane pan= new JScrollPane(panelContenedor);
		
		this.add(pan,BorderLayout.WEST);
		
		
		jcDescarga= new JComboBox();
		btEliminarDescarga=new JButton("Eliminar descarga");
		btEliminarDescarga.addActionListener(this);
		btEliminarDescarga.setActionCommand(ELIMINAR);
		
		btContinuarDescarga=new JButton("Continuar descarga");
		btContinuarDescarga.addActionListener(this);
		btContinuarDescarga.setActionCommand(CONTINUAR);
		
		JPanel sur= new JPanel();
		sur.setLayout(new GridLayout(1,3));
		
		sur.add(jcDescarga);
		sur.add(btEliminarDescarga);
		sur.add(btContinuarDescarga);
		
		
		this.add(sur,BorderLayout.SOUTH);
		
	}
	
	public void actualizarPanelDescargaEnCurso()
	{
		panelContenedor.removeAll();
		
		ArrayList<Descarga> descargas=principal.darDescargasEnCurso();
		jcDescarga.removeAllItems();
		panelContenedor.setLayout(new GridLayout(descargas.size(),1));
		for (int i = 0; i < descargas.size(); i++) {
			
			Descarga des = descargas.get(i);
			jcDescarga.addItem(des);
			JPanel desc= new JPanel();
			desc.setBorder(BorderFactory.createTitledBorder("Descarga "+i));
			desc.setLayout(new GridLayout(1,2));
			
			JLabel nom= new JLabel(des.getNombreImagen());
			
			JProgressBar pro= new JProgressBar(0,100);
			pro.setValue(des.darProgreso());
			desc.add(nom);
			desc.add(pro);
			panelContenedor.add(desc);
			
			
		}
		
		panelContenedor.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String comando=arg0.getActionCommand();
		
		if(comando.equals(CONTINUAR))
		{
			
			Descarga descar=(Descarga) jcDescarga.getSelectedItem();
			if(descar!=null)
			{
				principal.mostrarMensaje("Se reiniciara la descarga");
				principal.continuarDescarga(descar);
			}
			
		}
		else if(comando.equals(ELIMINAR))
		{
			Descarga descar=(Descarga) jcDescarga.getSelectedItem();
			if(descar!=null)
			{
				principal.mostrarMensaje("Se eliminara la descarga");
				principal.eliminarDescarga(descar);
			}
		}
		
	}
	
	
	
}
