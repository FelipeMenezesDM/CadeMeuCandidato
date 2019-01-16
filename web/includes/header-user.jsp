<%@page import="utils.Utils" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title><%= Utils.getTitle() %></title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="shortcut icon" type="image/x-icon" href="<%= Utils.getHomeUrl( request, "assets/img/favicon.png" ) %>" />
	<link rel="stylesheet" href="<%= Utils.getHomeUrl( request, "assets/css/style-fields.css" ) %>" />
	<link rel="stylesheet" href="<%= Utils.getHomeUrl( request, "assets/css/style-forms.css" ) %>" />
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/solid.css" />
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/fontawesome.css" />
</head>
<body>
<div class="main user-forms">
	<div class="container">