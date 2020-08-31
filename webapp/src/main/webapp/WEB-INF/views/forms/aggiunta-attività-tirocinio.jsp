<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>

<%-- Etichette campi form --%>
<spring:message var="formLabelNomeAttivitàTirocinio" code="form.label.nomeAttivitàTirocinio" />
<spring:message var="formLabelDescrizioneAttivitàTirocinio" code="form.label.descrizioneAttivitàTirocinio" />
<spring:message var="formLabelOraInizio" code="form.label.oraInizio" />
<spring:message var="formLabelOraFine" code="form.label.oraFine" />
<spring:message var="formLabelNumOre" code="form.label.numOre" />

<spring:message var="formPlaceholderOra" code="form.placeholder.ora" />
<spring:message var="formPlaceholderMinuti" code="form.placeholder.minuti" />


<spring:message var="modalTitoloAggiungiAttivitaTirocinio"
                code="modal.titolo.aggiungiAttivitaTirocinio" />
<spring:message var="modalDescrizioneAggiungiAttivitaTirocinio"
                code="modal.descrizione.aggiungiAttivitaTirocinio" />
                
<%-- Definizione etichette pulsanti --%>
<spring:message var="buttonCommonAnnulla" code="button.common.annulla" />
<spring:message var="buttonAttivitaTirocinioAggiungi" code="button.attivitaTirocinio.aggiungi" />
                
<c:set var="i" value="0"></c:set>

<%-- Definizione modal --%>
<div id="${param.idModal}" class="modal">
 <div class="modal-content">
 
 	<div class="row">
 		<div class="col s12">
 		<h4>
 			<c:out value="${modalTitoloAggiungiAttivitaTirocinio}" />
 		</h4>
 		<p>
 			<c:out value="${modalDescrizioneAggiungiAttivitaTirocinio}" />
 		</p>
 		
 		<%-- Definizione form --%>
 		<form:form action="/dashboard/attività/aggiungi"
 				   method="POST"
 				   modelAttribute="attivitàForm"
 				   novalidate="novalidate"
 				    cssClass="col s12">

 			<div class="row">
 				 <div class="input-field col s6">
		          <i class="material-icons prefix">business_center</i>
		          <form:input path="nomeAttivita" id="nomeAttivita"/>
		          <label for="nomeAttivita">
		            <c:out value="${formLabelNomeAttivitàTirocinio}" />
		          </label>
		          <form:errors path="nomeAttivita" cssClass="helper-text" />
		        </div>
		          
		         <div class="input-field col s6">
		          <i class="material-icons prefix">business_center</i>
		          <form:input id="attività-tirocinio-descrizione"
		          		 path = "descrizioneAttivita"/>
		          <label for="attività-tirocinio-descrizione">
		            <c:out value="${formLabelDescrizioneAttivitàTirocinio}" />
		          </label>
		          <form:errors path="descrizioneAttivita" cssClass="helper-text"/>
		        </div>		        		      
		       
		       </div>
		         
				<div class="row">
				 <div class="col s12">
				  <label class="row-label">
				   <c:out value="${formLabelOraInizio}" />
				  </label>
				</div>
				 	<div class="input-field col s6">
				 	 <i class="material-icons prefix">event</i>
				 	<form:select path="oraInizio">
				 	<form:option value="">
				 	<c:out value="${formPlaceholderOra}" />
				 	</form:option>
				 	<c:forEach var="i" begin="0" end="23">
				 		<form:option value="${i}">
				 		<c:choose>
				 		<c:when test="${i < 10}">
				 		<c:out value= "0${i}" />	
				 		</c:when>
				 		<c:otherwise>
				 		<c:out value= "${i}" />	
				 		</c:otherwise>	
				 		</c:choose>
					 	</form:option>
					 	</c:forEach>								
				 	</form:select>
				 	<form:errors path="oraInizio" cssClass="helper-text"/>
				 	</div>
				 	
				 	
				 	

				 	<div class="input-field col s6">
				 	 <i class="material-icons prefix">event</i>
				 	<form:select path="minutiInizio">
				 	<form:option value="">
				 	<c:out value="${formPlaceholderMinuti}" />
				 	</form:option>
				 	<c:forEach var="i" begin="0" end="59">
				 		<form:option value="${i}">
				 		<c:choose>
				 		<c:when test="${i < 10}">
				 		<c:out value= "0${i}" />	
				 		</c:when>
				 		<c:otherwise>
				 		<c:out value= "${i}" />	
				 		</c:otherwise>	
				 		</c:choose>				 			 						 					 					 	
					 	</form:option>
					 	</c:forEach>								
				 	</form:select>
				 	</div>	
				 	
				 <div class="col s12">
				  <label class="row-label">
				   <c:out value="${formLabelOraFine}" />				  
				  </label>
				 </div>
				 	<div class="input-field col s6">
				 	 <i class="material-icons prefix">event</i>
				 	<form:select path="oraFine">
				 	<form:option value="">
				 	<c:out value="${formPlaceholderOra}" />
				 	</form:option>
				 	<c:forEach var="i" begin="0" end="23">
				 		<form:option value="${i}">
				 		<c:choose>
				 		<c:when test="${i < 10}">
				 		<c:out value= "0${i}" />	
				 		</c:when>
				 		<c:otherwise>
				 		<c:out value= "${i}" />	
				 		</c:otherwise>	
				 		</c:choose>				 			 						 					 					 	
					 	</form:option>
					 	</c:forEach>								
				 	</form:select>
				 	<form:errors path="oraFine" cssClass="helper-text"/>
				 	</div>	
				 	

				 	<div class="input-field col s6">
				 	 <i class="material-icons prefix">event</i>
				 	<form:select path="minutiFine">
				 	<form:option value="">
				 	<c:out value="${formPlaceholderMinuti}" />
				 	</form:option>
				 	<c:forEach var="i" begin="0" end="59">
				 		<form:option value="${i}">
				 		<c:choose>
				 		<c:when test="${i < 10}">
				 		<c:out value= "0${i}" />	
				 		</c:when>
				 		<c:otherwise>
				 		<c:out value= "${i}" />	
				 		</c:otherwise>	
				 		</c:choose>				 			 						 					 					 	
					 	</form:option>
					 	</c:forEach>								
				 	</form:select>
				 	</div>	
				 	
			</div>	
			
			<div class="row">
		        <div class="input-field col s4 m6">
			        <i class="material-icons prefix">school</i>
			        <form:input path="numOre" />
			        <label class="row-label">
			          <c:out value="${formLabelNumOre}" />
			        </label>
			        <form:errors path="numOre" cssClass="helper-text" />
			      </div>
		      </div>
		      
		      	<div class="row">
		        <div class="col s12 right-align">
		          <a class="btn-flat waves-effect modal-close">
		            <c:out value="${buttonCommonAnnulla}" />
		          </a>
		          <input type="hidden" name="idRegistro" value="${param.idRegistro}">
		          <button class="btn waves-effect waves-light" type="submit" name="action">
		            <i class="material-icons right">send</i>
		            <c:out value="${buttonAttivitaTirocinioAggiungi}" />
		          </button>
		        </div>
		      </div>		 					 							  
				      
	</form:form>
      </div> 
 	</div>
 </div>
 </div>