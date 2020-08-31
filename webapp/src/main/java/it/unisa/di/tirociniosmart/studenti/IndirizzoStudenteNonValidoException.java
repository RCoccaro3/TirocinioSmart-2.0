package it.unisa.di.tirociniosmart.studenti;

/**
 * Eccezione lanciata quando si tenta di inserire uno studente il cui indirizzo non è valido oppure
 * è nullo.
 */
public class IndirizzoStudenteNonValidoException extends Exception {
  
  private static final long serialVersionUID = -5037764297707333946L;
  
  /** Stringa che definisce il messaggio di default utilizzato nell'eccezione. */
  private static final String messaggioDefault = "Indirizzo non valido";
  
  /**
   * Costruisce un'eccezione che ha come messaggio {@link #messaggioDefault}.
   */
  public IndirizzoStudenteNonValidoException() {
    super(messaggioDefault);
  }
  
  /**
   * Costruisce un'eccezione che ha come messaggio la stringa specificata come parametro.
   * 
   * @param messaggio Stringa che rappresenta il messaggio da mostrare
   */
  public IndirizzoStudenteNonValidoException(String messaggio) {
    super(messaggio);
  }
  
}

