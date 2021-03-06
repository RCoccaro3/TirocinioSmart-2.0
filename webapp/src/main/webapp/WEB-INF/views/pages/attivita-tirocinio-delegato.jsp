<%@page import="it.unisa.di.tirociniosmart.registroTirocinio.RegistroTirocinio"%>
<%@page import="it.unisa.di.tirociniosmart.registroTirocinio.Attivit�"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili utilizzate per titolo e men� --%>
<spring:message var="titoloPagina" scope="request" code="pagina.attivitaTirocinio.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.dashboard" />
<spring:message var="voceMenu" scope="request" code="vmenu.dashboard.progettiFormativi" />

<%-- Definizione etichette tooltips --%>
<spring:message var="tooltipRegistroChiudi" code="tooltip.registro.chiudi" />
<spring:message var="tooltipAziendaIndirizzo" code="tooltip.azienda.indirizzo" />
<spring:message var="tooltipCommonEmail" code="tooltip.common.email" />
<spring:message var="tooltipAziendaTelefono" code="tooltip.azienda.telefono" />
<spring:message var="tooltipAziendaAccessibilit�" code="tooltip.azienda.accessibilit�" />
<spring:message var="tooltipAziendaSenzaBarriere" code="tooltip.azienda.senzaBarriere" />
<spring:message var="tooltipAttivitaPresenze" code="tooltip.attivita.presenze" />

<%-- Definizione variabili stato oggetti --%>
<c:set var="statoAttivit�InAttesa" value="<%= Attivit�.IN_ATTESA %>" />
<c:set var="statoAttivit�Validata" value="<%= Attivit�.VALIDATA %>" />
<c:set var="statoAttivit�NonValida" value="<%= Attivit�.NON_VALIDA %>" />

<c:set var="statoRegistroInAttesa" value="<%= RegistroTirocinio.IN_ATTESA %>" />
<c:set var="statoRegistroTerminato" value="<%= RegistroTirocinio.TERMINATO %>" />
<c:set var="statoRegistroValidato" value="<%= RegistroTirocinio.VALIDATO %>" />

<%-- Definizioni variabili --%>
<c:set var="inattesa" value="${registro.oreInAttesa}" scope="page"></c:set>
<c:set var="validate" value="${registro.oreValidate}" scope="page"></c:set>
<c:set var="rimanenti" value="${registro.oreDaEffettuare}" scope="page"></c:set>

<%-- Definizione variabili per l'identificazione dei modal --%>
	  <c:set var="idModalChiudi" value="registro-tirocinio-modal-chiudi" />

<%-- Definizione etichette badges --%>
<spring:message var="badgeRegistroTirocinioInAttesa" code="badge.registroTirocinio.inAttesa" />
<spring:message var="badgeRegistroTirocinioTerminato" code="badge.registroTirocinio.terminato" />
<spring:message var="badgeRegistroTirocinioValidato" code="badge.registroTirocinio.validato" />

<%-- Inclusione header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" >
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main> 
  <div class="row">
    <div class="col l4 xl3">
    
      <%-- Inclusione men� laterale --%>
      <jsp:include page="/WEB-INF/views/menu/azienda-dashboard.jsp" />
      
    </div>
    <div class="col s12 m12 l8 xl9">
      <div class="card single-row-header">
        
        
        <%-- Intestazione --%>
        <div class="card-image">
          <img src="<c:url value="/resources/images/backgrounds/attivita-tirocinio.png"/>">
          
          <span class="card-title">       
            <c:out value="${azienda.nome}" />          
          </span> 
                   
            
          <div class="col s11 right-align">
          <c:choose>
              <c:when test="${registro.status == statoRegistroTerminato}">
              <span class="new badge yellow darken-1 black-text"
                    data-badge-caption="<c:out value="${badgeRegistroTirocinioTerminato}" />" >
          	  </span>
               </c:when>
          </c:choose> 
          </div>

                      
		<c:if test="${utente.getClass().getSimpleName() == 'DelegatoAziendale'}">
          <c:choose>
          <c:when test="${(rimanenti > 0) or (registro.status != statoRegistroInAttesa)}">
              <a class="modal-trigger halfway-fab btn-floating btn-large waves-effect waves-light red tooltipped disabled"
                 data-delay="50"
                 data-position="left"
                 href="#<c:out value="${idModalChiudi}"/>" 
                 data-tooltip="${tooltipRegistroChiudi}">
                <i class="large material-icons">done</i>
              </a>
          </c:when>
          <c:otherwise>
          <a class="modal-trigger halfway-fab btn-floating btn-large waves-effect waves-light red tooltipped"
                 data-delay="50"
                 data-position="left"
              	 href="#<c:out value="${idModalChiudi}"/>" 
                 data-tooltip="${tooltipRegistroChiudi}">
                <i class="large material-icons">done</i>
              </a>
          </c:otherwise>
          </c:choose>
          </c:if>
        
        </div>
        
        <div class="card-content">
          
          
          <%-- Sede dell'azienda --%>
          <div class="row valign-wrapper">
            <div class="col s1">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipAziendaIndirizzo}">
                  <i class="small material-icons">location_city</i>
               </a>
             </div>
             <div class="col s11">
               <c:out value="${azienda.indirizzo}" />
             </div>
          </div>
          
          
          <%-- E-mail del delegato --%>
          <div class="row valign-wrapper">
            <div class="col s1">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipCommonEmail}">
                 <i class="small material-icons">email</i>
               </a>
             </div>
             <div class="col s11">
               <c:out value="${azienda.delegato.email}" />
             </div>
          </div>
          
          
          <%-- Contatto telefonico --%>
          <div class="row valign-wrapper">
            <div class="col s1">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipAziendaTelefono}">
                  <i class="small material-icons">phone</i>
               </a>
             </div>
             <div class="col s11">
               <c:out value="${azienda.delegato.telefono}" />
             </div>
          </div>
          
          <%-- Contatto telefonico --%>
          <div class="row valign-wrapper">
            <div class="col s2">
               <a class="tooltipped tooltipped-icon"
                  data-position="right"
                  data-delay="50"
                  data-tooltip="${tooltipAttivitaPresenze}">
                  <i class="small material-icons">assignment</i>
               </a>
             </div>
             <div class="col s8">
               <c:out value="Ore di tirocinio validate: ${validate} ore" />              
             </div>
             
             <div class="col s8">
               <c:out value="Ore di tirocinio in attesa: ${inattesa} ore" />              
             </div>
             
             <div class="col s8">
               <c:out value="Ore di tirocinio da effettuare: ${rimanenti} ore" />              
             </div>
             
          </div>          
                   
        </div>
      </div>   
      
      <%-- Inclusione lista progetti formativi --%>
      <jsp:include page="/WEB-INF/views/lists/attivita-tirocinio-delegato.jsp"/>
  
    </div>
  </div>
  
 <jsp:include page="/WEB-INF/views/forms/chiudi-registro-tirocinio.jsp">
       <jsp:param value="${idModalChiudi}" name="idModal"/>
       <jsp:param value="${registro.id}" name="idRegistro" />
 </jsp:include>
    
    <%-- Inclusione form per l'aggiunta di un attivit� di tirocinio --%>
  <jsp:include page="/WEB-INF/views/forms/aggiunta-attivit�-tirocinio.jsp" >
    <jsp:param name="idModal" value="aggiungi-attivit�-tirocinio"/>
  </jsp:include>
  
</main>
        
    
 <%-- Inclusione footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />