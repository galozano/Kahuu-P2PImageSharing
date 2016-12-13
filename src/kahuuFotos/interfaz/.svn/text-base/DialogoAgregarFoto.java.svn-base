package kahuuFotos.interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * 
 * @author gustavolozano
 *
 */
public class DialogoAgregarFoto extends JDialog implements ActionListener
{
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Constantes
    //------------------------------------------------------------------------------------------------------------------------------
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String AGREGAR = "AGREGAR";
    
	private static final String CANCELAR = "Cancelar";
	
	private static final String FOTO = "Foto";
	
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Atributos Interfaz
    //------------------------------------------------------------------------------------------------------------------------------
	
    private JTextField txtNombreFoto;
    
    private JTextField txtTag;
    
    private JButton btnAgregar;
    
    private JButton btnCancelar;
    
    private JButton btnFotoChosser;
    
    //------------------------------------------------------------------------------------------------------------------------------
    // Atributos
    //------------------------------------------------------------------------------------------------------------------------------
    
    private InterfazKahuuImagenes interfaz;
    
    private File tempFile;
    
    
    //------------------------------------------------------------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------------------------------------------------------------
    
    public DialogoAgregarFoto(InterfazKahuuImagenes interfaz)
    {
        this.interfaz = interfaz;
      
        setLayout( new GridLayout(4,2) );
        setSize(200,150);
        
        txtNombreFoto = new JTextField( );
        txtTag = new JTextField( );
        
        btnAgregar = new JButton(AGREGAR);
        btnAgregar.setActionCommand( AGREGAR );
        btnAgregar.addActionListener( this );
        
        btnCancelar = new JButton(CANCELAR);
        btnCancelar.setActionCommand(CANCELAR);
        btnCancelar.addActionListener(this);
        
        btnFotoChosser = new JButton(FOTO);
        btnFotoChosser.setActionCommand(FOTO);
        btnFotoChosser.addActionListener(this);
              
        add(new JLabel("Nombre Foto:"));
        add(txtNombreFoto);
        
        add(new JLabel("Tag:"));
        add(txtTag);
        
        add(new JLabel("Elige Foto:"));
        add(btnFotoChosser);
        add(btnAgregar);
        add(btnCancelar);
        
        setLocationRelativeTo(interfaz);
        
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // MŽtodos
    //------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String mensaje = e.getActionCommand( );
        
        if(mensaje.equals( AGREGAR ))
        {
        	if(!txtNombreFoto.getText().equals("") && !txtTag.getText( ).equals("") && tempFile != null )
        	{
        		interfaz.agregarFoto( txtNombreFoto.getText( ), txtTag.getText( ) , tempFile,false);
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(null, "Ingrese todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
        	}        	
            
        }   
        else if(mensaje.equals( CANCELAR ))
        {
            this.dispose();
        }  
        else if(mensaje.equals( FOTO ))
        {
        	JFileChooser chooser = new JFileChooser("./");

        	chooser.setFileFilter(new ImageFilter());
        	
        	int returnVal = chooser.showOpenDialog(this);
        	if(returnVal == JFileChooser.APPROVE_OPTION) 
        	{
        		File file = chooser.getSelectedFile();
        		this.tempFile = file;
        	}
        } 
    }  
}
