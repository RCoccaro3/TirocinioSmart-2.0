package it.unisa.di.tirociniosmart.mailsender;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.registroTirocinio.Attività;
import it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinio;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;
import it.unisa.di.tirociniosmart.studenti.Studente;

/**
 * Classe che offre i casi di test di ProgettoFormativoService.
 * 
 * @see TirocinioSmartMailSender
 * 
 */ 
@RunWith(MockitoJUnitRunner.class)
public class TirocinioSmartMailSenderTest {

	@Mock
	private JavaMailSender javaMailSender;
	
	@InjectMocks
	private TirocinioSmartMailSender mailSender;
	
	@Captor 
	ArgumentCaptor<String> captor;
	
	private RichiestaIscrizione richiestaIscrizione;
	
	private RichiestaConvenzionamento richiestaConvenzionamento;
	
	private DomandaTirocinio domandaTirocinio;
	
	private Studente studente;
	
	private DelegatoAziendale delegato;
	
	private Azienda azienda;
	
	private ProgettoFormativo progettoFormativo;
	
	private Attività attività;
	
	private RegistroTirocinio registroTirocinio;
	
	@Before
	public void setUp() {
		studente = new Studente();
		studente.setNome("Mario");
		studente.setCognome("Rossi");
		studente.setEmail("m.rossi@studenti.unisa.it");
		
		richiestaIscrizione = studente.getRichiestaIscrizione();
		richiestaIscrizione.setDataRichiesta(LocalDateTime.now().minusDays(2));
		richiestaIscrizione.setCommentoUfficioTirocini("CIAONE");
		
		azienda = new Azienda();
		azienda.setId("NetData");
		azienda.setNome("Nome");
		
		delegato = azienda.getDelegato();
		delegato.setNome("Paolo");
		delegato.setCognome("Bianchi");
		delegato.setEmail("p.bianchi@gmail.com");
		
		progettoFormativo = new ProgettoFormativo();
		progettoFormativo.setNome("DataScience");
		progettoFormativo.setAzienda(azienda);
	
		domandaTirocinio = new DomandaTirocinio();
		domandaTirocinio.setStudente(studente);
		domandaTirocinio.setProgettoFormativo(progettoFormativo);
		domandaTirocinio.setData(LocalDateTime.now().minusDays(10));
		domandaTirocinio.setCommentoImpiegato("Commento1");
		domandaTirocinio.setInizioTirocinio(LocalDate.now().plusDays(15));
		
		richiestaConvenzionamento = azienda.getRichiesta();
		richiestaConvenzionamento.setDataRichiesta(LocalDateTime.now());
		richiestaConvenzionamento.setCommentoUfficioTirocini("CIAO");	
		

		
		registroTirocinio = new RegistroTirocinio();
		registroTirocinio.setDomandaTirocinio(domandaTirocinio);
		registroTirocinio.setOreDaEffettuare(13);
		registroTirocinio.setOreInAttesa(10);
		registroTirocinio.setOreValidate(0);	
		
		attività = new Attività();
		attività.setNomeAttivita("nome");
		attività.setDescrizioneAttivita("descrizione");
		attività.setData(LocalDateTime.now());
		attività.setNumOre(1);
		attività.setRegistroTirocinio(registroTirocinio);
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta d'iscrizione 
	 * viene approvata.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaIscrizioneApprovata() {
		String destinatario = studente.getEmail(); 
		richiestaIscrizione.setStatus(RichiestaIscrizione.APPROVATA);
		
		mailSender.sendEmail(richiestaIscrizione, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta d'iscrizione 
	 * viene rifiutata.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaIscrizioneRifiutata() {
		String destinatario = studente.getEmail(); 
		richiestaIscrizione.setStatus(RichiestaIscrizione.RIFIUTATA);
		
		mailSender.sendEmail(richiestaIscrizione, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta d'iscrizione 
	 * si trova in uno stato non idoneo
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaIscrizioneNoMessaggioInstanziato() {
		String destinatario = studente.getEmail(); 
		richiestaIscrizione.setStatus(3);
		
		mailSender.sendEmail(richiestaIscrizione, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta d'iscrizione 
	 * viene inviata correttamente.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaIscrizioneInAttesa() {
		String destinatario = studente.getEmail(); 
		richiestaIscrizione.setStatus(RichiestaIscrizione.IN_ATTESA);
		
		mailSender.sendEmail(richiestaIscrizione, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta di convenzionamento 
	 * viene inviata correttamente.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaConvenzionamentoInAttesa() {
		String destinatario = delegato.getEmail(); 
		richiestaConvenzionamento.setStatus(RichiestaConvenzionamento.IN_ATTESA);
		
		mailSender.sendEmail(richiestaConvenzionamento, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta di convenzionamento 
	 * viene approvata.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaConvenzionamentoApprovata() {
		String destinatario = delegato.getEmail(); 
		richiestaConvenzionamento.setStatus(RichiestaConvenzionamento.APPROVATA);
		
		mailSender.sendEmail(richiestaConvenzionamento, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta di convenzionamento 
	 * viene rifiutata.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaConvenzionamentoRifiutata() {
		String destinatario = delegato.getEmail(); 
		richiestaConvenzionamento.setStatus(RichiestaConvenzionamento.RIFIUTATA);
		
		mailSender.sendEmail(richiestaConvenzionamento, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando la richiesta di convenzionamento 
	 * si trova in uno stato non idoneo.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRichiestaConvenzionamentoNoMessaggioInstanziato() {
		String destinatario = delegato.getEmail(); 
		richiestaConvenzionamento.setStatus(6);
		
		mailSender.sendEmail(richiestaConvenzionamento, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene inviata correttamente.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioInAttesa() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.IN_ATTESA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene accettata dall'azienda.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioAccettata() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.ACCETTATA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene rifiutata dall'azienda.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioRifiutata() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.RIFIUTATA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene approvata dall'ufficio tirocini.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioApprovata() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.APPROVATA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene respinta dall'ufficio tirocini.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioRespinta() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.RESPINTA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene validata dal Presidente del Consiglio Didattico
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioValidata() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.VALIDATA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * viene annullata dal Presidente del Consiglio Didattico
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioAnnullata() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(DomandaTirocinio.ANNULLATA);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando una domanda di tirocinio 
	 * si trova in uno stato non idoneo
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenDomandaTirocinioNoMessaggioIstanziato() {
		String destinatario = studente.getEmail(); 
		domandaTirocinio.setStatus(8);
		
		mailSender.sendEmail(domandaTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un'attività di tirocinio 
	 * viene inviata correttamente.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenAttivitàInAttesa() {
		String destinatario = studente.getEmail(); 
		attività.setStatus(Attività.IN_ATTESA);
		
		mailSender.sendEmail(attività, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un'attività di tirocinio 
	 * viene validata dal Delegato Aziendale.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenAttivitàValidata() {
		String destinatario = studente.getEmail(); 
		attività.setStatus(Attività.VALIDATA);
		
		mailSender.sendEmail(attività, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un'attività di tirocinio 
	 * viene annullata dal Delegato Aziendale.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenAttivitàAnnullata() {
		String destinatario = studente.getEmail(); 
		attività.setStatus(Attività.NON_VALIDA);
		
		mailSender.sendEmail(attività, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un'attività di tirocinio 
	 * si trova in uno stato non idoneo
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenAttivitàNoMessaggioIstanziato() {
		String destinatario = studente.getEmail(); 
		attività.setStatus(8);
		
		mailSender.sendEmail(attività, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un registro di tirocinio 
	 * viene chiuso dal Delegato Aziendale.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRegistroChiuso() {
		String destinatario = studente.getEmail(); 
		registroTirocinio.setStatus(RegistroTirocinio.TERMINATO);
		
		mailSender.sendEmail(registroTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un registro di tirocinio 
	 * viene validato dal Presidente del Consiglio Didattico.
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRegistroValidato() {
		String destinatario = studente.getEmail(); 
		registroTirocinio.setStatus(RegistroTirocinio.VALIDATO);
		
		mailSender.sendEmail(registroTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa l'invio della mail quando un registro di tirocinio 
	 * si trova in uno stato non idoneo
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailWhenRegistroNoMessaggioIstanziato() {
		String destinatario = studente.getEmail(); 
		registroTirocinio.setStatus(8);
		
		mailSender.sendEmail(registroTirocinio, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
	}
	
	/**
	 * Metodo che testa il caso in cui il messaggio non venga istanziato 
	 * 
	 * @test {@link TirocinioSmartMailSender#sendEmail(Object, String)}
	 * 
	 * @result Il test e' superato se il metodo send della classe javaMailSender 
	 *		   e' correttamente invocato
	 */
	@Test
	public void testaSendEmailMessaggioNonIstanziato() {
		String destinatario = "m.rossi@studenti.unisa.it";
		Object object = new Object(); 
		
		mailSender.sendEmail(object, destinatario);
		verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));	
	}
}
