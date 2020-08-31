package it.unisa.di.tirociniosmart.registroTirocinio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;

/**
 * Classe che definisce le operazioni per la gestione l'accesso alle informazioni persistenti
 * relative alle attività di tirocinio da memorizzare nel registro di tirocinio
 * 
 * @see Attività
 *
 */
@Repository
public interface AttivitàRepository 
	extends JpaRepository<Attività, Long>{

	/**
	 * Permette di ottenere l'eenco delle attività con un determinato status
	 * 
	 * @param status Intero che rappresenta lo stato assegnato all'attività di tirocinio
	 * 
	 * @return Lista di {@link Attività} che rappresenta la lista delle attività di tirocinio
	 * 
	 * @pre status = {@link Attività#IN_ATTESA} or 
	 * 		status = {@link Attività#VALIDATA} or
	 * 		status = {@link Attività#NON_VALIDA}
	 */
	List<Attività> findAllByStatus(int status);
	
	/**
     * Permette di ottenere un'attività di tirocinio a partire dal suo identificatore.
     * 
     * @param id Numero di tipo long che rappresenta l'identificativo dell'attività di
     *           tirocinio
     *           
     * @return Oggetto {@link Attività} che rappresenta l'attività di tirocinio. <b>Può
     *          essere null</b> se nel database non è presente un'attività di tirocinio 
     *          con l'id passato come parametro
     * 
     * @pre id > 0
     * 		   
   	 */
	Attività findById(long id);
	
	/**
	 * Permette di ottenere l'elenco delle attività di tirocinio a partire dalla domanda di tirocinio
	 * selezionato.
	 * 
	 * @param idDomanda Intero long che rappresenta l'id dell'oggetto {@link DomandaTirocinio} 
	 *                  associata alle attività di tirocinio
	 *           
	 * @return Lista di {@link Attività} che rappresenta la lista delle attività 
	 *         di tirocinio
	 * 
	 */
	List<Attività> findAllByDomandaTirocinioId(long idDomanda);
	
	/**
	 * Permette di ottenere l'elenco delle attività di tirocinio a partire dal registro di tirocinio
	 * selezionato.
	 * 
	 * @param idRegistro Intero long che rappresenta l'id dell'oggetto {@link RegistroTirocinio} 
	 *                   associato alle attività di tirocinio
	 *           
	 * @return Lista di {@link Attività} che rappresenta la lista delle attività 
	 *         di tirocinio
	 * 
	 */
	List<Attività> findAllByRegistroTirocinioId(long idRegistro);

	/**
	 * Permette di ottenere l'elenco delle attività di tirocinio a partire dal registro di tirocinio
	 * selezionato.
	 * 
	 * @param idRegistro Intero long che rappresenta l'id dell'oggetto {@link RegistroTirocinio} 
	 *                   associato alle attività di tirocinio
	 *           
	 * @return Lista di {@link Attività} che rappresenta la lista delle attività 
	 *         di tirocinio
	 * 
	 */
	List<Attività> findAllByStatusAndDomandaTirocinioId(int status, long idDomanda);
}
