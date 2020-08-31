package it.unisa.di.tirociniosmart.registroTirocinio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.CommentoRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.ConvenzioniService;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaEsistenteException;
import it.unisa.di.tirociniosmart.convenzioni.IdAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.IdRichiestaConvenzionamentoNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.IndirizzoAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.NomeAziendaNonValidoException;
import it.unisa.di.tirociniosmart.convenzioni.PartitaIvaAziendaEsistenteException;
import it.unisa.di.tirociniosmart.convenzioni.PartitaIvaAziendaNonValidaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoGestitaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoInAttesaException;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamentoRifiutataException;
import it.unisa.di.tirociniosmart.domandetirocinio.CommentoDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.DataDiFineTirocinioNonValidaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DataDiInizioTirocinioNonValidaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinioGestitaException;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandeTirocinioService;
import it.unisa.di.tirociniosmart.domandetirocinio.IdDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.NumeroCfuNonValidoException;
import it.unisa.di.tirociniosmart.domandetirocinio.ProgettoFormativoArchiviatoException;
import it.unisa.di.tirociniosmart.domandetirocinio.StatoDomandaNonIdoneoException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirociniRepository;
import it.unisa.di.tirociniosmart.presidenteConsiglioDidattico.PresidenteConsiglioDidattico;
import it.unisa.di.tirociniosmart.progettiformativi.DescrizioneProgettoNonValidaException;
import it.unisa.di.tirociniosmart.progettiformativi.NomeProgettoNonValidoException;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettiFormativiService;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.DataDiNascitaStudenteNonValidaException;
import it.unisa.di.tirociniosmart.studenti.IdRichiestaIscrizioneNonValidoException;
import it.unisa.di.tirociniosmart.studenti.IndirizzoStudenteNonValidoException;
import it.unisa.di.tirociniosmart.studenti.MatricolaStudenteEsistenteException;
import it.unisa.di.tirociniosmart.studenti.MatricolaStudenteNonValidaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneGestitaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneInAttesaException;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizioneRifiutataException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.studenti.StudentiService;
import it.unisa.di.tirociniosmart.utenza.CognomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.CredenzialiNonValideException;
import it.unisa.di.tirociniosmart.utenza.EmailEsistenteException;
import it.unisa.di.tirociniosmart.utenza.EmailNonValidaException;
import it.unisa.di.tirociniosmart.utenza.NomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.PasswordNonValidaException;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.SessoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.TelefonoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UsernameEsistenteException;
import it.unisa.di.tirociniosmart.utenza.UsernameNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;


