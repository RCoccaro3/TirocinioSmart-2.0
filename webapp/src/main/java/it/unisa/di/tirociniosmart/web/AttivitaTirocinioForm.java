package it.unisa.di.tirociniosmart.web;

/**
 * Oggetto utilizzato per la mappatura dei campi del form di aggiunta di
 * un'attività di tirocinio HTML. Questo oggetto viene passato come parametro
 * ai controller dalla dispatcher della servlet quando uno studente sottomette
 * il modulo per aggiungere una nuova attività di tirocinio.
 * 
 * @author rosan
 *
 */
public class AttivitaTirocinioForm {

	public AttivitaTirocinioForm() {
		
	}
	
    public Integer getStatus() {
		return status;
	}
    
	public void setStatus(Integer status) {
		this.status = status;
	} 
	
	public Integer getNumOre() {
		return numOre;
	}
	
	public void setNumOre(Integer numOre) {
		this.numOre = numOre;
	} 
	
	public Integer getOraInizio() {
		return oraInizio;
	}
	
	public void setOraInizio(Integer oraInizio) {
		this.oraInizio = oraInizio;
	}
	
	public Integer getMinutiInizio() {
		return minutiInizio;
	}
	
	public void setMinutiInizio(Integer minutiInizio) {
		this.minutiInizio = minutiInizio;
	}
	
	public Integer getOraFine() {
		return oraFine;
	}
	
	public void setOraFine(Integer oraFine) {
		this.oraFine = oraFine;
	}
	
	public Integer getMinutiFine() {
		return minutiFine;
	}
	
	public void setMinutiFine(Integer minutiFine) {
		this.minutiFine = minutiFine;
	}
	
	public String getDescrizioneAttivita() {
		return descrizioneAttivita;
	}
	
	public void setDescrizioneAttivita(String descrizioneAttivita) {
		this.descrizioneAttivita = descrizioneAttivita;
	}
	
	
	public String getNomeAttivita() {
		return nomeAttivita;
	}
	
	public void setNomeAttivita(String nomeAttivita) {
		this.nomeAttivita = nomeAttivita;
	}
	
	private String nomeAttivita;
	private Integer status;
	private Integer numOre;
	private Integer oraInizio;
	private Integer minutiInizio;
	private Integer oraFine;
	private Integer minutiFine;
	private String descrizioneAttivita;

}

