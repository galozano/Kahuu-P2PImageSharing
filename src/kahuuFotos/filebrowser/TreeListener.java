/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kahuuFotos.filebrowser;

import java.io.File;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

/**
 *
 * @author Kevin
 */
public class TreeListener implements TreeSelectionListener 
{
   
    DirectoryModel model;
    public TreeListener( DirectoryModel mdl ) 
    {
            model = mdl;
            
    }
    
    
    public void valueChanged( TreeSelectionEvent e ) 
    {
           File fileSysEntity = (File)e.getPath().getLastPathComponent();
            if ( fileSysEntity.isDirectory() ) {
                model.setDirectory( fileSysEntity );
                
            }
            else {
                model.setDirectory( null );
                
            }
   }
}
