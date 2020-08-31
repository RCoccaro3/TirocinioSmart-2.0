<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>


<%-- Definizione variabili utilizzate per titolo e men� --%>
<spring:message var="titoloPagina" scope="request" code="pagina.domandeRicevute.titolo" />
<spring:message var="titoloTab" scope="request" code="tab.navbar.dashboard" />
<spring:message var="voceMenu" scope="request" code="vmenu.dashboard.domandeTirocinioRicevute" />


<%-- Inclusione header --%>
<jsp:include page="/WEB-INF/views/common/header.jsp" >
  <jsp:param name="titoloPagina" value="${titoloPagina}"/>
</jsp:include>


<%-- Corpo della pagina --%>
<main>
  <div class="row">
    
    
    <%-- Inclusione men� laterale --%>
    <div class="col l4 xl3">
      <jsp:include page="/WEB-INF/views/menu/azienda-dashboard.jsp" />
    </div>
    
    
    <div class="col s12 m12 l8 xl9">
      <div class="card single-row-header">
        <div class="card-content">
        
          <%-- Titolo della pagina --%>
          <span class="card-title">
            <c:out value="${titoloPagina}" />
          </span>
          
        </div>
      </div>
      
      <%-- Inclusione lista domande ricevuto dall'azienda --%>
      <jsp:include page="/WEB-INF/views/lists/domande-ricevute-azienda.jsp" />
      
    </div>
  </div>
</main>


<%-- Inclusione del footer --%>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />