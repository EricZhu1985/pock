/**
 * 关键字提醒列表js
 * 
 * 2016-04-28
 * 建立此文件
 * 
 */

/**
 * 初始化关键词
 */
function loadKwList() {
	$('#kwdg').datagrid('load', {
		keyword: $('#query_kw_keyword').textbox('getValue'),
		memo: $('#query_kw_memo').textbox('getValue')
	});
}

/**
 * 新建关键词页面
 */
function newKeyword(){
	$('#kwdlg').dialog('open').dialog('center').dialog('setTitle','增加关键词');
	$('#kwfm').form('clear');
}

/**
 * 删除关键词
 */
function deleteKeyword() {
	var row = $('#kwdg').datagrid("getSelected");
	
}
/**
 * 保存黑名单用户
 */
function saveKeyword() {
	$('#kwfm').form('submit',{
		url: 'keywordwarning/insert',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
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
				$('#kwdlg').dialog('close');
				$('#kwdg').datagrid('reload');    // reload the user data
			}
		}
	});
}	

function updateKeyword() {
	$('#kwupdatefm').form('submit',{
		url: 'keywordwarning/update',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
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
				$('#kwupdatedlg').dialog('close');
				$('#kwdg').datagrid('reload');    // reload the user data
			}
		}
	});
}

function addKwAmount() {
	$('#kwaddamountfm').form('submit',{
		url: 'keywordwarning/addamount',
		onSubmit: function() {
			return $(this).form('validate');
		},
		success: function(result){
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
				$('#kwaddamountdlg').dialog('close');
				$('#kwdg').datagrid('reload');    // reload the user data
			}
		}
	});
}

function updateKeywordWarningDlg(keywordWarningID) {
	$('#kwupdatedlg').dialog('open').dialog('center').dialog('setTitle','修改关键词');
	$('#kwupdatefm').form('clear');
	$('#kwupdatefm').form('load', 'keywordwarning/detail?keywordWarningID=' + keywordWarningID);
}

function addKeywordWarningAmountDlg(keywordWarningID) {
	$('#kwaddamountdlg').dialog('open').dialog('center').dialog('setTitle','补充数量');
	$('#kwaddamountfm').form('clear');
	$('#kwaddamountfm').form('load', 'keywordwarning/detail?keywordWarningID=' + keywordWarningID);
	$('#kwaddamountfm_addAmount').textbox("setValue", "0");
}

function deleteKeywordWarning(keywordWarningID) {
	$.messager.confirm('提示','您确认要删除这条记录吗？',function(r){
		if (r){
			$('#ff').form('submit', {
				url: 'keywordwarning/delete',
				onSubmit: function(param){
					param.keywordWarningID = keywordWarningID;
				},
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
						$('#kwdg').datagrid('reload');    // reload the user data
					}
				}
			});
		}
	});
}


function formatKWOperation(val,row) {
	return '<a href="#" onclick="javascript: updateKeywordWarningDlg(\'' + row.keywordWarningID + '\')">修改</a>&nbsp;&nbsp;'
		+ '<a href="#" onclick="javascript: addKeywordWarningAmountDlg(\'' + row.keywordWarningID + '\')">补货</a>&nbsp;&nbsp;'
		+ '<a href="#" onclick="javascript: deleteKeywordWarning(\'' + row.keywordWarningID + '\')">删除</a>&nbsp;&nbsp;';
}