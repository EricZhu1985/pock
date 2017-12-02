function loadInventoryItem() {
	$('#inventoryitemdg').datagrid('load', {
		name: $('#query_inventoryitem_name').textbox('getValue'),
		memo: $('#query_inventoryitem_memo').textbox('getValue'),
	    ts: new Date()
	});
}

/**
 * 订单列表操作列生成规则
 */
function iiOperationFormatter(val,row,index) {
	var inventoryItemId = row.inventoryItemId;
	var ret	= '<a href="#" onclick="javascript: openEditInventoryItemDlg(\'' + inventoryItemId + '\')">修改</a>&nbsp;'
		+ '<a href="#" onclick="javascript: delInventoryItem(\'' + inventoryItemId + '\')">删除</a>&nbsp;';
	return ret;
}

function iOperationFormatter(val,row,index) {
	var inventoryId = row.inventoryId;
	var ret	= '<a href="#" onclick="javascript: openEditInventoryDlg(\'' + inventoryId + '\')">修改</a>&nbsp;'
	+ '<a href="#" onclick="javascript: delInventory(\'' + inventoryId + '\')">删除</a>&nbsp;';
	return ret;
}


function delInventory(inventoryId) {
	$.messager.confirm('提示','您确认要删除这条记录吗？',function(r){
		if(r) {
			$.ajax({
		        url : 'inventory/deleteinventory?inventoryId=' + inventoryId,
		        cache : false,
		        async : false,
		        type : "POST",
		        contentType: "application/json; charset=utf-8",
		        success : function (result){
					$('#inventorydg').datagrid('reload'); 
					if(result.msg) {
			        	alert(result.msg);
					}
					if(result.errMsg) {
			        	alert(result.errMsg);
					}
		        }
		    });
		}
	});
}

function openEditInventoryDlg(inventoryId) {
	$('#editinventoryfm').form({'onLoadSuccess': function(data, arg2) {
		
		var inventoryItem = data.inventoryItem;
		if(inventoryItem) {
			$('#editinventoryfm_name').textbox('setValue', inventoryItem.name);
			$('#editinventoryfm_unit').textbox('setValue', inventoryItem.unit);
		}
	}});
	
	$('#editinventoryfm').form('load', 'inventory/inventorybyid?inventoryId=' + inventoryId);
    $('#editinventorydlg').dialog('open').dialog('center');
}

function inventoryItemNameFormatter(value, row, index) {
	if(row.inventoryItem) {
		return row.inventoryItem.name;
	}
	return "";
}

function inventoryItemUnitFormatter(value, row, index) {
	if(row.inventoryItem) {
		return row.inventoryItem.unit;
	}
	return "";
}

function openAddInventoryDlg() {
    $('#addinventorydlg').dialog('open').dialog('center');
}

function addInventoryItem() {
	$('#addinventoryitemfm').form('submit', {
		url: 'inventory/addinventoryitem',
		success:function(result){
			var result = eval('('+result+')');
			if (result.errMsg){
				$.messager.alert({
					title: 'Error',
					msg: result.errMsg
				});
			} else {
				$.messager.alert({
					title: '成功',
					msg: result.msg
				});
                $('#addinventoryitemfm').form('reset');    // reload the user data
                $('#addinventoryitemdlg').dialog('close');
				$('#inventoryitemdg').datagrid('reload');    // reload the user data
			}
		}
	});
}

function openEditInventoryItemDlg(inventoryItemId) {
	$('#editinventoryitemfm').form('load', 'inventory/inventoryitembyid?inventoryItemId=' + inventoryItemId);
    $('#editinventoryitemdlg').dialog('open').dialog('center');
}

function editInventoryItem() {

	$('#editinventoryitemfm').form('submit', {
		url: 'inventory/updateinventoryitem',
		success:function(result){
			var result = eval('('+result+')');
			if (result.errMsg){
				$.messager.alert({
					title: 'Error',
					msg: result.errMsg
				});
			} else {
				$.messager.alert({
					title: '成功',
					msg: result.msg
				});
                $('#editinventoryitemfm').form('reset');    // reload the user data
                $('#editinventoryitemdlg').dialog('close');
				$('#inventoryitemdg').datagrid('reload');    // reload the user data
			}
		}
	});
}

function delInventoryItem(inventoryItemId) {
	$.messager.confirm('提示','您确认要删除这条记录吗？',function(r){
		if(r) {
			$.ajax({
		        url : 'inventory/deleteinventoryitem?inventoryItemId=' + inventoryItemId,
		        cache : false,
		        async : false,
		        type : "POST",
		        contentType: "application/json; charset=utf-8",
		        success : function (result){
					$('#inventoryitemdg').datagrid('reload'); 
					if(result.msg) {
			        	alert(result.msg);
					}
					if(result.errMsg) {
			        	alert(result.errMsg);
					}
		        }
		    });
		}
	});
}

function openAddInventoryItemDlg() {
    $('#addinventoryitemdlg').dialog('open').dialog('center');
}