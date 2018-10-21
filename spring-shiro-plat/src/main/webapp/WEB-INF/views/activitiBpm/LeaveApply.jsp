<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>


<script type="text/javascript">
$(function() {
    $('#LeaveApplyForm').form({
        url : '${path}/bpm/StartLeaveApply',
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
                var form = $('#LeaveApplyForm');
                parent.$.messager.alert('错误', eval(result.msg), 'error');
            }
           
        }
    });
});
</script>
    <div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" style="overflow: hidden;padding: 3px;" >
        <form id="LeaveApplyForm"  method="post" enctype="multipart/form-data" >
            <table class="grid">
                <tr>
                    <td>请假类型</td> 
                    <td>
                    	<select id="leaveType" name="leaveType" class="easyui-combobox">
                    		<option value="sj">事假</option>
                    		<option value="nj">年假</option>
                    		<option value="cj">产假</option>
                    		<option value="sangjia">丧假</option>
                    		<option value="bj">病假</option>
                    	</select>
                    </td> 
                </tr> 
                <tr>
                	<td>开始时间</td>
                	<td>
                		<input id="startLeaveTime" name="startLeaveTime" class="Wdate" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'2006-09-10',maxDate:'2098-12-20'})"/>
                	</td>
                </tr>
               	<tr>
                	<td>结束时间</td>
                	<td>
                		<input id="endLeaveTime" name="endLeaveTime" class="Wdate" type="text" onclick="WdatePicker({skin:'whyGreen',minDate:'2006-09-10',maxDate:'2098-12-20'})"/>
                	</td>
                </tr>
                <tr>
                	<td>请假原因</td>
                	<td><input type="text" name="reason"></td>
                </tr>
                <tr>
                	<td>备注</td>
                	<td><input type="text" name="memo"></td>
                </tr>
            </table>
        </form>
    </div>
</div>