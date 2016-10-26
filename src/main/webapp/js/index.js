/**
 * 支付表格合并行处理
 * @param data
 * @returns
 */
function payDGOnLoadSuccess(data) {
	var len = 0;
	var start = 0;
	var curDate = "";
	var pays = data.rows;
	for(var i = 0; i < pays.length; i++) {
		var d = pays[i].paymentDate;
		if(d == curDate) {
			len++;
		} else {
			if(len > 0) {
				$(this).datagrid('mergeCells',{
	                index: start,
	                field: 'paymentDate',
	                rowspan: len + 1
	            });
			}
			start = i;
			len = 0;
			curDate = d;
		}
	}
}

function branchCBLoadSuccess(data) {
	$(this).combobox("setValue", data[0].branchID);
}

function paymentAccountCBLoadSuccess(data) {
	$(this).combobox("setValue", data[0].paymentAccountID);
}

function payTBPaymentAccountCBLoadSuccess(data, cb) {
	$(this).combobox("setValue", data[0].paymentAccountID);
}
/**
 * 字符串转换日期对象
 */
function strToDate(str) {
  var val=Date.parse(str);
  var newDate=new Date(val);
  return newDate;
}  
/**
 * 订单列表部门列背景颜色规则
 */
function branchNameCellStyler(value,row,index){
	if(row.branch) {
		if (row.branch.branchID != 1 && !row.deliverFlag){
			return 'background-color:#FF00FF;';
		}
	}
}
/**
 * 订单列表标识列背景颜色规则
 */
function remarkCellStyler(value,row,index){
	if (row.remark == 1){
		return 'background-color:#FF0000;';
	}
}
/**
 * 日期格式化规则
 */
function myformatter(date){
	var y = date.getFullYear();
	var m = date.getMonth()+1;
	var d = date.getDate();
	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
/**
 * 日期格式化规则
 */
function myparser(s){
	if (!s) return new Date();
	var ss = (s.split('-'));
	var y = parseInt(ss[0],10);
	var m = parseInt(ss[1],10);
	var d = parseInt(ss[2],10);
	if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
		return new Date(y,m-1,d);
	} else {
		return new Date();
	}
}
        
var clipboardTextMap = new Array();
        

/**
 * 订单列表操作列生成规则
 */
function formatOperation(val,row,index) {
    var branch_name = row.branch? row.branch.brancNname : "";
    var customer_tel = row.customer? row.customer.tel : "";
    var orderTime = row.orderTime;
    var price = row.price;
    if(!branch_name) {
        branch_name = "待确认";
    }
    if(!customer_tel) {
        customer_tel = "待确认";
    }
    if(!orderTime || orderTime == "00:00") {
        orderTime = "时间待确认";
    }
    //var copyText = row.order_date + " " + orderTime + "," + branch_name + "," + row.content + "," + customer_tel;
    var copyText = "请核对如下信息:" + "<br>"
    	+ "蛋糕：" + row.content + "<br>"
    	+ "取货时间：" + row.orderDate + "&nbsp;&nbsp;" + orderTime + "<br>"
    	+ "取货地址：" + branch_name + "<br>"
    	+ "联系方式：" + customer_tel + "<br>"
    	+ "总价：" + price + "&nbsp;&nbsp;" + "已付款：" + row.paid;
    clipboardTextMap[index] = copyText;
	
	var ret = '';
	if(row.finished == 0) {
		ret += '<a href="#" onclick="javascript: openFinishOrderDlg(\'' + val + '\')">取货</a>&nbsp;'
			+ '<a href="#" onclick="javascript: openPayOrderDlg(\'' + val + '\')">支付</a>&nbsp;';
	} else if(row.finished == 1) {
		ret += '<a href="#" onclick="javascript: unfinishOrder(\'' + val + '\')">撤销取货</a>&nbsp;';
	}
	ret	+= '<a href="#" onclick="javascript: loadEditForm(\'' + val + '\')">修改</a>&nbsp;'
		+ '<a href="#" onclick="javascript: deleteOrder(\'' + val + '\')">删除</a><br/>'
		+ '<a href="#" onclick="javascript: remarkOrder(\'' + val + '\', \'' + row.remark + '\')">标记</a>&nbsp;'
		+ '<a href="#" onclick="javascript: detailWindow(\'' + val + '\')">打印</a>&nbsp;'
		+ '<a href="#" onclick="javascript: copyToClipboard(\'' + index + '\')">复制</a>&nbsp;';
		
    if(USER_RIGHTS == 0) {
		ret += '<a href="#" onclick="javascript: detailKitchenWindow(\'' + val + '\')">后厨</a>&nbsp;';
    }
		
	return ret;
}
/**
 * 可转让列表操作列生成规则
 */
