<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili header modal --%>
<spring:message var="modalTitoloValidaDomandaTirocinio"
                code="modal.titolo.validaDomandaTirocinio" />
<spring:message var="modalDescrizioneValidaDomandaTirocinio"
                code="modal.descrizione.validaDomandaTirocinio" />
                
                
<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonCommonValida" code="button.common.valida" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">
  
  
    <%-- Testata del modal --%>
		<div class="row">
		  <div class="col s12">
		  
		  
		    <%-- Titolo del modal --%>
		    <h4>
		      <c:out value="${modalTitoloValidaDomandaTirocinio}" />
		    </h4>
		    
		    
		    <%-- Descrizione del modal --%>
		    <p>
		      <c:out value="${modalDescrizioneValidaDomandaTirocinio}" />
		    </p>
		    
		    
		    <%-- Definizione form --%>
		    <form action="/dashboard/domande/valida"
		          method="POST">
		          
		      
		      <%-- Pulsanti per conferma e annullamento dell'operazione --%>
		      <div class="row">
		        <div class="col s12 right-align">
		          <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
		          <input type="hidden" name="idDomanda" value="${param.idDomanda}">
		          <button class="btn waves-effect waves-light green" type="submit" name="action">
		            <c:out value="${buttonCommonValida}" />
		            <i class="material-icons right">check</i>
		          </button>
		        </div>
		      </div>
		      
		      
		    </form>
		    
		    
		  </div>
		</div>
		
		
	</div>
</div>