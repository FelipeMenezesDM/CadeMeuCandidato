<%@page import="controller.BuildContent,org.json.JSONObject,org.json.JSONArray" %>
<div class="filter">
	<form method="get">
		<div class="col nome">
			<input type="text" class="text-field" name="nome" placeholder="Pesquise um candidato:" value="<%= ( request.getParameter( "nome" ) != null ? request.getParameter( "nome" ) : "" ) %>" />
		</div>
		<div class="col partido">
			<select name="partido">
				<option value="">Partido</option>
			<%
				// Obter lista de partidos.
				JSONArray partidos = (JSONArray) BuildContent.getPartidos().get( "dados" );
				JSONObject partido;
				String currentPartido = "";
				
				if( request.getParameter( "partido" ) != null )
					currentPartido = request.getParameter( "partido" );
				
				for( Object partidoIt : partidos ) {
					partido = (JSONObject) partidoIt;
					out.print("<option value=\"" + partido.getString( "sigla" ) + "\"" + ( currentPartido.equals( partido.getString( "sigla" ) ) ? " selected" : "" ) + ">" +  partido.getString( "nome" ) + " (" + partido.getString( "sigla" ) + ")" + "</option>" );
				}
			%>
			</select>
		</div>
		<div class="col uf">
			<select name="uf">
				<option value="">UF</option>
			<%
				// Obter lista de unidades federativas.
				JSONArray ufs = (JSONArray) BuildContent.getUFs().get( "dados" );
				JSONObject uf;
				String currentUF = "";
				
				if( request.getParameter( "uf" ) != null )
					currentUF = request.getParameter( "uf" );
				
				for( Object ufIt : ufs ) {
					uf = (JSONObject) ufIt;
					out.print("<option value=\"" + uf.getString( "sigla" ) + "\"" + ( currentUF.equals( uf.getString( "sigla" ) ) ? " selected" : "" ) + ">" +  uf.getString( "sigla" ) + "</option>" );
				}
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
	<tbody>
	<%
		// Obter lista de parlamentares.
		JSONArray parlamentares = (JSONArray) BuildContent.getParlamentares().get( "dados" );
		JSONObject p;
		
		if( parlamentares.isEmpty() ) {
			out.print( "<tr><td colspan=\"5\" class=\"center\">Não a dados para exibição</td></tr>" );
		}else{
			for( Object parlamentar : parlamentares ) {
				p = (JSONObject) parlamentar; 
				
				out.print("<tr>");
				out.print("<td>" + p.getString( "nome" ) + "</td>");
				out.print("<td class=\"center\">" + p.getString( "siglaPartido" ) + "</td>");
				out.print("<td class=\"center\">" + p.getString( "siglaUf" ) + "</td>");
				out.print("<td class=\"center\"><a href=\"?p=consultar&id=" + p.getInt( "id" ) + "\" class=\"button\">Detalhar</a></td>");
				out.print("</tr>");
			}
		}
	%>
	</tbody>
	<tfooter></tfooter>
</table>