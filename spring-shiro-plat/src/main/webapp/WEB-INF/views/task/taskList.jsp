<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var taskDataGrid;
    $(function() {
        taskDataGrid = $('#taskDataGrid').datagrid({
        url : '${path}/task/dataGrid',
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
            width : '60',
            title : 'job名称',
            field : 'jobName',
            sortable : true
        }, {
            width : '80',
            title : '任务分组',
            field : 'jobGroup',
            sortable : true
        },{
            width : '80',
            title : '任务状态',
            field : 'jobStatus',
            sortable : true
        }, {
            width : '80',
            title : '任务运行时间表达式',
            field : 'cronExpression',
            sortable : true
        }, {
            width : '80',
            title : '任务描述',
            field : 'desc',
            sortable : true
        },  {
            field : 'action',
            title : '操作',
            width : 300,
            formatter : function(value, row, index) {
            	
                var str = '';
                <shiro:hasPermission name="/task/edit">
                    str += $.formatString('<a href="javascript:void(0)" class="task-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'glyphicon-ok icon-green\'" onclick="taskEditFun(\'{0}\');" >编辑</a>', row.id);
                </shiro:hasPermission>
                <shiro:hasPermission name="/task/delete">
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="task-easyui-linkbutton-del" data-options="plain:true,iconCls:\'glyphicon-trash icon-red\'" onclick="taskDeleteFun(\'{0}\');" >删除</a>', row.id);
                </shiro:hasPermission>
                <shiro:hasPermission name="/task/start">
                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                str += $.formatString('<a href="javascript:void(0)" class="task-easyui-linkbutton-start" data-options="plain:true,iconCls:\'glyphicon-random icon-blue\'" onclick="taskStart(\'{0}\');" >手动执行</a>', row.id);
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

/**
 * 添加框
 * @param url
 */
function taskAddFun() {
    parent.$.modalDialog({
        title : '添加',
        width : 700,
        height : 600,
        href : '${path}/task/addPage',
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = taskDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#taskAddForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 编辑
 */
function taskEditFun(id) {
    if (id == undefined) {
        var rows = taskDataGrid.datagrid('getSelections');
        id = rows[0].id;
    } else {
        taskDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
    }
    parent.$.modalDialog({
        title : '编辑',
        width : 700,
        height : 600,
        href :  '${path}/task/editPage?id=' + id,
        buttons : [ {
            text : '确定',
            handler : function() {
                parent.$.modalDialog.openner_dataGrid = taskDataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                var f = parent.$.modalDialog.handler.find('#taskEditForm');
                f.submit();
            }
        } ]
    });
}


/**
 * 删除
 */
 function taskDeleteFun(id) {
     if (id == undefined) {//点击右键菜单才会触发这个
         var rows = taskDataGrid.datagrid('getSelections');
         id = rows[0].id;
     } else {//点击操作里面的删除图标会触发这个
         taskDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
     }
     parent.$.messager.confirm('询问', '您是否要删除当前角色？', function(b) {
         if (b) {
             progressLoad();
             $.post('${path}/task/delete', {
                 id : id
             }, function(result) {
                 if (result.success) {
                     parent.$.messager.alert('提示', result.msg, 'info');
                     taskDataGrid.datagrid('reload');
                 }
                 progressClose();
             }, 'JSON');
         }
     });
}


/**
 * 清除
 */
function taskCleanFun() {
    $('#taskSearchForm input').val('');
    taskDataGrid.datagrid('load', {});
}
/**
 * 搜索
 */
function taskSearchFun() {
     taskDataGrid.datagrid('load', $.serializeObject($('#taskSearchForm')));
}
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
        <table id="taskDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="taskToolbar" style="display: none;">
    <shiro:hasPermission name="/task/add">
        <a onclick="taskAddFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加</a>
    </shiro:hasPermission>
</div>