function formatSDGOperation(val,row){
	//return '';
	return '<a href="#" onclick="javascript: transferOrder(\'' + val + '\')">转让</a>&nbsp;&nbsp;';
}
        
function formatCJOperation(val, row) {
	return '<a href="#" onclick="javascript: editCJ(\'' + val + '\')">修改</a>&nbsp;&nbsp;'
		+ '<a href="#" onclick="javascript: delCJ(\'' + val + '\')">删除</a>&nbsp;&nbsp;';
}
        
/**
 * 可转让列表操作列生成规则
 */

function getOrder(orderID) {
	var ret = {};
	$.ajax({
        url : 'order/detail?orderID=' + orderID,
        cache : false,
        async : false,
        type : "POST",
        contentType: "application/json; charset=utf-8",
        success : function (result){
        	ret = result;
        }
    });
	return ret;
}
/**
 * 转让操作
 */
function transferOrder(orderID) {
	$('#ff').form('submit', {
		url: '/sql/order/transferorder_json.php',
		onSubmit: function(param){
			param.orderID = orderID;
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
				//$('#sdg').datagrid('reload');    // reload the user data
                //$('#dg').datagrid('reload');    // reload the user data
                loadList();
				printTransferOrder(result.orderID);
			}
		}
	});
}
/**
 * 订单列表取货操作
 */
function openFinishOrderDlg(orderID) {
	var order = getOrder(orderID);
	var paymentArr = [];
	$.ajax({
        url : 'payment/getorderpayment?orderID=' + orderID,
        cache : false,
        async : false,
        type : "POST",
        contentType: "application/json; charset=utf-8",
        success : function (result){
        	paymentArr = result;
        }
    });

	var totalPaidText = "";
	var totalPaid = 0;
	for(var i = 0; i < paymentArr.length; i++) {
		var payment = paymentArr[i];
		if(totalPaidText != "") {
			totalPaidText += "<br/>";
		}
		totalPaid += payment.paid;
		totalPaidText += payment.paid + "&nbsp;&nbsp;&nbsp;" + payment.account.paymentAccountName + "&nbsp;&nbsp;&nbsp;" + payment.paymentTime;
	}
    $('#finishdlg').dialog('open').dialog('center');
    document.getElementById('finishOrderPaidTD').innerHTML = totalPaid;
    //document.getElementById('finishOrderTotalTD').innerHTML = order.price;
    document.getElementById('finishOrderContentTD').innerHTML = order.content;
    document.getElementById('finishOrderFmOrderID').value = orderID;
    $('#finishOrderFmPrice').textbox("setValue", order.price);
    $('#finishOrderFmPaid').textbox("setValue", order.price - totalPaid);
    
}

function openPayOrderDlg(orderID) {
	var order = getOrder(orderID);
	var paymentArr = [];
	$.ajax({
        url : 'payment/getorderpayment?orderID=' + orderID,
        cache : false,
        async : false,
        type : "POST",
        contentType: "application/json; charset=utf-8",
        success : function (result){
        	paymentArr = result;
        }
    });

	var totalPaidText = "";
	var totalPaid = 0;
	for(var i = 0; i < paymentArr.length; i++) {
		var payment = paymentArr[i];
		if(totalPaidText != "") {
			totalPaidText += "<br/>";
		}
		totalPaid += payment.paid;
		totalPaidText += payment.paymentDate + "&nbsp;" + payment.paymentTime.substr(0, 8) + "&nbsp;&nbsp;&nbsp;" + payment.account.paymentAccountName + "&nbsp;&nbsp;&nbsp;" + payment.paid + "元";
	}
    $('#paymentdlg').dialog('open').dialog('center');
    document.getElementById('payOrderPaidTD').innerHTML = totalPaid;
    document.getElementById('payOrderContentTD').innerHTML = totalPaidText;
    document.getElementById('payOrderFmOrderID').value = orderID;
    document.getElementById('payOrderFmPriceTD').innerHTML = order.price;
    var p = order.price - totalPaid;
    if(p < 0) p = 0;
    $('#payOrderFmPaid').textbox("setValue", p);
    
}

