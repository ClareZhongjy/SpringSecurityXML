<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#taskEditForm').form({
            url : '${path}/task/edit',
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
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    var form = $('#taskEditForm');
                    parent.$.messager.alert('错误', eval(result.msg), 'error');
                }
            }
        });
        
        $("#editStatus").val('${task.status}'); 
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="taskEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>定时任务名称</td>
                    <td><input name="id" type="hidden"  value="${job.jobName}">
                    <input name="jobName" type="text"  class="easyui-validatebox" readonly="readonly" value="${job.jobName}"></td>
                </tr>
                <tr>
                    <td>定时任务分组</td>
                    <td >
                        <input name="jobGroup" type="text" class="easyui-validatebox" readonly="readonly" value="${job.jobGroup}"></td>
                    </td>
                </tr>
                <tr>
                    <td>定时任务Cron</td>
                    <td >
                        <input name="cronExpression" type="text" class="easyui-validatebox" data-options="required:true" value="${job.cronExpression}"></td>
                    </td>
                </tr>
                <tr>
                    <td>定时任务Class</td>
                    <td >
                        <input name="beanClass" type="text" class="easyui-validatebox" data-options="required:true" value="${job.beanClass}"></td>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>