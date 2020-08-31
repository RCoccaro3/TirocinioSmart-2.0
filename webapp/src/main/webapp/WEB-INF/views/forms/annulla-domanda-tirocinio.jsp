<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili header modal --%>
<spring:message var="modalTitoloAnnullaDomandaTirocinio"
                code="modal.titolo.annullaDomandaTirocinio" />
<spring:message var="modalDescrizioneAnnullaDomandaTirocinio"
                code="modal.descrizione.annullaDomandaTirocinio" />
                
                
<%-- Definizione etichette campi del form --%>
<spring:message var="formLabelMotivazione" code="form.label.motivazione" />

                
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
			    <c:out value="${modalTitoloAnnullaDomandaTirocinio}" />
			  </h4>
			  
			  
			  <%-- Descrizione del modal --%>
			  <p>
			    <c:out value="${modalDescrizioneAnnullaDomandaTirocinio}" />
			  </p>
			  
			  
			  <%-- Definizione form --%>
			  <form action="/dashboard/domande/annulla"
			        method="POST">
			    
			    
			    <%-- Campo motivazione --%>
			    <div class="row">
		        <div class="input-field col s12">
		          <i class="material-icons prefix">feedback</i>
		          <textarea id="commento-${param.idDomanda}"
		                    name="commentoPresidente" 
		                    class="materialize-textarea"></textarea>
		          <label for="commento-${param.idDomanda}">
		            <c:out value="${formLabelMotivazione}" />
		          </label>
		        </div>
		      </div>
			    
			    
			    <%-- Pulsanti per conferma e annullamento dell'operazione --%>
			    <div class="row">
			      <div class="col s12 right-align">
			        <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
			        <input type="hidden" name="idDomanda" value="${param.idDomanda}">
				      <button class="btn waves-effect waves-light red" type="submit" name="action">
				        <c:out value="${buttonCommonProcedi}" />
				        <i class="material-icons right">clear</i>
				      </button>
			      </div>
			    </div>
			    
			    
			  </form>
			  
			  
		  </div>
		</div>
		
		
	</div>
</div>