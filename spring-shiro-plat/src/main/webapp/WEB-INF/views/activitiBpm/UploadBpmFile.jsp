<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">

	function checkBpmFile(){
		var filename = $('#bpmFile').val();
		var fileindex = filename.lastIndexOf(".");
		var fileType = filename.substr(fileindex+1);
		if(fileType=='bpmn'){
			return true;
		}else{
			return false;
		}
	}
	$(function() {
    $('#uploadBpmForm').form({
        url : '${path}/bpm/uploadBpmFileByMultipart',
        onSubmit : function() {
        	
            progressLoad();
            var isValid = $(this).form('validate');
            if (!isValid) {
                progressClose();
            }
            if(!checkBpmFile()){
            	 $.messager.alert('提示', '请上传bpmn文件！','error');
        		isValid = false;
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
                var form = $('#uploadBpmForm');
                parent.$.messager.alert('错误', eval(result.msg), 'error');
            }
           
        }
    });
});
</script>


<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="uploadBpmForm"  method="post" enctype="multipart/form-data" >
            <table class="grid">
                <tr>
                    <td>工作流文件</td>  
                </tr> 
                <td><input id="bpmFile" name="bpmFile" type="file" placeholder="请选择bpm文件(*.bpmn)" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                	
            </table>
        </form>
    </div>
</div>