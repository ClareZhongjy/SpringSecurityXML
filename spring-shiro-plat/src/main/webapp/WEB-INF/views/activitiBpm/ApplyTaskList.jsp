<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<style>
	h2 {
		
		
	}
	
</style>
<script type="text/javascript">
function preToLeaveApply(){
	parent.$.modalDialog({
        title : '请假申请',
        width : 600,
        height : 800,
        href : '${path}/bpm/preStartLeaveApply',
        buttons : [ {
            text : '确定'
           
        } ]
    });
	
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    
 	<table style="margin-left:30px;">
 		<tr>
 			<th><h2 style="color:green;">人事申请&nbsp;<span class="glyphicon glyphicon-user" aria-hidden="true"></span></h2></th>
 		</tr>
 		<tr>
 			<td>
 				<img src="${staticPath }/static/icons/timg.jpg" height="50px" width="50px" title="请假申请"  onclick="preToLeaveApply();"><br>
    			<span >请假申请</span>
 			</td>
 			
 		</tr>
 		<tr>
			<th><h2 style="color:darkgoldenrod;">财务申请&nbsp;<span class="glyphicon glyphicon-jpy" aria-hidden="true"></span></h2></th>
		</tr>
 		<tr>
 			<td>
 			<img src="${staticPath }/static/icons/purchase.png" height="50px" width="50px" title="采购申请"><br>
    		<span >采购申请</span>
    		</td>
 		</tr>
 		<tr>
 			<th><h2 style="color:darkblue">其他申请&nbsp;<span class="glyphicon glyphicon-tasks" aria-hidden="true"></span></h2></th>
 		</tr>
 		<tr>
 			<td>
 			
 			</td>
 		</tr>
 	</table>
    
</div>