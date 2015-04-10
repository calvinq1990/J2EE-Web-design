<!-- 
 Name: Jiaqi Luo
 ID: jiaqiluo
 Course: 08600 -->
<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Add a new url:
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="upload.do" enctype="multipart/form-data">
		<table>
			<tr>
				<td>URL: </td>
				<td colspan="2"><input type="text" name="url" value=""/></td>
			</tr>
			<tr>
				<td>Comment: </td>
				<td><input type="text" name="comment" value=""/></td>
				<%-- <td><input type="text" name="caption" value="${caption}"/></td> --%>
				
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input type="submit" name="button" value="Add Favourite"/>
				</td>
			</tr>
		</table>
	</form>
</p>
<hr/>
<%@ page import="databeans.Favorite" %>
<p>
	<table>
<%
		Favorite[] favorites = (Favorite[])request.getAttribute("favoriteList");
		System.out.println("manage.jsp : " + favorites.length);
		for (int i=0; i < favorites.length; i++) { 
		
%>
        <tr>
            <td valign="top">
                <form method="POST" action="remove.do" style="margin-bottom:0px;">
                    <input type="hidden" name="id" value="<%=favorites[i].getId()%>"/>
                    <input type="submit" value="X"/>
                </form>
             </td> 
         	 <td valign="top"><a href="view.do?id=<%=favorites[i].getId()%>"><%=favorites[i].getUrl()%></a></td>
         	
		</tr>
		
        <tr>
         	<td>Comment:</td>
         	<td><%=favorites[i].getComment()%></td>
        </tr> 
        
        <tr>
        	 <td>Click count:</td>
        	 <td><%=favorites[i].getClickCount()%></td>
        	
        </tr> 
     	
<%
		}
%>
	</table>
</p>


