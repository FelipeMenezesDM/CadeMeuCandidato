<%@page import="utils.Utils,java.util.Map,java.util.HashMap" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	// Obter erros do formulário em lista.
	Map<String, String> errors = new HashMap<String, String>();

	if( request.getAttribute( "errors" ) != null )
		errors = (Map) request.getAttribute( "errors" );
%>

<%@include file="../includes/header-error.jsp" %>

		<div class="error-body">
			<h1 class="title">Criação de Usuário</h1>
			<p class="description">Crie um usuário inicial para administrar o sistema.</p>
		<%
			if( errors.containsKey( "form" ) )
				out.print( "<p class=\"callback-error\">" + errors.get( "form" ) + "</p>" );
		%>
			<form action="instalacao" method="post">
				<table>
					<tr>
						<th><label for="name">Nome completo <span class="required">*</span></label></th>
						<td>
							<input type="text" id="name" name="name" class="text-field" value="<%= ( request.getParameter( "name" ) != null ? request.getParameter( "name" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "name" ) )
								out.print( "<p class=\"error\">" + errors.get( "name" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="username">Nome de usuário <span class="required">*</span></label></th>
						<td>
							<input type="text" id="username" name="username" class="text-field" value="<%= ( request.getParameter( "username" ) != null ? request.getParameter( "username" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "username" ) )
								out.print( "<p class=\"error\">" + errors.get( "username" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="email">E-mail <span class="required">*</span></label></th>
						<td>
							<input type="text" id="email" name="email" class="text-field" value="<%= ( request.getParameter( "email" ) != null ? request.getParameter( "email" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "email" ) )
								out.print( "<p class=\"error\">" + errors.get( "email" ) + "</p>" );
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
					<tr>
						<th><label for="rtPassword">Repetir Senha <span class="required">*</span></label></th>
						<td>
							<input type="password" id="rtPassword" name="rtPassword" class="text-field" />
						<%
							if( errors.containsKey( "rtPassword" ) )
								out.print( "<p class=\"error\">" + errors.get( "rtPassword" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="Finalizar" class="button" />
							<input type="submit" value="Voltar" name="back" class="button secondary" />
						</td>
					</tr>
				</table>
			<form>
		</div>
		
<%@include file="../includes/footer-error.jsp" %>