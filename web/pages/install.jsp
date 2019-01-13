<%@page import="utils.Utils,java.util.Map,java.util.HashMap" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// Obter erros do formulário em lista.
	Map<String, String> errors = new HashMap<String, String>();

	if( request.getAttribute( "errors" ) != null )
		errors = (Map) request.getAttribute( "errors" );
%>

<%@include file="../includes/header-error.jsp" %>

		<div class="error-body">
			<h1 class="title">Preparando a instalação</h1>
			<p class="description">O arquivo de configuração não foi encontrado, por isso será necessário realizar a instalação da aplicação. Por favor, preencha os campos abaixo para configurar a conexão com a Base de Dados.</p>
			<%
				if( errors.containsKey( "form" ) )
					out.print( "<p class=\"callback-error\">" + errors.get( "form" ) + "</p>" );
			%>
			<form action="instalacao" method="post">
				<table>
					<tr>
						<th><label for="host">Host</label></th>
						<td>
							<input type="text" id="host" name="host" class="text-field" placeholder="localhost" value="<%= ( request.getParameter( "host" ) != null ? request.getParameter( "host" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "host" ) )
								out.print( "<p class=\"error\">" + errors.get( "host" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="port">Porta</label></th>
						<td>
							<input type="text" id="port" name="port" class="text-field" placeholder="1433" value="<%= ( request.getParameter( "port" ) != null ? request.getParameter( "port" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "port" ) )
								out.print( "<p class=\"error\">" + errors.get( "port" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="dbName">Base de Dados <span class="required">*</span></label></th>
						<td>
							<input type="text" id="dbName" name="dbName" class="text-field" value="<%= ( request.getParameter( "host" ) != null ? request.getParameter( "dbName" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "dbName" ) )
								out.print( "<p class=\"error\">" + errors.get( "dbName" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="user">Usuário <span class="required">*</span></label></th>
						<td>
							<input type="text" id="user" name="user" class="text-field" value="<%= ( request.getParameter( "user" ) != null ? request.getParameter( "user" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "user" ) )
								out.print( "<p class=\"error\">" + errors.get( "user" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="password">Senha <span class="required">*</span></label></th>
						<td>
							<input type="password" id="password" name="password" class="text-field" />
						<%
							if( errors.containsKey( "password" ) )
								out.print( "<p class=\"error\">" + errors.get( "password" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr><td colspan="2"><input type="submit" value="Próximo" class="button" /></td></tr>
				</table>
			<form>
		</div>
		
<%@include file="../includes/footer-error.jsp" %>