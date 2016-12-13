package kahuuFotos.interfaz2;







import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kahuuFotos.interfaz.InterfazKahuuImagenes;

/**
 *
 * @author alvar-go@uniandes.edu.co
 */
public class PanelAgregarImagen extends JPanel implements ActionListener, Observer{

    

    public final static String AGREGAR_IMAGEN = "AGREGAR_IMAGEN";
    public final static String AGREGAR_PERSONA = "AGREGAR_PERSONA";
    public final static String AGREGAR_ARCHIVO = "CARGAR_ARCHIVO";
    public final static String ELIMINAR_PERSONA = "ELIMINAR_PERSONA";
    

    private JButton btAgregarImagen;
    private JButton btAgregarPersona;
    private JButton btAgregarArchivo;
     private JButton btEliminarPersona;
    private JTextField txNombrePersona;
    private JTextField txNombreFoto;
    private JList listaPersonas;
    private JLabel lbImagen;
    private JCheckBox jCBSensible;
    

    private InterfazKahuuImagenes principal;
    private File file;
    private ArrayList<String> personas;
 

    public PanelAgregarImagen(InterfazKahuuImagenes interfaz) {
        setLayout( new BorderLayout());
        setBorder( new TitledBorder("Agregar imagen"));
        jCBSensible=new JCheckBox("Imagen sensible");
        personas=new ArrayList<String>();
       
       
        principal = interfaz;
        btAgregarArchivo=new JButton("Cargar archivo");
        btAgregarArchivo.setActionCommand(AGREGAR_ARCHIVO);
        btAgregarArchivo.addActionListener(this);
        
        btAgregarImagen=new JButton("Agregar imagen");
        btAgregarImagen.setActionCommand(AGREGAR_IMAGEN);
        btAgregarImagen.addActionListener(this);
        
        btAgregarPersona=new JButton("Agregar persona");
        btAgregarPersona.setActionCommand(AGREGAR_PERSONA);
        btAgregarPersona.addActionListener(this);
        
         btEliminarPersona=new JButton("Eliminar persona");
        btEliminarPersona.setActionCommand(ELIMINAR_PERSONA);
        btEliminarPersona.addActionListener(this);
        
        listaPersonas= new JList();
        txNombrePersona= new JTextField();
        txNombreFoto= new JTextField();
        JLabel labelText= new JLabel("Persona:"); 
        lbImagen=new JLabel();
        
        JLabel labelText2= new JLabel("Nombre imagen:"); 
        
        
        ImageIcon ima=new ImageIcon("data/imagenes/no_image.jpg");
        lbImagen.setIcon(ima);
        
        JPanel personas=new JPanel();
        personas.setLayout(new BorderLayout());
        JPanel per= new JPanel();
        per.setLayout(new GridLayout(3,2));
        
        per.add(labelText2);
        per.add(txNombreFoto);
        
        per.add(labelText);
        per.add(txNombrePersona);
        
       
        
       per.add(btAgregarPersona);
       per.add(btEliminarPersona);
        
        
       personas.add(per,BorderLayout.NORTH);
       
       JPanel centroT=new JPanel();
       centroT.setLayout(new BorderLayout());
      
        JScrollPane panel=new JScrollPane(listaPersonas); 
        
        centroT.add(panel,BorderLayout.CENTER);
        
        centroT.add(jCBSensible,BorderLayout.SOUTH);
        personas.add(centroT,BorderLayout.CENTER);
        personas.add(btAgregarArchivo,BorderLayout.SOUTH);
        
        
        JPanel imagen=new JPanel();
        imagen.setLayout(new BorderLayout());
        //imagen.add(lbImagen,BorderLayout.CENTER);
        imagen.add(btAgregarImagen,BorderLayout.SOUTH);
        add(personas,BorderLayout.WEST);
        JScrollPane panel3=new JScrollPane(lbImagen); 
        add(panel3,BorderLayout.CENTER);
        add(imagen,BorderLayout.SOUTH);
         ///add(btAgregarArchivo,BorderLayout.SOUTH);
      
    }


    public void actionPerformed(ActionEvent e) {
       String comando = e.getActionCommand();
       if( comando.equals(AGREGAR_IMAGEN)){
          
           if(file!=null&&txNombreFoto.getText()!=null&&!txNombreFoto.equals(""))
           {
        	   String tags="";
        	   for (int i = 0; i < personas.size(); i++) {
				String temp=personas.get(i);
				if(i!=(personas.size()-1))
				tags+=temp+" ";
				else
				{
					tags+=temp;
				}
				
				
			}
        	   
        	   principal.agregarFoto(txNombreFoto.getText(), tags, file, jCBSensible.isSelected());
               file=null;
               personas=new ArrayList<String>();
               ImageIcon ima=new ImageIcon("data/imagenes/no_image.jpg");
               lbImagen.setIcon(ima);
               
               actualizarLista();
               
           }
           else{
               JOptionPane.showMessageDialog(this, "No se ha cargado ninguna imagen o no has puesto un nombre valido para la imagen", "Cargar imagen",JOptionPane.ERROR_MESSAGE);
           }
       }
       else if(comando.equals(AGREGAR_PERSONA))
       {
           
           String texto=txNombrePersona.getText();
           if(texto!=null&&!texto.equals(""))
           {
           personas.add(texto);
           txNombrePersona.setText("");
           actualizarLista();
           }
       }
       else if(comando.equals(AGREGAR_ARCHIVO))
       {
           JFileChooser choss= new JFileChooser();
           int resp=  choss.showOpenDialog(this); 
           if(resp==JFileChooser.APPROVE_OPTION)
           {
               File arch =choss.getSelectedFile();
               
               if(arch.getName().contains(".jpg")||arch.getName().contains(".png")||
                  arch.getName().contains(".JPG")||arch.getName().contains(".jpeg")||
                  arch.getName().contains(".gif")||arch.getName().contains(".GIF")||
                  arch.getName().contains(".png")     
                 )
               {
                   file=arch;
                   actualizarImagen();
               }
               else
               {
                   JOptionPane.showMessageDialog(this,  "Tipo de archivo incorrecto. Solo se admite PNG, JPG y GIF","Error", JOptionPane.ERROR_MESSAGE);
                   
               }
           }
             
       }
       
       else if(comando.equals(ELIMINAR_PERSONA))
       {
           String elemento=(String) listaPersonas.getSelectedValue();
           if(elemento!=null)
          personas.remove(elemento);
           
           actualizarLista();
       }
    }

    public void update(Observable o, Object ob) {
        
    }

    private void actualizarLista() 
    {
      listaPersonas.setListData(personas.toArray());
      listaPersonas.setSelectedIndex(0);  
    }

    private void actualizarImagen() {
          if(file!=null)
          {
        ImageIcon im= new ImageIcon(file.getPath());
        lbImagen.setIcon(im);
          }
    }


}
