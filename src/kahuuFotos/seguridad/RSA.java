package kahuuFotos.seguridad;




import java.math.BigInteger;
import java.util.Random;


public class RSA 
{		
	int tamPrimo;
	
	BigInteger n, q, p;
	
	BigInteger totient;
	
	BigInteger e, d;
	
	/** Constructor de la clase RSA */
	public RSA(int tamPrimo) 
	{
		this.tamPrimo = tamPrimo;
		generaPrimos(); //Genera p y q
		generaClaves(); //Genera e y d
	}	
	
	
	public void generaPrimos()
	{
		p = new BigInteger(tamPrimo, 10, new Random());
				
		do 
		{			
			q = new BigInteger(tamPrimo, 10, new Random());
		}			
		while(q.compareTo(p) == 0);
		
	}
	
	public void generaClaves()
	{	
		// n = p * q		
		n = p.multiply(q);		
		
		// toltient = (p-1)*(q-1)
		totient = p.subtract(BigInteger.valueOf(1));
		totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
		
		// Elegimos un e coprimo de y menor que n
		do 
		{
			e = new BigInteger(2 * tamPrimo, new Random());
		}			
		while((e.compareTo(totient) != -1) || (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
						
		// d = e^1 mod totient
		d = e.modInverse(totient);	
	}
	/**
	* Encripta el texto usando la clave pública
	*
	* @param mensaje Ristra que contiene el mensaje a encriptar
	* @return El mensaje cifrado como un vector de BigIntegers
	*/
	public String encripta(String mensaje, BigInteger e , BigInteger n)
	{
		int i;
		byte[] temp = new byte[1];
		byte[] digitos = mensaje.getBytes();
		String mensajeE = "";		
		
		BigInteger[] bigdigitos = new BigInteger[digitos.length];
		BigInteger[] encriptado = new BigInteger[bigdigitos.length];
		
		for(i=0; i< bigdigitos.length;i++)
		{
			temp[0] = digitos[i];
			bigdigitos[i] = new BigInteger(temp);
			encriptado[i] = bigdigitos[i].modPow(e,n);
			mensajeE += encriptado[i].toString() + "A";
		}	
		
		return mensajeE;
	}
	/**
	* Desencripta el texto cifrado usando la clave privada
	*
	* @param encriptado Array de objetos BigInteger que contiene el texto cifrado
	* que será desencriptado
	* @return The decrypted plaintext
	*/
	public String desencripta(String mensaje) 
	{	
		String[] men = mensaje.split("A");				
			
		BigInteger[] encriptado = new BigInteger[men.length];
		BigInteger[] desencriptado = new BigInteger[encriptado.length];
		char[] charArray = new char[desencriptado.length];
		
		for (int i = 0; i < men.length; i++) 
		{
			encriptado[i] = new BigInteger(men[i]);
			desencriptado[i] = encriptado[i].modPow(d,n);
			charArray[i] = (char) (desencriptado[i].intValue());
		}
		
		return new String(charArray);
	}
	
	public String desencriptaPersonalizado(String mensaje, BigInteger e , BigInteger n) 
	{	
		String[] men = mensaje.split("A");				
			
		BigInteger[] encriptado = new BigInteger[men.length];
		BigInteger[] desencriptado = new BigInteger[encriptado.length];
		char[] charArray = new char[desencriptado.length];
		
		for (int i = 0; i < men.length; i++) 
		{
			encriptado[i] = new BigInteger(men[i]);
			desencriptado[i] = encriptado[i].modPow(e,n);
			charArray[i] = (char) (desencriptado[i].intValue());
		}
		
		return new String(charArray);
	}
	
	public BigInteger damep() 
	{
		return p;
	}
	
	public BigInteger dameq() 
	{
		return q;
	}
	
	public BigInteger dametotient() 
	{
		return totient;
	}
	
	/**
	 * Para los 2, la llave publica y la privada
	 * @return
	 */
	public BigInteger damen() 
	{
		return n;
	}
	
	/**
	 * LLave publica
	 * @return
	 */
	public BigInteger damee() 
	{
		return e;
	}
	
	/**
	 * llave privada
	 * @return
	 */
	public BigInteger damed()
	{
		return d;
	}
}
