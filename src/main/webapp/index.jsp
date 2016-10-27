<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pockorder.constant.*,com.pockorder.domain.*" %>
<% int rights = ((User) session.getAttribute(SessionConst.USER)).getRights(); %>
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
    <script type="text/javascript" src="js/datagrid-groupview.js"></script>
    <script type="text/javascript" src="js/LodopFuncs.js"></script>
    <script type="text/javascript" src="js/blacklist.js"></script>
    <script type="text/javascript" src="js/sellablelist.js"></script>
    <script type="text/javascript" src="js/lottery.js"></script>
    <script type="text/javascript" src="js/orderlist.js"></script>
	<script type="text/javascript">
	/**
	 * 刷新订单列表
	 */
	function loadList() {
        setTimeout("dgInit()", 500 );
	}
    
	var USER_RIGHTS = '';
	var CANPRINT = '';
	
	$(function(){

		$.extend($.fn.validatebox.defaults.rules, {
		    validateDay: {
		        validator: function(value, param){
					var selBranchId = $(param[0]).combobox('getValue');
					var datestr = $(param[1]).datebox('getValue');
					var day = strToDate(datestr).getDay();
					if(day == 2 && selBranchId == 2) {
						return false;
					}
					return true;
		        },
		        message: '提示：雅居乐店周2休息。'
		    },
		    addOrderFormValidatePayType: {
		        validator: function(value, param){
		        	if($(param[0]).numberbox('getValue') > 0 && !$(param[1]).combobox('getValue')) {
		        		return false;
		        	}
					return true;
		        },
		        message: '请选择'
		    }
		});

		$('#dg').datagrid('options').url = 'order/indexlist';
		loadList();
		$('#paydg').datagrid('options').url = 'payment/statement';
		loadPayList();
		var curr_time = new Date();
		var strDate = curr_time.getFullYear()+"-";
		strDate += curr_time.getMonth()+1+"-";
		strDate += curr_time.getDate();

		var oneMonthAgo = new Date();
		oneMonthAgo.setMonth(oneMonthAgo.getMonth()-1);
		var oneMonthAgoStr = oneMonthAgo.getFullYear() + "-" + (oneMonthAgo.getMonth() + 1) + "-" + oneMonthAgo.getDate();
		$('#order_date_start').datebox('setValue', strDate);
		$('#paytoolbar_date_start').datebox('setValue', oneMonthAgoStr);
		$('#paytoolbar_date_end').datebox('setValue', strDate);
		$('#tt').tabs('disableTab', '修改订单');
		$('#editOrderForm').form({
			onLoadSuccess:function(data){    
				if(data.orderNo <= 0) {
					document.getElementById("editFormOrderNoFlag").checked = true;
	            } else {
					document.getElementById("editFormOrderNoFlag").checked = false;
	            }
			}  
		});   

	});
	</script>
    <script type="text/javascript" src="js/index.js"></script>
