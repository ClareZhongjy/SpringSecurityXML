<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/main.css">
</head>


<div class="easyui-layout" data-options="fit:false,border:false">
	<div data-options="fit:false,border:false">
	<h3><span class="glyphicon glyphicon-user"></span>&nbsp;<shiro:principal></shiro:principal></h3>
	</div>
</div>