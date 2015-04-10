<!-- 
 Name: Jiaqi Luo
 ID: jiaqiluo
 Course: 08600 -->
<jsp:include page="template-top.jsp" />

<%@ page import="databeans.Favorite" %>
<p>
	<form method=\"POST\" action=\"?\">
	<table>
<%
	if (request.getAttribute("favoriteList") != null) {
    	for (Favorite favorite : (Favorite[])request.getAttribute("favoriteList")) {
%>
 		<tr>
         	 <td valign="top"><a href="view.do?id=<%=favorite.getId()%>"><%=favorite.getUrl()%></a></td>    	
		</tr>
        <tr>
         	<td valign="top">Comment:</td>
         	<td valign="top"><%=favorite.getComment()%></td>
        </tr> 
        
        <tr>
        	 <td valign="top">Click count:</td>
        	 <td valign="top"><%=favorite.getClickCount()%></td>
        </tr> 
		<tr>
			<td> </td>
		</tr>
   
<%
		}
	}
%>
	</table>
	</form>
</p>


