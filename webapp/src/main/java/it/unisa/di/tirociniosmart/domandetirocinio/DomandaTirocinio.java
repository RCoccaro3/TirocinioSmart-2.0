package it.unisa.di.tirociniosmart.domandetirocinio;

import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinio;
import it.unisa.di.tirociniosmart.studenti.Studente;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Classe che modella una domanda di tirocinio.
 */
@Entity
public class DomandaTirocinio implements Comparable<DomandaTirocinio> {

  /**
   * Costruisce un oggetto DomandaTirocinio vuoto che deve essere popolata con i metodi setters.
   */
  public DomandaTirocinio() {	  
  }
  
  /**
   * Permette di determinare se due oggetti rappresentano la stessa domanda di tirocinio sulla base
   * dell'identificatore.
   */
  public boolean equals(Object object) {
    if (object == null) {
      return false;
    }
    
    if (getClass() != object.getClass()) {
      return false;
    }
    
    DomandaTirocinio domandaTirocinio = (DomandaTirocinio) object;
    
    return id == domandaTirocinio.getId();
  }
  
  /**
   * Permette di definire una stringa che può essere considerata come la 
   * "rappresentazione testuale" dell'oggetto DomandaTirocinio.
   * 
   * @return Stringa che rappresenta una descrizione più accurata e consona dell'oggetto
   */
  @Override
  public String toString() {
    return "DomandaTirocinio [id=" + id + ", status=" + status + ", data=" + data
        + ", inizioTirocinio=" + inizioTirocinio + ", fineTirocinio=" + fineTirocinio
        + ", commentoAzienda=" + commentoAzienda + ", commentoStudente=" + commentoStudente
        + ", cfu=" + cfu + ", studente=" + studente.getUsername() + ", progettoFormativo="
        + progettoFormativo.getId() + "]";
  }

  /**
   * Definisce l'ordine di comparazione tra le domande di tirocinio in base al campo data.
   */
  @Override
  public int compareTo(DomandaTirocinio domanda) {
    
    if (getData().isBefore(domanda.getData())) {
      return -1;
    } else if (getData().isAfter(domanda.getData())) {
      return 1;
    } else {
      return 0;
    }
  }
  
  /**
   * Permette di ottenere l'identificatore della domanda di tirocinio.
   * 
   * @return Long che rappresenta l'identificatore della domanda di tirocinio
   */
  public long getId() {
    return id;
  }

  /**
   * Permette di ottenere lo stato della domanda di tirocinio.
   * 
   * @return {@link DomandaTirocinio#IN_ATTESA} se la domanda è in attesa,
   *         {@link DomandaTirocinio#ACCETTATA} se la domanda è accettata,
   *         {@link DomandaTirocinio#RIFIUTATA} se la domanda è rifiutata,
   *         {@link DomandaTirocinio#APPROVATA} se la domanda è approvata,
   *         {@link DomandaTirocinio#RESPINTA} se la domanda è respinta
   */
  public int getStatus() {
    return status;
  }

  /**
   * Permette di specificare lo stato della domanda di tirocinio.
   * 
   * @param status Intero che rappresenta lo stato della domanda di tirocinio
   * 
   * @pre stauts = {@link DomandaTirocinio#IN_ATTESA} or
   *      stauts = {@link DomandaTirocinio#ACCETTATA} or
   *      stauts = {@link DomandaTirocinio#RIFIUTATA} or
   *      stauts = {@link DomandaTirocinio#APPROVATA} or
   *      stauts = {@link DomandaTirocinio#RESPINTA}
   *      
   * @post getStatus() = status
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * Permette di ottenere la data e l'ora in cui la domanda è stata inviata.
   * 
   * @return Oggetto LocalDateTime che rappresenta data ed ora in cui la domanda è stata inviata
   */
  public LocalDateTime getData() {
    return data;
  }

  /**
   * Permette di specificare la data e l'ora in cui la domanda è stata inviata.
   * 
   * @param data Oggetto LocalDateTime che rappresenta data ed ora in cui la domanda è stata inviata
   * 
   * @pre data != null
   * 
   * @post getData().isEqual(data)
   */
  public void setData(LocalDateTime data) {
    this.data = data;
  }

  /**
   * Permette di ottenere la data d'inizio del tirocinio.
   * 
   * @return Oggetto LocalDate che rappresenta la data d'inizio del tirocinio
   */
  public LocalDate getInizioTirocinio() {
    return inizioTirocinio;
  }

  /**
   * Permette di specificare la data d'inizio del tirocinio.
   * 
   * @param inizioTirocinio Oggetto LocalDate che rappresenta la data d'inizio del tirocinio
   * 
   * @pre inizioTirocinio != null
   * 
   * @post getDataInizioTirocinio().isEqual(inizioTirocinio)
   */
  public void setInizioTirocinio(LocalDate inizioTirocinio) {
    this.inizioTirocinio = inizioTirocinio;
  }

