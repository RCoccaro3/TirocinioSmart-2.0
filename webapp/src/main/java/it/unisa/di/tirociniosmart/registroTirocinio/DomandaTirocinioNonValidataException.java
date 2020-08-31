package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando si tenta di inserire una data di fine tirocinio che non è valida
 * o non rientra nell'intervallo prestabilito.
 */
public class DomandaTirocinioNonValidataException extends Exception {
  
  private static final long serialVersionUID = 9050689633291237864L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Domanda di Tirocinio"
  		+ " non validata";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DomandaTirocinioNonValidataException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DomandaTirocinioNonValidataException(String messaggio) {
    super(messaggio);
  }
  
}



