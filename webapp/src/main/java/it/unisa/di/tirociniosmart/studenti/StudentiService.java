package it.unisa.di.tirociniosmart.studenti;

import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.mailsender.TirocinioSmartMailSender;
import it.unisa.di.tirociniosmart.utenza.CognomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.EmailEsistenteException;
import it.unisa.di.tirociniosmart.utenza.EmailNonValidaException;
import it.unisa.di.tirociniosmart.utenza.NomeNonValidoException;
import it.unisa.di.tirociniosmart.utenza.PasswordNonValidaException;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.SessoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.TelefonoNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UsernameEsistenteException;
import it.unisa.di.tirociniosmart.utenza.UsernameNonValidoException;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe che definisce la logica di business per le operazioni possibili nell'ambito delle
 * richieste di iscrizione da parte di uno studente.
 *
 * @see Studente
 * @see StudenteRepository
 */
@Service
public class StudentiService {
  
  @Autowired
  private StudenteRepository studenteRepository;
  
  @Autowired
  private RichiestaIscrizioneRepository richiestaIscrizioneRepository;
  
  @Autowired
  private UtenzaService utenzaService;
  
  @Autowired
  private TirocinioSmartMailSender mailSender;
  
  /**
   * Permette di richiedere al sistema il salvataggio di uno studente. La procedura registra
   * uno studente assegnandogli una {@link RichiestaIscrizione} inizialmente in attesa.
   * 
   * @param studente {@link Studente} per cui si vuole registrare una richiesta di iscrizione.
   *                 Non è necessario specificare la data della richiesta di iscrizione ad essa
   *                 associata poiché è il metodo stesso ad impostarla.
   * 
   * @return Lo studente passato come parametro la cui richiesta d'iscrizione è stata registrata
   * 
   * @pre studente != null
   */
  @Transactional(rollbackFor = Exception.class)
  public Studente registraRichiestaIscrizione(Studente studente) 
         throws UsernameNonValidoException, PasswordNonValidaException, UsernameEsistenteException,
                EmailEsistenteException, EmailNonValidaException, 
                NomeNonValidoException, CognomeNonValidoException, 
                TelefonoNonValidoException, SessoNonValidoException,
                IndirizzoStudenteNonValidoException, 
                MatricolaStudenteEsistenteException, MatricolaStudenteNonValidaException,
                DataDiNascitaStudenteNonValidaException, RichiestaNonAutorizzataException {
    // Solamente gli ospiti possono registrare nuove richieste di iscrizione
    if (utenzaService.getUtenteAutenticato() != null) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Valida i campi dello studente
    studente.setUsername(utenzaService.validaUsername(studente.getUsername()));
    studente.setPassword(utenzaService.validaPassword(studente.getPassword()));
    studente.setEmail(utenzaService.validaEmail(studente.getEmail()));
    studente.setNome(utenzaService.validaNome(studente.getNome()));
    studente.setCognome(utenzaService.validaCognome(studente.getCognome()));
    studente.setTelefono(utenzaService.validaTelefono(studente.getTelefono()));
    studente.setSesso(utenzaService.validaSesso(studente.getSesso()));
    studente.setIndirizzo(validaIndirizzoStudente(studente.getIndirizzo()));
    studente.setMatricola(validaMatricolaStudente(studente.getMatricola()));
    studente.setDataDiNascita(validaDataDiNascitaStudente(studente.getDataDiNascita()));
    
    // Imposta stato e data della richiesta
    RichiestaIscrizione richiesta = studente.getRichiestaIscrizione();
    richiesta.setStatus(RichiestaIscrizione.IN_ATTESA);
    richiesta.setDataRichiesta(LocalDateTime.now());
    
    // Registra le informazioni
    studente = studenteRepository.save(studente);
    mailSender.sendEmail(richiesta, richiesta.getStudente().getEmail());
    return studente;
  }
  
