package kahuuFotos.filebrowser;



import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import kahuuFotos.interfaz2.PanelVisorImagenes;


public class FileSystemTreePanel extends JPanel implements TreeSelectionListener {
    private JTree tree;
    DirectoryModel model;
    PanelVisorImagenes principal;
    File lastParent;
    public FileSystemTreePanel(DirectoryModel directoryModel,PanelVisorImagenes interfaz) {
        
        this( new FileSystemModel(),directoryModel,interfaz );
    }

    public FileSystemTreePanel( String startPath,DirectoryModel directoryModel,PanelVisorImagenes interfaz ) {
        this( new FileSystemModel( startPath ),directoryModel,interfaz );
    }

    public FileSystemTreePanel( FileSystemModel nModel ,DirectoryModel directoryModel,PanelVisorImagenes interfaz) {
        
        //setPreferredSize(new Dimension(200,200));
        //setSize(new Dimension(200, 200));
        principal=interfaz;
        model=directoryModel;
        tree = new JTree( nModel ) {       
            public String convertValueToText(Object value, boolean selected,
                                             boolean expanded, boolean leaf, int row,
                                             boolean hasFocus) {
                return ((File)value).getName();
            }
        };

        //tree.setLargeModel( true );        
        tree.setRootVisible( false );
        tree.setShowsRootHandles( true );
        tree.putClientProperty( "JTree.lineStyle", "Angled" );
        tree.addTreeSelectionListener(this);
        setLayout( new BorderLayout() );
        add( tree, BorderLayout.CENTER );
    }
     public void valueChanged( TreeSelectionEvent e ) 
    {
           File fileSysEntity = (File)e.getPath().getLastPathComponent();
            if ( fileSysEntity.isDirectory() ) {
                model.setDirectory( fileSysEntity );
                lastParent=fileSysEntity;
                principal.visualizarImagen(fileSysEntity);
            }
            else {
                
                model.setDirectory( null );
                principal.visualizarImagen(fileSysEntity);
                
            }
   }

    public JTree getTree() {
       return tree;
    }
}


