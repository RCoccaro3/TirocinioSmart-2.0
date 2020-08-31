<%@page import="it.unisa.di.tirociniosmart.registroTirocinio.Attività"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%-- Definizione variabili stato domande --%>
<c:set var="statoAttivitàInAttesa" value="<%= Attività.IN_ATTESA %>" />
<c:set var="statoAttivitàValidata" value="<%= Attività.VALIDATA %>" />
<c:set var="statoAttivitàNonValida" value="<%= Attività.NON_VALIDA %>" />

<%-- Definizione etichette badges --%>
<spring:message var="badgeAttivitàInAttesa" code="badge.attività.inAttesa" />
<spring:message var="badgeAttivitàValidata" code="badge.attività.validata" />
<spring:message var="badgeAttivitàNonValida" code="badge.attività.nonValida" />

<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipAttivitaDescrizione"
                code="tooltip.attivita.descrizione" />
<spring:message var="tooltipAttivitaProgettoFormativo"
                code="tooltip.attivita.progettoFormativo" />
<spring:message var="tooltipAttivitàOrario"
                code="tooltip.attivita.orario" />
<spring:message var="tooltipAttivitàDurata"
                code="tooltip.attivita.durata" />               
                
<%-- Definizione lista --%>
<c:forEach items="${attivita}" var="current" varStatus="loop">

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
            <c:when test="${current.status == statoAttivitàInAttesa}">
              <span class="new badge yellow darken-1 black-text"
                    data-badge-caption="<c:out value="${badgeAttivitàInAttesa}" />" >
              </span>
            </c:when>
            <c:when test="${current.status == statoAttivitàNonValida}">
              <span class="new badge red"
                    data-badge-caption="<c:out value="${badgeAttivitàNonValida}" />" >
              </span>
            </c:when>
            <c:when test="${current.status == statoAttivitàValidata}">
              <span class="new badge green"
                    data-badge-caption="<c:out value="${badgeAttivitàValidata}" />" >
              </span>
            </c:when>            
            </c:choose>                      
        </div>  
              
      </div>
	  
	  <%-- Corpo del collassabile --%>
      <div class="collapsible-body grey lighten-4">
        <div class="row">
        
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
                   data-tooltip="${tooltipAttivitàOrario}">
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
                   data-tooltip="${tooltipAttivitàDurata}">
                  <i class ="small material-icons">hourglass_full</i>
                </a>      
              </div>
              <div class="col s11">
                <c:out value="${current.numOre} ore" />
              </div>
            </div>
          </div>
          </div>
          </div>

	  
	  </li>
	 </ul>


</c:forEach>

	