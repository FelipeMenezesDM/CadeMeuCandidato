<%@page import="java.util.Map,java.util.HashMap" %>
<%
	// Obter erros do formul�rio em lista.
	Map<String, String> errors = new HashMap<String, String>();

	if( request.getAttribute( "errors" ) != null )
		errors = (Map) request.getAttribute( "errors" );
%>
<%@include file="../includes/header-error.jsp" %>

		<div class="error-body">
			<h1 class="title">Cria��o de Usu�rio</h1>
			<p class="description">Crie um usu�rio inicial para administrar o sistema.</p>
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
						<th><label for="username">Nome de usu�rio <span class="required">*</span></label></th>
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
					<tr>
						<th>&nbsp;</th>
						<td>
							<label>
								<input type="checkbox" class="check-field" value="1" name="remember"<%= ( request.getParameter( "remember" ) != null ? " checked" : "" ) %> />
								<strong>Mantenha-me conectado</strong>
							</label>
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