function payOrder() {
	$('#payOrderFm').form('submit',{
		url: 'order/pay',
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
				$('#paymentdlg').dialog('close');
                loadList();
			}
		}
	});
}

function cancelPayOrder() {
	$('#paymentdlg').dialog('close');
}
/**
 * 撤销取货
 * @returns
 */
function unfinishOrder(orderID) {
	$.messager.confirm('提示','您确认要修改这条记录吗？',function(r){
		if (r){
			$('#ff').form('submit', {
				url: 'order/unfinish',
				onSubmit: function(param){
					param.orderID = orderID
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
						//$('#sdg').datagrid('reload');    // reload the user data
					}
				}
			});
		}
	});
}


function payAndFinishOrder() {
	
	$('#finishOrderFm').form('submit',{
		url: 'order/payAndFinish',
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
				$('#finishdlg').dialog('close');
				//$('#finishOrderFm').form('reset');
                loadList();
				//$('#sdg').datagrid('reload');    // reload the user data
			}
		}
	});
}	

function cancelPayAndFinishOrder() {
	$('#finishdlg').dialog('close');
	$('#finishOrderFm').form('reset');
}
/**
 * 订单列表删除订单操作
 */
function deleteOrder(orderID) {

	$.messager.confirm('提示','您确认要删除这条记录吗？',function(r){
		if (r){
			$('#ff').form('submit', {
				url: 'order/delete',
				onSubmit: function(param){
					param.orderID = orderID;
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
                        //$('#dg').datagrid('reload');    // reload the user data
						//$('#sdg').datagrid('reload');    // reload the user data
                        loadList();
					}
				}
			});
		}
	});
}
/**
 * 订单列表标记订单操作
 */
function remarkOrder(orderID, remark) {
	$.messager.confirm('提示','您确认要修改这条记录吗？',function(r){
		if (r){
			if(remark == 0) {
				remark = 1;
			} else {
				remark = 0;
			}
			$('#ff').form('submit', {
				url: 'order/remark',
				onSubmit: function(param){
					param.orderID = orderID;
					param.remark = remark;
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
                        //$('#dg').datagrid('reload');    // reload the user data
                        loadList();
					}
				}
			});
		}
	});
}
/**
 * 加载修改订单数据
 */
function loadEditForm(orderId) {
	$('#tt').tabs('enableTab', '修改订单');
	$('#tt').tabs('select', '修改订单');
	$('#editOrderForm').form({'onLoadSuccess': function(data, arg2) {
		var customer = data.customer;
		if(customer) {
			//$('editFormCustomerName').val(customer.name);
			//document.getElementById("editFormCustomerName").value = customer.name;
			//document.getElementById("editFormCustomerTel").value = customer.tel;
			//document.getElementById("editFormCustomerWx").value = customer.wx;
		}
		var branch = data.branch;
		if(branch) {
			$('#editFormBranchId').combobox('setValue', branch.branchID);
		}
	}});
	
	$('#editOrderForm').form('load', 'order/detail?orderID=' + orderId);
}
/**
 * 取消修改订单
 */
function cancelEdit() {
	$('#editOrderForm').form('clear');
	$('#tt').tabs('select', '订单');
	$('#tt').tabs('disableTab', '修改订单');
}
/**
 * 取消增加订单
 */
function cancelAdd() {
	$('#addOrderForm').form('clear');
	$('#tt').tabs('select', '订单');
}
/**
 * 修改订单操作
 */
