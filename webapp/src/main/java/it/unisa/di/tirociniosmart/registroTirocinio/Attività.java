package it.unisa.di.tirociniosmart.registroTirocinio;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;

/**
 * Classe che modella un attività presente nel registro di Tirocinio
 * 
 *@see RegistroTirocinio
 */
@Entity
public class Attività {

	   /**
	   * Costruisce un oggetto Attività vuoto che deve essere popolata con i metodi setters.
	   */
	  public Attività() {
	  }
	  
	  /**
	   * Permette di determinare se due oggetti rappresentano la stessa attività sulla base
	   * dell'identificatore.
	   */
	  public boolean equals(Object object) {
	    if (object == null) {
	      return false;
	    }
	    
	    if (getClass() != object.getClass()) {
	      return false;
	    }
	    
	    Attività attività = (Attività) object;
	    
	    return id == attività.getId();
	  }
	  
	  /**
	   * Permette di ottenere l'identificatore dell'attività.
	   * 
	   * @return Long che rappresenta l'identificatore dell'attività
	   */
	  public long getId() {
	    return id;
	  }
	  
	  /**
	   * Permette di ottenere lo stato della domanda di tirocinio.
	   * 
	   * @return {@link Attività#IN_ATTESA} se l'attività è in attesa,
	   *         {@link Attività#VALIDATA} se la domanda è validata
	   */
	  public int getStatus() {
	    return status;
	  }
	  
	  /**
	   * Permette di specificare lo stato dell'attività di tirocinio.
	   * 
	   * @param status Intero che rappresenta lo stato dell'attività di tirocinio
	   * 
	   * @pre status = {@link Attività#IN_ATTESA} or
	   *      status = {@link Attività#VALIDATA} or
	   *      status = {@link Attività#NON_VALIDA}
	   *      
	   * @post getStatus() = status
	   */
	  public void setStatus(int status) {
	    this.status = status;
	  }	  
	  
	/**
	 *  Permette di ottenere il numero di ore dell'attività di tirocinio
	 *   
	 * @return un intero che rappresenta il numero di ore dell'attività di tirocinio
	 */
	public int getNumOre() {
		return numOre;
	}

	/**
	 * Permette di specificare il numero di ore dell'attività di tirocinio
	 * 
	 * @param d Oggetto int che rappresenta il numero di ore dell'attività di tirocinio
	 * 
	 * @pre numOre != null
	 * 
	 * @post getNumOre().isEqual(numOre)
	 */
	public void setNumOre(int d) {
		this.numOre = d;
	}

	public RegistroTirocinio getRegistroTirocinio() {
		return registroTirocinio;
	}

	public void setRegistroTirocinio(RegistroTirocinio registroTirocinio) {
		this.registroTirocinio = registroTirocinio;
	}

	/**
	 * 
	 * @return
	 */
	public LocalTime getOraInizio() {
		return oraInizio;
	}

	/**
	 * 
	 * @param oraInizio
	 */
	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public String getNomeAttivita() {
		return nomeAttivita;
	}

	public void setNomeAttivita(String nomeAttivita) {
		this.nomeAttivita = nomeAttivita;
	}

	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}

	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita= descrizioneAttivita;
	}

	/**
	   * Permette di ottenere la data e l'ora in cui l'attività è stata registrata.
	   * 
	   * @return Oggetto LocalDateTime che rappresenta data ed ora in cui l'attività è stata registrata
	   */
	  public LocalDateTime getData() {
	    return data;
	  }

	  /**
	   * Permette di specificare la data e l'ora in cui l'attività è stata registrata.
	   * 
	   * @param data Oggetto LocalDateTime che rappresenta data ed ora in cui l'attività è stata registrata
	   * 
	   * @pre data != null
	   * 
	   * @post getData().isEqual(data)
	   */
	  public void setData(LocalDateTime data) {
	    this.data = data;
	  }	  
	  
	  
	public DomandaTirocinio getDomandaTirocinio() {
		return domandaTirocinio;
	}

	public void setDomandaTirocinio(DomandaTirocinio domandaTirocinio) {
		this.domandaTirocinio = domandaTirocinio;
	}

	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  private int status;
	  private int numOre;
	  private LocalDateTime data;
	  private LocalTime oraInizio;
	  private LocalTime oraFine;
	  
	  @Lob 
	  private String nomeAttivita;
	  
	  @Lob
	  private String descrizioneAttivita;	  
	
	  @ManyToOne
	  private RegistroTirocinio registroTirocinio;

	  @OneToOne
	  private DomandaTirocinio domandaTirocinio;  
	  
	  /**
	   * Costante che rappresenta lo stato "in_attesa" di un'attività di tirocinio.
	   * Un'attività si trova in questo stato quando non è stata ancora gestita e validata
	   * dal Delegato Aziendale.
	   */
	  public static final int IN_ATTESA = 0;
	  
	  /**
	   * Costante che rappresenta lo stato "validato" di un'attività di tirocinio.
	   * Un'attività si trova in questo stato quando è stata gestita e validata
	   * dal Delegato Aziendale.
	   */
	  public static final int VALIDATA = 1;
	  
	  /**
	   * Costante che rappresenta lo stato "non_valida" di un'attività di tirocinio.
	   * Un'attività si trova in questo stato quando è stata gestita e e ritenuta non valida
	   * dal Delegato Aziendale.
	   */
	  public static final int NON_VALIDA = 2;
	  
	  /** Costante che definisce la minima lunghezza del campo nome. */
	  public static final int MIN_LUNGHEZZA_NOME = 2;
	  
	  /** Costante che definisce la massima lunghezza del campo nome. */
	  public static final int MAX_LUNGHEZZA_NOME = 255;
	  
	  /** Costante che definisce la minima lunghezza del campo descrizione. */
	  public static final int MIN_LUNGHEZZA_DESCRIZIONE = 2;
	  
	  /** Costante che definisce il numero minimo di CFU da poter associare ad una domanda. */
	  public static final int MIN_NUMORE = 1;
	  
	  /** Costante che definisce il numero massimo di CFU da poter associare ad una domanda. */
	  public static final int MAX_NUMORE = 12;
}
