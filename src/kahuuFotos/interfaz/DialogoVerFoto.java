package kahuuFotos.interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * 
 * @author gustavolozano
 *
 */
public class DialogoVerFoto extends JDialog implements ActionListener
{	
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Constantes
    //------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CERRAR = "Cerrar";

	
    //------------------------------------------------------------------------------------------------------------------------------
    //Atributos Interfaz
    //------------------------------------------------------------------------------------------------------------------------------
	
	private JButton btnCerrar;

	public DialogoVerFoto(String path)
	{	
		setLayout(new BorderLayout());
		setBackground( Color.WHITE );
		
		ImageIcon icono = new ImageIcon( path );
		
		setSize(icono.getIconWidth(), icono.getIconHeight()+40);

		btnCerrar = new JButton(CERRAR);
		btnCerrar.addActionListener(this);
		btnCerrar.setActionCommand(CERRAR);


		JLabel imagen = new JLabel( "" );
		imagen.setIcon( icono );
		add( imagen , BorderLayout.CENTER);
		add(btnCerrar, BorderLayout.SOUTH);
		
		setLocationRelativeTo(null);
	}

    //------------------------------------------------------------------------------------------------------------------------------
    // MŽtodos
    //------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String mensaje = e.getActionCommand( );

		if(mensaje.equals( CERRAR ))
		{
			this.dispose();
		}   

	}


}
