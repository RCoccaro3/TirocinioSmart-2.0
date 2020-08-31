package it.unisa.di.tirociniosmart.web;

import java.time.LocalTime;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.unisa.di.tirociniosmart.convenzioni.Azienda;
import it.unisa.di.tirociniosmart.convenzioni.DelegatoAziendale;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinioRepository;
import it.unisa.di.tirociniosmart.domandetirocinio.IdDomandaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.impiegati.ImpiegatoUfficioTirocini;
import it.unisa.di.tirociniosmart.presidenteConsiglioDidattico.PresidenteConsiglioDidattico;
import it.unisa.di.tirociniosmart.progettiformativi.ProgettoFormativo;
import it.unisa.di.tirociniosmart.registroTirocinio.Attività;
import it.unisa.di.tirociniosmart.registroTirocinio.DescrizioneAttivitàNonValidaException;
import it.unisa.di.tirociniosmart.registroTirocinio.DomandaTirocinioNonValidataException;
import it.unisa.di.tirociniosmart.registroTirocinio.IdAttivitaTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.registroTirocinio.IdRegistroTirocinioNonValidoException;
import it.unisa.di.tirociniosmart.registroTirocinio.NomeAttivitàNonValidoException;
import it.unisa.di.tirociniosmart.registroTirocinio.NumOreNonValidoException;
import it.unisa.di.tirociniosmart.registroTirocinio.OraDiFineAttivitàNonValidaException;
import it.unisa.di.tirociniosmart.registroTirocinio.OraDiInizioAttivitàNonValidaException;
import it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinio;
import it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinioService;
import it.unisa.di.tirociniosmart.registroTirocinio.StatoAttivitaNonIdoneoException;
import it.unisa.di.tirociniosmart.registroTirocinio.StatoRegistroNonIdoneoException;
import it.unisa.di.tirociniosmart.studenti.Studente;
import it.unisa.di.tirociniosmart.utenza.RichiestaNonAutorizzataException;
import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;
import it.unisa.di.tirociniosmart.utenza.UtenzaService;

/**
 * Controller che espone via web i servizi relativi ai registri di tirocinio.
 * 
 * @see RegistroTirocinioService
 * @see RegistroTirocinio
 */
@Controller
public class RegistroTirocinioController {
	
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@Autowired
	private RegistroTirocinioService registroService;
	
	@Autowired
	private UtenzaService utenzaService;
	
	@Autowired
	private DomandaTirocinioRepository domandaRepository;
	
	@Autowired
	private AttivitàTirocinioFormValidator formValidator;
	
	
	@RequestMapping(value ="/dashboard/tirocini/registro/{idDomanda}", method = RequestMethod.GET)
	public String visualizzaElencoAttività(@PathVariable("idDomanda") long idDomanda,
			                               Model model,
										   RedirectAttributes redirectAttributes) {
		UtenteRegistrato utente = utenzaService.getUtenteAutenticato();
		List<Attività> attivita;
		
		DomandaTirocinio domanda = domandaRepository.findById(idDomanda);
		model.addAttribute("cfu", domanda.getCfu());		
		model.addAttribute("domanda",domanda);
		model.addAttribute("registro", domanda.getRegistroTirocinio());
		
		ProgettoFormativo progetto = domanda.getProgettoFormativo();
		Azienda azienda = progetto.getAzienda();
		model.addAttribute("azienda",azienda);
		
		try {
			if (!model.containsAttribute("attivitàForm")) {
				model.addAttribute("attivitàForm", new AttivitaTirocinioForm());
			}
			
			attivita = registroService.elencaAttività(idDomanda);
			model.addAttribute("attivita", attivita);
			
			
		} catch (IdDomandaTirocinioNonValidoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
					                             "toast.domandaTirocinio.idNonValido");
			return "redirect:/";
		} catch (RichiestaNonAutorizzataException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.autorizzazioni.richiestaNonAutorizzata");
			return "redirect:/";
		}
		
