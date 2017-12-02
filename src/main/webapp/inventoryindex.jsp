<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pockorder.constant.*,com.pockorder.domain.*" %>
<% int rights = ((User) session.getAttribute(SessionConst.USER)).getRights();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="jquery,ui,easy,easyui,web">
    <meta name="description" content="easyui help you build your web page easily!">
    <title>蛋糕小姐蛋糕店</title>
    <link rel="stylesheet" type="text/css" href="css/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="css/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="css/easyui/demo.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">
	<style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
	<script type="text/javascript">
    
	var USER_RIGHTS = '';
	$(function(){



	});

	        
	</script>
    <script type="text/javascript" src="js/inventory.js"></script>
</head>
<body>
    <div id="tt" class="easyui-tabs" style="width:90%;height:auto">
		<div title="货物">
			
			<table id="inventoryitemdg" class="easyui-datagrid" style="height:500px;width:100%"
				url="inventory/inventoryitemlist"
				toolbar="#iitoolbar" pagination="true" pageSize="10"
				rownumbers="true" fitColumns="true">
				<thead>
					<tr>
						<th field="inventoryItemId" width="80">编号</th>
						<th field="name" width="150">名字</th>
						<th field="unit" width="80">单位/规格</th>
						<th field="quantity" width="80">数量</th>
						<th field="memo" width="300">备注</th>
                        <th data-options="field:'op',width:150,formatter:iiOperationFormatter">操作</th>
					</tr>
				</thead>
			</table>
			<div id="iitoolbar">
				<div style="padding: 3px">
					名称: <input id="query_inventoryitem_name" class="easyui-textbox" style="width:110px">&nbsp;&nbsp;
					备注: <input id="query_inventoryitem_memo" class="easyui-textbox" style="width:110px">&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="javascript:loadInventoryItem()">查询</a>
				</div>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddInventoryItemDlg()">增加货物</a>
				</div>
			</div>
			<div id="addinventoryitemdlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" buttons="#addiidlg-buttons" title="增加货物">
				<div class="ftitle">增加货物</div>
				<form id="addinventoryitemfm" method="post" novalidate>
					<div class="fitem">
						<label>名称：</label>
						<input name="name" class="easyui-textbox"  required="true">
					</div>
					<div class="fitem">
						<label>单位/规格：</label>
						<input name="unit" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label>数量：</label>
						<input name="quantity" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label>备注：</label>
						<input name="memo" class="easyui-textbox">
					</div>
				</form>
			</div>
			<div id="addiidlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="addInventoryItem()" style="width:90px">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addinventoryitemdlg').dialog('close')" style="width:90px">取消</a>
			</div>
			<div id="editinventoryitemdlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" buttons="#editiidlg-buttons" title="修改货物">
				<div class="ftitle">修改货物</div>
				<form id="editinventoryitemfm" method="post" novalidate>
					<input name="inventoryItemId" type="hidden"/>
					<div class="fitem">
						<label>名称：</label>
						<input name="name" class="easyui-textbox"  required="true">
					</div>
					<div class="fitem">
						<label>单位：</label>
						<input name="unit" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label>数量：</label>
						<input name="quantity" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label>备注：</label>
						<input name="memo" class="easyui-textbox">
					</div>
				</form>
			</div>
			<div id="editiidlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="editInventoryItem()" style="width:90px">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editinventoryitemdlg').dialog('close')" style="width:90px">取消</a>
			</div>
        </div>
        <!--  <div title="库存">
			
			<table id="inventorydg" class="easyui-datagrid" style="height:500px;width:100%"
				url="inventory/inventorylist"
				toolbar="#itoolbar" pagination="true" pageSize="10"
				rownumbers="true" fitColumns="true">
				<thead>
					<tr>
						<th field="inventoryId" width="80">编号</th>
						<th field="name" width="150" formatter="inventoryItemNameFormatter">名字</th>
						<th field="unit" width="80" formatter="inventoryItemUnitFormatter">单位/规格</th>
						<th field="quantity" width="150">数量</th>
                        <th data-options="field:'op',width:150,formatter:iOperationFormatter">操作</th>
					</tr>
				</thead>
			</table>
			<div id="itoolbar">
				<div style="padding: 3px">
					名称: <input id="query_inventory_name" class="easyui-textbox" style="width:110px">&nbsp;&nbsp;
					备注: <input id="query_inventory_memo" class="easyui-textbox" style="width:110px">&nbsp;&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="javascript:loadInventory()">查询</a>
				</div>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAddInventoryDlg()">增加库存</a>
				</div>
			</div>
			<div id="addinventorydlg" class="easyui-dialog" style="width:750px;height:400px;padding:10px 20px"
					closed="true" buttons="#addidlg-buttons" title="增加库存">
				<div class="ftitle">增加库存</div>
				<form id="addinventoryfm" method="post" novalidate>
					<div class="fitem">
						<label>位置：</label>
						<input name="position" class="easyui-textbox"  required="true" readonly value="1">
					</div>
					<div class="fitem">
						<label>数量：</label>
						<input name="quantity" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label>货物：</label>
						<table id="addinventoryfmitemdg" class="easyui-datagrid" style="height:200px;width:100%"
							url="inventory/inventoryitemlist" pagination="true" pageSize="10"
							rownumbers="true" fitColumns="true">
							<thead>
								<tr>
									<th field="inventoryItemId" width="80">编号</th>
									<th field="name" width="150">名字</th>
									<th field="unit" width="80">单位/规格</th>
									<th field="memo" width="300">备注</th>
								</tr>
							</thead>
						</table>
					</div>
				</form>
			</div>
			<div id="addidlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="addInventoryItem()" style="width:90px">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addinventorydlg').dialog('close')" style="width:90px">取消</a>
			</div>
			<div id="editinventorydlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" buttons="#editidlg-buttons" title="修改库存">
				<div class="ftitle">修改库存</div>
				<form id="editinventoryfm" method="post" novalidate>
					<input name="inventoryId" type="hidden"/>
					<div class="fitem">
						<label>名称：</label>
						<input id="editinventoryfm_name" class="easyui-textbox" readonly>
					</div>
					<div class="fitem">
						<label>单位：</label>
						<input id="editinventoryfm_unit" class="easyui-textbox" readonly>
					</div>
					<div class="fitem">
						<label>数量：</label>
						<input name="quantity" class="easyui-textbox">
					</div>
				</form>
			</div>
			<div id="editidlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="editInventoryItem()" style="width:90px">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editinventoryitemdlg').dialog('close')" style="width:90px">取消</a>
			</div>
        </div>-->
</body>
</html>