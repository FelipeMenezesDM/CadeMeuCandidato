<%@page import="utils.Utils,controller.BuildContent,org.json.JSONObject,org.json.JSONArray" %>
<div class="filter">
	<form method="get">
		<div class="col nome">
			<input type="text" class="text-field" name="nome" placeholder="Pesquise um Parlamentar:" value="<%= ( request.getParameter( "nome" ) != null ? request.getParameter( "nome" ) : "" ) %>" />
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

<div class="list-wrapper">
	<table class="list">
		<thead>
			<tr>
				<th width="1%">#</th>
				<th>Nome</th>
				<th width="15%" class="center">Partido</th>
				<th width="15%" class="center">UF</th>
				<th width="15%" class="center"><i class="fas fa-cog"></i></th>
			</tr>
		</thead>
		<tbody>
		<%
			int currentPage = Utils.getCurrentPage( request );
			// Obter lista de parlamentares.
			JSONObject parlamentaresJSON = BuildContent.getParlamentares();
			JSONArray parlamentares = (JSONArray) parlamentaresJSON.get( "dados" );
			JSONObject p;
			
			if( parlamentares.isEmpty() ) {
				out.print( "<tr><td colspan=\"5\" class=\"center\">Não há dados para exibição</td></tr>" );
			}else{
				int index = ( currentPage - 1 ) * 5;
				
				for( Object parlamentar : parlamentares ) {
					p = (JSONObject) parlamentar; 
					
					out.print("<tr>");
					out.print("<td>" + ( ++index ) + "</td>");
					out.print("<td>" + p.getString( "nome" ) + "</td>");
					out.print("<td class=\"center\">" + p.getString( "siglaPartido" ) + "</td>");
					out.print("<td class=\"center\">" + p.getString( "siglaUf" ) + "</td>");
					out.print("<td class=\"center\"><a href=\"?p=consultar&id=" + p.getInt( "id" ) + "\" class=\"button\">Detalhar</a></td>");
					out.print("</tr>");
				}
			}
		%>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="5">
				<%
					JSONArray links = (JSONArray) parlamentaresJSON.get( "links" );
					boolean issetNextPage = false, issetPrevPage = false;
				
					// Verificar se página existem.
					for( Object link : links ) {
						String rel = ((JSONObject) link).getString( "rel" );
						
						if( rel.equals( "next" ) )
							issetNextPage = true;
						else if( rel.equals( "previous" ) )
							issetPrevPage = true;
					}
					
					// Página anterior.
					if( issetPrevPage ) {
						out.print( "<a href=\"" + Utils.addQuery( "pagina", ( currentPage - 1 ) + "", request ) + "\" class=\"pages prev\"><i class=\"fas fa-arrow-left\"></i></a>" );
					}else{
						out.print( "<span class=\"pages prev\"><i class=\"fas fa-arrow-left\"></i></span>" );
					}
					
					// Próxima página
					if( issetNextPage ) {
						out.print( "<a href=\"" + Utils.addQuery( "pagina", ( currentPage + 1 ) + "", request ) + "\" class=\"pages next\"><i class=\"fas fa-arrow-right\"></i></a>" );
					}else{
						out.print( "<span class=\"pages next\"><i class=\"fas fa-arrow-right\"></i></span>" );
					}
					
					// Mostrar página atual.
					if( !parlamentares.isEmpty() )
						out.print( "<h4>Página " + currentPage + "</h4>" );
				%>
				</td>
			</th>
		</tfoot>
	</table>
</div>