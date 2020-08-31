package it.unisa.di.tirociniosmart.registroTirocinio;

/**
 * Eccezione lanciata quando il controllo sul nome di un attività fallisce perché questo non
 * è valido oppure è nullo.
 */
public class NomeAttivitàNonValidoException extends Exception {
  
  private static final long serialVersionUID = 2044002301623135287L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Nome non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public NomeAttivitàNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public NomeAttivitàNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}