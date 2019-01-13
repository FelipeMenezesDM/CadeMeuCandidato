<%@page import="java.util.Map,java.util.HashMap" %>
<%
	// Obter erros do formulário em lista.
	Map<String, String> errors = new HashMap<String, String>();

	if( request.getAttribute( "errors" ) != null )
		errors = (Map) request.getAttribute( "errors" );
%>
<%@include file="../includes/header-user.jsp" %>

		<div class="user-form">
			<h2 class="title">Entrar</h2>
			<p class="description">Informe suas credenciais para acessar o sistema.</p>
		<%
			if( errors.containsKey( "form" ) )
				out.print( "<p class=\"callback-error\">" + errors.get( "form" ) + "</p>" );
		%>
			<form action="entrar" method="post">
				<table>
					<tr>
						<th><label for="username">Nome de usuário</label></th>
						<td>
							<input type="text" id="username" name="username" class="text-field" value="<%= ( request.getParameter( "username" ) != null ? request.getParameter( "username" ).toString() : "" ) %>" />
						<%
							if( errors.containsKey( "username" ) )
								out.print( "<p class=\"error\">" + errors.get( "username" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th><label for="password">Senha</label></th>
						<td>
							<input type="password" id="password" name="password" class="text-field" />
						<%
							if( errors.containsKey( "password" ) )
								out.print( "<p class=\"error\">" + errors.get( "password" ) + "</p>" );
						%>
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<label>
								<input type="checkbox" class="check-field" value="1" name="remember"<%= ( request.getParameter( "remember" ) != null ?" checked" : "" ) %> />
								<strong>Mantenha-me conectado</strong>
							</label>
						</td>
					</tr>
					<tr><td colspan="2">&nbsp;</td></tr>
					<tr>
						<td colspan="2">
							<div class="to-left">
								<a href="./cadastro" class="button">Cadastre-se</a>
							</div>
							<input type="submit" value="Entrar" class="button" />
						</td>
					</tr>
				</table>
			<form>
		</div>
		
<%@include file="../includes/footer-user.jsp" %>