package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando lo stato di un'attività di tirocinio non è idoneo.
 */
public class StatoAttivitaNonIdoneoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8647326249386299221L;
	
	/** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
	  private static final String messaggioDefault = "Stato attività di tirocinio non idoneo";
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
	   */
	  public StatoAttivitaNonIdoneoException() {
	    super(messaggioDefault);
	  }
	  
	  /**
	   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
	   * 
	   * @param messaggio Stringa che rappresenta il messaggio da mostrare
	   */
	  public StatoAttivitaNonIdoneoException(String messaggio) {
	    super(messaggio);
	  }
}