</head>
<body>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
    <div id="tt" class="easyui-tabs" style="width:90%;height:auto">
		<div title="订单">
			<table id="dg" class="easyui-datagrid" style="height:500px;width:100%"
					data-options="
						collapsible:true,
						rownumbers:false,
						fitColumns:true,
						nowrap:false,
						view:groupview,
						toolbar:'#tb',
						footer:'#ft',
						singleSelect:false,
						groupField:'orderDate',
						selectOnCheck: false,
						checkOnSelect: false,
						groupFormatter:function(value,rows){
							return value + ': ' + rows.length + '个订单';
						},
						rowStyler: function(index,row){
							if (row.finished == 1){
								return 'background-color:yellow;';
							} else if(row.deliverAddress) {
								return 'background-color:#FF00FF;';
                            } else if(!row.customer || (!row.customer.name && !row.customer.wx && !row.customer.tel)) {
								return 'background-color: #CCFFFF;';
                            }
						},
						onLoadSuccess: function(data) {
							if(data.totalPrice)
								document.getElementById('totalPriceSpan').innerHTML = data.totalPrice;
							if(data.weijujuNo)
								document.getElementById('weijujuLastNoSpan').innerHTML = data.weijujuNo;
						}
					">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'orderNo',width:50"></th>
						<th data-options="field:'orderTime',width:80,styler:remarkCellStyler">取货时间</th>
                        <th data-options="field:'content',width:450,nowrap:false">蛋糕</th>
						<th data-options="field:'price',width:60">价格</th>
						<th data-options="field:'paid',width:60">订金</th>
						<th data-options="field:'customer_wx',width:120" formatter="customerWxFormatter">客户微信</th>
						<th data-options="field:'customer_name',width:60" formatter="customerNameFormatter">客户名</th>
						<th data-options="field:'customer_tel',width:140" formatter="customerTelFormatter">客户电话</th>
						<th data-options="field:'memo',width:200" formatter="SDGMemoFormatter">备注</th>
						<th data-options="field:'branch_name',width:120,styler:branchNameCellStyler" formatter="branchNameFormatter">分店</th>
						<th data-options="field:'recorder',width:40">记录人</th>
						<th data-options="field:'orderID',width:200,formatter:formatOperation">操作</th>
					</tr>
				</thead>
			</table>
			<div id="tb" style="padding:2px 5px;">
				<div>
				日期: <input id="order_date_start" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:110px">
				至: <input id="order_date_end" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:110px">
				客户电话: <input id="query_customer_tel" class="easyui-textbox" style="width:110px">
				分店: <select id="query_branch_id" class="easyui-combobox" panelHeight="auto" style="width:100px"
				 data-options="
				 	editable:false,
				 	url: 'branch/searchcomboboxdata',
				 	valueField: 'branchID',
        			textField: 'branchName',
					onLoadSuccess: branchCBLoadSuccess">                  
				</select>
				已取: <select id="query_order_finished" class="easyui-combobox" panelHeight="auto" style="width:100px" data-options="editable:false">
					<option value="-1">全部</option>
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
				排序: <select id="order_by_col" class="easyui-combobox" panelHeight="auto" style="width:100px">
					<option value="ORDER_TIME">取货时间</option>
					<option value="ORDER_RECORD_TIME">记录时间</option>
				</select>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="javascript:loadList()">查询</a>
				</div>
				<div>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onClick="javascript:newOrder()">增加</a>
                <% if(rights == 0) { %>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="deliverOrder(1)">移到分店</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="deliverOrder(0)">取消移动</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="otherPayDlg()">支付</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-print" onclick="printLabel()">打印标签</a>
                <% } %>
				</div>
			</div>
			<div id="ft" style="padding:2px 5px;">
                <% if(rights == 0) { %>
				微聚聚订单：<span id="weijujuLastNoSpan" style="margin-right:20px"></span>总额：<span id="totalPriceSpan"></span>
                <% } else { %>
                <span id="weijujuLastNoSpan" style="margin-right:20px;display:none"></span>总额：<span id="totalPriceSpan"></span>
    			<% } %>
			</div>
			
			<div id="finishdlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" title="取货&支付">
					
				<form id="finishOrderFm" method="post">
					<input type="hidden" id="finishOrderFmOrderID" name="orderID"/>
					<table cellpadding="5" cellspacing="0" style="width:100%" class="finishOrderFmTb">
						<tr>
							<td id="finishOrderContentTD" colspan="4"></td>
						</tr>
						<tr>
							<td style="width:50px">总额：</td>
							<td id="finishOrderTotalTD"><input id="finishOrderFmPrice" name="price" class="easyui-numberbox" style="width:50px" data-options="required:true,min:0,editable:false"></td>
							<td style="width:50px">已付款：</td>
							<td id="finishOrderPaidTD"></td>
						</tr>
						<tr>
							<td>待付款：</td>
							<td colspan="3">
								<input id="finishOrderFmPaid" name="paid" class="easyui-numberbox" value="0" style="width:50px" data-options="required:true,validType:'number',min:0">
								<select name="paymentAccountID" class="easyui-combobox" panelHeight="auto" style="width:80px"
		                            	data-options="
										 	editable:false,
										 	url: 'payment/getuseraccount',
										 	valueField: 'paymentAccountID',
						        			textField: 'paymentAccountName',
											onLoadSuccess: paymentAccountCBLoadSuccess">
		                        </select>
		                    </td>
						</tr>
					</table>
					<div>
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="payAndFinishOrder()" style="width:90px">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-cancel" onclick="cancelPayAndFinishOrder()" style="width:90px">取消</a>
					</div>		
				</form>
			</div>
			<div id="paymentdlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" title="支付订单">
				<form id="payOrderFm" method="post">
					<input type="hidden" id="payOrderFmOrderID" name="orderID"/>
					<table cellpadding="5" cellspacing="0" style="width:100%" class="payOrderFmTb">
						<tr>
							<td>支付记录：</td>
							<td id="payOrderContentTD" colspan="3"></td>
						</tr>
						<tr>
							<td style="width:80px">总额：</td>
							<td id="payOrderFmPriceTD"></td>
							<td style="width:50px">已付款：</td>
							<td id="payOrderPaidTD"></td>
						</tr>
						<tr>
							<td>支付：</td>
							<td colspan="3">
								<input id="payOrderFmPaid" name="paid" class="easyui-numberbox" value="0" style="width:50px" data-options="required:true,validType:'number',min:0">
								<select name="paymentAccountID" class="easyui-combobox" panelHeight="auto" style="width:80px"
		                            	data-options="
										 	editable:false,
										 	url: 'payment/getuseraccount',
										 	valueField: 'paymentAccountID',
						        			textField: 'paymentAccountName',
											onLoadSuccess: paymentAccountCBLoadSuccess">
		                        </select>
		                    </td>
						</tr>
					</table>
					<div>
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="payOrder()" style="width:90px">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-cancel" onclick="cancelPayOrder()" style="width:90px">取消</a>
					</div>		
				</form>
			</div>
			
			<div id="otherpaydlg" class="easyui-dialog" style="width:400px;height:190px;padding:10px 20px"
					closed="true" title="支付">
				<form id="otherPayFm" method="post" novalidate>
					<table cellpadding="5" cellspacing="0" style="width:100%" class="finishOrderFmTb">
						<tr>
							<td>支付：</td>
							<td>
								<input name="paid" class="easyui-numberbox" value="0" style="width:50px" data-options="required:true,validType:'number'">
								<select name="paymentAccountID" class="easyui-combobox" panelHeight="auto" style="width:80px"
		                            	data-options="
		                            		required:true,
										 	editable:false,
										 	url: 'payment/getuseraccount',
										 	valueField: 'paymentAccountID',
						        			textField: 'paymentAccountName',
											onLoadSuccess: paymentAccountCBLoadSuccess">
		                        </select>
		                    </td>
						</tr>
						<tr>
							<td style="width:120px">备注：</td>
							<td>
								<input name="memo" class="easyui-textbox" data-options="multiline:true" style="height:50px;width:100%">
							</td>
						</tr>
					</table>
					<div style="margin-top:10px;text-align:center">
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="payOther()" style="width:90px">支付</a>
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-cancel" onclick="cancelPayOther()" style="width:90px">取消</a>
					</div>		
				</form>
			</div>
        </div>
		<div title="增加订单">
			<form id="addOrderForm" method="post" style="width:700;height:500;padding:10px 20px">
				<table cellpadding="5">
					<tr>
						<td style="color:red">订单日期：</td>
						<td><input id="addFormDate" name="orderDate" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" required="true">
							<input type="button" value="今天" onclick="today('#addFormDate')"/>
							<input type="button" value="明天" onclick="tomorrow('#addFormDate')"/></td>
						<td style="color:red">取货时间：</td>
						<td><input id="addFormTime" name="orderTime" class="easyui-textbox" required="true"></td>
					</tr>
					<tr>
						<td style="color:red">蛋糕：</td>
						<td colspan="3"><input id="addFormContent" name="content" class="easyui-textbox" data-options="multiline:true" style="height:50px;width:100%"></td>
					</tr>
					<tr>
						<td>不编号：</td>
						<td><input id="addFormOrderNoFlag" name="orderNoFlag" type="checkbox" value="no"></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="color:red">价格：</td>
						<td><input id="addFormPrice" name="price" class="easyui-numberbox" data-options="required:true,min:0" value="0"></td>
						<td style="color:red">订金：</td>
						<td><input id="addFormPaid" name="paid" class="easyui-numberbox" value="0" style="width:50px" data-options="required:true,min:0">
						<select id="addFormPaymentAccount" name="paymentAccountID" class="easyui-combobox" panelHeight="auto" style="width:80px"
                            	data-options="
								 	editable:false,
								 	url: 'payment/getuseraccount',
								 	valueField: 'paymentAccountID',
				        			textField: 'paymentAccountName',
									onLoadSuccess: paymentAccountCBLoadSuccess">
                            </select></td>
					</tr>
					<tr>
						<td>客户名：</td>
						<td><input id="addFormCustomerName" name="customerName" class="easyui-textbox"></td>
						<td style="font-color:red">客户电话：</td>
						<td><input id="addFormTel" name="customerTel" class="easyui-textbox"></td>
					</tr>
					<tr>
						<td>客户微信：</td>
						<td><input name="customerWx" class="easyui-textbox"></td>
						<td style="color:red">记录人：</td>
						<td><input name="recorder" class="easyui-textbox"></td>
					</tr>
					<tr>
						<td>分店：</td>
						<td>
                            <select id="addFormBranchId" name="branchID" class="easyui-combobox" panelHeight="auto" style="width:100px"
                            	data-options="
								 	editable:false,
								 	url: 'branch/comboboxdata',
								 	valueField: 'branchID',
				        			textField: 'branchName',
								 	validType:'validateDay[addFormBranchId, addFormDateId]',
									onLoadSuccess: branchCBLoadSuccess">
                            </select></td>
						<td>微聚聚订单号：</td>
						<td><input id="addFormWeijujuNo" name="weijujuNo" class="easyui-textbox"></td>
					</tr>
					<tr>
						<td>备注：</td>
						<td colspan="3"><input name="memo" class="easyui-textbox" data-options="multiline:true" style="height:50px;width:100%"></td>
					</tr>
					<tr>
						<td>多条插入：</td>
						<td><input id="addFormRepeatCount" name="repeat_count" class="easyui-textbox"></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>微聚聚订单：</td>
						<td colspan="3"><textarea id="weijujuOrderText" style="height:40px;"></textarea><!--input id="weijujuOrderText" class="easyui-textbox" data-options="multiline:true" style="height:40px;"--><input type="button" value="自动填充" onclick="weijuju()"></td>
					</tr>
				</table>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveOrder()" style="width:90px">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-cancel" onclick="cancelAdd()" style="width:90px">取消</a>
				</div>
			</form>
		</div>
		<div title="修改订单">
			<form id="editOrderForm" method="post" style="width:700;height:500;padding:10px 20px">
				<table cellpadding="5">
					<tr>
						<td>订单编号：</td>
						<td colspan="3"><input name="orderID" class="easyui-textbox" readonly required="true"></td>
					</tr>
					<tr>
						<td>订单日期：</td>
						<td><input id="editFormDate" name="orderDate" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" required="true">
							<input type="button" value="今天" onclick="today('#editFormDate')"/>
							<input type="button" value="明天" onclick="tomorrow('#editFormDate')"/></td>
						<td>取货时间：</td>
						<td><input id="editFormTime" name="orderTime" class="easyui-textbox"></td>
					</tr>
					<tr>
						<td>蛋糕：</td>
						<td colspan="3"><input name="content" class="easyui-textbox" data-options="multiline:true" style="height:60px;width:100%"></td>
					</tr>
					<tr>
						<td>不编号：</td>
						<td><input id="editFormOrderNoFlag" name="orderNoFlag" type="checkbox" value="no"></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td>价格：</td>
						<td><input name="price" class="easyui-numberbox" value="0" data-options="required:true,min:0"></td>
						<td>订金：</td>
						<td><input name="paid" class="easyui-textbox" value="0" readonly></td>
					</tr>
					<tr>
						<td>客户名：</td>
						<td><input id="editFormCustomerName" name="customerName" class="easyui-textbox"></td>
						<td>客户电话：</td>
						<td><input id="editFormCustomerTel" name="customerTel" class="easyui-textbox"></td>
					</tr>
					<tr>
						<td>客户微信：</td>
						<td><input id="editFormCustomerWx" name="customerWx" class="easyui-textbox"></td>
						<td>记录人：</td>
						<td><input name="recorder" class="easyui-textbox"></td>
					</tr>
					<tr>
						<td>备注：</td>
						<td colspan="3"><input name="memo" class="easyui-textbox" data-options="multiline:true" style="height:60px;width:100%"></td>
					</tr>
					<tr>
						<td>分店：</td>
						<td>
                            <select id="editFormBranchId" name="branchID" class="easyui-combobox" panelHeight="auto" style="width:100px"
                            	data-options="
								 	editable:false,
								 	url: 'branch/comboboxdata',
								 	valueField: 'branchID',
				        			textField: 'branchName',
								 	validType:'validateDay[addFormBranchId, addFormDateId]',
									onLoadSuccess: branchCBLoadSuccess">
                            </select>
                        </td>
						<td>微聚聚订单号：</td>
						<td><input name="weijuju_no" class="easyui-textbox"></td>
					</tr>
				</table>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="editOrder()" style="width:90px">保存</a>
					<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-cancel" onclick="cancelEdit()" style="width:90px">取消</a>
				</div>		
			</form>						
		</div>
         <% if(rights == 0) { %>
		<div title="黑名单管理">
			
			<table id="bldg" class="easyui-datagrid" style="height:500px;width:100%"
				url="blacklist/query"
				toolbar="#bltoolbar" pagination="true" pageSize="10"
				rownumbers="true" fitColumns="true" singleSelect="true">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th field="eventDate" width="50">日期</th>
						<th field="customerName" width="50">用户名</th>
						<th field="customerTel" width="50">电话</th>
						<th field="content" width="50">内容</th>
					</tr>
				</thead>
			</table>
			<div id="bltoolbar">
				<div style="padding: 3px">
					客户电话: <input id="query_bl_customer_tel" class="easyui-textbox" style="width:110px">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="javascript:loadBlList()">查询</a>
				</div>
				<div>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newBlUser()">增加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteBlUser()">删除</a>
				</div>
			</div>
		
			<div id="bldlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
					closed="true" buttons="#bldlg-buttons">
				<div class="ftitle">增加黑名单</div>
				<form id="blfm" method="post" novalidate>
					<div class="fitem">
						<label>日期：</label>
						<input name="eventDate" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" required="true">
					</div>
					<div class="fitem">
						<label>用户名：</label>
						<input name="customerName" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label>电话：</label>
						<input name="customerTel" class="easyui-textbox" required="true">
					</div>
					<div class="fitem">
						<label>内容：</label>
						<input name="content" class="easyui-textbox">
					</div>
				</form>
			</div>
			<div id="bldlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBlUser()" style="width:90px">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bldlg').dialog('close')" style="width:90px">取消</a>
			</div>
        </div>
        <% } %>
        <% if(rights == 0) { %>
		<div title="抽奖管理">
            <div class="easyui-tabs" style="width:100%;height:500">
                <div title="抽奖人">
                    <table id="cjgl" class="easyui-datagrid" style="height:100%;width:100%"
                        url="lottery/currentlist"
                        toolbar="#cjtoolbar" pagination="true"
                        rownumbers="true" fitColumns="true" singleSelect="true" data-options="
                                onLoadSuccess: function(data) {
                                }">
                        <thead>
                            <tr>
                                <th field="lotteryNo" width="50">抽奖号</th>
                                <th field="customerWx" width="150">客户微信</th>
                                <th field="customerTel" width="150">客户电话</th>
                                <th data-options="field:'lotteryID',width:100,formatter:formatCJOperation">操作</th>
                            </tr>
                        </thead>
                    </table>
                    <div id="cjtoolbar">
                        <form id="addCJForm" method="post" style="">
                            客户微信: <input name="customerWx" class="easyui-textbox" style="width:110px" required="true">
                            客户电话: <input name="customerTel" class="easyui-textbox" style="width:110px">
                            合格: <select class="easyui-combobox" name="isValid" style="width:50px">
                                    <option value="true">是</option>
                                    <option value="false">否</option>
                                </select>
                            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onClick="javascript:addCJ()">增加</a>
                            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onClick="javascript:cj()">抽奖</a>
                        </form>
                        <div>本期抽奖开始日期：<span id="cjStartDate"></span></div>
                    </div>
                    <div id="cjdlg" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px"
                            closed="true" buttons="#cjdlg-buttons">
                        <div class="ftitle">修改抽奖</div>
                        <form id="cjfm" method="post">
                            <div class="fitem">
                                <label>客户微信：</label>
                                <input name="lotteryID" type="hidden">
                                <input name="customerWx" class="easyui-textbox" required="true">
                            </div>
                            <div class="fitem">
                                <label>客户电话：</label>
                                <input name="customerTel" class="easyui-textbox">
                            </div>
                        </form>
                    </div>
                    <div id="cj2dlg" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px"
                            closed="true">
                        <div class="ftitle">开始抽奖</div>
                        <form id="cj2fm" method="post" novalidate>
                            <div class="fitem">
                                <label>密码：</label>
                                <input name="pwd" type="password">
                            </div>
                            <div>
                                <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="cjconfirm()" style="width:90px">保存</a>
                                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#cj2dlg').dialog('close')" style="width:90px">取消</a>
                            </div>
                        </form>
                    </div>
                    <div id="cjdlg-buttons">
                        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveCJ()" style="width:90px">保存</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#cjdlg').dialog('close')" style="width:90px">取消</a>
                    </div>
                </div>
                <div title="历史记录">
                    <table id="lotteryHistoryGD" class="easyui-datagrid" style="height:500;width:100%"
                        url="lottery/lotterytermlist" pagination="true"
                        rownumbers="true" fitColumns="true" singleSelect="true" data-options="
                                onLoadSuccess: function(data) {
                                }">
                        <thead>
                            <tr>
                                <th field="lotteryTermNo" width="50">编号</th>
                                <th field="lotteryStart" width="150">开始日期</th>
                                <th field="lotteryEnd" width="150">结束日期</th>
                                <th field="lotteryNo" width="150" formatter="lotteryHistoryGDLotteryNoFormatter">中奖号码</th>
                                <th field="customerWx" width="150" formatter="lotteryHistoryGDCustomerWxFormatter">中奖人</th>
                                <th field="customerTel" width="150" formatter="lotteryHistoryGDCustomerTelFormatter">联系电话</th>
                                <th field="isFinish" width="150" formatter="lotteryHistoryGDIsFinishFormatter">是否领奖</th>
                                <th field="finishTime" width="150" formatter="lotteryHistoryGDFinishTimeFormatter">领奖时间</th>
                                <th data-options="field:'lotteryID',width:100,formatter:formatLTOperation">操作</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>    
        </div>
    <% } %>
    
    <% if(rights == 0) { %>
		<div title="对账管理">
			<table id="paydg" class="easyui-datagrid" style="height:500px;width:100%"
			
				data-options="
						rownumbers: true,
						fitColumns:true,
						toolbar:'#paytoolbar',
						singleSelect:true,
						selectOnCheck: false,
						checkOnSelect: false,
						onLoadSuccess: payDGOnLoadSuccess">
				<thead>
					<tr>
						<th field="paymentDate" width="50">日期</th>
						<th field="paymentAcountID" width="50" formatter="paymentAccountFormatter">账号</th>
						<th field="amount" width="50">金额</th>
						<th field="branchID" width="50" formatter="paymentAccountBranchNameFormatter">分店</th>
						<th field="paymentID" width="50">操作</th>
					</tr>
				</thead>
			</table>
			<div id="paytoolbar">
				<div style="padding: 3px">
				日期: <input id="paytoolbar_date_start" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:110px">
				至: <input id="paytoolbar_date_end" class="easyui-datebox" data-options="formatter:myformatter,parser:myparser" style="width:110px">
					&nbsp;分店: <select id="payTBBranchCB" class="easyui-combobox" panelHeight="auto" style="width:100px"
					 data-options="
					 	editable:false,
					 	url: 'branch/searchcomboboxdata',
					 	valueField: 'branchID',
	        			textField: 'branchName',
						onLoadSuccess: branchCBLoadSuccess,
						onSelect: getPayDgCbAccount"></select>
					&nbsp;账号: <select id="payTBPaymentAccountCB" class="easyui-combobox" panelHeight="auto" style="width:80px"
                          	data-options="
						 	editable:false,
					 		url: 'payment/getbranchaccount?all=true',
						 	valueField: 'paymentAccountID',
		        			textField: 'paymentAccountName',
							onLoadSuccess: payTBPaymentAccountCBLoadSuccess">
                      </select>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="javascript:loadPayList()">查询</a>
				</div>
			</div>
        </div>
	<% } %>
    </div>
	<form id="ff" method="post"></form>
	<div id="page1" style="width: 160px; padding: 40px 10px;">
		<div class="ul1">
            <div><span id="jatoolsOrderNoSpan"></span>&nbsp;&nbsp;&nbsp;<span id="jatoolsOrderTimeSpan"></span>&nbsp;&nbsp;&nbsp;<span id="jatoolsBranchNameSpan"></span></div>
			<div><span id="jatoolsOrderContentSpan"></span></div>
		</div>
	</div>
    <?php if($canPrint) { ?>
	
	<!-- 插入打印控件 -->
	<OBJECT  ID="jatoolsPrinter" CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
                  codebase="jatoolsPrinter.cab#version=8,6,0,0"></OBJECT>  
    <?php } ?>
</body>
</html>