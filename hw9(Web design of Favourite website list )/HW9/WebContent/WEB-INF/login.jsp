<!-- 
 Name: Jiaqi Luo
 ID: jiaqiluo
 Course: 08600 -->
<jsp:include page="template-top.jsp" />

<p style="font-size:medium">
	Please login below or click <a href="register.do">here</a> to register as a new user.
</p>

<jsp:include page="error-list.jsp" />

<p>
	<form method="post" action="login.do">
		<table>
			<tr>
				<td> Email Address: </td>
			<%-- 	<td><input type="text" name="emailAddr" value="${form.emailAddr}"/></td> --%>
				<td><input type="text" name="emailAddr" value=""/></td>
			</tr>
			<tr>
				<td> Password: </td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" name="button" value="Login"/>
				</td>
			</tr>
		</table>
	</form>
</p>


