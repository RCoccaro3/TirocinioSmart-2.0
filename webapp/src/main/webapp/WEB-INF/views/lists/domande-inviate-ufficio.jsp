<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
         import="it.unisa.di.tirociniosmart.domandetirocinio.DomandaTirocinio" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<%-- Definizione variabili stato domande --%>
<c:set var="statoDomandaApprovata" value="<%= DomandaTirocinio.APPROVATA %>" />
<c:set var="statoDomandaValidata" value="<%= DomandaTirocinio.VALIDATA %>" />
<c:set var="statoDomandaAnnullata" value="<%= DomandaTirocinio.ANNULLATA %>" />


<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipProgettoFormativoNome" code="tooltip.progettoFormativo.nome"/>
<spring:message var="tooltipCommonNominativo" code="tooltip.common.nominativo" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipCommonTelefono" code="tooltip.common.telefono" />

<spring:message var="tooltipDomandaTirocinioCommentoAzienda"
                code="tooltip.domandaTirocinio.commentoAzienda" />
<spring:message var="tooltipDomandaTirocinioCommentoStudente"
                code="tooltip.domandaTirocinio.commentoStudente" />
<spring:message var="tooltipDomandaTirocinioCommentoPresidente"
                code="tooltip.domandaTirocinio.commentoPresidente" />
<spring:message var="tooltipDomandaTirocinioCommentoImpiegato"
                code="tooltip.domandaTirocinio.commentoImpiegato" />
<spring:message var="tooltipDomandaTirocinioPeriodoTirocinio"
                code="tooltip.domandaTirocinio.periodoTirocinio" />


<%-- Definizione etichette sesso --%>
<spring:message var="labelSessoMaschile" code="label.sesso.maschile" />
<spring:message var="labelSessoFemminile" code="label.sesso.femminile" />


<%-- Definizione etichette badges --%>
<spring:message var="badgeDomandaTirocinioInAttesa" code="badge.domandaTirocinio.inAttesa" />
<spring:message var="badgeDomandaTirocinioValidata" code="badge.domandaTirocinio.validata" />
<spring:message var="badgeDomandaTirocinioNonValida" code="badge.domandaTirocinio.nonValida" />


