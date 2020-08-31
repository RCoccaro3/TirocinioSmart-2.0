package it.unisa.di.tirociniosmart.progettiformativi;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.AziendaRepository;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Classe che definisce i casi di test per le operazioni sul database inerenti all'azienda e
 * definite dalla relativa repository.
 *
 * @see ProgettoFormativo
 * @see ProgettoFormativoRepository
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProgettoFormativoRepositoryIT {

  private List<ProgettoFormativo> listaProgettiFormativi;
  
  @Autowired
  private ProgettoFormativoRepository progettoRepository;
  
  @Autowired
  private AziendaRepository aziendaRepository;
  
  
  /**
   * Salva la lista dei progetti formativi su database prima di ogni singolo test.
   */
  @Before
  public void salvaProgettiFormativi() {
    listaProgettiFormativi = new ArrayList<ProgettoFormativo>();
    
    // Crea l'azienda possedente il progetto formativo #1
    Azienda azienda1 = new Azienda();
    azienda1.setId("acmeltd");
    azienda1.setNome("ACME Ltd.");
    azienda1.setPartitaIva("01234567890");
    azienda1.setSenzaBarriere(true);
    azienda1.setIndirizzo("Grand Canyon");
    
    DelegatoAziendale delegato1 = azienda1.getDelegato();
    delegato1.setUsername("wilee");
    delegato1.setPassword("beepbeep");
    delegato1.setEmail("wilee@coyote.com");
    delegato1.setNome("Wile E.");
    delegato1.setCognome("Coyote");
    delegato1.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato1.setTelefono("9876543210");
    
    RichiestaConvenzionamento richiesta1 = azienda1.getRichiesta();
    richiesta1.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta1.setDataRichiesta(LocalDateTime.of(2017, 12, 8, 23, 55));
    
    azienda1 = aziendaRepository.save(azienda1);
    
    
    // Crea l'azienda #2 possedente il progetto formativo #2
    Azienda azienda2 = new Azienda();
    azienda2.setId("starkind");
    azienda2.setNome("Stark Industries");
    azienda2.setPartitaIva("74598763241");
    azienda2.setSenzaBarriere(true);
    azienda2.setIndirizzo("Marvel Valley, 45");
    
    DelegatoAziendale delegato2 = azienda2.getDelegato();
    delegato2.setUsername("tonystark");
    delegato2.setPassword("ironman");
    delegato2.setEmail("tony@starkind.com");
    delegato2.setNome("Anthony Edward");
    delegato2.setCognome("Stark");
    delegato2.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato2.setTelefono("7485214786");
    
    RichiestaConvenzionamento richiesta2 = azienda2.getRichiesta();
    richiesta2.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta2.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
    
    azienda2 = aziendaRepository.save(azienda2);
   
    
    // Crea l'azienda possedente il progetto formativo #3
    Azienda azienda3 = new Azienda();
    azienda3.setId("cyberdynecorp");
    azienda3.setNome("Cyberdyne System Corporation");
    azienda3.setPartitaIva("54569814752");
    azienda3.setSenzaBarriere(false);
    azienda3.setIndirizzo("Steel Mountain, 57");
    
    DelegatoAziendale delegato3 = azienda3.getDelegato();
    delegato3.setUsername("milesdyson");
    delegato3.setPassword("terminator");
    delegato3.setEmail("miles@cyberdyne.net");
    delegato3.setNome("Miles");
    delegato3.setCognome("Dyson");
    delegato3.setSesso(UtenteRegistrato.SESSO_MASCHILE);
    delegato3.setTelefono("7451453658");
    
    RichiestaConvenzionamento richiesta3 = azienda3.getRichiesta();
    richiesta3.setStatus(RichiestaConvenzionamento.APPROVATA);
    richiesta3.setDataRichiesta(LocalDateTime.of(2017, 12, 31, 23, 59));
    
    azienda3 = aziendaRepository.save(azienda3);
    
    
    // Crea progetto formativo #1
    ProgettoFormativo progetto1 = new ProgettoFormativo();
    progetto1.setAzienda(azienda1);
    progetto1.setNome("ProjectX");
    progetto1.setDescrizione("descrizioneeeeee");
    progetto1.setStatus(ProgettoFormativo.ATTIVO);

    progetto1 = progettoRepository.save(progetto1);
    listaProgettiFormativi.add(progetto1);
    
    
    // Crea progetto formativo #2
    ProgettoFormativo progetto2 = new ProgettoFormativo();
    progetto2.setAzienda(azienda2);
    progetto2.setNome("Assiri");
    progetto2.setDescrizione("descrizioneeeeee");
    progetto2.setStatus(ProgettoFormativo.ARCHIVIATO);

    progetto2 = progettoRepository.save(progetto2);
    listaProgettiFormativi.add(progetto2);
    
    
    // Crea progetto formativo #3
    ProgettoFormativo progetto3 = new ProgettoFormativo();
    progetto3.setAzienda(azienda3);
    progetto3.setNome("America");
    progetto3.setDescrizione("descrizioneeeeee");
    progetto3.setStatus(ProgettoFormativo.ATTIVO);

    progetto3 = progettoRepository.save(progetto3);
    listaProgettiFormativi.add(progetto3);
    
    progettoRepository.flush();
    aziendaRepository.flush();
  }
  
  /**
   * Testa l'interazione con il database per il singolo caricamento dei progetti formativi della 
   * lista tramite identificatore.
   * 
   * @test {@link ProgettoFormativoRepository#findById(String)}
   * 
   * @result Il test è superato se ogni entità viene correttamente caricata dal database
   */
  @Test
  public void findById() {
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      ProgettoFormativo progettoSalvato = progettoRepository.findById(progetto.getId());
      assertThat(progetto, is(equalTo(progettoSalvato)));
    }
  }
  
  /**
   * Testa l'interazione con il database per il caricamento della lista di progetti formativi di una
   * data azienda che si trovano in uno stato specificato.
   * 
   * @test {@link ProgettoFormativoRepository#findAllByStatusAndAziendaId(int)}
   * 
   * @result Il test è superato se sono caricate solo i progetti formativi dell' azienda il cui 
   *         stato si trova nello stato specificato
   */
  @Test
  public void findAllByStatusAndAziendaId() {
    List<ProgettoFormativo> progettiAttiviAzienda = new ArrayList<ProgettoFormativo>();
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      if (progetto.getStatus() == ProgettoFormativo.ATTIVO 
          && progetto.getAzienda().getNome().equals("acmeltd")) { 
        progettiAttiviAzienda.add(progetto);
      }
    }
    
    // Controlla che ogni elemento della lista restituita dalla repository sia nella lista
    // utilizzata per il test
    List<ProgettoFormativo> progettiSalvati = progettoRepository.findAllByStatusAndAziendaId(
         ProgettoFormativo.ATTIVO, listaProgettiFormativi.get(0).getAzienda().getId());
    assertThat(progettiAttiviAzienda, everyItem(isIn(progettiSalvati)));
      
  }
  
  /**
   * Testa l'interazione con il database per determinare se la ricerca di un progetto formativo
   * tramite id avvenga correttamente.
   * 
   * @test {@link ProgettoFormativoRepository#existsById(String)}
   * 
   * @result Il test è superato se la ricerca degli id dei progetti formativi presenti nella lista
   *         utilizzata per il test ha successo
   */
  @Test
  public void existById() {
    for (ProgettoFormativo progetto: listaProgettiFormativi) {
      boolean progettoEsistente = progettoRepository.existsById(progetto.getId());
      assertThat(progettoEsistente, is(true));
    }
  }
}