function editOrder(){
    
    var editFormTime = $('#editFormTime').textbox('getValue');
    while(editFormTime.indexOf("：") > 0) {
       editFormTime = editFormTime.replace("：",":")
       $('#editFormTime').textbox('setValue', editFormTime);
    }
    
	$('#editOrderForm').form('submit',{
		url: 'order/update',
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
				var order = result.order;
				$('#editOrderForm').form('clear');
				$('#tt').tabs('select', '订单');
                //$('#dg').datagrid('reload');    // reload the user data
                loadList();
				if(order.orderDate == getNowFormatDate()) {
					printEditOrder(order.orderID);
				}
			}
			$('#tt').tabs('disableTab', '修改订单');
		}
	});
}
/**
 * 保存订单操作
 */
function saveOrder() {
    var addFormTime = $('#addFormTime').textbox('getValue');
    while(addFormTime.indexOf("：") > 0) {
       addFormTime = addFormTime.replace("：",":")
       $('#addFormTime').textbox('setValue', addFormTime);
    }
	var repeat_count = $('#addFormRepeatCount').textbox('getValue');
	if(repeat_count) {
		$.messager.confirm('提示',"您确认要一次性插入" + repeat_count + "条数据？",function(r){
			if (r){
				saveOrderForm();
			}
		});
	} else {
		saveOrderForm();
	}
}
	
function saveOrderForm() {
	$('#addOrderForm').form('submit',{
		url: 'order/add',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (!result){
				$.messager.alert({
					title: 'Error',
					msg: result.errMsg
				});
			} else {
				var order = result;
				$.messager.alert({
					title: '成功',
					msg: '增加成功！'
				});
				$('#addOrderForm').form('reset');
				$('#addFormPaymentAccount').combobox('select', $('#addFormPaymentAccount').combobox('getData')[0].paymentAccountID);
				$('#tt').tabs('select', '订单');
				

			    var orderDate = $('#addFormDate').datebox('getValue');
			    var tel = $('#addFormTel').textbox('getValue');

			    //判断是否黑名单
				$.ajax({
					type: "post",
					url: "blacklist/isblacklist",
					data: "tel=" + tel,
					success: function (result) {
						var isblacklist = eval('('+result+')');
						if(isblacklist) {
							$.messager.alert('提示','提醒：该号码是黑名单号码！');
						}
					}
				});

			    //判断是否重复订单
				$.ajax({
					type: "post",
					url: "order/testrepeatorder",
					data: "orderDate=" + orderDate + "&tel=" + tel,
					success: function (result) {
						var isrepeat = eval('('+result+')');
						if(isrepeat) {
							$.messager.alert('提示','提醒：该订单日期存在相同电话号码，请检查是否重复下单！');
						}
					}
				});
				
                //$('#dg').datagrid('reload');    // reload the user data
                loadList();
                
				if(order.orderDate == getNowFormatDate()) {
					printNewOrder(order.orderID);
				}
			}
		}
	});
}
var LODOP;
function CheckIsInstall() {	 
	CreateOneFormPage();	
	LODOP.PRINT();
}; 
function CreateOneFormPage(){
	var strFormHtml="<body>"+document.getElementById("lodopDiv").innerHTML+"</body>";
	LODOP=getLodop();  
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
	LODOP.SET_PRINT_PAGESIZE(1,1000,400,"Test");
	LODOP.ADD_PRINT_HTM(0,0,1000,400,strFormHtml);
};	
//打印新增订单
function printNewOrder(orderID) {
	var typeSymbol = "[新]";
	try {
        printOrder(typeSymbol, orderID);
	} catch(e) {
		alert(e);
	}
}
//打印修改订单
function printEditOrder(orderID) {
	var typeSymbol = "[改]";
	try {
		printOrder(typeSymbol, orderID);
	} catch(e) {
	}
}
//打印修改订单
function printTransferOrder(orderID) {
	var typeSymbol = "[转]";
	try {
		printOrder(typeSymbol, orderID);
	} catch(e) {
	}
}
	
function printOrder(typeSymbol, orderID) {
	$.ajax({
		type: "post",
		url: "order/detail",
		data: "orderID=" + orderID,
		success: function (result) {
			var order = eval('('+result+')');
			printOrderCallback(typeSymbol, order);
		}
	});
	
}

