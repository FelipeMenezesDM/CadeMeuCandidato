<%@page import="utils.Utils" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><%= Utils.getTitle() %> | Cadê meu candidato?</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="<%= Utils.getHomeUrl( request, "assets/css/style-fields.css" ) %>" />
	<link rel="stylesheet" href="<%= Utils.getHomeUrl( request, "assets/css/style.css" ) %>" />
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/solid.css" />
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/fontawesome.css" />
</head>
<body>
	<div class="header">
		<div class="header-wrapper">
			<h1 class="info"><a href=".">CADÊ MEU CANDIDATO?</a></h1>
			<div class="user-display">
				<a href="#" class="user-toogle-opts" onClick="return false;"><%= Utils.getUser().getName() %></a>
				<ul class="user-options">
					<li><a href="./sair">Sair</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="wrapper">
		<div class="container">