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
                <shiro:hasPermission name="/bpm/editProcess">
                    str += $.formatString('<a href="javascript:void(0)" class="bpm-easyui-linkbutton-edit" data-options="plain:true,iconCls:\'glyphicon-ok icon-green\'" onclick="bpmEditFun(\'{0}\',\'{1}\');" >编辑</a>');
                </shiro:hasPermission>
                <shiro:hasPermission name="/bpm/deleteProcess">
                    str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                    str += $.formatString('<a href="javascript:void(0)" class="bpm-easyui-linkbutton-del" data-options="plain:true,iconCls:\'glyphicon-trash icon-red\'" onclick="bpmDeleteFun(\'{0}\',\'{1}\');" >删除</a>');
                </shiro:hasPermission>
                <shiro:hasPermission name="/bpm/start">
                str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                str += $.formatString('<a href="javascript:void(0)" class="bpm-easyui-linkbutton-start" data-options="plain:true,iconCls:\'glyphicon-random icon-blue\'" onclick="bpmStart(\'{0}\',\'{1}\');" >手动执行</a>');
            	</shiro:hasPermission>
                return str;
            }
        } ] ],
        toolbar : '#bpmToolbar',
        onLoadSuccess:function(data){
            $('.bpm-easyui-linkbutton-edit').linkbutton({text:'编辑'});
            $('.bpm-easyui-linkbutton-del').linkbutton({text:'删除'});
            $('.bpm-easyui-linkbutton-start').linkbutton({text:'手动执行'});
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
 
 function bpmDeleteFun(){
	 
 }
 </script>
    
    
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
        <form id="bpmSearchForm">
            <table>
                <tr>
                    <th>名称1:</th>
                    <td><input name="processName" placeholder="流程名"/></td>
                    <td>
                        <a href="javascript:void(0);"class="easyui-linkbutton" data-options="iconCls:'glyphicon-search',plain:true" >查询</a>
                        <a href="javascript:void(0);"  class="easyui-linkbutton" data-options="iconCls:'glyphicon-remove-circle',plain:true" >清空</a>
                    </td>
                </tr>
            </table>
        </form>
     </div>
 
    <div data-options="region:'center',border:false">
        <table id="bpmDataGrid" data-options="fit:true,border:false"></table>
    </div>
</div>
<div id="bpmToolbar" style="display: none;">
    <shiro:hasPermission name="/bpm/preUploadBpmFile">
        <a onclick="bpmFileAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'fi-page-add'">添加流程</a>
    </shiro:hasPermission>
</div
