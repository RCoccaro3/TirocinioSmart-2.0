package it.unisa.di.tirociniosmart.studenti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



/**
 * Classe che definisce le operazioni per la modellazione e l'accesso 
 * alle informazioni persistenti relative ad uno studente.
 * 
 * @see Studente
 */
@Repository
public interface StudenteRepository extends JpaRepository<Studente, String> {

  /**
   * Permette di ottenere uno studente a partire dalle proprie credenziali di accesso.
   * 
   * @param password Stringa che rappresenta la password dello studente
   * @param username Stringa che rappresenta il nome utente dello studente     
   * 
   * @return  Oggetto {@link Studente} che rappresenta lo studente. <b>Può 
   *          essere null</b> se nel database non è presente uno studente con username 
   *          e password passati come parametro
   * 
   * @pre username != null && password != null
   */
  Studente findByUsernameAndPassword(String username, String password);
  
  /**
   * Permette di ottenere uno studente a partire dal proprio nome utente.
   * 
   * @param username Stringa che rappresenta il nome utente dello studente   
   * 
   * @return Oggetto {@link Studente} che rappresenta lo studente. <b>Può
   *         essere null</b> se nel database non è presente uno studente con l'username 
   *         passata come parametro
   *         
   * @pre username != null
   */
  Studente findByUsername(String username);
  
  /**
   * Permette di verificare se uno Studente esiste nel database attraverso la propria matricola.
   * 
   * @param matricola Stringa che rappresenta la matricola dello Studente
   *               
   * @return true se lo studente esiste nel database,
   *         false se lo studente non esiste nel database
   *         
   * @pre matricola != null
   */
  boolean existsByMatricola(String matricola);
}