function printOrderCallback(typeSymbol, order) {
	/*if(order.orderNo <= 0) {
		return;
	}
	var branchSymbol = order.branch_id == 2 ? "★★" : "";
	document.getElementById("lodopDivText").innerHTML = typeSymbol + branchSymbol + "&nbsp;&nbsp;" + order.orderTime + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='font-size:18px;font-weight:bold;'>" + order.customer_tel + "</span><br>" + order.content;
	document.getElementById("lodopDivQrCodeImg").src = "http://qr.liantu.com/api.php?el=l&text=" + encodeURI("<?php echo $_SERVER['HTTP_HOST']?>/oc.php?n=" + order.orderID);
	CheckIsInstall();*/
        
 
    if(CANPRINT) {
		document.getElementById("jatoolsOrderNoSpan").innerHTML = typeSymbol + order.orderNo;
	document.getElementById("jatoolsOrderTimeSpan").innerHTML = order.orderTime;
	document.getElementById("jatoolsBranchNameSpan").innerHTML = order.branch_id == 2 ? "▲" : "";
	document.getElementById("jatoolsOrderContentSpan").innerHTML = order.content;

	//打印文档对象9
	var myDoc = {
		settings : {
			paperWidth : 800,
			paperHeight : "auto" // 小票打印，高度自动					
		},
		marginIgnored : true,
		documents : document,
		copyrights : '杰创软件拥有版权  www.jatools.com'
	};
	// 调用打印方法
    //alert(jatoolsPrinter.printPreview);
    //jatoolsPrinter.printPreview(myDoc); // 打印预览
    //jatoolsPrinter.print(myDoc, true); // 打印前弹出打印设置对话框
		jatoolsPrinter.print(myDoc, false); // 不弹出对话框打印
    }
}

//日期处理
function getNowFormatDate(count) {
	if(!count) {
		count = 0
	}
	var day = new Date();
		day.setDate(day.getDate() + count);
	var Year = 0;
	var Month = 0;
	var Day = 0;
	var CurrentDate = "";
	//初始化时间
	//Year= day.getYear();//有火狐下2008年显示108的bug
	Year= day.getFullYear();//ie火狐下都可以
	Month= day.getMonth()+1;
	Day = day.getDate();
	//Hour = day.getHours();
	// Minute = day.getMinutes();
	// Second = day.getSeconds();
	CurrentDate += Year + "-";
	if (Month >= 10 )
	{
	CurrentDate += Month + "-";
	}
	else
	{
	CurrentDate += "0" + Month + "-";
	}
	if (Day >= 10 )
	{
	CurrentDate += Day ;
	}
	else
	{
	CurrentDate += "0" + Day ;
	}
	return CurrentDate;
} 

function today(id) {
	$(id).datebox('setValue', getNowFormatDate());
}

function tomorrow(id) {
	$(id).datebox('setValue', getNowFormatDate(1));
}
function newOrder() {
	$('#tt').tabs('select', '增加订单');
}
/**
 * 移到分店操作
 */
function deliverOrder(deliver_flag) {
	var sels = $("#dg").datagrid('getChecked');
	
	if(sels.length == 0) {
		return;
	}
	
	var orderIds = "";
	for(var i = 0; i < sels.length; i++) {
		if(orderIds != "") {
			orderIds += ",";
		}
		orderIds += sels[i].orderID;
	}
	
	$('#ff').form('submit', {
		url: 'order/deliver',
		onSubmit: function(param){
			param.orderID  = orderIds;
			param.deliverFlag = deliver_flag;
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
                //$('#dg').datagrid('reload');    // reload the user data
                loadList();
			}
		}
	});
}
/**
 * 打印标签
 */
