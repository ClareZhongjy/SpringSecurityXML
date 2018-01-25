
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>User Registration Form</title>
	<link href="<%=request.getContextPath()%>/static/bootstrap3/css/bootstrap.css"  rel="stylesheet"></link>
		<link href="<%=request.getContextPath()%>/static/css/app.css" rel="stylesheet"></link>
</head>

<body>

 	<div class="form-container">
 	
 	<h1>New User Registration Form</h1>
 	<c:url var="newUrl" value="/newUser" />
	<form:form method="post" action="${newUrl }" modelAttribute="user" class="form-horizontal">
		
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="userName">User Name</label>
				<div class="col-md-7">
					<form:input type="text"  id="userName" path="userName" name="userName" class="form-control input-sm"/>
					
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="password">password</label>
				<div class="col-md-7">
					<form:input type="password" id="password" path="password" name="password" class="form-control input-sm"/>
					
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="cn name">中文名</label>
				<div class="col-md-7">
					<form:input type="text" name="cnName" id="cnName" path="cnName" class="form-control input-sm"/>
					
				</div>
			</div>
		</div>

		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="Age">Age</label>
				<div class="col-md-7">
					<form:input type="text" name="age" id="age" path="age" class="form-control input-sm"/>
					
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-group col-md-12">
				<label class="col-md-3 control-lable" for="Role">Role</label>
				<div class="col-md-7">
					
					<form:select path="roleList" multiple="true" class="form-control input-sm">
						<form:option value="USER">USER</form:option>
						<form:option value="ADMIN">ADMIN</form:option>
						<form:option value="DBA">DBA</form:option>
					</form:select>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-actions floatRight">
				<input type="submit" value="Register" class="btn btn-primary btn-sm"> or <a href="<c:url value='/admin' />">Cancel</a>
			</div>
		</div>
	</form:form>
	</div>
</body>
</html>