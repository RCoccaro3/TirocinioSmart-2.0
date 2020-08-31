package it.unisa.di.tirociniosmart.presidenteConsiglioDidattico;

import it.unisa.di.tirociniosmart.utenza.UtenteRegistrato;

import javax.persistence.Entity;

/**
 * Classe che modella un presidente del consiglio didattico
 * 
 * @see UtenteRegistrato
 */
@Entity
public class PresidenteConsiglioDidattico extends UtenteRegistrato {

	/**
	 * Costruisce un oggetto PresidenteConsiglioDidattico dotato solo delle proprietà definite
	 * nella superclasse {@link UtenteRegistrato}
	 */
	public PresidenteConsiglioDidattico() {
	}
	
	/**
	 * Determina se due oggetti rappresentano lo stesso presidente del consiglio didattico
	 * guardando agli username dei suddetti.
	 */
	public boolean equals(Object object) {
		return super.equals(object);
	}

	/**
	 * Permette di definire una stringa che può essere considerata come la 
	 * "rappresentazione testuale" dell'oggetto PresidenteConsiglioDidattico
	 * 
	 * @return Stringa che rappresenta una descrizione più accurata dell'oggetto
	 */
	@Override
	public String toString() {
		return "PresidenteConsiglioDidattico [username=" + username + ", password=" + password + ", nome=" + nome
				+ ", cognome=" + cognome + ", email=" + email + "]";
	}
	
	
}
