<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    var bpmDataGrid;
    $(function() {
        bpmDataGrid = $('#bpmDataGrid').datagrid({
            url : '${path}/approve/dataGrid',
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
                title : 'processId',
                field : 'processId',
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
                        <shiro:hasPermission name="/bpm/deleteProcess">
                        str += $.formatString('<a href="javascript:void(0)" class="bpm-easyui-linkbutton-del" data-options="plain:true,iconCls:\'glyphicon-trash icon-red\'" onclick="bpmDeleteFun(\'{0}\');" >删除</a>', row.deploymentId);
                        </shiro:hasPermission>


                        return str;
                    }
                } ] ],
            toolbar : '#bpmToolbar',
            onLoadSuccess:function(data){
                $('.bpm-easyui-linkbutton-del').linkbutton({text:'删除'});
            }

        });
    });

    function  bpmFileAdd(){
        parent.$.modalDialog({
            title : '添加流程',
            width : 400,
            height : 600,
            href : '${path}/bpm/preUploadBpmFile',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = bpmDataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#uploadBpmForm');
                    f.submit();
                }
            } ]
        });
    }

    function bpmDeleteFun(id){
        debugger;
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = bpmDataGrid.datagrid('getSelections');
            id = rows[1].jobName;

        } else {//点击操作里面的删除图标会触发这个
            bpmDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        $.messager.confirm('询问','请问是否确定删除该流程？',function(r){
            if(r){
                progressLoad();
                $.post(
                    '${path}/bpm/deleteProcess',
                    {processId: id},
                    function(result){
                        if(result.success){
                            parent.$.messager.alert('提示', result.msg, 'info');
                            bpmDataGrid.datagrid('reload');
                        }else{
                            parent.$.messager.alert('提示', result.msg, 'error');
                            bpmDataGrid.datagrid('reload');
                        }
                        progressClose();
                    },
                    'JSON'
                );
            }
        })
    }
</script>


<div class="easyui-layout" data-options="fit:true,border:false">


    <div data-options="region:'center',border:false">
        <table id="bpmDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="bpmToolbar" style="display: none;">
    <shiro:hasPermission name="/bpm/preUploadBpmFile">
        <a onclick="bpmFileAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'glyphicon-plus icon-green'">添加流程</a>
    </shiro:hasPermission>
</div>