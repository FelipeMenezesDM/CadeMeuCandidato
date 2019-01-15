<%@page import="utils.Utils,controller.BuildContent,controller.javabeans.Parlamentar" %>
<%
	Parlamentar parlamentar = BuildContent.getParlamentar();

	if( parlamentar == null ) {
		pageContext.include( "errors/noExists.jsp" );
		return;
	}
%>

<div class="card">
	<div class="picture">
		<img src="http://www.camara.leg.br/internet/deputado/bandep/<%= parlamentar.getId() %>.jpg" />	
	</div>
	<div class="poker-chip">
		<table class="info">
			<tr>
				<th>Nome</th>
				<td><%= parlamentar.getNomeParlamentarAtual() %></td>
			</tr>
			<tr>
				<th>Data de nascimento</th>
				<td><%= parlamentar.getDataNascimento() %></td>
			</tr>
			<tr>
				<th>Partido Atual</th>
				<td><%= parlamentar.getPartidoAtual() %></td>
			</tr>
			<tr>
				<th>Sexo</th>
				<td><%= parlamentar.getSexo() %></td>
			</tr>
			<tr>
				<th>Situação</th>
				<td><%= parlamentar.getSituacaoNaLegislaturaAtual() %></td>
			</tr>
		</table>
	</div>
	<div class="back-link">
		<a href="<%= request.getHeader("referer") %>" class="button">Voltar</a>
	</div>
	<div class="clear"></div>
</div>