package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando il controllo sul nome di un attività fallisce perché questo non
 * è valido oppure è nullo.
 */
public class DescrizioneAttivitàNonValidaException extends Exception {
  
  private static final long serialVersionUID = 2044002301623135287L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Descrizione non valida";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public DescrizioneAttivitàNonValidaException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public DescrizioneAttivitàNonValidaException(String messaggio) {
    super(messaggio);
  }
  
}