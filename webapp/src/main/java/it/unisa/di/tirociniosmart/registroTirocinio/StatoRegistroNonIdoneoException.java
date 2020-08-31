package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando lo stato di un registro di tirocinio non è idoneo.
 */
public class StatoRegistroNonIdoneoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2470568012474252627L;
	
	/** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
	  private static final String messaggioDefault = "Stato attività di tirocinio non idoneo";
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
	   */
	  public StatoRegistroNonIdoneoException() {
	    super(messaggioDefault);
	  }
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
	   * 
	   * @param messaggio Stringa che rappresenta il messaggio da mostrare
	   */
	  public StatoRegistroNonIdoneoException(String messaggio) {
	    super(messaggio);
	  }
}
