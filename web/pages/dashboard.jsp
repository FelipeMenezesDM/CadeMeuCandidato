<%@page import="controller.BuildContent,org.json.JSONObject,org.json.JSONArray" %>
<div class="filter">
	<form method="get">
		<div class="col nome">
			<input type="text" class="text-field" name="nome" placeholder="Pesquise um candidato:" />
		</div>
		<div class="col partido">
			<select name="partido">
				<option value="">Partido</option>
			<%
				// Obter lista de partidos.
				JSONArray partidos = (JSONArray) BuildContent.getPartidos().get( "dados" ); 
				
				for( Object partido : partidos )
					out.print("<option value=\"" + ((JSONObject) partido).getString( "sigla" ) + "\">" +  ((JSONObject) partido).getString( "nome" ) + "</option>" );
			%>
			</select>
		</div>
		<div class="col uf">
			<select name="uf">
				<option value="">UF</option>
			<%
				// Obter lista de UFs.
				JSONArray ufs = (JSONArray) BuildContent.getUFs().get( "dados" ); 
				
				for( Object uf : ufs )
					out.print("<option value=\"" + ((JSONObject) uf).getString( "sigla" ) + "\">" +  ((JSONObject) uf).getString( "nome" ) + "</option>" );
			%>
			</select>
		</div>
		<div class="col pesquisar">
			<input type="submit" class="button" value="Pesquisar" />
		</div>
		<div class="clear"></div>
	</form>
</div>

<table class="list">
	<thead>
		<tr>
			<th>Nome</th>
			<th width="15%" class="center">Partido</th>
			<th width="15%" class="center">UF</th>
			<th width="15%" class="center"><i class="fas fa-cog"></i></th>
		</tr>
	</thead>
	<tbody></tbody>
	<tfooter></tfooter>
</table>