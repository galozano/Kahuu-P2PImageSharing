package kahuuFotos.interfaz2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;

import kahuuFotos.filebrowser.PanelFileExplorer;
import kahuuFotos.interfaz.InterfazKahuuImagenes;
import kahuuFotos.mundo.Imagen;
import kahuuFotos.mundo.KahuuException;

/**
 *
 * @author alvar-go@uniandes.edu.co
 */
public class PanelVisorImagenes extends JPanel implements ActionListener, Observer{

   public static final String COMANDO_SIGUIENTE="SIGUIENTE";
    public static final String COMANDO_ANTERIOR="ANTERIOR";

    private InterfazKahuuImagenes principal;

    private JButton btNext;
    private JButton btBack;
    private JButton btPlayPause;

    private JLabel lbImagen;
    private File fileParent;
    private int indiceActual;
    private PanelFileExplorer explorer;
    
     private JLabel lbFecha;
     private JLabel lbFechaDescarga;
     
      private JList listaPersonas;
      
     private JList listaPersonasConsulto;
      
       private JCheckBox jCBSensible;

    public PanelVisorImagenes(InterfazKahuuImagenes interfaz,String nombre) {
        setLayout( new BorderLayout());
        setBorder( new TitledBorder("Visor de imagenes"));
        jCBSensible=new JCheckBox("Imagen sensible");
        jCBSensible.setEnabled(false);
        principal = interfaz;
        indiceActual=-1;
        ImageIcon next=new ImageIcon("data/imagenes/next.png");
        ImageIcon back=new ImageIcon("data/imagenes/back.png");
        ImageIcon playpause=new ImageIcon("data/imagenes/playpause.png");
        btNext= new JButton();
        btNext.setIcon(next);
        btNext.setActionCommand(COMANDO_SIGUIENTE);
        btNext.addActionListener(this);
        
         btBack= new JButton();
        btBack.setIcon(back);
        btBack.setActionCommand(COMANDO_ANTERIOR);
        btBack.addActionListener(this);

         btPlayPause= new JButton();
        btPlayPause.setIcon(playpause);

        lbImagen= new JLabel();



        //Panel de botones del visor
        JPanel panelBotones= new JPanel();
        panelBotones.setLayout(new GridLayout(1, 5));
         panelBotones.add(new JLabel());
        panelBotones.add(btBack);
        panelBotones.add(new JLabel());
        panelBotones.add(btNext);
         panelBotones.add(new JLabel());
        //Panel del arbol de archivos
        JPanel panelArbolArchivos= new JPanel();
         panelArbolArchivos.setLayout(new GridLayout(1, 1));
          explorer= new PanelFileExplorer(this,nombre);
         panelArbolArchivos.add(explorer);
        //Panel del visualizador
        JPanel panelVisualizador= new JPanel();
        panelVisualizador.setLayout(new GridLayout(1, 1));
        JScrollPane panel3=new JScrollPane(lbImagen); 
        
        panelVisualizador.add(panel3);

        add(panelArbolArchivos,BorderLayout.WEST);

        JPanel pCentral= new JPanel();
        pCentral.setLayout(new BorderLayout());
        pCentral.add(panelVisualizador,BorderLayout.CENTER);
        pCentral.add(panelBotones,BorderLayout.SOUTH);

        add(pCentral,BorderLayout.CENTER);
        
        JPanel oriente= new JPanel();
        oriente.setLayout(new BorderLayout());
        JPanel subPan= new JPanel();
        subPan.setLayout(new GridLayout(2,2));
        listaPersonas=new JList();
        listaPersonasConsulto=new JList();
        
        lbFecha=new JLabel();
        lbFechaDescarga=new JLabel();
        subPan.add(new JLabel("Fecha agregada:"));
        subPan.add(lbFecha);
        subPan.add(new JLabel("Fecha descarga:"));
        subPan.add(lbFechaDescarga);
        JScrollPane pane = new JScrollPane(listaPersonas);
        
        JScrollPane pane2 = new JScrollPane(listaPersonasConsulto);
        oriente.add(subPan,BorderLayout.NORTH);
        
        JPanel subPanelCenter=new JPanel();
        subPanelCenter.setLayout(new GridLayout(2,1));
        subPanelCenter.add(pane);
        subPanelCenter.add(pane2);
        oriente.add(subPanelCenter,BorderLayout.CENTER);
        
        
        
        oriente.add(jCBSensible,BorderLayout.SOUTH);
        add(oriente,BorderLayout.EAST);
       
    }