<%-- Lista delle domande di tirocinio inviate --%>
<c:forEach items="${elencoDomandeInviate}" var="current" varStatus="loop">
  <ul class="collapsible">
    <li>
      
      
      <%-- Testata del collassabile --%>
      <div class="collapsible-header">
        
        
        <%-- Nominativo di studente e progetto formativo --%>
        <div class="col s8 valign-wrapper">
          <i class="material-icons">work</i>
          <c:out value="${current.studente.nome}"/> 
          <c:out value="${current.studente.cognome}"/> - 
          <c:out value="${current.progettoFormativo.nome}" />
        </div>
        
        
         <%-- Data ultima modifica e stato domanda --%>
        <div class="col s4 right-align">
          <span class="right-align"><tags:localDateTime date="${current.data}"/></span>
          <c:choose>
            <c:when test="${current.status == statoDomandaApprovata}">
              <span class="new badge yellow darken-1 black-text"
                    data-badge-caption="<c:out value="${badgeDomandaTirocinioInAttesa}" />" >
              </span>
            </c:when>
             <c:when test="${current.status == statoDomandaValidata}">
              <span class="new badge green"
                    data-badge-caption="<c:out value="${badgeDomandaTirocinioValidata}" />" >
              </span>
            </c:when>
            <c:when test="${current.status == statoDomandaAnnullata}">
              <span class="new badge red"
                    data-badge-caption="<c:out value="${badgeDomandaTirocinioNonValida}" />" >
              </span>
            </c:when>
          </c:choose> 
          	        
	        
        </div>
    </div>
    
    
      <%-- Corpo del collassabile --%>
      <div class="collapsible-body grey lighten-4">
        <div class="row">
        
        
	        <%-- Nominativo dello studente --%>
	        <div class="col s12">
		        <div class="row valign-wrapper" >
		          <div class="col s1">
		            <a class="tooltipped tooltipped-icon" 
		               data-position="right"
		               data-delay="50"
		               data-tooltip="${tooltipCommonNominativo}">
		               <i class ="small material-icons">face</i>
		            </a>      
		          </div>
		          <div class="col s11">
		            <c:out value="${current.studente.nome}" />  
		            <c:out value="${current.studente.cognome}" />
		          </div>
		        </div>
	        </div>
	          
	          
	        <%-- E-mail dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="<c:out value="${tooltipCommonEmail}"/>">
	                 <i class="small material-icons">email</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.email}" />
	            </div>
	          </div>
	        </div>
	        
	        
	        <%-- Telefono dello studente --%>
	        <div class="col s12">
	          <div class="row valign-wrapper">
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon"
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="<c:out value="${tooltipCommonTelefono}"/>">
	                 <i class="small material-icons">phone</i>
	              </a>
	            </div>
	            <div class="col s11">
	              <c:out value="${current.studente.telefono}" />
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
	                 data-tooltip="${tooltipProgettoFormativoNome}">
	                 <i class ="small material-icons">business_center</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <c:out value="${current.progettoFormativo.nome}" />
	            </div>
	          </div>
	        </div>
	          
	        
	        <%-- Periodo di tirocinio --%>
	        <div class="col s12">
	          <div class="row valign-wrapper" >
	            <div class="col s1">
	              <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioPeriodoTirocinio}">
	                <i class ="small material-icons">event</i>
	              </a>      
	            </div>
	            <div class="col s11">
	              <tags:localDate date="${current.inizioTirocinio}"/> - 
	              <tags:localDate date="${current.fineTirocinio}"/>
	            </div>
	          </div>
	        </div>
	          
		<%-- Commento dello studente --%>
           <c:choose>
           <c:when test="${current.status == statoDomandaInAttesa}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">watch_later</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> IN ATTESA di essere presa in carico da "<c:out value="${current.progettoFormativo.azienda.nome}"/>" </span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	        
	        <c:when test="${current.status == statoDomandaValidata}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">done_all</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> VALIDATA dal Presidente del Consiglio Didattico. Attendi la data di inizio del tirocinio per iniziare a svolgere le attività!</span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	        
	        <c:when test="${current.status == statoDomandaAccettata}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">watch_later</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> ACCETTATA da "<c:out value="${current.progettoFormativo.azienda.nome}"/>" ma IN ATTESA di essere presa in carico dall'Ufficio Tirocini</span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	        
	        <c:when test="${current.status == statoDomandaApprovata}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">watch_later</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> APPROVATA dall'Ufficio Tirocini ma IN ATTESA di essere presa in carico dal Presidente del Consiglio Didattico</span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	        
	            <c:when test="${current.status == statoDomandaRifiutata}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">error</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> RIFIUTATA da "<c:out value="${current.progettoFormativo.azienda.nome}"/>" </span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	        
	        <c:when test="${current.status == statoDomandaRespinta}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">error</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> ACCETTATA da "<c:out value="${current.progettoFormativo.azienda.nome}"/>" ma RESPINTA dall'Ufficio Tirocini </span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	        
	        <c:when test="${current.status == statoDomandaAnnullata}">
	        <div class="col s12">
	         <div class="row valign-wrapper">
	            	<div class="col s1">
	            <a class="tooltipped tooltipped-icon" 
	                 data-position="right"
	                 data-delay="50"
	                 data-tooltip="${tooltipDomandaTirocinioStatoDomanda}">
	                 <i class="small material-icons">error</i> 
	              </a>
	            </div>
	            <div class="col s11">
	            <span> APPROVATA dall'Ufficio Tirocini ma ritenuta NON VALIDA dal Presidente Del Consiglio Didattico </span>	            
	            </div>
	          </div>
	        </div>
	        </c:when>
	            </c:choose>

          
          
        </div>
      </div>
      
      
    </li>
  </ul>
</c:forEach>