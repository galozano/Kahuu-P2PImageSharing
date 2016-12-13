package kahuuFotos.filebrowser;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import kahuuFotos.interfaz2.PanelVisorImagenes;

public class PanelFileExplorer extends JPanel{
    
    private PanelVisorImagenes principal;
    
    private FileSystemModel model ;
    private    DirectoryModel directoryModel;
     private   JTable table;
     private FileSystemTreePanel fileTree;
     private String nombre;
    public PanelFileExplorer(PanelVisorImagenes interfaz,String nNombre)
    {
    	System.out.println("NOMBRE CARPETA:"+ nNombre);
        principal=interfaz;
        setLayout(new GridLayout(1,2));
        nombre=nNombre;
          model = new FileSystemModel(nombre);
         directoryModel = new DirectoryModel( (File)model.getRoot() );
        table = new JTable( directoryModel );
       // table.setPreferredScrollableViewportSize(new Dimension(232, 200));
      //  table.setPreferredSize(new Dimension(232, 200));
        
        table.setShowHorizontalLines( false );
        table.setShowVerticalLines( false );
        table.setIntercellSpacing( new Dimension( 0, 2 ) );
        table.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        table.getColumn( "Type" ).setCellRenderer( new DirectoryRenderer() );
        table.getColumn( "Type" ).setMaxWidth( 32 );
        table.getColumn( "Type" ).setMinWidth( 32 );
        table.getColumn( "Name" ).setCellRenderer( new DirectoryRenderer() );
        table.getColumn( "Name" ).setMaxWidth( 100 );
        table.getColumn( "Name" ).setMinWidth( 100 );
        table.getColumn( "Bytes" ).setCellRenderer( new DirectoryRenderer() );
        table.getColumn( "Bytes" ).setMaxWidth( 100 );
        table.getColumn( "Bytes" ).setMinWidth( 100 );

        fileTree = new FileSystemTreePanel( model,directoryModel,principal );
        //fileTree.getTree().addTreeSelectionListener( new TreeListener( directoryModel ) );

        JScrollPane treeScroller = new JScrollPane( fileTree );
        treeScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        treeScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        JScrollPane tableScroller = new JScrollPane( table );
        tableScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //tableScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        //treeScroller.setMinimumSize( new Dimension( 0, 0 ) );
        //tableScroller.setMinimumSize( new Dimension( 0, 0 ) );
        
        
        treeScroller.setPreferredSize( new Dimension( 200, 300 ) );
        tableScroller.setPreferredSize( new Dimension( 232,300 ) );
        
        tableScroller.setBackground( Color.white );
        JSplitPane splitPane = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT,
                                               treeScroller,
                                              tableScroller );
       
        splitPane.setContinuousLayout( true );
        
        //add( splitPane );
        
        add(treeScroller);
       // add(tableScroller);

        //setSize( 400, 400 );
        
        
        
        
        
    }

    public void actualizarModelo() {
        model = new FileSystemModel(nombre);
        directoryModel = new DirectoryModel( (File)model.getRoot() );
        //table.setModel(directoryModel); 
        //fileTree=new FileSystemTreePanel( model,directoryModel,principal );
        fileTree.getTree().setModel(model);
        //repaint();
        
    }
   
   
}
