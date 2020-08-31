package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando si fa riferimento ad un registro di tirocinio con un
 * identificatore inesistente
 */
public class IdRegistroTirocinioNonValidoException extends Exception {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 7376768741554002140L;
	
	/** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
	  private static final String messaggioDefault = "Registro di tirocinio inesistente";
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
	   */
	  public IdRegistroTirocinioNonValidoException() {
	    super(messaggioDefault);
	  }
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
	   * 
	   * @param messaggio Stringa che rappresenta il messaggio da mostrare
	   */
	  public IdRegistroTirocinioNonValidoException(String messaggio) {
	    super(messaggio);
	  }
}