function printLabel() {
	var sels = $("#dg").datagrid('getChecked');
	
	for(var i = 0; i < sels.length; i++) {
		printNewOrder(sels[i].orderID);
	}
	
}
        
        /**

订单号：501278680
/ 微信支付
/ 取货时间：2015-12-24 16:00-17:00
/ 门店：雅居乐店
交易号：
1219625801391512231882731162
	
详情  -  收藏  -  备注
[1件 芭比娃娃 （1磅(6寸),款式6）] 	
芭比娃娃（1磅(6寸),款式6）
	
¥228.00
（1件）
	生日蛋糕，指定要款式6的娃娃，谢谢 	黄小姐
13702937784 	2015-12-23 17:34 	待发货
卖家发货
卖家退货
	
228.00
（含运费：0.00）
打印订单
	
	
行拆分
0:
订单号：501278680
/ 微信支付
/ 取货时间：2015-12-24 16:00-17:00
/ 门店：雅居乐店
交易号：
1219625801391512231882731162

1:
详情  -  收藏  -  备注
[1件 芭比娃娃 （1磅(6寸),款式6）] 

2:
芭比娃娃（1磅(6寸),款式6）

3:
¥228.00
（1件）

4:生日蛋糕，指定要款式6的娃娃，谢谢

5:黄小姐
13702937784

6:2015-12-23 17:34

7:待发货
卖家发货
卖家退货

8:
228.00
（含运费：0.00）
打印订单

订单号：501326723
/ 微信支付
/ 取货时间：2016-04-28 11:30-13:00(怡翠店,西樵店)
/ 门店：怡翠玫瑰园
交易号：
1219625801391604261796984582
	
详情  -  收藏  -  备注
[1件 数码蛋糕－女孩款式 （1磅(6寸),水果忌廉,怡翠玫瑰园店,冰雪奇缘01）] 	
数码蛋糕－女孩款式（1磅(6寸),水果忌廉,怡翠玫瑰园店,冰雪奇缘01）
	
¥188.00
（1件）
	能另外在蛋糕上插个牌子 写 奶奶 生日快乐吗 	崔小姐
13929599971 	2016-04-26 20:35 	待发货
卖家发货
卖家退货
	
188.00
（含运费：0.00）
打印订单

             */
	function weijuju() {
	
		var weijujutext = document.getElementById("weijujuOrderText").value;
		if(weijujutext) {
			var mark = String.fromCharCode(10);
			
			var arr = weijujutext.split("\t");
			for(var i = 0; i < arr.length; i++) {
                //alert(i+":"+arr[i]);
			}
			
			var arr0 = arr[0].split("\n");
            var no = "";
            var d = "";
            var t = "";
            var branchId = 0;
            for(var i = 0; i < arr0.length; i++) {
                if(arr0[i].indexOf("订单号") >= 0) {
					no = arr0[i].substr(4);
                }
                if(arr0[i].indexOf("取货时间") >= 0) {
					d = arr0[i].substr(7, 10);
					t = arr0[i].substr(18, 5);
                }
                if(arr0[i].indexOf("门店") >= 0) {
                    if(arr0[i].indexOf("雅居乐店") >= 0) {
                        branchId = 2;
                    } else if(arr0[i].indexOf("怡翠玫瑰园") >= 0) {
                        branchId = 1;
                    } else if(arr0[i].indexOf("西樵店") >= 0) {
                        branchId = 3;
                    }
                }
            }
            
            //var paid = arr[1].trim();
            var pricetmp = arr[8].split("\n")[1];
            var price = pricetmp.substr(0, pricetmp.indexOf(".00"));
            //var price = arr[8].split("\n")[1];
            
            var getContent = function(arg0, arg1) {
                return arg0.split("\n")[1].trim() + arg1.split("\n")[2].trim();
            }
            
            var content = getContent(arr[2], arr[3]);
			var memo = arr[4];
			
			var nameTel = arr[5].trim().split("\n");
			var name = nameTel[0];
			var tel = nameTel[1];
            //var d = dateTime.substr(0, 10);
            //var t = dateTime.substr(11, 5);
            if(arr.length > 9) {
                for(var i = 9; i < arr.length; i=i+2) {
                    content += "," + getContent(arr[i], arr[i+1]);
                }
            }
			var contentText = content;
			if(memo) {
				contentText = contentText + ",备注:" + memo.trim();
			}	
			
			
			$("#addFormDate").datebox("setValue", d);
			$("#addFormTime").textbox("setValue", t);
			$("#addFormPrice").textbox("setValue", price);
			$("#addFormContent").textbox("setValue", contentText);
			$("#addFormCustomerName").textbox("setValue", name);
			$("#addFormTel").textbox("setValue", tel);
			$("#addFormWeijujuNo").textbox("setValue", no);
            //$("#addFormBranchId").combobox("select", branchId);
            $("#addFormBranchId")[0].value = branchId;
			
			if(arr[7].indexOf("待发货") >= 0){
				$("#addFormPaid").textbox("setValue", price);
            } else {
                $.messager.alert({
                    title: '提醒',
                    msg: '改订单尚未付款!'
                });
            }
		}
	}
        
	
