package kahuuFotos.mundo;

import net.jxta.protocol.PipeAdvertisement;

/**
 * 
 * @author gustavolozano
 *
 */
public class Peer 
{
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Atributos
    //------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 
	 */
	private PipeAdvertisement piveAdv;
	
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Metodos
    //------------------------------------------------------------------------------------------------------------------------------

	/**
	 * 
	 * @return
	 */
	public PipeAdvertisement getPiveAdv()
	{
		return piveAdv;
	}

	/**
	 * 
	 * @param piveAdv
	 */
	public void setPiveAdv(PipeAdvertisement piveAdv) 
	{
		this.piveAdv = piveAdv;
	}
}
