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
 * @author gustavolozano
 *
 */
public class MyAdvertisementPeers extends AdvertisementPeer
{
	public final static String mimeType= "text/xml";

	public static final String NOMBRE_PEER = "NombrePeer";

	/**
	 * Elementos que se usan para indexar el advertisement
	 */
	private final static String[] fields = {NOMBRE_PEER};


	//------------------------------------------------------------------------------------------------------------------------------
	// Constructor
	//------------------------------------------------------------------------------------------------------------------------------

	@SuppressWarnings("rawtypes")
	public MyAdvertisementPeers(InputStream  stream) throws IOException
	{
		super();

		StructuredTextDocument document= (StructuredTextDocument) StructuredDocumentFactory.newStructuredDocument( new MimeMediaType(mimeType), stream);
		readAdvertisement(document);
	}

	@SuppressWarnings("rawtypes")
	public MyAdvertisementPeers(Element document)
	{
		super();
		readAdvertisement((TextElement)document);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Document getDocument(MimeMediaType asMimeType) throws IllegalArgumentException
	{
		if((null!= getNombrePeer()) && (null!= getPipeAdv()))
		{
			StructuredDocument document= StructuredDocumentFactory.newStructuredDocument(asMimeType, getAdvertisementType());
			Element element;

			//añadir hijos al documento:

				element= document.createElement(NOMBRE_PEER, getNombrePeer());
				document.appendChild(element);

				PipeAdvertisement pipe = getPipeAdv();
				if(pipe!=null)
				{
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
				if(element.getName().equals(NOMBRE_PEER))
				{
					setNombrePeer(element.getTextValue());
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

	public MyAdvertisementPeers()
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
           return AdvertisementPeer.getAdvertisementType();
        }

        @Override
		public Advertisement newInstance() {
            return new MyAdvertisementPeers();
        }

        @Override
		public Advertisement newInstance(Element root) {
            return new MyAdvertisementPeers(root);
        }
	};
}
