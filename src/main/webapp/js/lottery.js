/**
 * 抽奖列表js
 * 
 * 2016-04-28
 * 建立此文件
 * 
 */
function saveCJ() {
	$('#cjfm').form('submit',{
		url: 'lottery/update',
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
                $('#cjfm').form('reset');
				$('#cjdlg').dialog('close');
   				$('#cjgl').datagrid('reload');  // reload the user data
			}
		}
	});
}
    
    //增加抽奖人
function addCJ(){
	$('#addCJForm').form('submit',{
		url: 'lottery/insert',
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
                $('#addCJForm').form('reset');
				$('#cjgl').datagrid('reload');    // reload the user data
			}
		}
	});
}
    
function editCJ(ID) {
	$('#cjdlg').dialog('open').dialog('center').dialog('setTitle','修改抽奖');
	$('#cjfm').form('clear');
	$('#cjfm').form('load', 'lottery/detail?lotteryID=' + ID);
}
    
function delCJ(ID) {
	$.messager.confirm('提示','您确认要删除这条记录吗？',function(r){
		if (r){
			$('#ff').form('submit', {
				url: 'lottery/delete',
				onSubmit: function(param){
					param.lotteryID = ID;
				},
				success:function(result){
					var result = eval('('+result+')');
					if (result.errorMsg){
						$.messager.alert({
							title: 'Error',
							msg: result.errMsg
						});
					} else {
						$.messager.alert({
							title: '成功',
							msg: result.msg
						});
						$('#cjgl').datagrid('reload');    // reload the user data
					}
				}
			});
		}
	});
}
    
    
function cj() {
    $('#cj2dlg').dialog('open').dialog('center').dialog('setTitle','请输入密码:');
    $('#cj2fm').form('rest');    // reload the user data
}
    
function cjconfirm() {
	$('#cj2fm').form('submit', {
		url: 'lottery/run',
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
                $('#cj2fm').form('reset');    // reload the user data
                $('#cj2dlg').dialog('close');
                $('#cjgl').datagrid('reload');    // reload the user data
				$('#lotteryHistoryGD').datagrid('reload');    // reload the user data
			}
		}
	});
}


function finishLottery(ID) {
	$.messager.confirm('提示','您确认要领奖吗？',function(r){
		if (r){
			$('#ff').form('submit', {
				url: 'lottery/finish',
				onSubmit: function(param){
					param.lotteryID = ID;
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
						$('#lotteryHistoryGD').datagrid('reload');    // reload the user data
					}
				}
			});
		}
	});    
}
        