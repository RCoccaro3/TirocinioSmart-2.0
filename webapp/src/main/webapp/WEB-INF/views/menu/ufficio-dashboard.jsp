<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%-- Voci del men� --%>
<spring:message var="voceMenuDomande" code="vmenu.dashboard.domandeTirocinioRicevute" />
<spring:message var="voceMenuDomandeInviate" code="vmenu.dashboard.domandeTirocinioInviate" />
<spring:message var="voceMenuIscrizioni" code="vmenu.dashboard.richiesteIscrizione" />
<spring:message var="voceMenuConvenzioni" code="vmenu.dashboard.richiesteConvenzionamento" />
<spring:message var="voceMenuTirocini" code="vmenu.dashboard.tirociniInCorso" />


<%-- Men� laterale --%>
<c:set var="count" value="0"></c:set>
<c:forEach items="${listaRischiesteIscrizione}" var="current" varStatus="loop">
<c:set var="count" value="${count+1}"></c:set>
</c:forEach>


<div class="card vertical-nav hide-on-med-and-down">
  <div class="card-content">
    <ul>
    
    
      <%-- Voce men� tirocini --%>
      <li <c:if test="${voceMenu == voceMenuTirocini}">class="active"</c:if> >
        <a href="/dashboard/tirocini"
           class="waves-effect">
          <i class="material-icons">content_paste</i>
          <c:out value="${voceMenuTirocini}" /> 	
        </a>                
      </li>
    
    
      <%-- Voce men� tirocini --%>
      <li <c:if test="${voceMenu == voceMenuDomande}">class="active"</c:if> >
        <a href="/dashboard/domande/ricevute"
           class="waves-effect">
          <i class="material-icons">work</i>
          <c:out value="${voceMenuDomande}" />
        </a>
      </li>
      
      <%-- Voce men� domande inviate --%>
      <li <c:if test="${voceMenu == voceMenuDomandeInviate}">class="active"</c:if> >
        <a href="/dashboard/domande/inviate"
           class="waves-effect">
          <i class="material-icons">call_made</i>
          <c:out value="${voceMenuDomandeInviate}" />
        </a>
      </li>
           
      <%-- Voce men� richieste d'iscrizione --%>
      <li <c:if test="${voceMenu == voceMenuIscrizioni}">class="active"</c:if> >
        <a href="/dashboard/richieste/iscrizione"
           class="waves-effect">
          <i class="material-icons">face</i>
          <c:out value="${voceMenuIscrizioni}" />
          <c:if test="${count>0}">
          <span class="new badge" style="margin-top: 14px;"><c:out value="${count}"></c:out></span> 
          </c:if>
        </a>
      </li>
      
      
      <%-- Voce men� richieste di convenzionamento --%>      
      <li <c:if test="${voceMenu == voceMenuConvenzioni}">class="active"</c:if> >
        <a href="/dashboard/richieste/convenzionamento"
           class="waves-effect">
          <i class="material-icons">business</i>
          <c:out value="${voceMenuConvenzioni}" />
        </a>
      </li>
     

    </ul>
  </div>
</div>