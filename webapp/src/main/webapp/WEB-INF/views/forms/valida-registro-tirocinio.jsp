<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonCommonValida" code="button.common.valida" />


<%-- Definizione variabili header modal --%>
<spring:message var="modalTitoloValidaRegistroTirocinio"
                code="modal.titolo.validaRegistroTirocinio" />
<spring:message var="modalDescrizioneValidaRegistroTirocinio"
                code="modal.descrizione.validaRegistroTirocinio" />


<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
  <div class="modal-content">
  
  
		<div class="row">
		  <div class="col s12">
		  
		    
		    <%-- Titolo del modal --%>
		    <h4>
		      <c:out value="${modalTitoloValidaRegistroTirocinio}" />
		    </h4>
		    
		    
		    <%-- Messaggio del modal --%>
		    <p>
		      <c:out value="${modalDescrizioneValidaRegistroTirocinio}" />
		    </p>
		    
		    
		    <%-- Definizione form --%>
		    <form action="/dashboard/registro/valida"
		          method="POST">
		          
		      <div class="row">
		        <div class="col s12 right-align">
		          <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
		          <input type="hidden" name="idRegistro" value="${param.idRegistro}">
		          <button class="btn waves-effect waves-light" type="submit" name="action">
		            <c:out value="${buttonCommonValida}" />
		            <i class="material-icons right">done_all</i>
		          </button>
		        </div>
		      </div>
		    </form>
		    
		    
		  </div>
		</div>
		
		
  </div>
</div>