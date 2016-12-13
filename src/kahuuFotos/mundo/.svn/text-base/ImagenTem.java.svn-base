package kahuuFotos.mundo;

import java.io.Serializable;

import net.jxta.protocol.PipeAdvertisement;

/**
 * Representa una imagen del P2P
 * @author gustavolozano
 *
 */
public class ImagenTem implements Serializable
{
	
    //------------------------------------------------------------------------------------------------------------------------------
    // Atributos
    //------------------------------------------------------------------------------------------------------------------------------
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nombreFoto;
    
    private String tags;
    
    private String tiempoDescarga;
    
    private String tiempoCreacion;
    
    private PipeAdvertisement piveAdv;
    
    private String peerAgrego;
    
    private double parecido;
    
    private boolean sensible;

    //------------------------------------------------------------------------------------------------------------------------------
    // Constructores
    //------------------------------------------------------------------------------------------------------------------------------

	public ImagenTem(String nombreFoto, String tags, String tiempoDescarga,
			String tiempoCreacion, PipeAdvertisement piveAdv,String peerAgrego,boolean sensible) 
	{
		super();
		this.nombreFoto = nombreFoto;
		this.tags = tags;
		this.tiempoDescarga = tiempoDescarga;
		this.tiempoCreacion = tiempoCreacion;
		this.piveAdv = piveAdv;
		this.peerAgrego=peerAgrego;
		this.sensible=sensible;
	}
	
	public boolean isSensible() {
		return sensible;
	}

	public void setSensible(boolean sensible) {
		this.sensible = sensible;
	}

	public ImagenTem(String nombreFoto, double parecido)
	{
		this.nombreFoto = nombreFoto;
		this.parecido = parecido;
	}

	
    //------------------------------------------------------------------------------------------------------------------------------
    // Metodos
    //------------------------------------------------------------------------------------------------------------------------------
	
	public String getNombreFoto( )
    {
        return nombreFoto;
    }

    public void setNombreFoto( String nombreFoto )
    {
        this.nombreFoto = nombreFoto;
    }

    public String getTags( )
    {
        return tags;
    }

    public void setTags( String tags )
    {
        this.tags = tags;
    }

	public PipeAdvertisement getPiveAdv() {
		return piveAdv;
	}

	public void setPiveAdv(PipeAdvertisement piveAdv) 
	{
		this.piveAdv = piveAdv;
	}

	public String getTiempoDescarga() 
	{
		return tiempoDescarga;
	}

	public void setTiempoDescarga(String tiempoDescarga) 
	{
		this.tiempoDescarga = tiempoDescarga;
	}

	public String getTiempoCreacion()
	{
		return tiempoCreacion;
	}

	public void setTiempoCreacion(String tiempoCreacion)
	{
		this.tiempoCreacion = tiempoCreacion;
	}


	public double getParecido() {
		return parecido;
	}


	public void setParecido(double parecido) {
		this.parecido = parecido;
	}

	public String getPeerAgrego() {
		return peerAgrego;
	}

	public void setPeerAgrego(String peerAgrego) {
		this.peerAgrego = peerAgrego;
	}

    
    
    
}
