/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package kahuuFotos.adv;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Enumeration;

import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Document;
import net.jxta.document.Element;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.StructuredDocumentUtils;
import net.jxta.document.StructuredTextDocument;
import net.jxta.document.TextElement;
import net.jxta.protocol.PipeAdvertisement;

/**
 *
 * @author alvar-go@uniandes.edu.co
 */
public class MyAdvertisementRecurso extends AdvertisementRecurso
{
	//------------------------------------------------------------------------------------------------------------------------------
	// Constantes
	//------------------------------------------------------------------------------------------------------------------------------
	
    public final static String mimeType= "text/xml";

    public static final String NOMBRE = "InfoNodo";

    public static final String TAGS = "tags";

    public static final String NOMBRE_FOTO = "NombreFoto";
    
    public static final String TIEMPO_CREACION = "TiempoCreacion";
    
    public static final String PEER_AGREGO = "PeerAgrego";
    
    public static final String SENSIBLE = "Sensible";

    /**
     * Elementos que se usan para indexar el advertisement
     */
    private final static String[] fields = {NOMBRE,TAGS};

    //------------------------------------------------------------------------------------------------------------------------------
    // Constructor
    //------------------------------------------------------------------------------------------------------------------------------
    
    @SuppressWarnings("rawtypes")
	public MyAdvertisementRecurso(InputStream  stream) throws IOException
    {
        super();

        StructuredTextDocument document= (StructuredTextDocument) StructuredDocumentFactory.newStructuredDocument( new MimeMediaType(mimeType), stream);
        readAdvertisement(document);
    }

    @SuppressWarnings("rawtypes")
    public MyAdvertisementRecurso(Element document)
    {
        super();
        readAdvertisement((TextElement)document);
    }
    
    //------------------------------------------------------------------------------------------------------------------------------
    // Métodos
    //------------------------------------------------------------------------------------------------------------------------------

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
	public Document getDocument(MimeMediaType asMimeType) throws IllegalArgumentException
    {
        if((null!= getNombreAdvertisement()) && (null!= getPipeAdv()))
        {
            StructuredDocument document= StructuredDocumentFactory.newStructuredDocument(asMimeType, getAdvertisementType());
            Element element;

            //a√±adir hijos al documento:

            element= document.createElement(NOMBRE, getNombreAdvertisement());
            document.appendChild(element);

            element= document.createElement(TAGS, getTags());
            document.appendChild(element);

            element= document.createElement(NOMBRE_FOTO, getNombreFoto());
            document.appendChild(element);
            
            element= document.createElement(TIEMPO_CREACION, getTiempoCreacion());
            document.appendChild(element);
            
            element= document.createElement(PEER_AGREGO, getPeerAgrego());
            document.appendChild(element);
            element= document.createElement(SENSIBLE, isSensible());
            document.appendChild(element);

            PipeAdvertisement pipe = getPipeAdv();
            if(pipe!=null){
                StructuredTextDocument advDoc = (StructuredTextDocument) pipe.getDocument(asMimeType);
                StructuredDocumentUtils.copyElements(document, document, advDoc);
            }
            return document;
        }
        else
        {
            throw new IllegalArgumentException("Faltan datos para hacer advert");
        }
    }

    @SuppressWarnings({ "rawtypes", "deprecation" })
    public void readAdvertisement(TextElement document)throws IllegalArgumentException
    {
        if(document.getName().equals(getAdvertisementType()))
        {
            Enumeration elements= document.getChildren();

            while(elements.hasMoreElements())
            {
                TextElement element= (TextElement) elements.nextElement();

                if(element.getName().equals(PipeAdvertisement.getAdvertisementType()))
                {
                    PipeAdvertisement pipe = (PipeAdvertisement) AdvertisementFactory.newAdvertisement(element);
                    setPipeAdv(pipe);
                    continue;
                }
                if(element.getName().equals(NOMBRE))
                {
                    setNombreAdvertisement(element.getTextValue());
                    continue;
                }
                if(element.getName().equals(TAGS)){
                    
                    setTags(element.getTextValue());
                    continue;
                }
                if(element.getName().equals(NOMBRE_FOTO))
                {
                    setNombreFoto(element.getTextValue());
                    continue;
                }
                
                if(element.getName().equals(TIEMPO_CREACION))
                {
                    setTiempoCreacion(element.getTextValue());
                    continue;
                }
                if(element.getName().equals(PEER_AGREGO))
                {
                    setPeerAgrego(element.getTextValue());
                    continue;
                }
                if(element.getName().equals(SENSIBLE))
                {
                    setSensible(element.getTextValue());
                    continue;
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("No Corresponde con el tipo de advertisement esperado");
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
	public String toString()
    {
        try 
        {
            StringWriter out = new StringWriter();
            StructuredTextDocument doc = (StructuredTextDocument) getDocument(new MimeMediaType(mimeType));
            doc.sendToWriter(out);
            return out.toString();
        } 
        catch (IOException ex) 
        {
            System.out.println("====== ERROR ======");
            System.out.println(ex.getMessage());
            return "";
        }
    }
    
    public MyAdvertisementRecurso()
    {
       super();
    }

    @Override
    public String[] getIndexFields() 
    {
        return fields;
    }

    @SuppressWarnings("rawtypes")
    public static class Instantiator implements AdvertisementFactory.Instantiator
    {

        @Override
		public String getAdvertisementType() {
           return AdvertisementRecurso.getAdvertisementType();
        }

        @Override
		public Advertisement newInstance() {
            return new MyAdvertisementRecurso();
        }

        @Override
		public Advertisement newInstance(Element root) {
            return new MyAdvertisementRecurso(root);
        }

    };
}
