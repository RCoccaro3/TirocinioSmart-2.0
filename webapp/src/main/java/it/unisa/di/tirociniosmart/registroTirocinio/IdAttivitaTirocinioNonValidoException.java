package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando si fa riferimento ad un'attività di tirocinio con un
 * identificatore inesistente
 */
public class IdAttivitaTirocinioNonValidoException extends Exception {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
	  private static final String messaggioDefault = "Attività di tirocinio inesistente";
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
	   */
	  public IdAttivitaTirocinioNonValidoException() {
	    super(messaggioDefault);
	  }
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
	   * 
	   * @param messaggio Stringa che rappresenta il messaggio da mostrare
	   */
	  public IdAttivitaTirocinioNonValidoException(String messaggio) {
	    super(messaggio);
	  }
}