		if(utente instanceof Studente) {
			return "attivita-tirocinio-studente";
		} else if ( utente instanceof DelegatoAziendale) {
			return "attivita-tirocinio-delegato";
		} else if (utente instanceof PresidenteConsiglioDidattico) {
			return "registro-tirocinio-presidente";
		} else if (utente instanceof ImpiegatoUfficioTirocini) {
			return "registro-tirocinio-impiegato";	
		} else {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    							 "toast.autorizzazioni.richiestaNonAutorizzata");
			return "redirect:/";
		}				
	}
	
	@RequestMapping(value = "/dashboard/attivita/valida", method = RequestMethod.POST)
	public String validaAttivitaTirocinio(@RequestParam Long idAttivita, 
										  RedirectAttributes redirectAttributes) {
		Long idDomanda = null;
			try {
				registroService.validaAttività(idAttivita);
				idDomanda = registroService.trovaDomandaTirocinio(idAttivita);
				redirectAttributes.addFlashAttribute("testoNotifica",   
						 							 "toast.attivitaTirocinio.attivitaValidata");
			} catch (RichiestaNonAutorizzataException e) {
			  redirectAttributes.addFlashAttribute("testoNotifica", 
                        						   "toast.autorizzazioni.richiestaNonAutorizzata");
			} catch (IdAttivitaTirocinioNonValidoException e) {
				redirectAttributes.addFlashAttribute("testoNotifica", 
                        						   "toast.attivitaTirocinio.idNonValido");
			} catch (StatoAttivitaNonIdoneoException e) {
			  redirectAttributes.addFlashAttribute("testoNotifica",  
					  							   "toast.attivitaTirocinio.StatoAttivitaNonIdoneo");
			}
			
			
			return "redirect:/dashboard/tirocini/registro/" + idDomanda;
			
	}
	
	@RequestMapping(value = "/dashboard/attivita/annulla", method = RequestMethod.POST)
	  public String annullaAttivitaTirocinio(@RequestParam Long idAttivita,
	                                         RedirectAttributes redirectAttributes) {
	    Long idDomanda = null;
		try {
	      registroService.annullaAttività(idAttivita);
	      idDomanda = registroService.trovaDomandaTirocinio(idAttivita);
	      redirectAttributes.addFlashAttribute("testoNotifica",   
	                                           "toast.domandaTirocinio.attivitaAnnullata");
	    } catch (IdAttivitaTirocinioNonValidoException e) {
	      redirectAttributes.addFlashAttribute("testoNotifica", 
	                                           "toast.domandaTirocinio.idNonValido");
	    } catch (StatoAttivitaNonIdoneoException e) {
	      redirectAttributes.addFlashAttribute("testoNotifica",  
	                                           "toast.attivitaTirocinio.StatoDomandaNonIdoneo");
	    } catch (RichiestaNonAutorizzataException e) {
	      redirectAttributes.addFlashAttribute("testoNotifica", 
	                                           "toast.autorizzazioni.richiestaNonAutorizzata");
	    } catch (Exception e) {
	      logger.severe(e.getMessage());
	      return "redirect:/errore";
	    }
	    
	    return "redirect:/dashboard/tirocini/registro/" + idDomanda;
	  }
	
	@RequestMapping(value = "/dashboard/attività/aggiungi", method = RequestMethod.POST)
	public String aggiungiAttivitàTirocinio(@ModelAttribute("attivitàForm")
											AttivitaTirocinioForm attivitàForm,
											@RequestParam Long idRegistro,
									        BindingResult result,
									        RedirectAttributes redirectAttributes) {

		Long idDomanda = null;
		try {
			idDomanda = registroService.trovaDomandaTirocinioPerRegistro(idRegistro);
		} catch (IdRegistroTirocinioNonValidoException e1) {
			e1.printStackTrace();
		}
		
		formValidator.validate(attivitàForm, result);
		
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
		  redirectAttributes
	          .addFlashAttribute("org.springframework.validation.BindingResult.attivitàForm",
	                             result);
	      redirectAttributes.addFlashAttribute("attivitàForm", attivitàForm);
	      redirectAttributes.addFlashAttribute("testoNotifica", "toast.attivitàTirocinio.richiestaNonValida");

	      return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		}
		
		LocalTime oraInizio = LocalTime.of(attivitàForm.getOraInizio(),
										   attivitàForm.getMinutiInizio());
		
		LocalTime oraFine = LocalTime.of(attivitàForm.getOraFine(),
									     attivitàForm.getMinutiFine());
		
		//Istanzia un oggetto AttivitàTirocinio
		Attività attività = new Attività();
		attività.setOraInizio(oraInizio);
		attività.setOraFine(oraFine);
		attività.setDescrizioneAttivita(attivitàForm.getDescrizioneAttivita());
		attività.setNomeAttivita(attivitàForm.getNomeAttivita());
		System.out.println(attivitàForm.getNomeAttivita());
		
		attività.setNumOre(attivitàForm.getNumOre());
		
		try {
			registroService.aggiungiAttivitaTirocinio(attività, idRegistro);
			redirectAttributes.addFlashAttribute("testoNotifica", "toast.attivitàTirocinio.inserita");
		    return "redirect:/dashboard/tirocini/registro/" + idDomanda; 
		} catch (RichiestaNonAutorizzataException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.autorizzazioni.richiestaNonAutorizzata");
			return "redirect:/";
		} catch (NomeAttivitàNonValidoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.attivitàTirocinio.nomeAttivitàNonValido");
			return "redirect:/dashboard/tirocini/registro/"+ idDomanda;
		} catch (DescrizioneAttivitàNonValidaException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.attivitàTirocinio.descrizioneAttivitàNonValido");
			return "redirect:/dashboard/tirocini/registro/"+ idDomanda;
		} catch (OraDiFineAttivitàNonValidaException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.attivitàTirocinio.oraFineAttivitàNonValido");
			return "redirect:/dashboard/tirocini/registro/"+ idDomanda;
		} catch (OraDiInizioAttivitàNonValidaException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.attivitàTirocinio.oraInizioAttivitàNonValido");
			return "redirect:/dashboard/tirocini/registro/"+ idDomanda;
		} catch (NumOreNonValidoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.attivitàTirocinio.numOreAttivitàNonValido");
			return "redirect:/dashboard/tirocini/registro/"+ idDomanda;
		} catch (DomandaTirocinioNonValidataException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.attivitàTirocinio.domandaTirocinioNonValidata");
			return "redirect:/dashboard/tirocini/registro/"+ idDomanda;
		} 
		
	}
	
	
	@RequestMapping(value="/dashboard/registro/valida", method = RequestMethod.POST)
	public String validaRegistroTirocinio(@RequestParam Long idRegistro,
										  RedirectAttributes redirectAttributes) {
		Long idDomanda = null;
		try {
			registroService.validaRegistroTirocinio(idRegistro);
			idDomanda = registroService.trovaDomandaTirocinioPerRegistro(idRegistro);
			redirectAttributes.addFlashAttribute("testoNotifica", 
					   "toast.registroTirocinio.registroValidato");
			return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		} catch (RichiestaNonAutorizzataException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
					   "toast.autorizzazioni.richiestaNonAutorizzata");
		    return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		} catch (IdRegistroTirocinioNonValidoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
					   "toast.registroTirocinio.idRegistroNonValido");
		    return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		} catch (StatoRegistroNonIdoneoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
					   "toast.registroTirocinio.statoRegistroNonValido");
		    return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		}
	}
	
	@RequestMapping(value = "/dashboard/registro/chiudi", method = RequestMethod.POST)
	public String chiudiRegistroTirocinio(RedirectAttributes redirectAttributes,
                                          @RequestParam Long idRegistro) {
		Long idDomanda = null;
		try {
			idDomanda = registroService.trovaDomandaTirocinioPerRegistro(idRegistro);
		} catch (IdRegistroTirocinioNonValidoException e1) {
			redirectAttributes.addFlashAttribute("testoNotifica",
                    "toast.registroTirocinio.statoRegistroNonValido");
			return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		}
		
		try {
			registroService.chiudiRegistroTirocinio(idRegistro);
		    redirectAttributes.addFlashAttribute("testoNotifica",
                      "toast.registroTirocinio.registroTerminato");
		    return "redirect:/dashboard/tirocini/registro/" + idDomanda;	
		   
		} catch (RichiestaNonAutorizzataException e) {
			redirectAttributes.addFlashAttribute("testoNotifica", 
                    "toast.autorizzazioni.richiestaNonAutorizzata");
			return "redirect:/";
		} catch (IdRegistroTirocinioNonValidoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica",
                    "toast.registroTirocinio.idRegistroNonValido");
			return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		} catch (StatoRegistroNonIdoneoException e) {
			redirectAttributes.addFlashAttribute("testoNotifica",
                    "toast.registroTirocinio.statoRegistroNonValido");
			return "redirect:/dashboard/tirocini/registro/" + idDomanda;
		}
	}
	
	
}
