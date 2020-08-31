package it.unisa.di.tirociniosmart.web;

import java.time.DateTimeException;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.unisa.di.tirociniosmart.registroTirocinio.DescrizioneAttivitàNonValidaException;
import it.unisa.di.tirociniosmart.registroTirocinio.NomeAttivitàNonValidoException;
import it.unisa.di.tirociniosmart.registroTirocinio.NumOreNonValidoException;
import it.unisa.di.tirociniosmart.registroTirocinio.OraDiFineAttivitàNonValidaException;
import it.unisa.di.tirociniosmart.registroTirocinio.OraDiInizioAttivitàNonValidaException;
import it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinioService;

/**
 * Classe che definisce un validatore per {@link AttivitaTirocinioForm} tramite i servizi offerti da
 * {@link AttivitàService}. Il controllo viene effettuato su tutti i campi del form.
 *
 * @see AttivitaTirocinioForm
 * @see AttivitàService
 */
@Component
public class AttivitàTirocinioFormValidator implements Validator {
	
	@Autowired
	private RegistroTirocinioService registroService;

	/**
	 * Permette di definire le classi cui il validatore è applicabile.
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return AttivitaTirocinioForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		AttivitaTirocinioForm form = (AttivitaTirocinioForm) target;
		
		try {
		if(form.getOraInizio() == null
		   || form.getMinutiInizio() == null) {
			throw new OraDiInizioAttivitàNonValidaException();
		}
		
		if(form.getOraFine() == null
		   || form.getMinutiFine() == null) {
			throw new OraDiFineAttivitàNonValidaException();
		}
		
		LocalTime oraInizio;
		LocalTime oraFine;
		
		try {
			oraInizio = LocalTime.of(form.getOraInizio(),
					                 form.getMinutiInizio());
		} catch (DateTimeException e) {
			throw new OraDiInizioAttivitàNonValidaException();
		}
		

		try {
			oraFine = LocalTime.of(form.getOraFine(),
					               form.getMinutiFine());
		} catch (DateTimeException e) {
			throw new OraDiFineAttivitàNonValidaException();
		}
		
		registroService.validaOraInizioAttività(oraInizio, oraFine);
		registroService.validaOraFineAttività(oraInizio, oraFine);
		
		} catch (OraDiInizioAttivitàNonValidaException ee) {
			errors.rejectValue("oraInizio", "attivitàTirocinioForm.oraInizio.nonValida");
		    errors.rejectValue("minutiInizio", "attivitàTirocinioForm.minutiInizio.nonValida");
		} catch (OraDiFineAttivitàNonValidaException ee) {
			errors.rejectValue("oraFine", "attivitàTirocinioForm.oraFine.nonValida");
		    errors.rejectValue("minutiFine", "attivitàTirocinioForm.minutiFine.nonValida");
		}
		
		
		try {
			registroService.validaNome(form.getNomeAttivita());
		} catch (NomeAttivitàNonValidoException e) {
			errors.rejectValue("nomeAttivita", "attivitàTirocinioForm.nome.nonValido");
		}
			
		try {
			registroService.validaDescrizione(form.getDescrizioneAttivita());
		} catch (DescrizioneAttivitàNonValidaException e) {
			errors.rejectValue("descrizioneAttivita", "attivitàTirocinioForm.descrizione.nonValido");
		}
		
		try {
			registroService.validaNumOre(form.getNumOre());
		} catch (NumOreNonValidoException e) {
			 errors.rejectValue("numOre", "attivitàTirocinioForm.numOre.nonValido");
		}

	}

}
