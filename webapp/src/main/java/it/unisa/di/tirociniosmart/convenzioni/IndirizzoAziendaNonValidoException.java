package it.unisa.di.tirociniosmart.convenzioni;

/**
 * Eccezione lanciata quando si tenta di inserire un'azienda il cui indirizzo non è valido oppure
 * è nullo.
 */
public class IndirizzoAziendaNonValidoException extends Exception {
  
  private static final long serialVersionUID = -4170303032370990897L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Indirizzo non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IndirizzoAziendaNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IndirizzoAziendaNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}
