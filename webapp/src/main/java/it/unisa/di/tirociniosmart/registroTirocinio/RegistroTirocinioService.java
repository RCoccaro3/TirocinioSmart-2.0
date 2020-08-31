package it.unisa.di.tirociniosmart.registroTirocinio;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinioRepository;
import it.unisa.di.tirociniosmart.domandetirocinio.IdDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.mailsender.TirocinioSmartMailSender;
import it.unisa.di.tirociniosmart.presidenteConsiglioDidattico.PresidenteConsiglioDidattico;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

/**
 * Classe che definisce la logica di business per le operazioni possibili
 * relativamente alla gestione del registro di tirocinio
 * 
 * @see RegistroTirocinio
 * @see RegistroTirocinioRepository
 */
@Service
public class RegistroTirocinioService {

	@Autowired
	private RegistroTirocinioRepository registroRepository;
	
	@Autowired
	private UtenzaService utenzaService;
	
	@Autowired
	private AttivitàRepository attivitàRepository;
	
	@Autowired
	private DomandaTirocinioRepository domandaTirocinioRepository;
	
	@Autowired
	private TirocinioSmartMailSender mailSender;
	
	  /**
	   * Permette di ottenere la lista delle attività di tirocinio a partire
	   * dall'identificatore di una domanda di tirocinio validata.
	   * 
	   * @param idDomanda Long che rappresenta l'identificatore della domanda 
	   * 
	   * @return Lista di oggetti {@link Attività} associati alla domanda che ha idDomandaTirocinio
	   *         come identificatore
	   *         
	   * @throws IdDomandaTirocinioNonValidoException se l'identificatore passato come parametro non si riferisce
	   *         ad alcuna domanda
	   * @throws RichiestaNonAutorizzataException 
	   */
	@Transactional(rollbackFor = Exception.class)
	public List<Attività> elencaAttività(Long idDomanda)  
		throws IdDomandaTirocinioNonValidoException, RichiestaNonAutorizzataException {
		
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
		
		if(!domandaTirocinioRepository.existsById(idDomanda)) {
			throw new IdDomandaTirocinioNonValidoException();
		}
		
		List<Attività> attività = null;
		
		if((utente instanceof Studente) || (utente instanceof DelegatoAziendale)) {
			attività = 
					attivitàRepository.findAllByDomandaTirocinioId(idDomanda);
		} else if ((utente instanceof PresidenteConsiglioDidattico) || (utente instanceof ImpiegatoUfficioTirocini)) {
			attività = attivitàRepository.findAllByStatusAndDomandaTirocinioId(Attività.VALIDATA, idDomanda);
		} else {
			throw new RichiestaNonAutorizzataException();
		}		
		return attività;
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public Long trovaDomandaTirocinio(long idAttivita) 
			throws IdAttivitaTirocinioNonValidoException {
		if(!attivitàRepository.exists(idAttivita)) {
			throw new IdAttivitaTirocinioNonValidoException();
		}
		Attività a = attivitàRepository.findById(idAttivita);
		Long idDomanda = a.getRegistroTirocinio().getDomandaTirocinio().getId();
		
		return idDomanda;
	}
		
		public Long trovaDomandaTirocinioPerRegistro(long idRegistro) 
				throws IdRegistroTirocinioNonValidoException {
			if(!registroRepository.exists(idRegistro)) {
				throw new IdRegistroTirocinioNonValidoException();
			}
			
			RegistroTirocinio s = registroRepository.findById(idRegistro);
			Long idDomanda = s.getDomandaTirocinio().getId();
			
			return idDomanda;
		}

	public DomandaTirocinio verificaStatoDomandaTirocinio(DomandaTirocinio domandaTirocinio) 
			throws DomandaTirocinioNonValidataException {
		if(!(domandaTirocinio.getStatus() == DomandaTirocinio.VALIDATA)) {
			throw new DomandaTirocinioNonValidataException();
		} else {
			return domandaTirocinio;
		}
	}

	public int validaNumOre(Integer numOre) throws NumOreNonValidoException { 
		if(numOre==null || numOre < Attività.MIN_NUMORE 
				|| numOre > Attività.MAX_NUMORE) {
			throw new NumOreNonValidoException();
		} else {
		    return numOre;
		 }   
	}

	public LocalTime validaOraInizioAttività(LocalTime oraInizio, LocalTime oraFine) throws OraDiInizioAttivitàNonValidaException {
		if(oraInizio == null) {
			throw new OraDiInizioAttivitàNonValidaException();
		} else {
			LocalTime now = LocalTime.now();
			
			if(oraInizio.isAfter(now)) {
				throw new OraDiInizioAttivitàNonValidaException();
			} else {
				return oraInizio;
			}
		}
	}

	public LocalTime validaOraFineAttività(LocalTime oraInizio, LocalTime oraFine) 
			throws OraDiFineAttivitàNonValidaException {
		if(oraFine == null) {
			throw new OraDiFineAttivitàNonValidaException();
		} else {
			LocalTime now = LocalTime.now();
			if(oraFine.isBefore(oraInizio) || oraFine.isAfter(now) 
					|| oraFine.equals(oraInizio)) {
				throw new OraDiFineAttivitàNonValidaException();
			} else {
				return oraFine;
			}
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public String validaDescrizione(String descrizioneAttività) throws DescrizioneAttivitàNonValidaException {
		if (descrizioneAttività == null) {
		      throw new DescrizioneAttivitàNonValidaException();
		    } else {
		    	descrizioneAttività = descrizioneAttività.trim();
		      
		      if (descrizioneAttività.length() < Attività.MIN_LUNGHEZZA_DESCRIZIONE) {
		        throw new DescrizioneAttivitàNonValidaException();
		      } else {
		        return descrizioneAttività;
		      }
		    }
		
	}

	@Transactional(rollbackFor = Exception.class)
	public String validaNome(String nomeAttività) throws NomeAttivitàNonValidoException {
		if(nomeAttività == null) {
			throw new NomeAttivitàNonValidoException();
		} else {
			nomeAttività = nomeAttività.trim();
			
			 if (nomeAttività.length() < Attività.MIN_LUNGHEZZA_NOME
			     || nomeAttività.length() > Attività.MAX_LUNGHEZZA_NOME) {
			        throw new NomeAttivitàNonValidoException();
			 } else {
			   return nomeAttività;
			 }	
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Attività validaAttività(long idAttività) 
			throws RichiestaNonAutorizzataException,
			IdAttivitaTirocinioNonValidoException, StatoAttivitaNonIdoneoException {
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
		
		//Solo un delegato aziendle può validare una attività di tirocinio
		if(!(utente instanceof DelegatoAziendale)) {
			throw new RichiestaNonAutorizzataException();
		}
		
		
		//Controlla che l'attività esista
		Attività attivita = attivitàRepository.findById(idAttività);
		if(attivita == null) {
			throw new IdAttivitaTirocinioNonValidoException();
		}
		
		RegistroTirocinio registro = attivita.getRegistroTirocinio();		
		
		if(attivita.getStatus() == Attività.IN_ATTESA) {
			attivita.setStatus(Attività.VALIDATA);
			registro.setOreValidate(registro.getOreValidate() + attivita.getNumOre());
			registro.setOreDaEffettuare(
					registro.getDomandaTirocinio().getOreTotaliTirocinio() - attivita.getNumOre());
			registro.setOreInAttesa(registro.getOreInAttesa() - attivita.getNumOre());
			mailSender.sendEmail(attivita, attivita.getDomandaTirocinio().getStudente().getEmail());
			return attivita;
		} else {
			throw new StatoAttivitaNonIdoneoException();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Attività annullaAttività(long idAttività) 
			throws RichiestaNonAutorizzataException,
			IdAttivitaTirocinioNonValidoException, StatoAttivitaNonIdoneoException {
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
		
		//Solo un delegato aziendle può validare una attività di tirocinio
		if(!(utente instanceof DelegatoAziendale)) {
			throw new RichiestaNonAutorizzataException();
		}
		
		//Controlla che l'attività esista
		Attività attivita = attivitàRepository.findById(idAttività);		
		if(attivita == null) {
			throw new IdAttivitaTirocinioNonValidoException();
		}
		
		RegistroTirocinio registro = attivita.getRegistroTirocinio();
		
		if(attivita.getStatus() == Attività.IN_ATTESA) {
			attivita.setStatus(Attività.NON_VALIDA);
			registro.setOreInAttesa(registro.getOreInAttesa() - attivita.getNumOre());
			mailSender.sendEmail(attivita, attivita.getDomandaTirocinio().getStudente().getEmail());
			return attivita;
		} else {
			throw new StatoAttivitaNonIdoneoException();
		}
	}

	
	public RegistroTirocinio aggiungiRegistroTirocinio(RegistroTirocinio registro) 
			throws DomandaTirocinioNonValidataException {
		registro.setDomandaTirocinio(verificaStatoDomandaTirocinio(registro.getDomandaTirocinio()));
		registro.setStatus(RegistroTirocinio.IN_ATTESA);
		registro.setOreDaEffettuare(0);
		registro.setOreValidate(0);
		registro.setOreInAttesa(0);
		registro = registroRepository.save(registro);
		return registro;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public RegistroTirocinio validaRegistroTirocinio(long idRegistro) 
			throws RichiestaNonAutorizzataException, 
			IdRegistroTirocinioNonValidoException,
			StatoRegistroNonIdoneoException {
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
		
		//Solo il Presidente del Consiglio Didattico può validare il registro di tirocinio
		if(!(utente instanceof PresidenteConsiglioDidattico)) {
			throw new RichiestaNonAutorizzataException();
		}
		
		RegistroTirocinio registro = registroRepository.findById(idRegistro);
		if(registro == null) {
			throw new IdRegistroTirocinioNonValidoException();
		}
		
		if(registro.getStatus() == RegistroTirocinio.TERMINATO) {
			registro.setStatus(RegistroTirocinio.VALIDATO);
			mailSender.sendEmail(registro, registro.getDomandaTirocinio().getStudente().getEmail());
			return registro;
		} else {
			throw new StatoRegistroNonIdoneoException();
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public RegistroTirocinio chiudiRegistroTirocinio(long idRegistro) 
			throws RichiestaNonAutorizzataException, 
			IdRegistroTirocinioNonValidoException,
			StatoRegistroNonIdoneoException {
	    
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
	    
	    // Solo un delegato aziendale può chiudere un registro di tirocinio
	    if (!(utente instanceof DelegatoAziendale)) {
	      throw new RichiestaNonAutorizzataException();
	    }
	    
	    //..se esiste..
	    if (!registroRepository.existsById(idRegistro)) {
	      throw new IdRegistroTirocinioNonValidoException();
	    }

	    DelegatoAziendale delegato = (DelegatoAziendale) utente;
	    RegistroTirocinio registro = registroRepository.findById(idRegistro);
	    
	    if(registro.getStatus() != RegistroTirocinio.IN_ATTESA) {
	    	throw new StatoRegistroNonIdoneoException();
	    }
	    
	    registro.setStatus(RegistroTirocinio.TERMINATO);
	    
		mailSender.sendEmail(registro, registro.getDomandaTirocinio().getStudente().getEmail());
	    return registro;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Attività aggiungiAttivitaTirocinio(Attività attivita, long idRegistro) 
			throws RichiestaNonAutorizzataException,
			NomeAttivitàNonValidoException,
			DescrizioneAttivitàNonValidaException,
			DomandaTirocinioNonValidataException,
			NumOreNonValidoException,
			OraDiInizioAttivitàNonValidaException,
			OraDiFineAttivitàNonValidaException {
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
	    
	    // Solo uno studente può aggiungere un'attività di tirocinio
	    if (!(utente instanceof Studente)) {
	      throw new RichiestaNonAutorizzataException();
	    }
	    
	    RegistroTirocinio registro = registroRepository.findById(idRegistro);
	    
	    attivita.setNomeAttivita(validaNome(attivita.getNomeAttivita()));
	    attivita.setDescrizioneAttivita(validaDescrizione(attivita.getDescrizioneAttivita()));
	    attivita.setData(LocalDateTime.now());
	    attivita.setStatus(Attività.IN_ATTESA);
	    attivita.setRegistroTirocinio(registro);
	    attivita.setNumOre(validaNumOre(attivita.getNumOre()));
	    attivita.setOraInizio(validaOraInizioAttività(attivita.getOraInizio(), attivita.getOraFine()));
	    attivita.setOraFine(validaOraFineAttività(attivita.getOraInizio(), attivita.getOraFine()));
	    attivita.setDomandaTirocinio(verificaStatoDomandaTirocinio(registro.getDomandaTirocinio()));
	    	    	   
	    registro.setOreInAttesa(new Integer(attivita.getNumOre()));
	    
	    attivita = attivitàRepository.save(attivita);
	    
		mailSender.sendEmail(attivita, attivita.getDomandaTirocinio().getStudente().getEmail());
	    
	    return attivita;
	
	}
	

}
