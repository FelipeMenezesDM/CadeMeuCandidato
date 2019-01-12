<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="utils.Utils" %>
<%@include file="includes/header.jsp" %>
<%
	String thisPage = request.getParameter( "p" );

	// Página padrão.
	if( thisPage == null || thisPage.trim().isEmpty() ) {
		pageContext.include( "pages/dashboard.jsp" );
	}
	// Página não existente.
	else if( !Utils.fileExists( "pages/" + thisPage + ".jsp" ) ) {
		pageContext.include( "pages/error404.jsp" );
	}
	// Nevagação.
	else{
		pageContext.include( "pages/" + thisPage.trim() + ".jsp" );
	}
%>
<%@include file="includes/footer.jsp" %>