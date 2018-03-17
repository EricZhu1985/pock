<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pockorder.constant.*,com.pockorder.domain.*" %>
<% int rights = ((User) session.getAttribute(SessionConst.USER)).getRights();
	boolean CANPRINT = session.getAttribute(SessionConst.BROWSER).equals("IE");%>
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
        .other_div div{
        	padding-left: 5px;
        	padding-top: 3px;
        	font-size: 15px;
        }
    </style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript" src="js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/datagrid-groupview.js"></script>
    <script type="text/javascript" src="js/LodopFuncs.js"></script>
    <script type="text/javascript" src="js/blacklist.js"></script>
    <script type="text/javascript" src="js/keywordwarning.js"></script>
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
	var CANPRINT = <%= CANPRINT %>;
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

        <% if(rights == 0) { %>
		$('#paydg').datagrid('options').url = 'payment/statement';
		loadPayList();
        <% } %>
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
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="bonusPointDlg()">积分</a>
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
			
			<div id="finishdlg" class="easyui-dialog" style="width:440px;height:280px;padding:10px 20px"
					closed="true" title="取货&支付">
					
				<form id="finishOrderFm" method="post">
					<input type="hidden" id="finishOrderFmOrderID" name="orderID"/>
					<table cellpadding="5" cellspacing="0" style="width:100%" class="finishOrderFmTb">
						<tr>
							<td id="finishOrderContentTD" colspan="4"></td>
						</tr>
						<tr>
							<td style="width:60px">总额：</td>
							<td id="finishOrderTotalTD"><input id="finishOrderFmPrice" name="price" class="easyui-numberbox" style="width:50px" data-options="required:true,min:0,editable:false"></td>
							<td style="width:60px">已付款：</td>
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
						<tr>
							<td>当前积分：</td>
							<td colspan="3" id="finishOrderMemberPointTd"></td>
						</tr>
						<tr>
							<td>会员号：</td>
							<td><input id="finishOrderMemberTel" name="memberTel" style="width:100px"><img class="easyui-linkbutton" iconCls="icon-search" plain="true" href="#" onclick="payOrderFormMemberInfo();"/></td>
							<td>积分金额：</td>
							<td><input id="finishOrderMemberPoint" name="bonusPoint" style="width:60px"></td>
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
			
			<div id="otherpaydlg" class="easyui-dialog" style="width:440px;height:280px;padding:10px 20px"
					closed="true" title="支付">
				<form id="otherPayFm" method="post" novalidate>
					<table cellpadding="5" cellspacing="0" style="width:100%" class="finishOrderFmTb">
						<tr>
							<td style="width:80px">支付：</td>
							<td colspan="3">
								<input id="otherPayFmPaid" name="paid" class="easyui-numberbox" value="0" style="width:50px" data-options="required:true,validType:'number',onChange: otherPayFmPaidOnChange">
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
							<td>会员号：</td>
							<td><input id="otherPayMemberTel" class="easyui-textbox" name="tel" style="width:100px"><img class="easyui-linkbutton" iconCls="icon-search" plain="true" href="#" onclick="otherPayFormMemberInfo();"/></td>
							<td>积分金额：</td>
							<td><input id="otherPayFmBonusPoint" class="easyui-textbox" name="bonusPoint" style="width:60px" data-options="validType:'number'"></td>
						</tr>
						<tr>
							<td>当前积分：</td>
							<td colspan="3" id="otherPayMemberPointTd"></td>
						</tr>
						<tr>
							<td style="width:120px">备注：</td>
							<td colspan="3">
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
			
			<div id="bonusPointDlg" class="easyui-dialog" style="width:440px;height:190px;padding:10px 20px"
					closed="true" title="积分">
				<form id="bonusPointFm" method="post">
					<table cellpadding="5" cellspacing="0" style="width:100%" class="finishOrderFmTb">
						<tr>
							<td>会员号：</td>
							<td><input id="bonusPointFmTel" class="easyui-textbox" name="tel" style="width:100px" required="true"><img class="easyui-linkbutton" iconCls="icon-search" plain="true" href="#" onclick="bonusPointFormMemberInfo();"/></td>
							<td>积分：</td>
							<td><input id="bonusPointFmBonusPoint" class="easyui-textbox" name="bonusPoint" style="width:60px" data-options="required:true,validType:'number'"></td>
						</tr>
						<tr>
							<td>当前积分：</td>
							<td colspan="3" id="bonusPointFmTd"></td>
						</tr>
						<tr>
							<td>备注：</td>
							<td colspan="3"><input name="content" class="easyui-textbox" required="true"></td>
						</tr>
					</table>
					<div style="margin-top:10px;text-align:center">
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveBonusPoint()" style="width:90px">支付</a>
						<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-cancel" onclick="cancelBonusPoint()" style="width:90px">取消</a>
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
						<td><input id="addFormTime" name="orderTime" class="easyui-textbox" required="true" value="00:00:00"></td>
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
						<td><input id="addFormRepeatCount" name="repeatCount" class="easyui-textbox"></td>
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
						<td><input name="weijujuNo" class="easyui-textbox"></td>
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
	<div title="关键字提醒">
		<table id="kwdg" class="easyui-datagrid" style="height:500px;width:100%"
			url="keywordwarning/query"
			toolbar="#kwtoolbar" pagination="true" pageSize="10"
			rownumbers="true" fitColumns="true" singleSelect="true"
			selectOnCheck="false" checkOnSelect="false">
			<thead>
				<tr>
					<th data-options="field:'ck',checkbox:true"></th>
					<th field="keyword" width="50">关键字</th>
					<th field="amount" width="50">数量</th>
					<th field="memo" width="50">备注</th>
                    <th data-options="field:'keywordWarningID',width:100,formatter:formatKWOperation">操作</th>
				</tr>
			</thead>
		</table>
		<div id="kwtoolbar">
			<div style="padding: 3px">
				关键字: <input id="query_kw_keyword" class="easyui-textbox" style="width:110px">
				备注: <input id="query_kw_memo" class="easyui-textbox" style="width:110px">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onClick="javascript:loadKwList()">查询</a>
			</div>
			<div>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newKeyword()">增加</a>
				<!-- a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteKeyword()">删除</a> -->
			</div>
		</div>
	
		<div id="kwdlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
				closed="true" buttons="#kwdlg-buttons">
			<div class="ftitle">增加关键词</div>
			<form id="kwfm" method="post" novalidate>
				<div class="fitem">
					<label>关键词：</label>
					<input name="keyword" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>数量：</label>
					<input name="amount" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>备注：</label>
					<input name="memo" class="easyui-textbox" data-options="multiline:true" style="height:50px;">
				</div>
			</form>
		</div>
		<div id="kwdlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveKeyword()" style="width:90px">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#kwdlg').dialog('close')" style="width:90px">取消</a>
		</div>
		<div id="kwupdatedlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
				closed="true" buttons="#kwupdatedlg-buttons">
			<div class="ftitle">修改关键词</div>
			<form id="kwupdatefm" method="post" novalidate>
				<input type="hidden" id="keywordWarningID" name="keywordWarningID"/>
				<div class="fitem">
					<label>关键词：</label>
					<input name="keyword" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>数量：</label>
					<input name="amount" class="easyui-textbox" required="true">
				</div>
				<div class="fitem">
					<label>备注：</label>
					<input name="memo" class="easyui-textbox" data-options="multiline:true" style="height:50px;">
				</div>
			</form>
		</div>
		<div id="kwupdatedlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="updateKeyword()" style="width:90px">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#kwupdatedlg').dialog('close')" style="width:90px">取消</a>
		</div>
		<div id="kwaddamountdlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
				closed="true" buttons="#kwaddamountdlg-buttons">
			<div class="ftitle">修改关键词</div>
			<form id="kwaddamountfm" method="post" novalidate>
				<input type="hidden" id="keywordWarningID" name="keywordWarningID"/>
				<div class="fitem">
					<label>关键词：</label>
					<input name="keyword" class="easyui-textbox" required="true" editable="false">
				</div>
				<div class="fitem">
					<label>补充数量：</label>
					<input id="kwaddamountfm_addAmount" name="addAmount" class="easyui-textbox" required="true">
				</div>
			</form>
		</div>
		<div id="kwaddamountdlg-buttons">
			<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="addKwAmount()" style="width:90px">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#kwaddamountdlg').dialog('close')" style="width:90px">取消</a>
		</div>
    </div>
	<div title="其它">
		<div class="other_div" style="height:500px;">
			<div>四磅蛋糕568元 方形4磅（23*23）</div>
			<div>五磅蛋糕728元 方形5磅（26*26）</div>
			<div>六磅蛋糕828元 方形6磅（28.5*28.5）</div>
			<div>七磅蛋糕980元 方形7磅（27*35）</div>
			<div>八磅蛋糕1118元 方形8磅（30*36）</div>
			<div>娃娃4磅双层是588元</div>
			<div>&nbsp;</div>
			<div>数码打印圆形1磅+20元 直径13cm</div>
			<div>数码打印圆形1.5磅+20元 直径16cm</div>
			<div>                    奖状方形 长16cm</div>
			<div>数码打印圆形2磅+25元 直径18cm</div>
			<div>                    方形13cm*17cm</div>
			<div>手机方形蛋糕2磅 13*23 高21.5cm</div>
			<div>数码打印圆形3磅+30元 直径 21</div>
			<div>数码蜘蛛侠：1磅高7cm，1.5磅8.5cm，2磅9.5cm</div>
			<div>立体足球蛋糕: 1磅198元，1.5磅283元，2磅358元</div>
			<div>简单翻糖蛋糕：1磅 500元  </div>
			<div>翻糖托马斯蛋糕：6寸：700元；8寸：1300元；双层6寸+8寸：2000元</div>
			<div>*优惠：一万元以上的订单可以打九折</div>
			<div>*甜品桌3000元起订</div>
			<div>甜品桌:
			<table border="1" cellspacing="0" style="font-size:15px">
				<tr>
					<th>名称</<th><th>价格</th>
					<th>名称</<th><th>价格</th>
				</tr>
				<tr>
					<td>插牌cupcake</td><td>25元/个</td>
					<td>谷物布丁</td><td>30元/个</td>
				</tr>
				<tr>
					<td>翻糖cupcake</td><td>35元/个</td>
					<td>翻糖饼干</td><td>30元/个</td>
				</tr>
				<tr>
					<td>大布丁</td><td>30元/个</td>
					<td>双层翻糖蛋糕</td><td>2000元/个</td>
				</tr>
				<tr>
					<td>曲奇一瓶</td><td>180元/瓶</td>
					<td>6寸裸蛋糕</td><td>500元/个</td>
				</tr>
				<tr>
					<td>水果挞</td><td>18元/个</td>
					<td>水果车轮泡芙</td><td>238元/个</td>
				</tr>
				<tr>
					<td>场地布置费</td><td>500元</td>
					<td>&nbsp;</td><td>&nbsp;</td>
				</tr>
			</table>
			</div>
			<div>
			账号：
			<table border="1" cellspacing="0" style="font-size:15px">
				<tr>
					<th>名称</<th><th>账号</th><th>密码</th>
				</tr>
				<tr>
					<td>美团</td><td>pieceofcake</td><td>pock2013</td>
				</tr>
				<tr>
					<td>大众点评</td><td>450923</td><td>pock2013</td>
				</tr>
				<tr>
					<td>糯米网</td><td>fspock@y.com</td><td>pock2013</td>
				</tr>
			</table>
			
			</div>
        </div>
    </div>
    </div>
	<form id="ff" method="post"></form>
	<div id="page1" style="width: 160px; padding: 40px 10px;">
		<div class="ul1">
            <div><span id="jatoolsOrderNoSpan"></span>&nbsp;&nbsp;&nbsp;<span id="jatoolsOrderTimeSpan"></span>&nbsp;&nbsp;&nbsp;<span id="jatoolsBranchNameSpan"></span></div>
			<div><span id="jatoolsOrderContentSpan"></span></div>
		</div>
	</div>
	<% if(CANPRINT) { %>
	<!-- 插入打印控件 -->
	<OBJECT  ID="jatoolsPrinter" CLASSID="CLSID:B43D3361-D075-4BE2-87FE-057188254255"
                  codebase="jatoolsPrinter.cab#version=8,6,0,0"></OBJECT>  
	<% } %>
</body>
</html>