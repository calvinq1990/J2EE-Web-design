<!-- 
 Name: Jiaqi Luo
 ID: jiaqiluo
 Course: 08600 -->

<html>
<head>
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="pragma" content="no-cache">
	<title> Favorite Web Page </title>
	<style>
		.menu-head {font-size: 12pt; font-weight: bold; color: black; }
		.menu-item {font-size: 12pt;  color: black }
    </style>
</head>

<body>
<%@ page import="databeans.User" %>

<table cellpadding="4" cellspacing="0">
    <tr>
	    <!-- Banner row across the top -->
        <td height="100" width="170" bgcolor="#A9BCF5"></td>
        <td bgcolor="#A9BCF5">&nbsp;  </td>
        <td width="1000" bgcolor="#A9BCF5">
            <p align="center">
<%
	if (request.getAttribute("title") == null) {
%>
		        <font size="7">Favorite Web Page</font>
<%
    } else {
%>
		        <font size="5"><%=request.getAttribute("title")%></font>
<%
    }
%>
			</p>
		</td>
    </tr>
	
	<!-- Spacer row -->
	<tr>
		<td bgcolor="#A9BCF5" style="font-size:10px">&nbsp;</td>
		<td colspan="2" style="font-size:8px">&nbsp;</td>
	</tr>
	
	<tr>
		<td bgcolor="#A9BCF5" valign="top" height="1000">
			<!-- Navigation bar is one table cell down the left side -->
            <p align="left">
<%
    User user = (User) session.getAttribute("user");
	if (user == null) {
%>
				<span class="menu-item"><a href="login.do">Login</a></span><br/>
				<span class="menu-item"><a href="register.do">Register</a></span><br/>
				<br/>
		        <span class="menu-head">Favorites From:</span><br/>
<%
		for (User u : (User[])request.getAttribute("userList")) {
		
%>				
  				<span class="menu-item">
					<a href="list.do?id=<%=u.getId()%>">
						<%=u.getFirstName()%> <%=u.getLastName()%>
					</a>
				</span>
				<br/>
<%
		 }
%>
<%
    } else {
%>
				<span class="menu-head"><%=user.getFirstName()%> <%=user.getLastName()%></span><br/>
				<span class="menu-item"><a href="manage.do">Manage Your Favorites</a></span><br/>
				<span class="menu-item"><a href="change-pwd.do">Change Password</a></span><br/>
				<span class="menu-item"><a href="logout.do">Logout</a></span><br/>
				<span class="menu-item">&nbsp;</span><br/>
			

<%
		
    }
%>
			</p>
		</td>
		
		<td>
			<!-- Padding (blank space) between navbar and content -->
		</td>
		<td  valign="top">
