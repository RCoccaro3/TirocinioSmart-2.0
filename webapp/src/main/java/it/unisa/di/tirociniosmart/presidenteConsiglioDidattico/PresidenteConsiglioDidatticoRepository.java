package it.unisa.di.tirociniosmart.presidenteConsiglioDidattico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative al Presidente del Consiglio Didattico-
 * 
 * @see PresidenteConsiglioDidattico
 * 
 */
@Repository
public interface PresidenteConsiglioDidatticoRepository extends 
	JpaRepository<PresidenteConsiglioDidattico, String>{

	  /**
	   * Permette di ottenere un Presidente del Consiglio didattico a partire dalle 
	   * proprie credenziali di accesso.
	   * 
	   * @param password Stringa che rappresenta la password del Presidente
	   * @param username Stringa che rappresenta il nome utente del Presidente     
	   * 
	   * @return  Oggetto {@link PresidenteConsiglioDidattico} che rappresenta il presidente 
	   *          del consiglio didattico. <b>Può essere null</b> se nel database non è presente un 
	   *          presidente con username e password passati come parametro
	   * 
	   * @pre username != null && password != null
	   */
	PresidenteConsiglioDidattico findByUsernameAndPassword(String username, String password);
	
	  /**
	   * Permette di ottenere un Presidente del Consiglio Didattico a partire dal proprio nome utente.
	   * 
	   * @param username Stringa che rappresenta il nome utente del presidente     
	   * 
	   * @return Oggetto {@link PresidenteConsiglioDidattico} che rappresenta il Presidente
	   *         del consiglio didattico. <b>Può essere null</b> se nel database non è presente 
	   *         un presidente con l'username passata come parametro
	   *         
	   * @pre username != null
	   */
	PresidenteConsiglioDidattico findByUsername(String username);
}