  /**
   * Permette di ottenere la data di fine del tirocinio.
   * 
   * @return Oggetto LocalDate che rappresenta la data di fine del tirocinio
   */
  public LocalDate getFineTirocinio() {
    return fineTirocinio;
  }

  /**
   * Permette di specificare la data di fine del tirocinio.
   * 
   * @param fineTirocinio Oggetto LocalDate che rappresenta la data di fine del tirocinio
   * 
   * @pre fineTirocinio != null
   * 
   * @post getDataFineTirocinio().isEqual(fineTirocinio)
   */
  public void setFineTirocinio(LocalDate fineTirocinio) {
    this.fineTirocinio = fineTirocinio;
  }

  /**
   * Permette di ottenere il commento dell'azienda alla domanda di tirocinio.
   * 
   * @return Stringa che rappresenta il commento dell'azienda alla domanda di tirocinio.
   *         <b>Tale stringa può essere null</b> se l'azienda non  ha commentato la richiesta
   */
  public String getCommentoAzienda() {
    return commentoAzienda;
  }

  /**
   * Permette di specificare il commento dell'azienda alla domanda di tirocinio.
   * 
   * @param commentoAzienda Stringa che rappresenta il commento dell'azienda alla domanda di
   *                        tirocinio
   *                        
   * @pre commentoAzienda != null
   * @pre commentoAzienda.length() > 0
   * 
   * @post getCommentoAzienda().equals(commentoAzienda)
   */
  public void setCommentoAzienda(String commentoAzienda) {
    this.commentoAzienda = commentoAzienda;
  }

  /**
   * Permette di ottenere il commento dello studente alla domanda di tirocinio.
   * 
   * @return Stringa che rappresenta il commento dello studente alla domanda di tirocinio
   */
  public String getCommentoStudente() {
    return commentoStudente;
  }

  /**
   * Permette di specificare il commento dello studente alla domanda di tirocinio.
   * 
   * @param commentoStudente Stringa che rappresenta il commento dello studente alla domanda di
   *                         tirocinio
   *                        
   * @pre commentoStudente != null
   * @pre commentoStudente.length() > 0
   * 
   * @post getCommentoStudente().equals(commentoStudente)
   */
  public void setCommentoStudente(String commentoStudente) {
    this.commentoStudente = commentoStudente;
  }
  
  /**
   * Permette di ottenere il commento dell'impiegato dell'ufficio tirocini alla domanda di 
   * tirocinio.
   * 
   * @return Stringa che rappresenta il commento dell'impiegato alla domanda di tirocinio
   */
  public String getCommentoImpiegato() {
    return commentoImpiegato;
  }
  
  /**
   * Permette di specificare il commento dell'impiegatio dell'ufficio tirocini alla domanda 
   * di tirocinio.
   * 
   * @param commentoImpiegato Stringa che rappresenta il commento dell'impiegato alla domanda di
   *                          tirocinio
   *                        
   * @pre commentoImpiegato != null
   * @pre commentoImpiegato.length() > 0
   * 
   * @post getcommentoImpiegato().equals(commentoImpiegato)
   */
  public void setCommentoImpiegato(String commentoImpiegato) {
    this.commentoImpiegato = commentoImpiegato;
  }

  /**
   * Permette di ottenere il commento del Presidente del Consiglio Didattico alla domanda di 
   * tirocinio.
   * 
   * @return Stringa che rappresenta il commento del Presidente alla domanda di tirocinio
   */
  public String getCommentoPresidente() {
    return commentoImpiegato;
  }
  
  /**
   * Permette di specificare il commento del Presidente del Consiglio Didattico alla domanda 
   * di tirocinio.
   * 
   * @param commentoPresidente Stringa che rappresenta il commento del Presidente alla domanda di
   *                          tirocinio
   *                        
   * @pre commentoPresidente != null
   * @pre commentoPresidente.length() > 0
   * 
   * @post getcommentoPresidente().equals(commentoPresidente)
   */
  public void setCommentoPresidente(String commentoPresidente) {
    this.commentoPresidente = commentoPresidente;
  }
  
  /**
   * Permette di ottenere il numero di CFU cui il tirocinio è associato.
   * 
   * @return Intero che rappresta il numero di CFU cui il tirocinio è associato
   */
  public int getCfu() {
    return cfu;
  }

  /**
   * Permette di specificare il numero di CFU cui il tirocinio è associato.
   * 
   * @param cfu Intero che rappresenta il numero di CFU cui il tirocinio è associato
   * 
   * @pre cfu > 0
   * 
   * @post getCfu() = cfu
   */
  public void setCfu(int cfu) {
    this.cfu = cfu;
  }

  /**
   * Permette di ottenere lo studente associato alla domanda di tirocinio.
   * 
   * @return L'oggetto {@link Studente} associato alla domanda di tirocinio
   */
  public Studente getStudente() {
    return studente;
  }

