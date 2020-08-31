package it.unisa.di.tirociniosmart.mailsender;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import it.unisa.di.tirociniosmart.convenzioni.RichiestaConvenzionamento;
import it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio;
import it.unisa.di.tirociniosmart.registroTirocinio.Attività;
import it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinio;
import it.unisa.di.tirociniosmart.studenti.RichiestaIscrizione;


/**
 * Classe singleton che consente l'invio di un'email al cambiamento di stato
 * di un oggetto
 *  
 * @author rosan
 *
 */
@Component
@Scope("singleton")
public class TirocinioSmartMailSender {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(Object object, String receiver) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(receiver);
		msg.setSubject(SUBJECT);
		String message = writeMessage(object);
		msg.setText(message);
		
		javaMailSender.send(msg);
	}
	
	private String writeMessage(Object obj) {
		if(obj instanceof RichiestaIscrizione) {
			RichiestaIscrizione richiestaIscrizione = (RichiestaIscrizione) obj;
			String nomeStudente = richiestaIscrizione.getStudente().getNome().toUpperCase();
			String cognomeStudente = richiestaIscrizione.getStudente().getCognome().toUpperCase();
			LocalDateTime date = richiestaIscrizione.getDataRichiesta();
			if(richiestaIscrizione.getStatus() == RichiestaIscrizione.APPROVATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA RICHIESTA DI ISCRIZIONE DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA APPROVATA CON SUCCESSO. \n\nVISITA LA PIATTAFORMA PER SCOPRIRE TUTTI I SERVIZI."
						+ "\n\nCORDIALI SALUTI.";
			} else if(richiestaIscrizione.getStatus() == RichiestaIscrizione.RIFIUTATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA RICHIESTA DI ISCRIZIONE DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA RIFIUTATA PER LE SEGUENTI MOTIVAZIONI:" + "\n" + richiestaIscrizione.getCommentoUfficioTirocini().toUpperCase() 
						+ "\n\nINVIA UNA MAIL ALL'INDIRIZZO tirociniosmartmail@gmail.com PER ULTERIORI INFORMAZIONI."
						+ "\n\nCORDIALI SALUTI.";
			} else if(richiestaIscrizione.getStatus() == RichiestaIscrizione.IN_ATTESA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA RICHIESTA DI ISCRIZIONE DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA PRESA IN CARICO DALL'UFFICIO TIROCINI." 
						+ "\n\nNON APPENA LA SUA RICHIESTA SARA' PROCESSATA VERRA' INFORMATA LO STATO."
						+ "\n\nCORDIALI SALUTI.";
			} else {
				return "";
			}
			
		} else if(obj instanceof RichiestaConvenzionamento) {
			RichiestaConvenzionamento richiestaConvenzionamento = (RichiestaConvenzionamento) obj;
			String nomeDelegato = richiestaConvenzionamento.getAzienda().getDelegato().getNome().toUpperCase();
			String cognomeDelegato = richiestaConvenzionamento.getAzienda().getDelegato().getCognome().toUpperCase();
			String azienda = richiestaConvenzionamento.getAzienda().getNome().toUpperCase();
			LocalDateTime date = richiestaConvenzionamento.getDataRichiesta();
			if(richiestaConvenzionamento.getStatus() == RichiestaConvenzionamento.IN_ATTESA) {
				return "GENTILE " + nomeDelegato + " " + cognomeDelegato + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA RICHIESTA DI CONVENZIONAMENTO PER L'AZIENDA " + azienda + " DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA PRESA IN CARICO DALL'UFFICIO TIROCINI." 
						+ "\n\nNON APPENA LA SUA RICHIESTA SARA' PROCESSATA VERRA' INFORMATA SULLO STATO."
						+ "\n\nCORDIALI SALUTI.";
			} else if(richiestaConvenzionamento.getStatus() == RichiestaConvenzionamento.APPROVATA) {
				return "GENTILE " + nomeDelegato + " " + cognomeDelegato + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA RICHIESTA DI CONVENZIONAMENTO PER L'AZIENDA " + azienda + " DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA APPROVATA CON SUCCESSO. \n\nVISITA LA PIATTAFORMA PER SCOPRIRE TUTTI I SERVIZI."
						+ "\n\nCORDIALI SALUTI.";
			} else if(richiestaConvenzionamento.getStatus() == RichiestaConvenzionamento.RIFIUTATA) {
				return "GENTILE " + nomeDelegato + " " + cognomeDelegato + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA RICHIESTA DI CONVENZIONAMENTO PER L'AZIENDA " + azienda + " DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA RIFIUTATA PER LE SEGUENTI MOTIVAZIONI:" + "\n" + richiestaConvenzionamento.getCommentoUfficioTirocini().toUpperCase() 
						+ "\n\nINVIA UNA MAIL ALL'INDIRIZZO tirociniosmartmail@gmail.com PER ULTERIORI INFORMAZIONI."
						+ "\n\nCORDIALI SALUTI.";
			}else {
				return "";
			}
		} else if(obj instanceof DomandaTirocinio) {
			DomandaTirocinio domandaTirocinio = (DomandaTirocinio) obj;
			String nomeStudente = domandaTirocinio.getStudente().getNome().toUpperCase();
			String cognomeStudente = domandaTirocinio.getStudente().getCognome().toUpperCase();
			String azienda = domandaTirocinio.getProgettoFormativo().getAzienda().getNome().toUpperCase();
			LocalDateTime date = domandaTirocinio.getData();
			LocalDate dataInizio = domandaTirocinio.getInizioTirocinio();
			if(domandaTirocinio.getStatus() == DomandaTirocinio.IN_ATTESA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA PRESA IN CARICO DALL' AZIENDA " + azienda + "." 
						+ "\n\nNON APPENA LA SUA DOMANDA SARA' PROCESSATA VERRA' INFORMATA SULLO STATO."
						+ "\n\nCORDIALI SALUTI.";
			} else if(domandaTirocinio.getStatus() == DomandaTirocinio.ACCETTATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA ACCETTATA DALL' AZIENDA " + azienda + " E PRESA IN CARICO DALL'UFFICIO TIROCINI." 
						+ "\n\nNON APPENA LA SUA DOMANDA SARA' PROCESSATA VERRA' INFORMATA SULLO STATO."
						+ "\n\nCORDIALI SALUTI.";
			} else if(domandaTirocinio.getStatus() == DomandaTirocinio.RIFIUTATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " E' STATA RIFIUTATA DALL' AZIENDA " + azienda + "." 
						+ "\n\nSI INVITA A CERCARE UN'ALTRA AZIENDA DISPOSTA AD ACCOGLIERE LA DOMANDA DI TIROCINIO."
						+ "\n\nCORDIALI SALUTI.";
			}  else if(domandaTirocinio.getStatus() == DomandaTirocinio.APPROVATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " RIVOLTA ALL'AZIENDA" + azienda + "E' STATA APPROVATA DALL'UFFICIO TIROCINIO E PRESA IN CARICO DAL PRESIDENTE DEL CONSIGLIO DIDATTICO." 
						+ "\n\nNON APPENA LA SUA DOMANDA SARA' PROCESSATA VERRA' INFORMATA SULLO STATO."
						+ "\n\nCORDIALI SALUTI.";
			} else if(domandaTirocinio.getStatus() == DomandaTirocinio.RESPINTA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " RIVOLTA ALL'AZIENDA" + azienda + " E' STATA RESPINTA DALL'UFFICIO TIROCINIO CON LE SEGUENTI MOTIVAZIONI:"
						+ "\n" + domandaTirocinio.getCommentoImpiegato().toUpperCase()
						+ "\n\nINVIA UNA MAIL ALL'INDIRIZZO tirociniosmartmail@gmail.com PER ULTERIORI INFORMAZIONI."
						+ "\n\nCORDIALI SALUTI.";
			} else if(domandaTirocinio.getStatus() == DomandaTirocinio.VALIDATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " RIVOLTA ALL'AZIENDA " + azienda + " E' STATA VALIDATA DAL PRESIDENTE DEL CONSIGLIO DIDATTICO." 
						+ "\n\nIN DATA " +  dataInizio.getDayOfMonth() + "/" + dataInizio.getMonthValue() + "/" + dataInizio.getYear() + "PUO' INIZIARE LE ATTIVITA' DI TIROCINIO"
						+ "\n\nCORDIALI SALUTI.";
			} else if(domandaTirocinio.getStatus() == DomandaTirocinio.ANNULLATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE LA DOMANDA DI TIROCINIO DA LEI INVIATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " RIVOLTA ALL'AZIENDA " + azienda + " E' STATA ANNULLATA DAL PRESIDENTE DEL CONSIGLIO DIDATTICO."
						+ "\n\nINVIA UNA MAIL ALL'INDIRIZZO tirociniosmartmail@gmail.com PER ULTERIORI INFORMAZIONI."
						+ "\n\nCORDIALI SALUTI.";
			} else {
				return "";
			}
		} else if(obj instanceof Attività) {	
			Attività attività = (Attività) obj;
			RegistroTirocinio registro = attività.getRegistroTirocinio();
			DomandaTirocinio domandaTirocinio = registro.getDomandaTirocinio();
			String nomeStudente = domandaTirocinio.getStudente().getNome().toUpperCase();
			String cognomeStudente = domandaTirocinio.getStudente().getCognome().toUpperCase();
			String azienda = domandaTirocinio.getProgettoFormativo().getAzienda().getNome().toUpperCase();
			LocalDateTime date = attività.getData();
			
			
			
			if(attività.getStatus() == Attività.IN_ATTESA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE L'ATTIVITA' DI TIROCINIO DA LEI EFFETTUATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " PRESSO L'AZIENDA " + azienda + " E' STATA REGISTRATA CORRETTAMENTE."
						+ "\n\nCORDIALI SALUTI.";	
			} else if(attività.getStatus() == Attività.VALIDATA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE L'ATTIVITA' DI TIROCINIO DA LEI EFFETTUATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " PRESSO L'AZIENDA " + azienda + " E' STATA VALIDATA DAL DELEGATO DELL'AZIENDA."
						+ "\n\nCORDIALI SALUTI.";	
			} else if(attività.getStatus() == Attività.NON_VALIDA) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE L'ATTIVITA' DI TIROCINIO DA LEI EFFETTUATA IN DATA " 
						+ date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() 
						+ " PRESSO L'AZIENDA " + azienda + " E' STATA ANNULATA DAL DELEGATO DELL'AZIENDA."
						+ "\n\nINVIA UNA MAIL ALL'INDIRIZZO tirociniosmartmail@gmail.com PER ULTERIORI INFORMAZIONI."
						+ "\n\nCORDIALI SALUTI.";	
			} else {
				return "";
			}
		} else if (obj instanceof RegistroTirocinio) {
			RegistroTirocinio registro = (RegistroTirocinio) obj;
			DomandaTirocinio domandaTirocinio = registro.getDomandaTirocinio();
			String nomeStudente = domandaTirocinio.getStudente().getNome().toUpperCase();
			String cognomeStudente = domandaTirocinio.getStudente().getCognome().toUpperCase();
			String azienda = domandaTirocinio.getProgettoFormativo().getAzienda().getNome().toUpperCase();
			
			if(registro.getStatus() == RegistroTirocinio.TERMINATO) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE IL REGISTRO DI TIROCINIO RELATIVO AL TIROCINIO SVOLTO " 
						+ " PRESSO L'AZIENDA " + azienda + " E' CONCLUSO."
						+ "\n\nCORDIALI SALUTI.";
			} else if (registro.getStatus() == RegistroTirocinio.VALIDATO) {
				return "GENTILE " + nomeStudente + " " + cognomeStudente + "," +
						"\n\nQUESTO MESSAGGIO E' STATO GENERATO AUTOMATICAMENTE DAL SISTEMA TIROCINIOSMART"
						+ " PER INFORMARLA CHE IL REGISTRO DI TIROCINIO RELATIVO AL TIROCINIO SVOLTO " 
						+ " PRESSO L'AZIENDA " + azienda + " E' STATO VALIDATO DAL PRESIDENTE DEL CONSIGLIO DIDATTICO."
						+ "\n\nINVIA UNA MAIL ALL'INDIRIZZO ufficiotirocini@unisa.it PER INFORMAZIONI RELATIVE ALLA CONVALIDA DEL TIROCINIO."
						+ "\n\nCORDIALI SALUTI.";
			}
		}
		return "";
		
	}

	public static final String SUBJECT = "COMUNICAZIONE DI SISTEMA TIROCINIOSMART";
}
