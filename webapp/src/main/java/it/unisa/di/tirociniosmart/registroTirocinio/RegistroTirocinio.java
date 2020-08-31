package it.unisa.di.tirociniosmart.registroTirocinio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;

/**
 * Classe che modella un Registro di Tirocinio relativo ad una Domanda di Tirocinio
 * 
 * @See DomandaTirocinio
 */
@Entity
public class RegistroTirocinio {

	public RegistroTirocinio() {
	  this.attivitàTirocinio = new ArrayList<Attività>();
	}
	
	  /**
	   * Permette di determinare se due oggetti rappresentano lo stesso registro sulla base
	   * dell'identificatore.
	   */
	  public boolean equals(Object object) {
	    if (object == null) {
	      return false;
	    }
	    
	    if (getClass() != object.getClass()) {
	      return false;
	    }
	    
	    RegistroTirocinio registroTirocinio = (RegistroTirocinio) object;
	    
	    return id == registroTirocinio.getId();
	  }



	/**
    * Permette di ottenere l'identificatore del progetto formativo.
	* 
	* @return Long che rappresenta l'identificatore del progetto formativo
	*/
	public long getId() {
		return id;
	}

	  /**
	   * Permette di ottenere la lista delle attività di tirocinio associate al egistro di Tirocinio.
	   * 
	   * @return Lista delle attività di tirocinio associate al registro di Tirocinio
	   */
	  public List<Attività> getAttivitàTirocinio() {
	    return attivitàTirocinio;
	  }
	  
	  /**
	   * Permette di aggiungere un'attività di tirocinio alla lista di quelle associate al registro
	   * di tirocinio.
	   * 
	   * @param attività Oggetto {@link Attività} che rappresenta l'attività di
	   *                         tirocinio da associare al regitro di tirocinio
	   */
	  public void addAttivitàTirocinio(Attività attività) {
	    if (!attivitàTirocinio.contains(attività)) {
	      attivitàTirocinio.add(attività);
	    }
	  }

	  /**
	   * Permette di ottenere la{@link DomandaTirocinio} associata al registro di Tirocinio.
	   * 
	   * @return L'oggetto {@link DomandaTirocinio} associata al registro di Tirocinio
	   */  
	  public DomandaTirocinio getDomandaTirocinio() {
		  return domandaTirocinio;
	  }
	
	  /**
	   * Permette di specificare la domanda di Tirocinio relativa al registro. È possibile usare questo
	   * metodo per associare la domanda al Registro.
	   * 
	   * @param domandaTirocinio la{@link DomandaTirocinio} correlata al registro di Tirocinio
	   * 
	   * @pre domandaTiocinio != null
	   */
	public void setDomandaTirocinio(DomandaTirocinio domandaTirocinio) {
		if(!domandaTirocinio.equals(this.domandaTirocinio)) {
			this.domandaTirocinio = domandaTirocinio;			
		}
	}
	
	

	@Override
	public String toString() {
		return "RegistroTirocinio [id=" + id + ", status=" + status + ", oreValidate=" + oreValidate
				+ ", oreDaEffettuare=" + oreDaEffettuare + ", oreInAttesa=" + oreInAttesa + ", attivitàTirocinio="
				+ attivitàTirocinio + ", domandaTirocinio=" + domandaTirocinio + "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOreValidate() {
		return oreValidate;
	}

	public void setOreValidate(int oreValidate) {
		this.oreValidate = oreValidate;
	}

	public int getOreDaEffettuare() {
		return oreDaEffettuare;
	}

	public void setOreDaEffettuare(int oreDaEffettuare) {
		this.oreDaEffettuare = oreDaEffettuare;
	}

	public int getOreInAttesa() {
		return oreInAttesa;
	}

	public void setOreInAttesa(int oreInAttesa) {
		this.oreInAttesa = oreInAttesa;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int status;
	private int oreValidate;
	private int oreDaEffettuare;
	private int oreInAttesa;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "registroTirocinio")
	private List<Attività> attivitàTirocinio;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DomandaTirocinio domandaTirocinio;
	
	public static final int IN_ATTESA = 0;
	
	public static final int TERMINATO = 1;
	
	public static final int VALIDATO = 2;
}
