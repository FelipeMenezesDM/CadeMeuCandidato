<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="utils.Utils" %>
<%
	// Verificar status de conexão com a base de dados.
	if( !Utils.checkDBConnection() ) {
		if( !Utils.fileExists( "config.init.json" ) ) {
			Utils.setTitle( "install" );
			response.sendRedirect( request.getContextPath() + "/instalacao" );
		} else {
			Utils.setTitle( "dbConnection" );
			pageContext.include( "pages/errors/errorDBConnection.jsp" );
		}
		
		return;
	}


	String thisPage = request.getParameter( "p" );

	// Página padrão.
	if( thisPage == null || thisPage.trim().isEmpty() ) {
		Utils.setTitle( "dashboard" );
		pageContext.include( "includes/header.jsp" );
		pageContext.include( "pages/dashboard.jsp" );
		pageContext.include( "includes/footer.jsp" );
	}
	// Página não existente.
	else if( !Utils.fileExists( "pages/" + thisPage + ".jsp" ) ) {
		Utils.setTitle( "error404" );
		pageContext.include( "pages/errors/error404.jsp" );
	}
	// Nevagação.
	else{
		Utils.setTitle( thisPage.trim() );
		pageContext.include( "includes/header.jsp" );
		pageContext.include( "pages/" + thisPage.trim() + ".jsp" );
		pageContext.include( "includes/footer.jsp" );
	}
%>