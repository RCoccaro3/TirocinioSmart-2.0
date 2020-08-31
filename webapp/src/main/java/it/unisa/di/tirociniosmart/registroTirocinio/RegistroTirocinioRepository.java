package it.unisa.di.tirociniosmart.registroTirocinio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroTirocinioRepository 
	extends JpaRepository<RegistroTirocinio, Long> {

	RegistroTirocinio findById(long id);
	
	  /**
	   * Permette di verificare se un progetto formativo esiste nel database 
	   * attraverso il proprio identificatore.
	   * 
	   * @param id long che rappresenta l'identificativo del progetto formativo
	   *               
	   * @return true se il progetto esiste nel database,
	   *         false se il progetto non esiste nel database
	   *         
	   * @pre id > 0
	   */
	 boolean existsById(long id);
		
}
