package kahuuFotos.interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * 
 * @author gustavolozano
 *
 */
public class PanelMenu extends JMenuBar implements ActionListener
{
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Constantes
    //------------------------------------------------------------------------------------------------------------------------------
	
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String AGREGAR_FOTO = "Agregar Foto";
    
    private static final String SALIR = "Salir";

    private InterfazKahuuImagenes interfaz;
    
    
    //------------------------------------------------------------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------------------------------------------------------------
    
    public PanelMenu(InterfazKahuuImagenes interfaz)
    {      
        this.interfaz = interfaz;
        
        JMenu file = new JMenu( "File");
        this.add( file );
        
        JMenuItem abrir = new JMenuItem("Abrir" );
        file.add( abrir );

        file.addSeparator( );
        
        JMenuItem exit = new JMenuItem("Salir") ;
        exit.setActionCommand(SALIR);
        exit.addActionListener(this);
        file.add( exit );
        
        JMenu foto = new JMenu( "Foto");
        this.add( foto);
        
        JMenuItem agregarFoto = new JMenuItem(AGREGAR_FOTO);        
        agregarFoto.setActionCommand( AGREGAR_FOTO );
        agregarFoto.addActionListener( this );
        foto.add( agregarFoto );
        
        
    }

    //------------------------------------------------------------------------------------------------------------------------------
    // MŽtodos
    //------------------------------------------------------------------------------------------------------------------------------
    
    @Override
    public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
      
        if(comando.equals( AGREGAR_FOTO ))
        {            
            interfaz.dialogoAgregarFoto( );
        }       
        else if(comando.equals( SALIR ))
        {            
            interfaz.dispose();
        }  
    }

    
}
