<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili header modal --%>
<spring:message var="modalTitoloValidaAttivitaTirocinio"
                code="modal.titolo.validaAttivitaTirocinio" />
<spring:message var="modalDescrizioneValidaAttivitaTirocinio"
                code="modal.descrizione.validaAttivitaTirocinio" />
                
                
<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonCommonProcedi" code="button.common.procedi" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">
  
  
    <%-- Testata del modal --%>
		<div class="row">
		  <div class="col s12">
		  
		  
		    <%-- Titolo del modal --%>
		    <h4>
		      <c:out value="${modalTitoloValidaAttivitaTirocinio}" />
		    </h4>
		    
		    
		    <%-- Descrizione del modal --%>
		    <p>
		      <c:out value="${modalDescrizioneAttivitaDomandaTirocinio}" />
		    </p>
		    
		    
		    <%-- Definizione form --%>
		    <form action="/dashboard/attivita/valida"
		          method="POST">
		          
		      
		      <%-- Pulsanti per conferma e annullamento dell'operazione --%>
		      <div class="row">
		        <div class="col s12 right-align">
		          <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
		          <input type="hidden" name="idAttivita" value="${param.idAttivita}">
		          <button class="btn waves-effect waves-light green" type="submit" name="action">
		            <c:out value="${buttonCommonProcedi}" />
		            <i class="material-icons right">check</i>
		          </button>
		        </div>
		      </div>
		      
		      
		    </form>
		    
		    
		  </div>
		</div>
		
		
	</div>
</div>