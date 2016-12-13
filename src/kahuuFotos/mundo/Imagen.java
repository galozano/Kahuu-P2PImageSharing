package kahuuFotos.mundo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Representa una imagen del P2P
 * @author gustavolozano
 *
 */
public class Imagen implements Serializable
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
    
    
    
    private String peerAgrego;
    
    private double parecido;
    
    private boolean sensible;
    
    private ArrayList<String> personasConsultaron;

    //------------------------------------------------------------------------------------------------------------------------------
    // Constructores
    //------------------------------------------------------------------------------------------------------------------------------

	public Imagen(String nombreFoto, String tags, String tiempoDescarga,
			String tiempoCreacion, String peerAgrego,boolean sensible) 
	{
		super();
		this.nombreFoto = nombreFoto;
		this.tags = tags;
		this.tiempoDescarga = tiempoDescarga;
		this.tiempoCreacion = tiempoCreacion;
		this.sensible=sensible;
		this.peerAgrego=peerAgrego;
		personasConsultaron=new ArrayList<String>();
	}
	
	public Imagen(String nombreFoto, double parecido)
	{
		this.nombreFoto = nombreFoto;
		this.parecido = parecido;
		personasConsultaron=new ArrayList<String>();
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

	public boolean isSensible() {
		return sensible;
	}

	public void setSensible(boolean sensible) {
		this.sensible = sensible;
	}

    public void agregarPersonaConsulto(String persona)
    {
    	personasConsultaron.add(persona);
    }
    public ArrayList<String> derPersonasConsultaron()
    {
    	return personasConsultaron;
    }
    
    
}
