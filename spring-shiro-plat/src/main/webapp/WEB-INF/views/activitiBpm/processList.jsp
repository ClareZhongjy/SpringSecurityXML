<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var bpmDataGrid;
    $(function() {
    	bpmDataGrid = $('#bpmDataGrid').datagrid({
        url : '${path}/bpm/dataGrid',
        striped : true,
        rownumbers : true,
        pagination : true,
        singleSelect : true,
        idField : 'id',
        sortName : 'id',
        sortOrder : 'asc',
        pageSize : 20,
        pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500],
        frozenColumns : [ [ {
            width : '100',
            title : 'DeploymentId',
            field : 'deploymentId',
            sortable : true
        }, {
            width : '100',
            title : 'id',
            field : 'id',
            sortable : true
        },{
            width : '100',
            title : 'key',
            field : 'key',
            sortable : true
        }, {
            width : '150',
            title : 'Name',
            field : 'name',
            sortable : true
        }, {
            width : '150',
            title : 'Resource Name',
            field : 'resourceName',
            sortable : true
        },
        {
            width : '150',
            title : 'Diagram resource name',
            field : 'diagramresourcename',
            sortable : true
        },{
            field : 'action',
            title : '操作',
            width : 300,
            formatter : function(value, row, index) {
            	
                var str = '';
                <shiro:hasPermission name="/task/edit">
                    str += $.formatString('<a href="javascript:void(0)" class="task-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'glyphicon-ok icon-green\'" onclick="taskEditFun(\'{0}\',\'{1}\');" >编辑</a>');
                </shiro:hasPermission>
                <shiro:hasPermission name="/task/delete">
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="task-easyui-linkbutton-del" data-options="plain:true,iconCls:\'glyphicon-trash icon-red\'" onclick="taskDeleteFun(\'{0}\',\'{1}\');" >删除</a>');
                </shiro:hasPermission>
                <shiro:hasPermission name="/task/start">
                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                str += $.formatString('<a href="javascript:void(0)" class="task-easyui-linkbutton-start" data-options="plain:true,iconCls:\'glyphicon-random icon-blue\'" onclick="taskStart(\'{0}\',\'{1}\');" >手动执行</a>');
            	</shiro:hasPermission>
                return str;
            }
        } ] ],
        onLoadSuccess:function(data){
            $('.task-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.task-easyui-linkbutton-del').linkbutton({text:'删除'});
            $('.task-easyui-linkbutton-start').linkbutton({text:'手动执行'});
        },
        toolbar : '#taskToolbar'
    });
});
    </script>
    
    
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="taskSearchForm">
            <table>
                <tr>
                    <th>名称:</th>
                    <td><input name="taskName" placeholder="搜索条件"/></td>
                    <td>
                        <a href="javascript:void(0);"class="easyui-linkbutton" data-options="iconCls:'glyphicon-search',plain:true"onclick="taskSearchFun();">查询</a>
                        <a href="javascript:void(0);"  class="easyui-linkbutton" data-options="iconCls:'glyphicon-remove-circle',plain:true"  onclick="taskCleanFun();">清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="bpmDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="taskToolbar" style="display: none;">
    <shiro:hasPermission name="/task/add">
        <a onclick="taskAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
    </shiro:hasPermission>
</div
