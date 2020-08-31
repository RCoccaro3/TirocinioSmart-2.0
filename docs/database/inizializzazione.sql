-- Script per l'inserimento di uno o più impiegati nel database da utilizzare solamente dopo aver eseguito creazione.sql
-- ed aver avviato per la prima volta la webapp.

USE tirociniosmart;

-- Inserimento dell'impiegato dell'ufficio tirocini
-- Specificare i valori dei campi username, password, email, nome e login da utilizzare per accedere all'account

-- IMPORTANTE: il primo parametro inserito nella prima riga VALUES deve coincidere con l'unico parametro della seconda riga VALUES
INSERT INTO utente_registrato (username, password, email, nome, cognome)
	   VALUES ('ufficiotirocini', 'ufficiotirocini', 'tirocini@di.unisa.it', 'Ufficio Tirocini', 'Unisa');
INSERT INTO impiegato_ufficio_tirocini (username)
       VALUES ('ufficiotirocini');
INSERT INTO utente_registrato (username, password, email, nome, cognome)
	   VALUES ('fferrucci', 'fferrucci', 'fferrucci@unisa.it', 'Filomena', 'Ferrucci');
INSERT INTO presidente_consiglio_didattico (username)
       VALUES ('fferrucci');
       
-- Ripetere le righe superiori tante volte quant'è il numero degli impiegati da voler inserire
-- IMPORTANTE: garantire l'unicità di username ed email