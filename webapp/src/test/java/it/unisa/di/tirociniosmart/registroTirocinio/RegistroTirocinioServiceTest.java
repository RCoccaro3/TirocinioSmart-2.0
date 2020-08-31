package it.unisa.di.tirociniosmart.registroTirocinio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinioRepository;
import it.unisa.di.tirociniosmart.domandetirocinio.IdDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.presidenteConsiglioDidattico.PresidenteConsiglioDidattico;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

/**
 * Classe che offre i casi di test di AttivitàService.
 * 
 * @see AttivitàRepository
 * @see AttivitàService
 * @see UtenzaService
 */

@RunWith(MockitoJUnitRunner.class)
public class RegistroTirocinioServiceTest {

	@InjectMocks
	private RegistroTirocinioService registroService;
	
	@Mock
	private RegistroTirocinioRepository registroRepository;
	
	@Mock
	private AttivitàRepository attivitàRepository;
	
	@Mock
	private UtenzaService utenzaService;
	
	@Mock
	private DomandaTirocinioRepository domandaTirocinioRepository;
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinio()}
	 *  
	 *  @result Il test è superato se l'attività viene aggiunta correttamente
	 */
	@Test
	public void testaAggiungiAttivitaTirocinio() {
		
		//Crea azienda
	    Azienda azienda = new Azienda();
	    azienda.setId("azienda");
	    azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
	    azienda.setPartitaIva("12345678901");
	    azienda.setSenzaBarriere(true);
	      
	    //Crea progetto formativo dell'azienda
	    ProgettoFormativo progetto = new ProgettoFormativo();
	    progetto.setNome("WEB");
	    progetto.setDescrizione("descrizione");
	    progetto.setStatus(ProgettoFormativo.ATTIVO);
	    progetto.setAzienda(azienda);
	      
	    //crea domanda di tirocinio
	    DomandaTirocinio domanda = new DomandaTirocinio();
	    domanda.setCfu(6);
	    domanda.setCommentoStudente("Mi piace il progetto!");
	    domanda.setData(LocalDateTime.now());
	    domanda.setProgettoFormativo(progetto);
	    domanda.setStatus(DomandaTirocinio.VALIDATA);
	    
	    // Definisci dinamicamente la data di inizio tirocinio
	    LocalDate dataInizioTirocinio = LocalDate.now();
	    dataInizioTirocinio = dataInizioTirocinio.plusDays(1);
	    domanda.setInizioTirocinio(dataInizioTirocinio);
	    
	    // Definisci dinamicamente la data di fine tirocinio
	    LocalDate dataFineTirocinio = dataInizioTirocinio;
	    dataFineTirocinio = dataFineTirocinio.plusDays(40);
	    domanda.setFineTirocinio(dataFineTirocinio);
	    
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
				
		RegistroTirocinio registro = new RegistroTirocinio();
		registro.setDomandaTirocinio(domanda);
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		domanda.setRegistroTirocinio(registro);
		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		attività.setRegistroTirocinio(registro);
		attività.setDomandaTirocinio(domanda);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
		when(attivitàRepository.save(attività)).thenReturn(null);
		when(registroRepository.findById(1111L)).thenReturn(registro);

	    try {
	    	//Permette di aggiungere un'attività di tirocinio
			registroService.aggiungiAttivitaTirocinio(attività, 1111L);
		} catch (RichiestaNonAutorizzataException | NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioRichiestaNonAutorizzata()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = RichiestaNonAutorizzataException.class)
	public void testaAggiungiAttivitaTirocinioRichiestaNonAutorizzata()
			throws RichiestaNonAutorizzataException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		
		RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (NomeAttivitàNonValidoException
				| DescrizioneAttivitàNonValidaException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioNomeAttivitàNull()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = NomeAttivitàNonValidoException.class)
	public void testaAggiungiAttivitaTirocinioNomeAttivitàNull()
			throws NomeAttivitàNonValidoException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita(null);
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException
				| DescrizioneAttivitàNonValidaException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioNomeAttivitàMin()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = NomeAttivitàNonValidoException.class)
	public void testaAggiungiAttivitaTirocinioNomeAttivitàMin()
			throws NomeAttivitàNonValidoException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException
				| DescrizioneAttivitàNonValidaException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioNomeAttivitàMin()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = NomeAttivitàNonValidoException.class)
	public void testaAggiungiAttivitaTirocinioNomeAttivitàMax()
			throws NomeAttivitàNonValidoException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaa    aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
				+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException
				| DescrizioneAttivitàNonValidaException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioDescrizioneAttivitàNull()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = DescrizioneAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioDescrizioneAttivitàNull()
			throws DescrizioneAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita(null);
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException
				| NomeAttivitàNonValidoException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioDescrizioneAttivitàMin()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = DescrizioneAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioDescrizioneAttivitàMin()
			throws DescrizioneAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException
				| NomeAttivitàNonValidoException | OraDiFineAttivitàNonValidaException
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioOraFineAttivitàNull()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = OraDiFineAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioOraFineAttivitàNull()
			throws OraDiFineAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(null);
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioOraFineAttivitàBefore()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = OraDiFineAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioOraFineAttivitàBefore()
			throws OraDiFineAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now().minusHours(2).minusMinutes(10));
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioOraFineAttivitàBefore()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = OraDiFineAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioOraFineAttivitàAfterNow()
			throws OraDiFineAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().minusHours(2));
		attività.setOraFine(LocalTime.now().plusHours(2).minusMinutes(10));
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiInizioAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}

	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioOraInizioAttivitàNull()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = OraDiInizioAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioOraInizioAttivitàNull()
			throws OraDiInizioAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(null);
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
		
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
	    
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiFineAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}	
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioOraInizioAttivitàNull()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = OraDiInizioAttivitàNonValidaException.class)
	public void testaAggiungiAttivitaTirocinioOraInizioAttivitàAfterNow()
			throws OraDiInizioAttivitàNonValidaException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setNumOre(2);
		attività.setOraInizio(LocalTime.now().plusMinutes(10));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
		
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
	    
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiFineAttivitàNonValidaException | NumOreNonValidoException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}

	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioNumOreMin()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = NumOreNonValidoException.class)
	public void testaAggiungiAttivitaTirocinioNumOreMin()
			throws NumOreNonValidoException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setOraInizio(LocalTime.now().minusHours(1));
		attività.setOraFine(LocalTime.now());
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiFineAttivitàNonValidaException | OraDiInizioAttivitàNonValidaException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioNumOreMax()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test (expected = NumOreNonValidoException.class)
	public void testaAggiungiAttivitaTirocinioNumOreMax()
			throws NumOreNonValidoException {		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setOraInizio(LocalTime.now().minusHours(1));
		attività.setOraFine(LocalTime.now());
		attività.setNumOre(19);
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
	    
	    RegistroTirocinio registro = new RegistroTirocinio();
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, registro.getId());
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiFineAttivitàNonValidaException | OraDiInizioAttivitàNonValidaException
				| DomandaTirocinioNonValidataException e) {
			fail(e.getMessage());
		}		
	}
	
	/**
	 * Metodo che testa l'aggiunta di un'attività di tirocinio.
	 * 
	 *  @test {@link RegistroTirocinioServiceTest#testaAggiungiAttivitaTirocinioDomandaTirocinioNonValidata()}
	 *  
	 *  @result Il test è superato se viene lanciata l'eccezione
	 */
	@Test(expected = DomandaTirocinioNonValidataException.class)
	public void testaAggiungiAttivitaTirocinioDomandaTirocinioNonValidata()
			 throws DomandaTirocinioNonValidataException {		
		
		DomandaTirocinio domanda = new DomandaTirocinio();
		domanda.setStatus(DomandaTirocinio.ANNULLATA);
		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setOraInizio(LocalTime.now().minusHours(1));
		attività.setOraFine(LocalTime.now());
		attività.setNumOre(1);
		
	    // Crea studente
	    Studente studente = new Studente();
	    studente.setNome("Francesco");
	    studente.setCognome("Facchinetti");
	    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
	    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
	    studente.setEmail("francesco@facchinetti.com");
	    studente.setIndirizzo("Via francesco, 9");
	    studente.setMatricola("0512103434");
	    studente.setTelefono("3331234123");
	    studente.setSesso(Studente.SESSO_MASCHILE);
	    studente.setUsername("FrancescoF");
	    studente.setPassword("francescof");
				
		RegistroTirocinio registro = new RegistroTirocinio();
		registro.setDomandaTirocinio(domanda);
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
	    
		//Stabilisce che l'utente autenticato sia uno studente
	    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
	    when(registroRepository.findById(1111L)).thenReturn(registro);
	    try {
	    	//Permette di aggiungere un'attività di tirocinio
	    	registroService.aggiungiAttivitaTirocinio(attività, 1111L);
		} catch (RichiestaNonAutorizzataException | DescrizioneAttivitàNonValidaException
				| NomeAttivitàNonValidoException 
				| OraDiFineAttivitàNonValidaException | OraDiInizioAttivitàNonValidaException
				| NumOreNonValidoException e) {
			fail(e.getMessage());
			
		}		
	}
	
	/**
	 * Metodo che testa l'invalidazione di un'attività di tirocinio.
	 * 
	 *  @test {@link AttivitàService#annullaAttività(long)}
	 *  
	 *  @result Il test è superato se l'attività viene invalidata correttamente
	 */
	@Test
	public void testaAnnullaAttività() {
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setOraInizio(LocalTime.now().minusHours(1));
		attività.setOraFine(LocalTime.now());
		attività.setNumOre(2);
		
		RegistroTirocinio registro = new RegistroTirocinio();
		registro.setOreValidate(15);
		registro.setOreDaEffettuare(60);
		registro.setOreInAttesa(12);
		
		attività.setRegistroTirocinio(registro);
		
		DomandaTirocinio domanda = new DomandaTirocinio();
		domanda.setCfu(6);
		domanda.setOreTotaliTirocinio();
		
		registro.setDomandaTirocinio(domanda);
		
		Azienda azienda = new Azienda();
		azienda.setId("azienda");
		azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
		azienda.setPartitaIva("12345678901");
		azienda.setSenzaBarriere(true);
		
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setNome("Giuseppe");
	    delegato.setCognome("Errore");
	    delegato.setEmail("giuseppe@errore.com");
	    delegato.setSesso("Maschile");
	    delegato.setTelefono("3333333333");
	    delegato.setUsername("user");
	    delegato.setPassword("user");
	    
	  //Permette di stabilire che l'operazione è eseguita da un delegato
	    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
	    
	 //permette di cercare l'attività con l'id specificato 
	  when(attivitàRepository.findById(2L)).thenReturn(attività);  
	   try {
		   registroService.annullaAttività(2L);
	} catch (RichiestaNonAutorizzataException | IdAttivitaTirocinioNonValidoException
			| StatoAttivitaNonIdoneoException e) {
		fail(e.getMessage());
	}
  }
	
   /**
	* Metodo che testa l'invalidazione di un'attività di tirocinio.
	* 
	* @test {@link AttivitàService#annullaAttività(long)}
	* 
	* @result Il test è superato se viene lanciata l'eccezione aspettata
	* 
	* @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
    *         tale operazione
	* 
	*/
	@Test (expected = RichiestaNonAutorizzataException.class)
	public void testAnnullaAttivitàRichiestaNonAutorizzata() 
			throws RichiestaNonAutorizzataException {
		
		Attività attività = new Attività();
		attività.setData(LocalDateTime.now());
		attività.setDescrizioneAttivita("descrizione");
		attività.setNomeAttivita("attività");
		attività.setOraInizio(LocalTime.now().minusHours(1));
		attività.setOraFine(LocalTime.now());
		attività.setNumOre(2);
		
		Azienda azienda = new Azienda();
		azienda.setId("azienda");
		azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
		azienda.setPartitaIva("12345678901");
		azienda.setSenzaBarriere(true);
		
	    DelegatoAziendale delegato = azienda.getDelegato();
	    delegato.setNome("Giuseppe");
	    delegato.setCognome("Errore");
	    delegato.setEmail("giuseppe@errore.com");
	    delegato.setSesso("Maschile");
	    delegato.setTelefono("3333333333");
	    delegato.setUsername("user");
	    delegato.setPassword("user");
	    
	  //Permette di stabilire che l'operazione è eseguita da un delegato
	    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
	    
	 //permette di cercare l'attività con l'id specificato 
	  when(attivitàRepository.findById(2L)).thenReturn(attività);  
	   try {
		   registroService.annullaAttività(2L);
	} catch ( IdAttivitaTirocinioNonValidoException
			| StatoAttivitaNonIdoneoException e) {
		fail(e.getMessage());
	}
  }
	
	
	   /**
		* Metodo che testa l'invalidazione di un'attività di tirocinio.
		* 
		* @test {@link AttivitàService#annullaAttività(long)}
		* 
		* @result Il test è superato se viene lanciata l'eccezione aspettata
		* 
		* @throws IdAttivitaTirocinioNonValidoException se non esiste alcuna attività di tirocinio con 
   *    *     	  l'identificatore specificato
		* 
		*/
		@Test (expected = IdAttivitaTirocinioNonValidoException.class)
		public void testAnnullaAttivitàRichiestaConIdNonValido() 
				throws IdAttivitaTirocinioNonValidoException {
			
			Attività attività = new Attività();
			attività.setData(LocalDateTime.now());
			attività.setDescrizioneAttivita("descrizione");
			attività.setNomeAttivita("attività");
			attività.setOraInizio(LocalTime.now().minusHours(1));
			attività.setOraFine(LocalTime.now());
			attività.setNumOre(2);
			
			Azienda azienda = new Azienda();
			azienda.setId("azienda");
			azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
			azienda.setPartitaIva("12345678901");
			azienda.setSenzaBarriere(true);
			
		    DelegatoAziendale delegato = azienda.getDelegato();
		    delegato.setNome("Giuseppe");
		    delegato.setCognome("Errore");
		    delegato.setEmail("giuseppe@errore.com");
		    delegato.setSesso("Maschile");
		    delegato.setTelefono("3333333333");
		    delegato.setUsername("user");
		    delegato.setPassword("user");
		    
		  //Permette di stabilire che l'operazione è eseguita da un delegato
		    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
		    
		 //permette di cercare l'attività con l'id specificato 
		  when(attivitàRepository.findById(2L)).thenReturn(null);  
		   try {
			   registroService.annullaAttività(2L);
		} catch ( RichiestaNonAutorizzataException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	  }

		   /**
		* Metodo che testa l'invalidazioe di un'attività di tirocinio.
		* 
		* @test {@link AttivitàService#annullaAttività(long)}
		* 
		* @result Il test è superato se viene lanciata l'eccezione aspettata
		* 
		* @throws StatoAttivitaNonIdoneoException se l'attività di tirocinio si trova in uno stato 
		*         diverso dallo stato {@link Attività#IN_ATTESA}
		* 
		*/
		@Test (expected = StatoAttivitaNonIdoneoException.class)
		public void testAnnullaAttivitàStatoNonIdoneo() 
				throws StatoAttivitaNonIdoneoException {
			
			Attività attività = new Attività();
			attività.setData(LocalDateTime.now());
			attività.setDescrizioneAttivita("descrizione");
			attività.setNomeAttivita("attività");
			attività.setOraInizio(LocalTime.now().minusHours(1));
			attività.setOraFine(LocalTime.now());
			attività.setNumOre(2);
			attività.setStatus(Attività.NON_VALIDA);
			
			Azienda azienda = new Azienda();
			azienda.setId("azienda");
			azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
			azienda.setPartitaIva("12345678901");
			azienda.setSenzaBarriere(true);
			
		    DelegatoAziendale delegato = azienda.getDelegato();
		    delegato.setNome("Giuseppe");
		    delegato.setCognome("Errore");
		    delegato.setEmail("giuseppe@errore.com");
		    delegato.setSesso("Maschile");
		    delegato.setTelefono("3333333333");
		    delegato.setUsername("user");
		    delegato.setPassword("user");
		    
		  //Permette di stabilire che l'operazione è eseguita da un delegato
		    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
		    
		 //permette di cercare l'attività con l'id specificato 
		  when(attivitàRepository.findById(2L)).thenReturn(attività);  
		   try {
			   registroService.annullaAttività(2L);
		} catch ( IdAttivitaTirocinioNonValidoException
				| RichiestaNonAutorizzataException e) {
			fail(e.getMessage());
		}
	  }

		
		/**
		 * Metodo che testa la validazione di un'attività di tirocinio.
		 * 
		 *  @test {@link RegistroTirocinioServiceTest#testaValidaAttività()}
		 *  
		 *  @result Il test è superato se l'attività viene validata correttamente
		 */
		@Test
		public void testaValidaAttività() {
			
			DomandaTirocinio domanda = new DomandaTirocinio();
			domanda.setCfu(6);
			domanda.setOreTotaliTirocinio();
			domanda.setData(LocalDateTime.now().minusMonths(2));
			domanda.setFineTirocinio(LocalDate.now().minusMonths(1));
			domanda.setInizioTirocinio(LocalDate.now().minusMonths(2).plusDays(15));
			domanda.setStatus(DomandaTirocinio.VALIDATA);			
			
			Attività attività = new Attività();
			attività.setData(LocalDateTime.now());
			attività.setDescrizioneAttivita("descrizione");
			attività.setNomeAttivita("attività");
			attività.setOraInizio(LocalTime.now().minusHours(1));
			attività.setOraFine(LocalTime.now());
			attività.setNumOre(2);
			attività.setStatus(Attività.IN_ATTESA);
			
			RegistroTirocinio registro = new RegistroTirocinio();
			registro.setOreValidate(15);
			registro.setOreDaEffettuare(60);
			registro.setOreInAttesa(12);
			
			attività.setRegistroTirocinio(registro);
			domanda.setRegistroTirocinio(registro);
			

			attività.setDomandaTirocinio(domanda);
			
			registro.setDomandaTirocinio(domanda);
			
			
			Studente studente = new Studente();
			studente.setNome("Francesco");
			studente.setCognome("Coccaro");
			studente.setEmail("francesco@coccaro.it");
			
			domanda.setStudente(studente);
			
			Azienda azienda = new Azienda();
			azienda.setId("azienda");
			azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
			azienda.setPartitaIva("12345678901");
			azienda.setSenzaBarriere(true);
			azienda.setNome("azienda");
			
			ProgettoFormativo progetto = new ProgettoFormativo();
			progetto.setNome("progetto");
			progetto.setDescrizione("descrizione del progetto");
			progetto.setAzienda(azienda);
			
			domanda.setProgettoFormativo(progetto);
			
		    DelegatoAziendale delegato = azienda.getDelegato();
		    delegato.setNome("Giuseppe");
		    delegato.setCognome("Errore");
		    delegato.setEmail("giuseppe@errore.com");
		    delegato.setSesso("Maschile");
		    delegato.setTelefono("3333333333");
		    delegato.setUsername("user");
		    delegato.setPassword("user");
		    
		  //Permette di stabilire che l'operazione è eseguita da un delegato
		    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
		    
		 //permette di cercare l'attività con l'id specificato 
		  when(attivitàRepository.findById(2L)).thenReturn(attività);  
		  
		   try {
			   registroService.validaAttività(2L);
		} catch (RichiestaNonAutorizzataException | IdAttivitaTirocinioNonValidoException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	  }
		
	   /**
		* Metodo che testa la validazione di un'attività di tirocinio.
		* 
		* @test {@link AttivitàService#validaAttività(long)}
		* 
		* @result Il test è superato se viene lanciata l'eccezione aspettata
		* 
		* @throws RichiestaNonAutorizzataException se l'utente autenticato non è autorizzato a svolgere
	   *         tale operazione
		* 
		*/
		@Test (expected = RichiestaNonAutorizzataException.class)
		public void testValidaAttivitàRichiestaNonAutorizzata() 
				throws RichiestaNonAutorizzataException {
			
			Attività attività = new Attività();
			attività.setData(LocalDateTime.now());
			attività.setDescrizioneAttivita("descrizione");
			attività.setNomeAttivita("attività");
			attività.setOraInizio(LocalTime.now().minusHours(1));
			attività.setOraFine(LocalTime.now());
			attività.setNumOre(2);
			
			Azienda azienda = new Azienda();
			azienda.setId("azienda");
			azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
			azienda.setPartitaIva("12345678901");
			azienda.setSenzaBarriere(true);
			
		    DelegatoAziendale delegato = azienda.getDelegato();
		    delegato.setNome("Giuseppe");
		    delegato.setCognome("Errore");
		    delegato.setEmail("giuseppe@errore.com");
		    delegato.setSesso("Maschile");
		    delegato.setTelefono("3333333333");
		    delegato.setUsername("user");
		    delegato.setPassword("user");
		    
		  //Permette di stabilire che l'operazione è eseguita da un delegato
		    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
		    
		 //permette di cercare l'attività con l'id specificato 
		  when(attivitàRepository.findById(2L)).thenReturn(attività);  
		   try {
			   registroService.validaAttività(2L);
		} catch ( IdAttivitaTirocinioNonValidoException
				| StatoAttivitaNonIdoneoException e) {
			fail(e.getMessage());
		}
	  }
		
		
		   /**
			* Metodo che testa la validazione di un'attività di tirocinio.
			* 
			* @test {@link AttivitàService#validaAttività(long)}
			* 
			* @result Il test è superato se viene lanciata l'eccezione aspettata
			* 
			* @throws IdAttivitaTirocinioNonValidoException se non esiste alcuna attività di tirocinio con 
	   *    *     	  l'identificatore specificato
			* 
			*/
			@Test (expected = IdAttivitaTirocinioNonValidoException.class)
			public void testValidaAttivitàRichiestaConIdNonValido() 
					throws IdAttivitaTirocinioNonValidoException {
				
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				
				Azienda azienda = new Azienda();
				azienda.setId("azienda");
				azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
				azienda.setPartitaIva("12345678901");
				azienda.setSenzaBarriere(true);
				
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setNome("Giuseppe");
			    delegato.setCognome("Errore");
			    delegato.setEmail("giuseppe@errore.com");
			    delegato.setSesso("Maschile");
			    delegato.setTelefono("3333333333");
			    delegato.setUsername("user");
			    delegato.setPassword("user");
			    
			  //Permette di stabilire che l'operazione è eseguita da un delegato
			    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
			    
			 //permette di cercare l'attività con l'id specificato 
			  when(attivitàRepository.findById(2L)).thenReturn(null);  
			   try {
				   registroService.validaAttività(2L);
			} catch ( RichiestaNonAutorizzataException
					| StatoAttivitaNonIdoneoException e) {
				fail(e.getMessage());
			}
		  }

			   /**
			* Metodo che testa la validazione di un'attività di tirocinio.
			* 
			* @test {@link AttivitàService#validaAttività(long)}
			* 
			* @result Il test è superato se viene lanciata l'eccezione aspettata
			* 
			* @throws StatoAttivitaNonIdoneoException se l'attività di tirocinio si trova in uno stato 
			*         diverso dallo stato {@link Attività#IN_ATTESA}
			* 
			*/
			@Test (expected = StatoAttivitaNonIdoneoException.class)
			public void testValidaAttivitàStatoNonIdoneo() 
					throws StatoAttivitaNonIdoneoException {
				
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.NON_VALIDA);
				
				Azienda azienda = new Azienda();
				azienda.setId("azienda");
				azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
				azienda.setPartitaIva("12345678901");
				azienda.setSenzaBarriere(true);
				
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setNome("Giuseppe");
			    delegato.setCognome("Errore");
			    delegato.setEmail("giuseppe@errore.com");
			    delegato.setSesso("Maschile");
			    delegato.setTelefono("3333333333");
			    delegato.setUsername("user");
			    delegato.setPassword("user");
			    
			  //Permette di stabilire che l'operazione è eseguita da un delegato
			    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
			    
			 //permette di cercare l'attività con l'id specificato 
			  when(attivitàRepository.findById(2L)).thenReturn(attività);  
			   try {
				   registroService.validaAttività(2L);
			} catch ( IdAttivitaTirocinioNonValidoException
					| RichiestaNonAutorizzataException e) {
				fail(e.getMessage());
			}
		  }
			

			@Test
			public void testaAggiungiRegistroTirocinio() {
				DomandaTirocinio domanda = new DomandaTirocinio();
				domanda.setStatus(DomandaTirocinio.VALIDATA);
				
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setDomandaTirocinio(domanda);
				
				try {
					registroService.aggiungiRegistroTirocinio(registro);
				} catch (DomandaTirocinioNonValidataException e) {
					fail(e.getMessage());
				}				
			}
			
			@Test(expected = DomandaTirocinioNonValidataException.class)
			public void testaAggiungiRegistroTirocinioStatoDomandaNonValido() 
					throws DomandaTirocinioNonValidataException {
				DomandaTirocinio domanda = new DomandaTirocinio();
				domanda.setStatus(DomandaTirocinio.ANNULLATA);
				
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setDomandaTirocinio(domanda);
	
					registroService.aggiungiRegistroTirocinio(registro);			
			}

			
			@Test
			public void testaValidaRegistroTirocinio() {
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.TERMINATO);
				
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(presidente);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					registroService.validaRegistroTirocinio(2L);
				} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException
						| StatoRegistroNonIdoneoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = RichiestaNonAutorizzataException.class)
			public void testaValidaRegistroTirocinioRichiestaNonAutorizzata() 
					throws RichiestaNonAutorizzataException {
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.TERMINATO);
				
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(null);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					registroService.validaRegistroTirocinio(2L);
				} catch (IdRegistroTirocinioNonValidoException
						| StatoRegistroNonIdoneoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = IdRegistroTirocinioNonValidoException.class)
			public void testaValidaRegistroTirocinioIdRegistroNonValido() 
					throws IdRegistroTirocinioNonValidoException{
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.TERMINATO);
				
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(presidente);
				
				when(registroRepository.findById(2L)).thenReturn(null);
				
				try {
					registroService.validaRegistroTirocinio(2L);
				} catch (RichiestaNonAutorizzataException 
						| StatoRegistroNonIdoneoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = StatoRegistroNonIdoneoException.class)
			public void testaValidaRegistroTirocinioStatoRegistroNonValido() 
					throws StatoRegistroNonIdoneoException {
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.IN_ATTESA);
				
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(presidente);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					registroService.validaRegistroTirocinio(2L);
				} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test
			public void testaChiudiRegistroTirocinio() {
				DomandaTirocinio domanda = new DomandaTirocinio();
				domanda.setCfu(6);
				domanda.setOreTotaliTirocinio();
				domanda.setData(LocalDateTime.now().minusMonths(2));
				domanda.setFineTirocinio(LocalDate.now().minusMonths(1));
				domanda.setInizioTirocinio(LocalDate.now().minusMonths(2).plusDays(15));
				domanda.setStatus(DomandaTirocinio.VALIDATA);	
				domanda.setCommentoAzienda("commento azienda");
				domanda.setCommentoImpiegato("commentoImpiegato");
				domanda.setCommentoStudente("commentoStudente");
				domanda.setCommentoPresidente("commentoPresidente");
				
				Studente studente = new Studente();
			    studente.setNome("Francesco");
			    studente.setCognome("Facchinetti");
			    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
			    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
			    studente.setEmail("francesco@facchinetti.com");
			    studente.setIndirizzo("Via francesco, 9");
			    studente.setMatricola("0512103434");
			    studente.setTelefono("3331234123");
			    studente.setSesso(Studente.SESSO_MASCHILE);
			    studente.setUsername("FrancescoF");
			    studente.setPassword("francescof");
			    
			    domanda.setStudente(studente);
				
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.IN_ATTESA);
				registro.setDomandaTirocinio(domanda);
				domanda.setRegistroTirocinio(registro);
				registro.setOreDaEffettuare(15);
				registro.setOreInAttesa(2);
				registro.setOreValidate(3);
				
				Azienda azienda = new Azienda();
				azienda.setId("azienda");
				azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
				azienda.setPartitaIva("12345678901");
				azienda.setSenzaBarriere(true);
				azienda.setNome("azienda");
				
				ProgettoFormativo progetto = new ProgettoFormativo();
				progetto.setAzienda(azienda);
				progetto.setNome("nome");
				progetto.setDescrizione("descrizione");
				progetto.setStatus(ProgettoFormativo.ATTIVO);
				
				domanda.setProgettoFormativo(progetto);
				
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setNome("Giuseppe");
			    delegato.setCognome("Errore");
			    delegato.setEmail("giuseppe@errore.com");
			    delegato.setSesso("Maschile");
			    delegato.setTelefono("3333333333");
			    delegato.setUsername("user");
			    delegato.setPassword("user");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
				
				when(registroRepository.existsById(2L)).thenReturn(true);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					registroService.chiudiRegistroTirocinio(2L);
				} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException
						| StatoRegistroNonIdoneoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = RichiestaNonAutorizzataException.class)
			public void testaChiudiRegistroTirocinioRichiestaNonAutorizzata() 
					throws RichiestaNonAutorizzataException {
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.IN_ATTESA);
				
				Azienda azienda = new Azienda();
				azienda.setId("azienda");
				azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
				azienda.setPartitaIva("12345678901");
				azienda.setSenzaBarriere(true);
				
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setNome("Giuseppe");
			    delegato.setCognome("Errore");
			    delegato.setEmail("giuseppe@errore.com");
			    delegato.setSesso("Maschile");
			    delegato.setTelefono("3333333333");
			    delegato.setUsername("user");
			    delegato.setPassword("user");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(null);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					registroService.chiudiRegistroTirocinio(2L);
				} catch (IdRegistroTirocinioNonValidoException
						| StatoRegistroNonIdoneoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = IdRegistroTirocinioNonValidoException.class)
			public void testaChiudiRegistroTirocinioIdRegistroNonValido() 
					throws IdRegistroTirocinioNonValidoException{
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.IN_ATTESA);
				
				Azienda azienda = new Azienda();
				azienda.setId("azienda");
				azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
				azienda.setPartitaIva("12345678901");
				azienda.setSenzaBarriere(true);
				
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setNome("Giuseppe");
			    delegato.setCognome("Errore");
			    delegato.setEmail("giuseppe@errore.com");
			    delegato.setSesso("Maschile");
			    delegato.setTelefono("3333333333");
			    delegato.setUsername("user");
			    delegato.setPassword("user");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
				
				when(registroRepository.findById(2L)).thenReturn(null);
				
				try {
					registroService.chiudiRegistroTirocinio(2L);
				} catch (RichiestaNonAutorizzataException 
						| StatoRegistroNonIdoneoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = StatoRegistroNonIdoneoException.class)
			public void testaChiudiRegistroTirocinioStatoRegistroNonValido() 
					throws StatoRegistroNonIdoneoException {
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.TERMINATO);
				
				Azienda azienda = new Azienda();
				azienda.setId("azienda");
				azienda.setIndirizzo("Via Giuseppe De Stefano, 3");
				azienda.setPartitaIva("12345678901");
				azienda.setSenzaBarriere(true);
				
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setNome("Giuseppe");
			    delegato.setCognome("Errore");
			    delegato.setEmail("giuseppe@errore.com");
			    delegato.setSesso("Maschile");
			    delegato.setTelefono("3333333333");
			    delegato.setUsername("user");
			    delegato.setPassword("user");
				
				when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
				
				when(registroRepository.existsById(2L)).thenReturn(true);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					registroService.chiudiRegistroTirocinio(2L);
				} catch (RichiestaNonAutorizzataException | IdRegistroTirocinioNonValidoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test
			public void testaElencaAttivitàStudente() {
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				
				Attività attività2 = new Attività();
				attività2.setData(LocalDateTime.now().minusDays(1));
				attività2.setDescrizioneAttivita("descrizione");
				attività2.setNomeAttivita("attività");
				attività2.setOraInizio(LocalTime.now().minusHours(1));
				attività2.setOraFine(LocalTime.now());
				attività2.setNumOre(2);
				attività2.setStatus(Attività.IN_ATTESA);
				
				//Crea lista delle attività
				List<Attività> listaAttività = new ArrayList<Attività>();
				listaAttività.add(attività);
				listaAttività.add(attività2);				
				
				//Crea Studente
			    Studente studente = new Studente();
			    studente.setNome("Francesco");
			    studente.setCognome("Facchinetti");
			    studente.setDataDiNascita(LocalDate.of(1990, 12, 12));
			    studente.setDataRegistrazione(LocalDateTime.of(2017, 12, 25, 23, 45));
			    studente.setEmail("francesco@facchinetti.com");
			    studente.setIndirizzo("Via francesco, 9");
			    studente.setMatricola("0512103434");
			    studente.setTelefono("3331234123");
			    studente.setSesso(Studente.SESSO_MASCHILE);
			    studente.setUsername("FrancescoF");
			    studente.setPassword("francescof");
			    
			    when(utenzaService.getUtenteAutenticato()).thenReturn(studente);
			
			    when(domandaTirocinioRepository.existsById(2L)).thenReturn(true);
			    
			    when(attivitàRepository.findAllByDomandaTirocinioId(2L)).thenReturn(listaAttività); 
			   
					try {
						assertThat(listaAttività, is(equalTo(registroService.elencaAttività(2L))));
					} catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException e) {
						fail(e.getMessage());
					}				
			}
			
			@Test
			public void testaElencaAttivitàDelegatoAziendale() {
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				
				Attività attività2 = new Attività();
				attività2.setData(LocalDateTime.now().minusDays(1));
				attività2.setDescrizioneAttivita("descrizione");
				attività2.setNomeAttivita("attività");
				attività2.setOraInizio(LocalTime.now().minusHours(1));
				attività2.setOraFine(LocalTime.now());
				attività2.setNumOre(2);
				attività2.setStatus(Attività.IN_ATTESA);
				
				//Crea lista delle attività
				List<Attività> listaAttività = new ArrayList<Attività>();
				listaAttività.add(attività);
				listaAttività.add(attività2);				
				
				//Crea Delegato
			    Azienda azienda = new Azienda();
			    azienda.setId("idAzienda");
			    DelegatoAziendale delegato = azienda.getDelegato();
			    delegato.setUsername("Andrea1");
			    delegato.setPassword("andrea1");
			    delegato.setEmail("azndrea@carozza.com");
			    delegato.setNome("Andrea");
			    delegato.setCognome("Carozza");
			    delegato.setSesso(UtenteRegistrato.SESSO_MASCHILE);
			    delegato.setTelefono("9876543210");
			    
			    when(utenzaService.getUtenteAutenticato()).thenReturn(delegato);
			
			    when(domandaTirocinioRepository.existsById(2L)).thenReturn(true);
			    
			    when(attivitàRepository.findAllByDomandaTirocinioId(2L)).thenReturn(listaAttività); 
			   
					try {
						assertThat(listaAttività, is(equalTo(registroService.elencaAttività(2L))));
					} catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException e) {
						fail(e.getMessage());
					}				
			}
			
			@Test
			public void testaElencaAttivitàPresidenteConsiglioDidattico() {
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				
				Attività attività2 = new Attività();
				attività2.setData(LocalDateTime.now().minusDays(1));
				attività2.setDescrizioneAttivita("descrizione");
				attività2.setNomeAttivita("attività");
				attività2.setOraInizio(LocalTime.now().minusHours(1));
				attività2.setOraFine(LocalTime.now());
				attività2.setNumOre(2);
				attività2.setStatus(Attività.IN_ATTESA);
				
				//Crea lista delle attività
				List<Attività> listaAttività = new ArrayList<Attività>();
				listaAttività.add(attività);
				listaAttività.add(attività2);				
				
				//Crea Presidente
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
			    
			    when(utenzaService.getUtenteAutenticato()).thenReturn(presidente);
			
			    when(domandaTirocinioRepository.existsById(2L)).thenReturn(true);
			    
			    when(attivitàRepository.findAllByStatusAndDomandaTirocinioId(Attività.VALIDATA, 2L)).thenReturn(listaAttività); 
			   
					try {
						assertThat(listaAttività, is(equalTo(registroService.elencaAttività(2L))));
					} catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException e) {
						fail(e.getMessage());
					}				
			}
			
			@Test
			public void testaElencaAttivitàImpiegatoUfficioTirocini() {
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				
				Attività attività2 = new Attività();
				attività2.setData(LocalDateTime.now().minusDays(1));
				attività2.setDescrizioneAttivita("descrizione");
				attività2.setNomeAttivita("attività");
				attività2.setOraInizio(LocalTime.now().minusHours(1));
				attività2.setOraFine(LocalTime.now());
				attività2.setNumOre(2);
				attività2.setStatus(Attività.IN_ATTESA);
				
				//Crea lista delle attività
				List<Attività> listaAttività = new ArrayList<Attività>();
				listaAttività.add(attività);
				listaAttività.add(attività2);				
				
				//Crea Presidente
				ImpiegatoUfficioTirocini impiegato = new ImpiegatoUfficioTirocini();
				impiegato.setNome("Filomena");
				impiegato.setCognome("Ferrucci");
				impiegato.setUsername("fferrucci");
				impiegato.setPassword("fferrucci");
				impiegato.setEmail("fferrucci@unisa.it");
			    
			    when(utenzaService.getUtenteAutenticato()).thenReturn(impiegato);
			
			    when(domandaTirocinioRepository.existsById(2L)).thenReturn(true);
			    
			    when(attivitàRepository.findAllByStatusAndDomandaTirocinioId(Attività.VALIDATA, 2L)).thenReturn(listaAttività); 
			   
					try {
						assertThat(listaAttività, is(equalTo(registroService.elencaAttività(2L))));
					} catch (IdDomandaTirocinioNonValidoException | RichiestaNonAutorizzataException e) {
						fail(e.getMessage());
					}				
			}
			
			
			@Test(expected = RichiestaNonAutorizzataException.class)
			public void testaElencaAttivitàRichiestaNonAutorizzata() 
					throws RichiestaNonAutorizzataException {
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				
				Attività attività2 = new Attività();
				attività2.setData(LocalDateTime.now().minusDays(1));
				attività2.setDescrizioneAttivita("descrizione");
				attività2.setNomeAttivita("attività");
				attività2.setOraInizio(LocalTime.now().minusHours(1));
				attività2.setOraFine(LocalTime.now());
				attività2.setNumOre(2);
				attività2.setStatus(Attività.IN_ATTESA);
				
				//Crea lista delle attività
				List<Attività> listaAttività = new ArrayList<Attività>();
				listaAttività.add(attività);
				listaAttività.add(attività2);				
				
				//Crea Presidente
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
			    
			    when(utenzaService.getUtenteAutenticato()).thenReturn(null);
			
			    when(domandaTirocinioRepository.existsById(2L)).thenReturn(true);
			    
			    when(attivitàRepository.findAllByStatusAndDomandaTirocinioId(Attività.VALIDATA, 2L)).thenReturn(listaAttività); 
			   
					try {
						assertThat(listaAttività, is(equalTo(registroService.elencaAttività(2L))));
					} catch (IdDomandaTirocinioNonValidoException e) {
						fail(e.getMessage());
					}				
			}
			
			@Test(expected = IdDomandaTirocinioNonValidoException.class)
			public void testaElencaAttivitàIdDomandaTirocinioNonValido() 
					throws IdDomandaTirocinioNonValidoException {
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				
				Attività attività2 = new Attività();
				attività2.setData(LocalDateTime.now().minusDays(1));
				attività2.setDescrizioneAttivita("descrizione");
				attività2.setNomeAttivita("attività");
				attività2.setOraInizio(LocalTime.now().minusHours(1));
				attività2.setOraFine(LocalTime.now());
				attività2.setNumOre(2);
				attività2.setStatus(Attività.IN_ATTESA);
				
				//Crea lista delle attività
				List<Attività> listaAttività = new ArrayList<Attività>();
				listaAttività.add(attività);
				listaAttività.add(attività2);				
				
				//Crea Presidente
				PresidenteConsiglioDidattico presidente = new PresidenteConsiglioDidattico();
				presidente.setNome("Filomena");
				presidente.setCognome("Ferrucci");
				presidente.setUsername("fferrucci");
				presidente.setPassword("fferrucci");
				presidente.setEmail("fferrucci@unisa.it");
			    
			    when(utenzaService.getUtenteAutenticato()).thenReturn(presidente);
			
			    when(domandaTirocinioRepository.existsById(2L)).thenReturn(false);
			   
			    when(attivitàRepository.findAllByStatusAndDomandaTirocinioId(Attività.VALIDATA, 2L)).thenReturn(listaAttività); 
					try {
						assertThat(listaAttività, is(equalTo(registroService.elencaAttività(2L))));
					} catch (RichiestaNonAutorizzataException e) {
						fail(e.getMessage());
					}				
			}
			
			@Test
			public void testaTrovaDomandaTirocinio() {
				DomandaTirocinio domanda = new DomandaTirocinio();
				domanda.setStatus(DomandaTirocinio.VALIDATA);
				
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.IN_ATTESA);
				registro.setDomandaTirocinio(domanda);
				
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				attività.setRegistroTirocinio(registro);			
				
				when(attivitàRepository.exists(2L)).thenReturn(true);
				
				when(attivitàRepository.findById(2L)).thenReturn(attività);
				
				try {
					assertThat(domanda.getId(), is(equalTo(registroService.trovaDomandaTirocinio(2L))));
				} catch (IdAttivitaTirocinioNonValidoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test(expected = IdAttivitaTirocinioNonValidoException.class)
			public void testaTrovaDomandaTirocinioIdAttivitàNonValido() 
					throws IdAttivitaTirocinioNonValidoException {
				DomandaTirocinio domanda = new DomandaTirocinio(); 
				Attività attività = new Attività();
				attività.setData(LocalDateTime.now());
				attività.setDescrizioneAttivita("descrizione");
				attività.setNomeAttivita("attività");
				attività.setOraInizio(LocalTime.now().minusHours(1));
				attività.setOraFine(LocalTime.now());
				attività.setNumOre(2);
				attività.setStatus(Attività.IN_ATTESA);
				attività.setDomandaTirocinio(domanda);
				
				when(attivitàRepository.exists(2L)).thenReturn(false);
				
				when(attivitàRepository.findById(2L)).thenReturn(null);
				
				assertThat(domanda.getId(), is(equalTo(registroService.trovaDomandaTirocinio(2L))));				
			}
			
			@Test
			public void testaTrovaDomandaTirocinioPerRegistro() {
				DomandaTirocinio domanda = new DomandaTirocinio();
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.TERMINATO);
				registro.setDomandaTirocinio(domanda);
				
				when(registroRepository.exists(2L)).thenReturn(true);
				
				when(registroRepository.findById(2L)).thenReturn(registro);
				
				try {
					assertThat(domanda.getId(), is(equalTo(registroService.trovaDomandaTirocinioPerRegistro(2L))));
				} catch (IdRegistroTirocinioNonValidoException e) {
					fail(e.getMessage());
				}
			}
			
			@Test (expected = IdRegistroTirocinioNonValidoException.class)
			public void testaTrovaDomandaTirocinioPerRegistroIdRegistroNonValido() 
					throws IdRegistroTirocinioNonValidoException {
				DomandaTirocinio domanda = new DomandaTirocinio();
				RegistroTirocinio registro = new RegistroTirocinio();
				registro.setStatus(RegistroTirocinio.TERMINATO);
				registro.setDomandaTirocinio(domanda);
				
				when(registroRepository.exists(2L)).thenReturn(false);
				
				when(registroRepository.findById(2L)).thenReturn(null);
				
				assertThat(domanda.getId(), is(equalTo(registroService.trovaDomandaTirocinioPerRegistro(2L))));
				
			}
}
