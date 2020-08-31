package it.unisa.di.tirociniosmart.domandetirocinio;

import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso alle informazioni persistenti
 * relative alle domande di tirocinio.
 * 
 * @see DomandaTirocinio
 */
@Repository
public interface DomandaTirocinioRepository
    extends JpaRepository<DomandaTirocinio, Long> {

  /**
   * Permette di ottenere l'elenco delle domande di tirocinio con un determinato status.
   * 
   * @param status Intero che rappresenta lo stato assegnato alla domanda di tirocinio
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre status = {@link DomandaTirocinio#IN_ATTESA} or
   *      status = {@link DomandaTirocinio#ACCETTATA} or
   *      status = {@link DomandaTirocinio#RIFIUTATA} or
   *      status = {@link DomandaTirocinio#APPROVATA} or
   *      status = {@link DomandaTirocinio#RESPINTA}
   */
  List<DomandaTirocinio> findAllByStatus(int status);
  
  /**
   * Permette di ottenere una domanda di tirocinio a partire dal suo identificatore.
   * 
   * @param id Numero di tipo long che rappresenta l'identificativo della domanda di
   *           tirocinio
   *           
   * @return Oggetto {@link DomandaTirocinio} che rappresenta la domanda di tirocinio. <b>Può
   *          essere null</b> se nel database non è presente una domanda di tirocinio 
   *          con l'id passato come parametro
   * 
   * @pre id > 0
   * 
   */
  DomandaTirocinio findById(long id);
  
  /**
   * Permette di ottenere l'elenco delle domande di tirocinio inviate da un 
   * determinato studente.
   * 
   * @param username Stringa che rappresenta l'username dello studente assegnato alla domanda di 
   *                 tirocinio
   *                 
   * @param status intero che rappresenta lo stato assegnato alla domanda di tirocinio
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre studente != null
   *      status > 0
   * 
   */
  List<DomandaTirocinio> findAllByStatusAndStudenteUsername(int status, String username);
  
  /**
   * Permette di ottenere l'elenco delle domande di tirocinio inviate con un determinato stato
   * giunte ad una determinata azienda.
   * 
   * @param status int che rappresenta lo stato assegnato alla domanda di  tirocinio
   *     
   * @param idAzienda Striga che rappresenta l'id dell'azienda
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   *                                         
   * @pre int > 0
   * @pre idAzienda != null
   * 
   */
  List<DomandaTirocinio> findAllByStatusAndProgettoFormativoAziendaId(int status, String idAzienda);
  
  /**
   * Permette di ottenere l'elenco delle domande di tirocinio a partire dal progetto formativo 
   * selezionato.
   * 
   * @param idProgetto Intero lungo che rappresenta l'id dell'oggetto {@link ProgettoFormativo} 
   *                   associato alla domanda di tirocinio
   *           
   * @return Lista di {@link DomandaTirocinio} che rappresenta la lista delle domande 
   *         di tirocinio
   * 
   */
  List<DomandaTirocinio> findAllByProgettoFormativoId(long idProgetto);
  
  /**
   * Permette di ottenere la lista di domande di tirocinio inviate da uno studente,
   * indipendentemente dallo stato in cui esse si trovano.
   * 
   * @param username String che rappresenta l'username dell'utente per cui si cercano domande
   * 
   * @return Lista delle domande di tirocinio inviate da uno studente
   */
  List<DomandaTirocinio> findAllByStudenteUsername(String username);
  
  /**
   * Permette di verificare se una domanda di tirocinio esiste nel database 
   * attraverso il proprio identificatore.
   * 
   * @param id long che rappresenta l'identificativo della domanda di tirocinio
   *               
   * @return true se la domanda esiste nel database,
   *         false se la domanda non esiste nel database
   *         
   * @pre id > 0
   */
  boolean existsById(long id);
}