/**
 * Testa i servizi offerti da {@link RegistroTirocinioService} con tutte le dipedenze.
 * 
 * @see RegistroTirocinioService  
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class RegistroTirocinioServiceIT {

	@Autowired
	private UtenzaService utenzaService;
	
	@Autowired
	private RegistroTirocinioService registroService;
	
	@Autowired 
	private ImpiegatoUfficioTirociniRepository impiegatoRepository;

	@Autowired
	private ConvenzioniService convenzioniService;
	
	@Autowired
	private ProgettiFormativiService progettiService;
	  
	@Autowired
	private StudentiService studentiService;
	
	@Autowired
	private DomandeTirocinioService domandeService;

	  /**
	   * Testa il metodo che permette ad uno studente di inserire un'attività di tirocinio
	   * all'interno del registro di tirocinio 
	   * 
	   * @test {@link RegistroTirocinioService#aggiungiAttivitaTirocinio(Attività, long)}
	   * 
	   * @result il test è superato se l'attività di tirocinio viene salvata correttamente 
	   * 
	   */
	  @Test
	  public void aggiungiAttivitaTirocinio() {
	    // Crea un azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("starkind");
	    azienda.setNome("Stark Industries");
	    azienda.setPartitaIva("74598763241");
	    azienda.setSenzaBarriere(true);
	    azienda.setIndirizzo("Marvel Valley, 45");
	    
	    // Crea un delegato da associare all'azienda
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setUsername("tonystark");
	    delegato.setPassword("ironman");
	    delegato.setEmail("tony@starkind.com");
	    delegato.setNome("Anthony Edward");
	    delegato.setCognome("Stark");
	    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
	    delegato.setTelefono("7485214786");
	    
	    // Crea una richiesta di convenzionamento
	    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
	    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
	    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
	    
	    utenzaService.setUtenteAutenticato(null);
	    
	    //Registra richiesta di convenzionamente
	    try {
	      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
	      richiesta = azienda.getRichiesta();
	    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
	        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
	        | IdAziendaNonValidoException | IdAziendaEsistenteException
	        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
	        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
	        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
	      fail(e.getLocalizedMessage());
	    }
	    
	    // Crea un impiegato
	    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
	    impiegato.setNome("Antonio");
	    impiegato.setCognome("Albanese");
	    impiegato.setEmail("antonio@albanese.com");
	    impiegato.setUsername("impiegato");
	    impiegato.setPassword("impiegato");
	    
	    // Inserisci l'impiegato nel sistema
	    impiegatoRepository.save(impiegato);
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta convenzionamento
	    try {
	      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
	    } catch (IdRichiestaConvenzionamentoNonValidoException
	        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Esegue logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea un nuovo progetto formativo
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("Sviluppo applicazioni web");
	    progetto.setAzienda(azienda);
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Aggiunge un nuovo progetto formativo
	    try {
	      progettiService.aggiungiProgettoFormativo(progetto);
	    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
	        | DescrizioneProgettoNonValidaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Crea lo studente 
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.now());
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescoF");
	    
	    // Crea la richiesta iscrizione
	    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
	    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
	    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
	    richiestaIscrizione.setCommentoUfficioTirocini("commento");
	    
	    // Registra richiesta d'iscrizione sul database
	    try {
	      studente = studentiService.registraRichiestaIscrizione(studente);
	      richiestaIscrizione = studente.getRichiestaIscrizione();   
	    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
	        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
	        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
	        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	      e.printStackTrace();
	    }
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta d'iscrizione dello studente
	    try {
	      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
	    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea una domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStudente(studente);
	    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
	    domanda.setCfu(8);
	    domanda.setData(LocalDateTime.now());
	    domanda.setInizioTirocinio(LocalDate.of(2021, 03, 11));
	    domanda.setFineTirocinio(LocalDate.of(2021, 04, 01));
	    domanda.setCommentoStudente("commento studente");
	    
	    //Effettua login dello studente
	    try {
	      utenzaService.login("FrancescoF", "FrancescoF");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	   
	    try {
	      // Registra domanda di tirocinio sul database
	      domanda = domandeService.registraDomandaTirocinio(domanda);
	    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
	        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
	        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    //
	    try {
	      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
	    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
	    presidente.setUsername("fferrucci");
	    presidente.setPassword("fferrucci");
	    
	 // Effettua il login come presidente
	    try {
	      utenzaService.login("fferrucci", "fferrucci");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.validaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException | DomandaTirocinioNonValidataException e) {
	      fail(e.getMessage());
	    }
	    
	   //Effettua il logout del Presidente
	    utenzaService.logout();
	 
	    RegistroTirocinio registro = domanda.getRegistroTirocinio();
	    	    
	    Attività attivita = new Attività();
	    attivita.setNomeAttivita("Ricerca");
	    attivita.setDescrizioneAttivita("ricerca materiale tesi");
	    attivita.setRegistroTirocinio(registro);
	    attivita.setOraInizio(LocalTime.now().minusHours(2));
	    attivita.setOraFine(LocalTime.now());
	    attivita.setNumOre(2);
	    attivita.setStatus(Attività.IN_ATTESA);	 
	    
	    try {
			utenzaService.login("FrancescoF", "FrancescoF");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());			
		}
	    
	    Attività attivitaSalvata = new Attività();
	    try {
			attivitaSalvata = registroService.aggiungiAttivitaTirocinio(attivita, registro.getId());
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | DomandaTirocinioNonValidataException
				| NumOreNonValidoException | OraDiInizioAttivitàNonValidaException
				| OraDiFineAttivitàNonValidaException e) {
			e.printStackTrace();
		}
	    
	    assertThat(attivita, is(equalTo(attivitaSalvata)));
	    
	    //effettua logout dello studente
	    utenzaService.logout();	    
	} 
	  
	  /**
	   * Testa il metodo che permette ad un delegato aziendale di validare un'attività di tirocinio
	   * all'interno del registro di tirocinio 
	   * 
	   * @test {@link RegistroTirocinioService#validaAttività(long)}
	   * 
	   * @result il test è superato se l'attività di tirocinio viene validata correttamente 
	   * 
	   */
	  @Test
	  public void validaAttività() {
	    // Crea un azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("starkind");
	    azienda.setNome("Stark Industries");
	    azienda.setPartitaIva("74598763241");
	    azienda.setSenzaBarriere(true);
	    azienda.setIndirizzo("Marvel Valley, 45");
	    
	    // Crea un delegato da associare all'azienda
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setUsername("tonystark");
	    delegato.setPassword("ironman");
	    delegato.setEmail("tony@starkind.com");
	    delegato.setNome("Anthony Edward");
	    delegato.setCognome("Stark");
	    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
	    delegato.setTelefono("7485214786");
	    
	    // Crea una richiesta di convenzionamento
	    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
	    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
	    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
	    
	    utenzaService.setUtenteAutenticato(null);
	    
	    //Registra richiesta di convenzionamente
	    try {
	      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
	      richiesta = azienda.getRichiesta();
	    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
	        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
	        | IdAziendaNonValidoException | IdAziendaEsistenteException
	        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
	        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
	        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
	      fail(e.getLocalizedMessage());
	    }
	    
	    // Crea un impiegato
	    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
	    impiegato.setNome("Antonio");
	    impiegato.setCognome("Albanese");
	    impiegato.setEmail("antonio@albanese.com");
	    impiegato.setUsername("impiegato");
	    impiegato.setPassword("impiegato");
	    
	    // Inserisci l'impiegato nel sistema
	    impiegatoRepository.save(impiegato);
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta convenzionamento
	    try {
	      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
	    } catch (IdRichiestaConvenzionamentoNonValidoException
	        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Esegue logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea un nuovo progetto formativo
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("Sviluppo applicazioni web");
	    progetto.setAzienda(azienda);
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Aggiunge un nuovo progetto formativo
	    try {
	      progettiService.aggiungiProgettoFormativo(progetto);
	    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
	        | DescrizioneProgettoNonValidaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Crea lo studente 
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.now());
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescoF");
	    
	    // Crea la richiesta iscrizione
	    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
	    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
	    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
	    richiestaIscrizione.setCommentoUfficioTirocini("commento");
	    
	    // Registra richiesta d'iscrizione sul database
	    try {
	      studente = studentiService.registraRichiestaIscrizione(studente);
	      richiestaIscrizione = studente.getRichiestaIscrizione();   
	    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
	        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
	        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
	        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	      e.printStackTrace();
	    }
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta d'iscrizione dello studente
	    try {
	      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
	    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea una domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStudente(studente);
	    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
	    domanda.setCfu(8);
	    domanda.setData(LocalDateTime.now());
	    domanda.setInizioTirocinio(LocalDate.of(2021, 03, 11));
	    domanda.setFineTirocinio(LocalDate.of(2021, 04, 01));
	    domanda.setCommentoStudente("commento studente");
	    
	    //Effettua login dello studente
	    try {
	      utenzaService.login("FrancescoF", "FrancescoF");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	   
	    try {
	      // Registra domanda di tirocinio sul database
	      domanda = domandeService.registraDomandaTirocinio(domanda);
	    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
	        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
	        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    //
	    try {
	      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
	    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
	    presidente.setUsername("fferrucci");
	    presidente.setPassword("fferrucci");
	    
	 // Effettua il login come presidente
	    try {
	      utenzaService.login("fferrucci", "fferrucci");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.validaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException | DomandaTirocinioNonValidataException e) {
	      fail(e.getMessage());
	    }
	    
	   //Effettua il logout del Presidente
	    utenzaService.logout();
	 
	    RegistroTirocinio registro = domanda.getRegistroTirocinio();
	    	    
	    Attività attivita = new Attività();
	    attivita.setNomeAttivita("Ricerca");
	    attivita.setDescrizioneAttivita("ricerca materiale tesi");
	    attivita.setRegistroTirocinio(registro);
	    attivita.setOraInizio(LocalTime.now().minusHours(2));
	    attivita.setOraFine(LocalTime.now());
	    attivita.setNumOre(2);
	    attivita.setStatus(Attività.IN_ATTESA);	 
	    
	    try {
			utenzaService.login("FrancescoF", "FrancescoF");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());			
		}
	    
	    try {
			attivita = registroService.aggiungiAttivitaTirocinio(attivita, registro.getId());
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | DomandaTirocinioNonValidataException
				| NumOreNonValidoException | OraDiInizioAttivitàNonValidaException
				| OraDiFineAttivitàNonValidaException e) {
			fail(e.getMessage());
		}
	    
	    //effettua logout dello studente
	    utenzaService.logout();	 
	    
	    //Effettua login del delegato
	    try {
			utenzaService.login("tonystark", "ironman");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());
		}
	    
	    try {
			attivita = registroService.validaAttività(attivita.getId());
		} catch (RichiestaNonAutorizzataException | IdAttivitaTirocinioNonValidoException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	    
	    assertThat(attivita.getStatus(), is((equalTo(Attività.VALIDATA))));
	
	  //Effettua logout del delegato
	    utenzaService.logout();
	 }
	  	
	  /**
	   * Testa il metodo che permette ad un delegato aziendale di invalidare un'attività di tirocinio
	   * all'interno del registro di tirocinio 
	   * 
	   * @test {@link RegistroTirocinioService#annullaAttività(long)}
	   * 
	   * @result il test è superato se l'attività di tirocinio viene annullata correttamente 
	   * 
	   */
	  @Test
	  public void annullaAttività() {
	    // Crea un azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("starkind");
	    azienda.setNome("Stark Industries");
	    azienda.setPartitaIva("74598763241");
	    azienda.setSenzaBarriere(true);
	    azienda.setIndirizzo("Marvel Valley, 45");
	    
	    // Crea un delegato da associare all'azienda
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setUsername("tonystark");
	    delegato.setPassword("ironman");
	    delegato.setEmail("tony@starkind.com");
	    delegato.setNome("Anthony Edward");
	    delegato.setCognome("Stark");
	    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
	    delegato.setTelefono("7485214786");
	    
	    // Crea una richiesta di convenzionamento
	    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
	    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
	    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
	    
	    utenzaService.setUtenteAutenticato(null);
	    
	    //Registra richiesta di convenzionamente
	    try {
	      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
	      richiesta = azienda.getRichiesta();
	    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
	        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
	        | IdAziendaNonValidoException | IdAziendaEsistenteException
	        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
	        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
	        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
	      fail(e.getLocalizedMessage());
	    }
	    
	    // Crea un impiegato
	    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
	    impiegato.setNome("Antonio");
	    impiegato.setCognome("Albanese");
	    impiegato.setEmail("antonio@albanese.com");
	    impiegato.setUsername("impiegato");
	    impiegato.setPassword("impiegato");
	    
	    // Inserisci l'impiegato nel sistema
	    impiegatoRepository.save(impiegato);
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta convenzionamento
	    try {
	      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
	    } catch (IdRichiestaConvenzionamentoNonValidoException
	        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Esegue logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea un nuovo progetto formativo
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("Sviluppo applicazioni web");
	    progetto.setAzienda(azienda);
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Aggiunge un nuovo progetto formativo
	    try {
	      progettiService.aggiungiProgettoFormativo(progetto);
	    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
	        | DescrizioneProgettoNonValidaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Crea lo studente 
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.now());
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescoF");
	    
	    // Crea la richiesta iscrizione
	    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
	    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
	    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
	    richiestaIscrizione.setCommentoUfficioTirocini("commento");
	    
	    // Registra richiesta d'iscrizione sul database
	    try {
	      studente = studentiService.registraRichiestaIscrizione(studente);
	      richiestaIscrizione = studente.getRichiestaIscrizione();   
	    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
	        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
	        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
	        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	      e.printStackTrace();
	    }
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta d'iscrizione dello studente
	    try {
	      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
	    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea una domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStudente(studente);
	    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
	    domanda.setCfu(8);
	    domanda.setData(LocalDateTime.now());
	    domanda.setInizioTirocinio(LocalDate.of(2021, 03, 11));
	    domanda.setFineTirocinio(LocalDate.of(2021, 04, 01));
	    domanda.setCommentoStudente("commento studente");
	    
	    //Effettua login dello studente
	    try {
	      utenzaService.login("FrancescoF", "FrancescoF");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	   
	    try {
	      // Registra domanda di tirocinio sul database
	      domanda = domandeService.registraDomandaTirocinio(domanda);
	    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
	        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
	        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    //
	    try {
	      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
	    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
	    presidente.setUsername("fferrucci");
	    presidente.setPassword("fferrucci");
	    
	 // Effettua il login come presidente
	    try {
	      utenzaService.login("fferrucci", "fferrucci");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.validaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException | DomandaTirocinioNonValidataException e) {
	      fail(e.getMessage());
	    }
	    
	   //Effettua il logout del Presidente
	    utenzaService.logout();
	 
	    RegistroTirocinio registro = domanda.getRegistroTirocinio();
	    	    
	    Attività attivita = new Attività();
	    attivita.setNomeAttivita("Ricerca");
	    attivita.setDescrizioneAttivita("ricerca materiale tesi");
	    attivita.setRegistroTirocinio(registro);
	    attivita.setOraInizio(LocalTime.now().minusHours(2));
	    attivita.setOraFine(LocalTime.now());
	    attivita.setNumOre(2);
	    attivita.setStatus(Attività.IN_ATTESA);	 
	    
	    try {
			utenzaService.login("FrancescoF", "FrancescoF");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());			
		}
	    
	    try {
			attivita = registroService.aggiungiAttivitaTirocinio(attivita, registro.getId());
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | DomandaTirocinioNonValidataException
				| NumOreNonValidoException | OraDiInizioAttivitàNonValidaException
				| OraDiFineAttivitàNonValidaException e) {
			fail(e.getMessage());
		}
	    
	    //effettua logout dello studente
	    utenzaService.logout();	 
	    
	    //Effettua login del delegato
	    try {
			utenzaService.login("tonystark", "ironman");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());
		}
	    
	    try {
			attivita = registroService.annullaAttività(attivita.getId());
		} catch (RichiestaNonAutorizzataException | IdAttivitaTirocinioNonValidoException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	    
	    assertThat(attivita.getStatus(), is((equalTo(Attività.NON_VALIDA))));
	
	  //Effettua logout del delegato
	    utenzaService.logout();
	 }

	  /**
	   * Testa il metodo che permette ad un delegato aziendale di chiudere un registro di tirocinio
	   * 
	   * @test {@link RegistroTirocinioService#chiudiRegistroTirocinio(long)}
	   * 
	   * @result il test è superato se il registro di tirocinio viene chiuso correttamente 
	   * 
	   */
	  @Test
	  public void chiudiRegistroTirocinio() {
	    // Crea un azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("starkind");
	    azienda.setNome("Stark Industries");
	    azienda.setPartitaIva("74598763241");
	    azienda.setSenzaBarriere(true);
	    azienda.setIndirizzo("Marvel Valley, 45");
	    
	    // Crea un delegato da associare all'azienda
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setUsername("tonystark");
	    delegato.setPassword("ironman");
	    delegato.setEmail("tony@starkind.com");
	    delegato.setNome("Anthony Edward");
	    delegato.setCognome("Stark");
	    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
	    delegato.setTelefono("7485214786");
	    
	    // Crea una richiesta di convenzionamento
	    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
	    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
	    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
	    
	    utenzaService.setUtenteAutenticato(null);
	    
	    //Registra richiesta di convenzionamente
	    try {
	      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
	      richiesta = azienda.getRichiesta();
	    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
	        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
	        | IdAziendaNonValidoException | IdAziendaEsistenteException
	        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
	        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
	        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
	      fail(e.getLocalizedMessage());
	    }
	    
	    // Crea un impiegato
	    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
	    impiegato.setNome("Antonio");
	    impiegato.setCognome("Albanese");
	    impiegato.setEmail("antonio@albanese.com");
	    impiegato.setUsername("impiegato");
	    impiegato.setPassword("impiegato");
	    
	    // Inserisci l'impiegato nel sistema
	    impiegatoRepository.save(impiegato);
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta convenzionamento
	    try {
	      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
	    } catch (IdRichiestaConvenzionamentoNonValidoException
	        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Esegue logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea un nuovo progetto formativo
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("Sviluppo applicazioni web");
	    progetto.setAzienda(azienda);
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Aggiunge un nuovo progetto formativo
	    try {
	      progettiService.aggiungiProgettoFormativo(progetto);
	    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
	        | DescrizioneProgettoNonValidaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Crea lo studente 
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.now());
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescoF");
	    
	    // Crea la richiesta iscrizione
	    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
	    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
	    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
	    richiestaIscrizione.setCommentoUfficioTirocini("commento");
	    
	    // Registra richiesta d'iscrizione sul database
	    try {
	      studente = studentiService.registraRichiestaIscrizione(studente);
	      richiestaIscrizione = studente.getRichiestaIscrizione();   
	    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
	        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
	        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
	        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	      e.printStackTrace();
	    }
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta d'iscrizione dello studente
	    try {
	      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
	    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea una domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStudente(studente);
	    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
	    domanda.setCfu(8);
	    domanda.setData(LocalDateTime.now());
	    domanda.setInizioTirocinio(LocalDate.of(2021, 03, 11));
	    domanda.setFineTirocinio(LocalDate.of(2021, 04, 01));
	    domanda.setCommentoStudente("commento studente");
	    
	    //Effettua login dello studente
	    try {
	      utenzaService.login("FrancescoF", "FrancescoF");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	   
	    try {
	      // Registra domanda di tirocinio sul database
	      domanda = domandeService.registraDomandaTirocinio(domanda);
	    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
	        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
	        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    //
	    try {
	      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
	    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
	    presidente.setUsername("fferrucci");
	    presidente.setPassword("fferrucci");
	    
	 // Effettua il login come presidente
	    try {
	      utenzaService.login("fferrucci", "fferrucci");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.validaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException | DomandaTirocinioNonValidataException e) {
	      fail(e.getMessage());
	    }
	    
	   //Effettua il logout del Presidente
	    utenzaService.logout();
	 
	    RegistroTirocinio registro = domanda.getRegistroTirocinio();
	    	    
	    Attività attivita = new Attività();
	    attivita.setNomeAttivita("Ricerca");
	    attivita.setDescrizioneAttivita("ricerca materiale tesi");
	    attivita.setRegistroTirocinio(registro);
	    attivita.setOraInizio(LocalTime.now().minusHours(2));
	    attivita.setOraFine(LocalTime.now());
	    attivita.setNumOre(2);
	    attivita.setStatus(Attività.IN_ATTESA);	 
	    
	    try {
			utenzaService.login("FrancescoF", "FrancescoF");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());			
		}
	    
	    try {
			attivita = registroService.aggiungiAttivitaTirocinio(attivita, registro.getId());
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | DomandaTirocinioNonValidataException
				| NumOreNonValidoException | OraDiInizioAttivitàNonValidaException
				| OraDiFineAttivitàNonValidaException e) {
			fail(e.getMessage());
		}
	    
	    //effettua logout dello studente
	    utenzaService.logout();	 
	    
	    //Effettua login del delegato
	    try {
			utenzaService.login("tonystark", "ironman");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());
		}
	    
	    try {
			attivita = registroService.annullaAttività(attivita.getId());
		} catch (RichiestaNonAutorizzataException | IdAttivitaTirocinioNonValidoException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	    
	   try {
		registro = registroService.chiudiRegistroTirocinio(registro.getId());
	} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException
			| StatoRegistroNonIdoneoException e) {
		fail(e.getMessage());
	}
	
	   assertThat(registro.getStatus(), is(equalTo(RegistroTirocinio.TERMINATO)));
	  
	   //Effettua logout del delegato
	    utenzaService.logout();
	    	  
	 }

	  /**
	   * Testa il metodo che permette al presidente del consiglio didattico di validare
	   * un registro di tirocinio
	   * 
	   * @test {@link RegistroTirocinioService#validaRegistroTirocinio(long)}
	   * 
	   * @result il test è superato se il registro di tirocinio viene validato correttamente 
	   * 
	   */
	  @Test
	  public void validaRegistroTirocinio() {
	    // Crea un azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("starkind");
	    azienda.setNome("Stark Industries");
	    azienda.setPartitaIva("74598763241");
	    azienda.setSenzaBarriere(true);
	    azienda.setIndirizzo("Marvel Valley, 45");
	    
	    // Crea un delegato da associare all'azienda
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setUsername("tonystark");
	    delegato.setPassword("ironman");
	    delegato.setEmail("tony@starkind.com");
	    delegato.setNome("Anthony Edward");
	    delegato.setCognome("Stark");
	    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
	    delegato.setTelefono("7485214786");
	    
	    // Crea una richiesta di convenzionamento
	    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
	    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
	    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
	    
	    utenzaService.setUtenteAutenticato(null);
	    
	    //Registra richiesta di convenzionamente
	    try {
	      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
	      richiesta = azienda.getRichiesta();
	    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
	        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
	        | IdAziendaNonValidoException | IdAziendaEsistenteException
	        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
	        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
	        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
	      fail(e.getLocalizedMessage());
	    }
	    
	    // Crea un impiegato
	    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
	    impiegato.setNome("Antonio");
	    impiegato.setCognome("Albanese");
	    impiegato.setEmail("antonio@albanese.com");
	    impiegato.setUsername("impiegato");
	    impiegato.setPassword("impiegato");
	    
	    // Inserisci l'impiegato nel sistema
	    impiegatoRepository.save(impiegato);
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta convenzionamento
	    try {
	      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
	    } catch (IdRichiestaConvenzionamentoNonValidoException
	        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Esegue logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea un nuovo progetto formativo
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("Sviluppo applicazioni web");
	    progetto.setAzienda(azienda);
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Aggiunge un nuovo progetto formativo
	    try {
	      progettiService.aggiungiProgettoFormativo(progetto);
	    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
	        | DescrizioneProgettoNonValidaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Crea lo studente 
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.now());
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescoF");
	    
	    // Crea la richiesta iscrizione
	    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
	    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
	    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
	    richiestaIscrizione.setCommentoUfficioTirocini("commento");
	    
	    // Registra richiesta d'iscrizione sul database
	    try {
	      studente = studentiService.registraRichiestaIscrizione(studente);
	      richiestaIscrizione = studente.getRichiestaIscrizione();   
	    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
	        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
	        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
	        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	      e.printStackTrace();
	    }
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta d'iscrizione dello studente
	    try {
	      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
	    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea una domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStudente(studente);
	    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
	    domanda.setCfu(8);
	    domanda.setData(LocalDateTime.now());
	    domanda.setInizioTirocinio(LocalDate.of(2021, 03, 11));
	    domanda.setFineTirocinio(LocalDate.of(2021, 04, 01));
	    domanda.setCommentoStudente("commento studente");
	    
	    //Effettua login dello studente
	    try {
	      utenzaService.login("FrancescoF", "FrancescoF");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	   
	    try {
	      // Registra domanda di tirocinio sul database
	      domanda = domandeService.registraDomandaTirocinio(domanda);
	    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
	        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
	        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    //
	    try {
	      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
	    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
	    presidente.setUsername("fferrucci");
	    presidente.setPassword("fferrucci");
	    
	 // Effettua il login come presidente
	    try {
	      utenzaService.login("fferrucci", "fferrucci");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.validaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException | DomandaTirocinioNonValidataException e) {
	      fail(e.getMessage());
	    }
	    
	   //Effettua il logout del Presidente
	    utenzaService.logout();
	 
	    RegistroTirocinio registro = domanda.getRegistroTirocinio();
	    	    
	    Attività attivita = new Attività();
	    attivita.setNomeAttivita("Ricerca");
	    attivita.setDescrizioneAttivita("ricerca materiale tesi");
	    attivita.setRegistroTirocinio(registro);
	    attivita.setOraInizio(LocalTime.now().minusHours(2));
	    attivita.setOraFine(LocalTime.now());
	    attivita.setNumOre(2);
	    attivita.setStatus(Attività.IN_ATTESA);	 
	    
	    try {
			utenzaService.login("FrancescoF", "FrancescoF");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());			
		}
	    
	    try {
			attivita = registroService.aggiungiAttivitaTirocinio(attivita, registro.getId());
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | DomandaTirocinioNonValidataException
				| NumOreNonValidoException | OraDiInizioAttivitàNonValidaException
				| OraDiFineAttivitàNonValidaException e) {
			fail(e.getMessage());
		}
	    
	    //effettua logout dello studente
	    utenzaService.logout();	 
	    
	    //Effettua login del delegato
	    try {
			utenzaService.login("tonystark", "ironman");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());
		}
	    
	    try {
			attivita = registroService.annullaAttività(attivita.getId());
		} catch (RichiestaNonAutorizzataException | IdAttivitaTirocinioNonValidoException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	    
	   try {
		registro = registroService.chiudiRegistroTirocinio(registro.getId());
	} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException
			| StatoRegistroNonIdoneoException e) {
		fail(e.getMessage());
	}
	
	  //Effettua logout del delegato
	    utenzaService.logout();
	    
	  //Effettua login del delegato
	    try {
			utenzaService.login("fferrucci", "fferrucci");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());
		}
		
	    try {
			registro = registroService.validaRegistroTirocinio(registro.getId());
		} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException
				| StatoRegistroNonIdoneoException e) {
			fail(e.getMessage());
		}
		
	    assertThat(registro.getStatus(), is(equalTo(RegistroTirocinio.VALIDATO)));
	    
	   //Effettua logout del delegato
	    utenzaService.logout();
	}
	  
	  /**
	   * Testa il metodo che permette di ottenere l'elenco dell'attività di tirocinio
	   * relative a un registro di tirocinio
	   * 
	   * @test {@link RegistroTirocinioService#elencaAttività(Long)}
	   * 
	   * @result il test è superato se l'elenco delle attività viene caricato correttamente
	   * dal database
	   * 
	   */
	  @Test
	  public void elencaAttività() {
	    // Crea un azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("starkind");
	    azienda.setNome("Stark Industries");
	    azienda.setPartitaIva("74598763241");
	    azienda.setSenzaBarriere(true);
	    azienda.setIndirizzo("Marvel Valley, 45");
	    
	    // Crea un delegato da associare all'azienda
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setUsername("tonystark");
	    delegato.setPassword("ironman");
	    delegato.setEmail("tony@starkind.com");
	    delegato.setNome("Anthony Edward");
	    delegato.setCognome("Stark");
	    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
	    delegato.setTelefono("7485214786");
	    
	    // Crea una richiesta di convenzionamento
	    RichiestaConvenzionamento richiesta = azienda.getRichiesta();
	    richiesta.setStatus(RichiestaConvenzionamento.APPROVATA);
	    richiesta.setDataRichiesta(LocalDateTime.of(2017, 11, 17, 18, 32));
	    
	    //Registra richiesta di convenzionamente
	    try {
	      azienda = convenzioniService.registraRichiestaConvenzionamento(azienda);
	      richiesta = azienda.getRichiesta();
	    } catch (IndirizzoAziendaNonValidoException | PartitaIvaAziendaNonValidaException
	        | PartitaIvaAziendaEsistenteException | NomeAziendaNonValidoException
	        | IdAziendaNonValidoException | IdAziendaEsistenteException
	        | CommentoRichiestaConvenzionamentoNonValidoException | RichiestaNonAutorizzataException
	        | UsernameNonValidoException | UsernameEsistenteException | PasswordNonValidaException
	        | EmailNonValidaException | EmailEsistenteException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException e) {
	      fail(e.getLocalizedMessage());
	    }
	    
	    // Crea un impiegato
	    ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
	    impiegato.setNome("Antonio");
	    impiegato.setCognome("Albanese");
	    impiegato.setEmail("antonio@albanese.com");
	    impiegato.setUsername("impiegato");
	    impiegato.setPassword("impiegato");
	    
	    // Inserisci l'impiegato nel sistema
	    impiegatoRepository.save(impiegato);
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta convenzionamento
	    try {
	      convenzioniService.approvaRichiestaConvenzionamento(richiesta.getId());
	    } catch (IdRichiestaConvenzionamentoNonValidoException
	        | RichiestaConvenzionamentoGestitaException | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Esegue logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea un nuovo progetto formativo
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("Sviluppo applicazioni web");
	    progetto.setAzienda(azienda);
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Aggiunge un nuovo progetto formativo
	    try {
	      progettiService.aggiungiProgettoFormativo(progetto);
	    } catch (RichiestaNonAutorizzataException | NomeProgettoNonValidoException
	        | DescrizioneProgettoNonValidaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Crea lo studente 
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.now());
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescoF");
	    
	    // Crea la richiesta iscrizione
	    RichiestaIscrizione richiestaIscrizione = studente.getRichiestaIscrizione();
	    richiestaIscrizione.setDataRichiesta(LocalDateTime.of(2017, 11, 24, 15, 12));
	    richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
	    richiestaIscrizione.setCommentoUfficioTirocini("commento");
	    
	    // Registra richiesta d'iscrizione sul database
	    try {
	      studente = studentiService.registraRichiestaIscrizione(studente);
	      richiestaIscrizione = studente.getRichiestaIscrizione();   
	    } catch (UsernameNonValidoException | PasswordNonValidaException | UsernameEsistenteException
	        | EmailEsistenteException | EmailNonValidaException | NomeNonValidoException
	        | CognomeNonValidoException | TelefonoNonValidoException | SessoNonValidoException
	        | IndirizzoStudenteNonValidoException | MatricolaStudenteEsistenteException
	        | MatricolaStudenteNonValidaException | DataDiNascitaStudenteNonValidaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	      e.printStackTrace();
	    }
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    // Approva richiesta d'iscrizione dello studente
	    try {
	      studentiService.approvaRichiestaIscrizione(richiestaIscrizione.getId());
	    } catch (IdRichiestaIscrizioneNonValidoException | RichiestaIscrizioneGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    // Crea una domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStudente(studente);
	    domanda.setStatus(DomandaTirocinio.IN_ATTESA);
	    domanda.setCfu(8);
	    domanda.setData(LocalDateTime.now());
	    domanda.setInizioTirocinio(LocalDate.of(2021, 03, 11));
	    domanda.setFineTirocinio(LocalDate.of(2021, 04, 01));
	    domanda.setCommentoStudente("commento studente");
	    
	    //Effettua login dello studente
	    try {
	      utenzaService.login("FrancescoF", "FrancescoF");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	   
	    try {
	      // Registra domanda di tirocinio sul database
	      domanda = domandeService.registraDomandaTirocinio(domanda);
	    } catch (RichiestaNonAutorizzataException | DataDiInizioTirocinioNonValidaException
	        | DataDiFineTirocinioNonValidaException | NumeroCfuNonValidoException
	        | CommentoDomandaTirocinioNonValidoException | ProgettoFormativoArchiviatoException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua login del delegato aziendale
	    try {
	      utenzaService.login("tonystark", "ironman");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    //
	    try {
	      domanda = domandeService.accettaDomandaTirocinio(domanda.getId(), "commentoAzienda");
	    } catch (IdDomandaTirocinioNonValidoException | DomandaTirocinioGestitaException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout del delegato
	    utenzaService.logout();
	    
	    // Effettua il login come impiegato
	    try {
	      utenzaService.login("impiegato", "impiegato");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.approvaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException e) {
	      fail(e.getMessage());
	    }
	    
	    // Effettua logout dell'impiegato
	    utenzaService.logout();
	    
	    PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
	    presidente.setUsername("fferrucci");
	    presidente.setPassword("fferrucci");
	    
	 // Effettua il login come presidente
	    try {
	      utenzaService.login("fferrucci", "fferrucci");
	    } catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
	        | CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
	        | RichiestaIscrizioneInAttesaException e) {
	      fail(e.getMessage());
	    }
	    
	    try {
	      domanda = domandeService.validaDomandaTirocinio(domanda.getId());
	    } catch (IdDomandaTirocinioNonValidoException | StatoDomandaNonIdoneoException
	        | RichiestaNonAutorizzataException | DomandaTirocinioNonValidataException e) {
	      fail(e.getMessage());
	    }
	    
	   //Effettua il logout del Presidente
	    utenzaService.logout();
	 
	    RegistroTirocinio registro = domanda.getRegistroTirocinio();
	    	    
	    Attività attivita = new Attività();
	    attivita.setNomeAttivita("Ricerca");
	    attivita.setDescrizioneAttivita("ricerca materiale tesi");
	    attivita.setRegistroTirocinio(registro);
	    attivita.setOraInizio(LocalTime.now().minusHours(2));
	    attivita.setOraFine(LocalTime.now());
	    attivita.setNumOre(2);
	    attivita.setStatus(Attività.IN_ATTESA);	 
	    
	    try {
			utenzaService.login("FrancescoF", "FrancescoF");
		} catch (RichiestaConvenzionamentoRifiutataException | RichiestaIscrizioneRifiutataException
				| CredenzialiNonValideException | RichiestaConvenzionamentoInAttesaException
				| RichiestaIscrizioneInAttesaException e) {
			fail(e.getMessage());			
		}
	    
	    try {
			attivita = registroService.aggiungiAttivitaTirocinio(attivita, registro.getId());
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | DomandaTirocinioNonValidataException
				| NumOreNonValidoException | OraDiInizioAttivitàNonValidaException
				| OraDiFineAttivitàNonValidaException e) {
			fail(e.getMessage());
		}
	    
	    List<Attività> elencoAttivita = new ArrayList<Attività>();
	    elencoAttivita.add(attivita);
	    
	    List<Attività> attivitaOttenute = null;
	    try {
			attivitaOttenute = registroService.elencaAttività(domanda.getId());
		} catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException e) {
			fail(e.getMessage());
		}
	    
	    assertThat(elencoAttivita, is(equalTo(attivitaOttenute)));
	    
	    //Effettua logout dello studente
	   utenzaService.logout();
	    
	}
}
