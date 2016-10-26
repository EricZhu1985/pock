/**
 * 黑名单列表js
 * 
 * 2016-04-28
 * 建立此文件
 * 
 */

/**
 * 初始化黑名单
 */
function loadBlList() {
	$('#bldg').datagrid('load', {
		customerTel: $('#query_bl_customer_tel').textbox('getValue')
	});
}

/**
 * 新建黑名单用户页面
 */
function newBlUser(){
	$('#bldlg').dialog('open').dialog('center').dialog('setTitle','新增黑名单');
	$('#blfm').form('clear');
}

/**
 * 删除黑名单用户
 */
function deleteBlUser() {
	var row = $('#bldg').datagrid("getSelected");
	if(row) {
		$.messager.confirm('提示','您确认要删除这条记录吗？',function(r){
		if (r){
			$('#ff').form('submit', {
				url: 'blacklist/delete',
				onSubmit: function(param){
					param.blackListID = row.blackListID;
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
						$('#bldg').datagrid('reload');    // reload the user data
					}
				}
			});
		}
	});
	}
}
/**
 * 保存黑名单用户
 */
function saveBlUser() {
	
	
	
	$('#blfm').form('submit',{
		url: 'blacklist/insert',
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
				$('#bldlg').dialog('close');
				$('#bldg').datagrid('reload');    // reload the user data
			}
		}
	});
}	