  /**
   * Permette di specificare lo studente associato alla domanda di tirocinio.
   * 
   * @param studente L'oggetto {@link Studente} da associare alla domanda di tirocinio
   * 
   * @pre studente != null
   * 
   * @post getStudente() = studente
   */
  public void setStudente(Studente studente) {
    if (this.studente != studente) {
      this.studente = studente;
      studente.addDomandaTirocinio(this);
    }
  }

  /**
   * Permette di ottenere il progetto formativo associato alla domanda di tirocinio.
   * 
   * @return L'oggetto {@link ProgettoFormativo} associato alla domanda di tirocinio
   */
  public ProgettoFormativo getProgettoFormativo() {
    return progettoFormativo;
  }

  /**
   * Permette di specificare il progetto formativo associato alla domanda di tirocinio.
   * 
   * @param progettoFormativo L'oggetto {@link ProgettoFormativo} da associare alla domanda di
   *                          tirocinio
   *                          
   * @pre progettoFormativo != null
   * 
   * @post getProgettoFormativo().equals(progettoFormativo)
   */
  public void setProgettoFormativo(ProgettoFormativo progettoFormativo) {
    if (this.progettoFormativo != progettoFormativo) {
      this.progettoFormativo = progettoFormativo;
      progettoFormativo.addDomandaTirocinio(this);
    }
  }
  
  /**
   * Permette di ottenere il numero di ore totali del tirocinio associato alla domanda di tirocinio.
   * 
   * @return Intero che rappresenta il numero totale di ore del tirocinio
   */
  public int getOreTotaliTirocinio() {
	return oreTotaliTirocinio;
  }

  public void setOreTotaliTirocinio() {
	  oreTotaliTirocinio = cfu * 25;
  }
   
  public RegistroTirocinio getRegistroTirocinio() {
	return registroTirocinio;
}

public void setRegistroTirocinio(RegistroTirocinio registroTirocinio) {
	this.registroTirocinio = registroTirocinio;
}

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private int status;
  private LocalDateTime data;
  private LocalDate inizioTirocinio;
  private LocalDate fineTirocinio;
  private int oreTotaliTirocinio;
  
  @Lob
  private String commentoAzienda;
  
  @Lob
  private String commentoStudente;
  
  @Lob
  private String commentoImpiegato;
  
  @Lob
  private String commentoPresidente;
  private int cfu;
  
  @ManyToOne
  private Studente studente;
  
  @ManyToOne
  private ProgettoFormativo progettoFormativo;
  
  @OneToOne(mappedBy = "domandaTirocinio")
  private RegistroTirocinio registroTirocinio;

/**
   * Costante che rappresenta lo stato "in attesa" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata inviata dallo studente ma
   * non è ancora stata esaminata e gestita dall'azienda che offre il progetto formativo ad essa
   * associato.
   */
  public static final int IN_ATTESA = 0;
  
  /**
   * Costante che rappresenta lo stato "accettato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita ed accettata 
   * dall'azienda che offre il progetto formativo ad essa associato.
   */
  public static final int ACCETTATA = 1;
  
  /**
   * Costante che rappresenta lo stato "rifiutato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita e rifiutata 
   * dall'azienda che offre il progetto formativo ad essa associato.
   */
  public static final int RIFIUTATA = 2;
  
  /**
   * Costante che rappresenta lo stato "approvato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita ed approvata
   * dall'ufficio tirocini. <b>La domanda dev'essere stata precedentemente accettata</b> per potervi
   * assegnare questo stato.
   */
  public static final int APPROVATA = 3;
  
  /**
   * Costante che rappresenta lo stato "respinto" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando, sebbene accettata dall'azienda, la
   * domanda è stata respinta dall'ufficio tirocini.
   */
  public static final int RESPINTA = 4;
  
  /**
   * Costante che rappresenta lo stato "validato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando è stata gestita e validata
   * dal Presidente del Consiglio Didattico. <b>La domanda dev'essere stata precedentemente approvata</b> 
   * per potervi assegnare questo stato.
   */
  public static final int VALIDATA = 5;
  
  /**
   * Costante che rappresenta lo stato "annullato" di una domanda di tirocinio.
   * Una domanda di tirocinio si trova in questo stato quando, sebbene approvata dall'Immpiegato
   * dell'Ufficio Tirocini, la domanda è stata annullata dal Presidente del Coniglio Didattico.
   */
  public static final int ANNULLATA = 6;
  
  /** Costante che definisce la minima lunghezza dei campi commento. */
  public static final int MIN_LUNGHEZZA_COMMENTO = 2;

  /** Costante che definisce il numero minimo di CFU da poter associare ad una domanda. */
  public static final int MIN_CFU = 1;
  
  /** Costante che definisce il numero massimo di CFU da poter associare ad una domanda. */
  public static final int MAX_CFU = 18;
  
}