     private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
    DefaultMutableTreeNode node =
      new DefaultMutableTreeNode(hierarchy[0]);
    DefaultMutableTreeNode child;
    for(int i=1; i<hierarchy.length; i++) {
      Object nodeSpecifier = hierarchy[i];
      if (nodeSpecifier instanceof Object[])  // Ie node with children
        child = processHierarchy((Object[])nodeSpecifier);
      else
        child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
      node.add(child);
    }
    return(node);
  }


    public void actionPerformed(ActionEvent e) {
       String comando = e.getActionCommand();
       if(comando.equals(COMANDO_ANTERIOR))
       {
           if(fileParent!=null)
           {
           String[] listaArchivos=fileParent.list();
           if(indiceActual==listaArchivos.length)
           {
               indiceActual--;
           }
           else if(indiceActual==0)
           {
               //Indice actual
           }
           else{
               indiceActual--;
           }
           System.out.println("BACK "+fileParent.getPath()+"/"+listaArchivos[indiceActual]);
           
           
           ImageIcon im =new ImageIcon(darArchivoDesencriptado(fileParent.getPath()+"/"+listaArchivos[indiceActual]) );
           
           
         //ImageIcon im= new ImageIcon(fileParent.getPath()+"/"+listaArchivos[indiceActual]);
        lbImagen.setIcon(im); 
        actualizarDatosImagen(listaArchivos[indiceActual]);
           }
           
       }
       else if(comando.equals(COMANDO_SIGUIENTE))
       {
           if(fileParent!=null)
           {
            String[] listaArchivos=fileParent.list();
           if((indiceActual+1)==listaArchivos.length)
           {
               //Indice actual
           }
           else if(indiceActual==0)
           {
               indiceActual++;
           }
           else{
               indiceActual++;
           }
           
             System.out.println("ANTERIOR "+fileParent.getPath()+"/"+listaArchivos[indiceActual]);
             
             ImageIcon im =new ImageIcon(darArchivoDesencriptado(fileParent.getPath()+"/"+listaArchivos[indiceActual]) );
             
         //ImageIcon im= new ImageIcon(fileParent.getPath()+"/"+listaArchivos[indiceActual]);
        lbImagen.setIcon(im); 
        actualizarDatosImagen(listaArchivos[indiceActual]);
           }
       }
       
       
    }

    private byte[] darArchivoDesencriptado(String string) {
		// TODO Auto-generated method stub
    	try {
    	FileInputStream in=new FileInputStream(new File(string));
		

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		byte[] desencriptado;
		
		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = in.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		

	
		
			desencriptado = principal.desencriptarFoto(buffer.toByteArray());
			
			buffer.flush();
			in.close();
			
			return desencriptado;
		} catch (KahuuException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void update(Observable o, Object ob) {
       
    }
    public void visualizarImagen(File imagen)
    {
    	
    	 ImageIcon im =new ImageIcon(darArchivoDesencriptado(imagen.getPath()) );
         
        //ImageIcon im= new ImageIcon(imagen.getPath());
        lbImagen.setIcon(im);
        
        actualizarDatosImagen(imagen.getName());
        
         String[] listaArchivos=null;
       
            fileParent=imagen.getParentFile();
            
            
            listaArchivos=fileParent.list();
            
          
        for (int i = 0; i < listaArchivos.length; i++) {
            File arch=new File(listaArchivos[i]);
            System.out.println(arch.getPath());
            if(arch.getName().equals(imagen.getName()))
            {
               
                indiceActual=i;
                 System.out.println("Indice:"+indiceActual+" Archivo:"+arch.getName());
                break;
            }
        }
       
        
        
                
    }

    public void actualizar() {
        
      explorer.actualizarModelo();
      //  explorer=new PanelFileExplorer(this);
        repaint();
        
    }

    private void actualizarDatosImagen(String imagen) {
       
        System.out.println("Actualizando datos de la imagen");
        
            
           Imagen ima= principal.getImagenPorId(imagen);
           if(ima!=null)
           {
                System.out.println("Imagen encontrada");
               lbFecha.setText(ima.getTiempoCreacion());
               lbFechaDescarga.setText(ima.getTiempoDescarga());
              listaPersonas.setListData(ima.getTags().split(" "));
              listaPersonasConsulto.setListData(ima.derPersonasConsultaron().toArray());
            listaPersonas.setSelectedIndex(0);
            jCBSensible.setSelected(ima.isSensible());
            
           }else{
                lbFecha.setText("-");
                listaPersonas=new JList();
                System.out.println("La imagen es null");
           }
          
        
        
    }


}
