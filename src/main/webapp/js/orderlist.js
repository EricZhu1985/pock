/**
 * 初始化订单列表
 */
function dgInit() {
	$('#dg').datagrid('load', {
		startDate: $('#order_date_start').datebox('getValue'),
		endDate: $('#order_date_end').datebox('getValue'),
		customerTel: $('#query_customer_tel').textbox('getValue'),
		branchID: $('#query_branch_id').combobox('getValue'),
		finished: $('#query_order_finished').combobox('getValue'),
		orderBy: $('#order_by_col').combobox('getValue'),
	    ts: new Date()
	});
}
/**
 * 打开支付窗口
 * @returns
 */
function otherPayDlg() {
	$('#otherpaydlg').dialog('open');
	$('#otherPayFm').form('clear');
}
/**
 * 支付确认
 * @returns
 */
function payOther() {

	$('#otherPayFm').form('submit', {
		url: 'payment/payother',
		onSubmit: function(param){
			return $(this).form('validate');
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
                loadList();
			}
			$('#otherpaydlg').dialog('close');
		}
	});
}
/**
 * 取消支付
 * @returns
 */
function cancelPayOther() {
	$('#otherpaydlg').dialog('close');
}

