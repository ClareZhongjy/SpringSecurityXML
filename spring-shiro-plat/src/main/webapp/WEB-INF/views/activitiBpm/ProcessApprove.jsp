<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">


	$(function() {
    $('#processApproveForm').form({
        url : '${path}/bpm/processApprove',
        onSubmit : function() {
        	
            progressLoad();
            var isValid = $(this).form('validate');
            if (!isValid) {
                progressClose();
            }
            return isValid;
        },
        success : function(result) {
            progressClose();
            result = $.parseJSON(result);
            debugger;
            if (result.success) {
                //之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                 $.messager.alert('成功', result.msg,'info');
                parent.$.modalDialog.openner_dataGrid.datagrid('reload');
                
                parent.$.modalDialog.handler.dialog('close');
               
               
            } else {
                var form = $('#processApproveForm');
                parent.$.messager.alert('错误', eval(result.msg), 'error');
            }
           
        }
    });
});
</script>


<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="processApproveForm"  method="post" enctype="multipart/form-data" >
            <table class="grid">
                <tr>
                    <td>操作</td>  
                    <td>
                    <select id="approveType" name="approveType">
                    	<option value="Y">同意</option>
                    	<option value="N">否决</option>
                    </select>
                     </td>
                </tr> 
                <tr>
                	<td>意见</td>
                	<td><input type="text" name="opinion" data-options="required:true"></td>
                </tr>                	
            </table>
        </form>
    </div>
</div>