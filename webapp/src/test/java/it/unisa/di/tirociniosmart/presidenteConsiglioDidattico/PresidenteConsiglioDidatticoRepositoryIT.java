package it.unisa.di.tirociniosmart.presidenteConsiglioDidattico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import it.unisa.di.tirociniosmart.presidenteConsiglioDidattico.PresidenteConsiglioDidattico;
import it.unisa.di.tirociniosmart.presidenteConsiglioDidattico.PresidenteConsiglioDidatticoRepository;

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti al presidente
 * del consiglio didattico e definite dalla relativa repository.
 * 
 * @see PresidenteConsiglioDidattico
 * @see PresidenteConsiglioDidatticoRepository
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PresidenteConsiglioDidatticoRepositoryIT {
	
	@Autowired
	private PresidenteConsiglioDidatticoRepository repository;
	
	private List<PresidenteConsiglioDidattico> lista;

	/**
	 * Salva la lista di presidenti del consiglio didattico su database prima dell'esecuzione
	 * di ogni singolo test.
	 */
	@Before
	public void salvaPresidenti() {
		lista = new ArrayList<PresidenteConsiglioDidattico>();
		
		//Crea oggetto presidente #1
		PresidenteConsiglioDidattico presidente1 = new PresidenteConsiglioDidattico();
		presidente1.setNome("Giovanni");
		presidente1.setCognome("Verdi");
		presidente1.setUsername("Giovanni55");
		presidente1.setPassword("GiovanniUnisa");
		presidente1.setEmail("gverdi@unisa.it");
		
		presidente1 = repository.save(presidente1);
		lista.add(presidente1);
		
		//Crea oggetto presidente #2
		PresidenteConsiglioDidattico presidente2 = new PresidenteConsiglioDidattico();
		presidente2.setNome("Attilio");
		presidente2.setCognome("Venuti");
		presidente2.setUsername("VenutiAttilio65");
		presidente2.setPassword("VenutiAttilio65");
		presidente2.setEmail("avenuti@unisa.it");
					
		presidente2 = repository.save(presidente2);
		lista.add(presidente2);
		
		//Crea oggetto presidente #3
		PresidenteConsiglioDidattico presidente3 = new PresidenteConsiglioDidattico();
		presidente3.setNome("Valeria");
		presidente3.setCognome("Grippo");
		presidente3.setUsername("ValeriaGRP");
		presidente3.setPassword("ValeriaGRP");
		presidente3.setEmail("vgrippo@unisa.it");
							
		presidente3 = repository.save(presidente3);
		lista.add(presidente3);
		
		repository.flush();
	}
	
	/**
	 * Testa l'interazione con il database per il caricamento della lista di 
	 * presidenti tramite username e password.
	 * 
	 * @test {@link PresidenteConsiglioDidatticoRepository#findByUsernameAndPassword(String, String)}
	 * 
	 * @result Il test è superato se l'entità coivolta viene correttamente caricata dal database
	 */
	@Test
	public void findByUsernameAndPassword() {
		//Controlla che ogni presidente della lista per il test sia presente su database ricercandolo 	
		//per username e password
		for(PresidenteConsiglioDidattico presidente: lista) {
			PresidenteConsiglioDidattico salvato = repository
					.findByUsernameAndPassword(presidente.getUsername(), presidente.getPassword());
			
			assertThat(presidente, is(equalTo(salvato)));
		}
	}
	/**
	 * Testa l'interazione con il database per il caricamento della lista di 
	 * presidenti tramite username.
	 * 
	 * @test {@link PresidenteConsiglioDidatticoRepository#findByUsername(String)}
	 * 
	 * @result Il test è superato se l'entità coivolta viene correttamente caricata dal database
	 */
	@Test
	public void findByUsername() {
		//Controlla che ogni presidente inserito per il test sia presente su database ricercandolo
	    //per username
		for(PresidenteConsiglioDidattico presidente: lista) {
			PresidenteConsiglioDidattico salvato = repository
					.findByUsername(presidente.getUsername());
			
			assertThat(presidente, is(equalTo(salvato)));
		}
	}	
}