function detailWindow(id) {
	window.open('order/detail?orderID=' + id, 'newwindow', 'height=700, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');   
}

function detailKitchenWindow(id) {
	//window.open('detailkitchen.php?ID=' + id, 'newwindow', 'height=700, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');   
	printOrder("", id);
}
	
	

var addFormDateId = '#addFormDate';
var editFormDateId = '#editFormDate';
var addFormBranchId = '#addFormBranchId';
var editFormBranchId = '#editFormBranchId';
	
	
        
function formatLTOperation(val,row){
	var id = "";
	if(row.prizedLottery) {
		//如果已经领奖不显示
		if(row.prizedLottery.isFinish == 1) {
			return "";
		}
		id = row.prizedLottery.lotteryID;
	} else {
		return "";
	}
	return '<a href="#" onclick="javascript: finishLottery(\'' + id + '\')">领奖</a>&nbsp;&nbsp;';
}
        
        
        
function copyToClipboard(dataIndex) {
    var copyText = clipboardTextMap[dataIndex];
    
    $.messager.alert({
        title: '复制信息',
        msg: copyText
    });
}

function getPayDgCbAccount(data) {
	$('#payTBPaymentAccountCB').combobox('reload', 'payment/getbranchaccount?branchID=' + data.branchID + '&all=true');
}

function loadPayList() {
	$('#paydg').datagrid('load', {
		start: $('#paytoolbar_date_start').datebox('getValue'),
		end: $('#paytoolbar_date_end').datebox('getValue'),
		branchID: $('#payTBBranchCB').combobox('getValue'),
		accountID: $('#payTBPaymentAccountCB').combobox('getValue'),
	    ts: new Date()
	});
}
        
/***********************	EasyUI DataGrid Column Formatter	***************************/
function customerWxFormatter(value, row, index) {
	if(row.customer) {
		return row.customer.wx;
	}
	return "";
}
function customerNameFormatter(value, row, index) {
	if(row.customer) {
		return row.customer.name;
	}
	return "";
}
function customerTelFormatter(value, row, index) {
	if(row.customer) {
		return row.customer.tel;
	}
	return "";
}
function branchNameFormatter(value, row, index) {
	if(row.branch) {
		return row.branch.branchName;
	}
	return "";
}
function paymentAccountFormatter(value, row, index) {
	if(row.account) {
		return row.account.paymentAccountName;
	}
	return "";
}
function paymentAccountBranchNameFormatter(value, row, index) {
	if(row.account) {
		if(row.account.branch) {
			return row.account.branch.branchName;
		}
	}
	return "";
}
function SDGMemoFormatter(value, row, index) {
	var memo = row.memo;
	var deliverAddress = row.deliverAddress;
	if(deliverAddress) {
		if(memo) {
			memo += "<br>送货地址：" + deliverAddress;
		} else {
			memo = "送货地址：" + deliverAddress;
		}
	}
	return memo;
}
function lotteryHistoryGDLotteryNoFormatter(value, row, index) {
	if(row.prizedLottery) {
		return row.prizedLottery.lotteryNo;
	}
	return "";
}
function lotteryHistoryGDCustomerWxFormatter(value, row, index) {
	if(row.prizedLottery) {
		return row.prizedLottery.customerWx;
	}
	return "";
}
function lotteryHistoryGDCustomerTelFormatter(value, row, index) {
	if(row.prizedLottery) {
		return row.prizedLottery.customerTel;
	}
	return "";
}
function lotteryHistoryGDIsFinishFormatter(value, row, index) {
	if(row.prizedLottery) {
		return row.prizedLottery.isFinish ? "是" : "<font color='red'>否</font>";
	}
	return "";
}
function lotteryHistoryGDFinishTimeFormatter(value, row, index) {
	if(row.prizedLottery && row.prizedLottery.finishTime) {
		return row.prizedLottery.finishTime.substring(0, 19);
	}
	return "";
}