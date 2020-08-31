<%@page import="it.unisa.di.tirociniosmart.registroTirocinio.Attivit�"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%-- Definizione variabili stato domande --%>
<c:set var="statoAttivit�InAttesa" value="<%= Attivit�.IN_ATTESA %>" />
<c:set var="statoAttivit�Validata" value="<%= Attivit�.VALIDATA %>" />
<c:set var="statoAttivit�NonValida" value="<%= Attivit�.NON_VALIDA %>" />

<%-- Definizione etichette badges --%>
<spring:message var="badgeAttivit�InAttesa" code="badge.attivit�.inAttesa" />
<spring:message var="badgeAttivit�Validata" code="badge.attivit�.validata" />
<spring:message var="badgeAttivit�NonValida" code="badge.attivit�.nonValida" />

<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipAttivitaDescrizione"
                code="tooltip.attivita.descrizione" />
<spring:message var="tooltipAttivitaProgettoFormativo"
                code="tooltip.attivita.progettoFormativo" />
<spring:message var="tooltipAttivit�Orario"
                code="tooltip.attivita.orario" />
<spring:message var="tooltipAttivit�Durata"
                code="tooltip.attivita.durata" /> 
                
<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonCommonValida" code="button.common.valida" />              
                
<%-- Definizione lista --%>
<c:forEach items="${attivita}" var="current" varStatus="loop">

<%-- Imposta variabili per gli id dei modal --%>
<c:set var="idModalAnnulla" value="attivita-tirocinio-modal-annulla-${loop.index}" />
<c:set var="idModalValida" value="attivita-tirocinio-modal-valida-${loop.index}" />

	<%-- Collassabile --%>
	<ul class="collapsible">
	  <li>
	  
	  <%-- Testata del collassabile --%>
	    <div class="collapsible-header">
	    
	    
	      <div class="col s8 valign-wrapper">
	        <i class="material-icons">work_outline</i>
	        <c:out value="${current.nomeAttivita}"/> 
            - <c:out value="${current.domandaTirocinio.studente.nome}" />
            <c:out value="${current.domandaTirocinio.studente.cognome}" />
	      </div>
	   
	    <%-- Data ultima modifica e stato domanda --%>
        <div class="col s4 right-align">
          <span class="right-align"><tags:localDateTime date="${current.data}"/></span>
          
            <c:choose>
            <c:when test="${current.status == statoAttivit�InAttesa}">
              <span class="new badge yellow darken-1 black-text"
                    data-badge-caption="<c:out value="${badgeAttivit�InAttesa}" />" >
              </span>
            </c:when> 
             <c:when test="${current.status == statoAttivit�NonValida}">
              <span class="new badge red"
                    data-badge-caption="<c:out value="${badgeAttivit�NonValida}" />" >
              </span>
            </c:when>
            <c:when test="${current.status == statoAttivit�Validata}">
              <span class="new badge green"
                    data-badge-caption="<c:out value="${badgeAttivit�Validata}" />" >
              </span>
            </c:when>          
            </c:choose>                      
        </div>  
           
      </div>
	  
	  <%-- Corpo del collassabile --%>
      <div class="collapsible-body grey lighten-4">
        <div class="row row-group">
        
       <%-- Nome del progetto formativo --%>
          <div class="col s12">
            <div class="row valign-wrapper" >
              <div class="col s1">
                <a class="tooltipped tooltipped-icon" 
                   data-position="right"
                   data-delay="50"
                   data-tooltip="${tooltipAttivitaDescrizione}">
                  <i class ="small material-icons">description</i>
                </a>      
              </div>
              <div class="col s11">
                <c:out value="${current.descrizioneAttivita}" /> 
              </div>
            </div>
          </div>
        
        <%-- Nome del progetto formativo --%>
          <div class="col s12">
            <div class="row valign-wrapper" >
              <div class="col s1">
                <a class="tooltipped tooltipped-icon" 
                   data-position="right"
                   data-delay="50"
                   data-tooltip="${tooltipAttivitaProgettoFormativo}">
                  <i class ="small material-icons">business_center</i>
                </a>      
              </div>
              <div class="col s11">
                <c:out value="${current.domandaTirocinio.progettoFormativo.nome}" />
                (<c:out value="${azienda.nome}" />)
              </div>
            </div>
          </div>
          
        
     <%-- Nome del progetto formativo --%>
          <div class="col s12">
            <div class="row valign-wrapper" >
              <div class="col s1">
                <a class="tooltipped tooltipped-icon" 
                   data-position="right"
                   data-delay="50"
                   data-tooltip="${tooltipAttivit�Orario}">
                  <i class ="small material-icons">access_time</i>
                </a>      
              </div>
              <div class="col s11">
                <c:out value="${current.oraInizio}" />
                - <c:out value="${current.oraFine}" />
              </div>
            </div>
          </div>
          
          <%-- Nome del progetto formativo --%>
          <div class="col s12">
            <div class="row valign-wrapper" >
              <div class="col s1">
                <a class="tooltipped tooltipped-icon" 
                   data-position="right"
                   data-delay="50"
                   data-tooltip="${tooltipAttivit�Durata}">
                  <i class ="small material-icons">hourglass_full</i>
                </a>      
              </div>
              <div class="col s11">
                <c:out value="${current.numOre} ore" />
              </div>
            </div>
          </div>
                   
          </div>
          
          
    <c:if test="${current.status == statoAttivit�InAttesa}">
          <%-- Pulsanti per approvazione e rifiuto --%>
          <div class="row">
            <div class="col s12 right-align">
	            <a class="btn red white-text waves-effect waves-light modal-trigger"
	              href="#<c:out value="${idModalAnnulla}"/>">
	              <c:out value="${buttonCommonAnnulla}" />
	              <i class="material-icons right">clear</i>
	            </a>
	            <a class="btn green white-text waves-effect waves-light modal-trigger"
	              href="#<c:out value="${idModalValida}"/>">
	              <c:out value="${buttonCommonValida}" />
	              <i class="material-icons right">check</i>
	            </a>
            </div>
          </div>	     	
	</c:if>
           
	   </div>
	   
	   
	  </li>
	 </ul>

	 
    <%-- Includi modal per rifiuto domanda --%>
    <jsp:include page="/WEB-INF/views/forms/annulla-attivita-tirocinio.jsp">
      <jsp:param value="${current.id}" name="idAttivita" />
      <jsp:param value="${idModalAnnulla}" name="idModal" />
    </jsp:include>
  
    
    <%-- Includi modal per accettazione domanda --%>
    <jsp:include page="/WEB-INF/views/forms/valida-attivita-tirocinio.jsp">
      <jsp:param value="${current.id}" name="idAttivita" />
      <jsp:param value="${idModalValida}" name="idModal" />
    </jsp:include>	 

</c:forEach>