  /**
   * Permette ad un impiegato dell'ufficio tirocini di approvare una richiesta di iscrizione già 
   * presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di iscrizione
   *                    da approvare
   *                   
   * @return richiesta Oggetto {@link RichiestaIscrizione} che rappresenta la richiesta d'iscrizione
   *                   che è stata approvata                   
   * 
   * @throws IdRichiestaIscrizioneNonValidoException se non esiste alcuna richiesta di
   *         iscrizione nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaIscrizioneGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello "in attesa"
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che tenta di approvare la richiesta
   *         d'iscrizione non è un impiegato dell'ufficio tirocini
   */
  @Transactional(rollbackFor = Exception.class)
  public RichiestaIscrizione approvaRichiestaIscrizione(long idRichiesta)
         throws IdRichiestaIscrizioneNonValidoException,
                RichiestaIscrizioneGestitaException, RichiestaNonAutorizzataException {
    // Solamente gli impiegati dell'ufficio tirocini possono approvare le richieste d'iscrizione
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la richiesta esista
    RichiestaIscrizione richiesta = richiestaIscrizioneRepository.findById(idRichiesta);
    if (richiesta == null) {
      throw new IdRichiestaIscrizioneNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza
    if (richiesta.getStatus() != RichiestaIscrizione.IN_ATTESA) {
      throw new RichiestaIscrizioneGestitaException();
    } else {
      richiesta.setStatus(RichiestaIscrizione.APPROVATA);
      mailSender.sendEmail(richiesta, richiesta.getStudente().getEmail());
      return richiesta;
    }
  }
  
  /**
   * Permette di ottenere la lista delle richieste d'iscrizione in attesa di essere gestite.
   * 
   * @return Lista di {@link RichiestaIscrizione} contenente tutte le richieste d'iscrizione non
   *         ancora gestite con status uguale a {@link RichiestaIscrizione#IN_ATTESA}
   */
  @Transactional
  public List<RichiestaIscrizione> elencaListaRichiesteIscrizione()
         throws RichiestaNonAutorizzataException {    
    //Solo gli impiegato dell'ufficio tirocini possono visualizzare l'elenco delle richieste
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    List<RichiestaIscrizione> richiesteIscrizione = richiestaIscrizioneRepository.findAllByStatus(
                                                                     RichiestaIscrizione.IN_ATTESA);
    return richiesteIscrizione;
  }
  
  /**
   * Permette ad un impiegato dell'ufficio tirocini di rifiutare una richiesta di iscrizione già 
   * presente nel sistema.
   * 
   * @param idRichiesta Long che rappresenta l'identificatore della richiesta di iscrizione
   *                    da rifiutare
   * 
   * @return richiesta {@link RichiestaIscrizioneGestitaException} che rappresenta la richiesta 
   *         di iscrizione che è stata rifiutata
   * 
   * @throws IdRichiestaIscrizioneNonValidoException se non esiste alcuna richiesta di
   *         iscrizione nel sistema con identificatore uguale ad idRichiesta
   * 
   * @throws RichiestaIscrizioneGestitaException se la richiesta identificata da idRichiesta
   *         si trova in uno stato diverso da quello "in attesa"
   *         
   * @throws CommentoRichiestaIscrizioneNonValidoException se il commento da associare alla
   *         richiesta è nullo o vuoto
   *         
   * @throws RichiestaNonAutorizzataException se l'utente che tenta di rifiutare la richiesta
   *        d'iscrizione non è un impiegato dell'ufficio tirocini
   */
  @Transactional(rollbackFor = Exception.class)
  public RichiestaIscrizione rifiutaRichiestaIscrizione(long idRichiesta, String commento)
         throws IdRichiestaIscrizioneNonValidoException, RichiestaIscrizioneGestitaException,
                CommentoRichiestaIscrizioneNonValidoException, RichiestaNonAutorizzataException {
    // Solamente gli impiegati dell'ufficio tirocini possono rifiutare le richieste d'iscrizione
    if (!(utenzaService.getUtenteAutenticato() instanceof ImpiegatoUfficioTirocini)) {
      throw new RichiestaNonAutorizzataException();
    }
    
    // Controlla che la richiesta esista
    RichiestaIscrizione richiesta = richiestaIscrizioneRepository.findById(idRichiesta);
    if (richiesta == null) {
      throw new IdRichiestaIscrizioneNonValidoException();
    }
    
    // Controlla che la richiesta non sia già stata gestita in precedenza ed impostane lo stato
    if (richiesta.getStatus() != RichiestaIscrizione.IN_ATTESA) {
      throw new RichiestaIscrizioneGestitaException();
    } else {
      richiesta.setStatus(RichiestaIscrizione.RIFIUTATA); 
    }
    
    richiesta.setCommentoUfficioTirocini(validaCommentoRichiesta(commento));
    mailSender.sendEmail(richiesta, richiesta.getStudente().getEmail());
    return richiesta;
  }
  
  /**
   * Controlla che il commento sul rifiuto di una richiesta sia specificato.
   * 
   * @param commento Stringa che rappresenta il commento da controllare
   * 
   * @return La stringa che rappresenta il commento da controllare bonificata
   * 
   * @throws CommentoRichiestaIscrizioneNonValidoException se il commento passato come parametro
   *         è nullo oppure è rappresentato da una stringa con un numero di caratteri 
   *         {@link RichiestaIscrizione#MIN_LUNGHEZZA_COMMENTO}
   */
  public String validaCommentoRichiesta(String commento) 
        throws CommentoRichiestaIscrizioneNonValidoException {
    if (commento == null) {
      throw new CommentoRichiestaIscrizioneNonValidoException();
    } else {
      commento = commento.trim();
      
      if (commento.length() < RichiestaIscrizione.MIN_LUNGHEZZA_COMMENTO) {
        throw new CommentoRichiestaIscrizioneNonValidoException();
      } else {
        return commento;
      }
    }
  }
  
  /**
   * Controlla che la matricola di un'azienda sia specificata e che rispetti il formato
   * prestabilito. Controlla inoltre che tale matricola non sia già presente nel sistema.
   * 
   * @param matricolaStudente Stringa che rappresenta la matricola da controllare
   * 
   * @return La stringa che rappresenta la matricola da controllare bonificata
   * 
   * @throws MatricolaStudenteNonValidaException se la matricola passata come parametro è nulla
   *         oppure se non rispetta il formato {@link Studente#MATRICOLA_PATTERN}
   * 
   * @throws MatricolaStudenteEsistenteException se la matricola passata come parametro è già
   *         presente nel sistema
   */
  public String validaMatricolaStudente(String matricolaStudente)
         throws MatricolaStudenteNonValidaException, MatricolaStudenteEsistenteException {
    if (matricolaStudente == null) {
      throw new MatricolaStudenteNonValidaException();
    } else {
      matricolaStudente = matricolaStudente.trim();
      
      if (!matricolaStudente.matches(Studente.MATRICOLA_PATTERN)) {
        throw new MatricolaStudenteNonValidaException();
      } else if (studenteRepository.existsByMatricola(matricolaStudente)) {
        throw new MatricolaStudenteEsistenteException();
      } else {
        return matricolaStudente;
      }
    }
  }
  
  /**
   * Controlla che l'indirizzo di uno studente sia specificato e che la sua lunghezza rispetti i
   * parametri prestabiliti.
   * 
   * @param indirizzoStudente Stringa che rappresenta l'indirizzo da controllare
   * 
   * @return La stringa che rappresenta l'indirizzo da controllare bonificata
   * 
   * @throws IndirizzoStudenteNonValidoException se l'indirizzo dello studente è nullo o se la sua
   *         lunghezza non rientra nell'intervallo che va da 
   *         {@link Studente#MIN_LUNGHEZZA_INDIRIZZO} a {@link Studente#MAX_LUNGHEZZA_INDIRIZZO}
   */
  public String validaIndirizzoStudente(String indirizzoStudente)
         throws IndirizzoStudenteNonValidoException {
    if (indirizzoStudente == null) {
      throw new IndirizzoStudenteNonValidoException();
    } else {
      indirizzoStudente = indirizzoStudente.trim();
   
      if (indirizzoStudente.length() < Studente.MIN_LUNGHEZZA_INDIRIZZO
          || indirizzoStudente.length() > Studente.MAX_LUNGHEZZA_INDIRIZZO) {
        throw new IndirizzoStudenteNonValidoException();
      } else {
        return indirizzoStudente;
      } 
    }
  }
  
  /**
   * Controlla che la data di nascita di uno studente sia specificato e che la sua lunghezza 
   * rispetti i parametri prestabiliti.
   * 
   * @param dataDiNascita LocalDate che rappresenta la data da controllare
   * 
   * @return Oggetto LocalDate che rappresenta la data di nascita da controllare bonificata
   * 
   * @throws DataDiNascitaStudenteNonValidaException se la data è nulla o se la distanza
   *         dall'anno corrente è maggiore di {#MAX_DISTANZA_ANNO_NASCITA} o è minore di
   *         {#MIN_DISTANZA_ANNO_NASCITA}
   */
  public LocalDate validaDataDiNascitaStudente(LocalDate dataDiNascita) 
         throws DataDiNascitaStudenteNonValidaException {
         
    if (dataDiNascita == null) {
      throw new DataDiNascitaStudenteNonValidaException();
    } else {
      LocalDate oggi = LocalDate.now();
      long distanza = ChronoUnit.YEARS.between(dataDiNascita, oggi);
      
      if (distanza < MIN_DISTANZA_ANNO_NASCITA || distanza > MAX_DISTANZA_ANNO_NASCITA) {
        throw new DataDiNascitaStudenteNonValidaException();
      } else {
        return dataDiNascita;
      } 
    }
  }
  
  /** 
   * Costante che rappresenta la minima distanza in anni dalla data corrente 
   * per la data di nascita.
   */
  public static final int MIN_DISTANZA_ANNO_NASCITA = 17;
 
  /** 
   * Costante che rappresenta la massima distanza in anni dalla data corrente 
   * per la data di nascita.
   */
  public static final int MAX_DISTANZA_ANNO_NASCITA = 130;

}
