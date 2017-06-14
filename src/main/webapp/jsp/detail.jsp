<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pockorder.constant.*,com.pockorder.domain.*" %>
<!DOCTYPE html>
<html>
    <head>
		<title>订单列表</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script language="javascript" src="../js/LodopFuncs.js"></script>
        
        <script type="text/javascript">
            var LODOP;
            function CheckIsInstall() {	 
                CreateOneFormPage();
                //LODOP.PREVIEW();
                LODOP.PRINTA();	
            }; 
            function CreateOneFormPage(){
                LODOP=getLodop();  
                LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
                //LODOP.SET_PRINT_STYLE("FontSize",18);
                //LODOP.SET_PRINT_STYLE("Bold",1);
                //LODOP.ADD_PRINT_TEXT(50,231,260,39,"打印页面部分内容");
               	LODOP.SET_PRINT_PAGESIZE(1,450,1200,"Test");
                //LODOP.ADD_PRINT_IMAGE(0,0,200,200,"<img border='0' src='http://2.pocktest.sinaapp.com/sql/order/logo2.jpg' />");
                //LODOP.SET_PRINT_STYLEA(0,"Stretch",2);//按原图比例(不变形)缩放模式
                LODOP.ADD_PRINT_HTM(0,0,450,1200,document.getElementById("form1").innerHTML);
            };	
        </script>
    </head>
    
	<body>
       <script language="javascript" src="../js/LodopFuncs.js"></script>
<object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
       <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
</object>
    <input type="button" value="打印" onclick="CheckIsInstall();" id="pBtn"/>
    <% Order order = (Order) request.getAttribute("order"); %>
    <div id="form1" style="width:200px;layout:fixed;">
        Piece Of Cake 蛋糕小姐<br/>
        <span style="font-size:9pt"><?=date('Y-m-d H:i:s',time());?></span><br/><br/>
    		取货日期： <%= order.getOrderDate() %><br /><br />
    		取货时间：<%= order.getOrderTime() %><br /><br />
        蛋糕：<div style="width:150px"><%= order.getContent() %></div><br/>
    		价格：<%= order.getPrice() %><br /><br />
    		已收款：<%= order.getPaid() %><br /><br /> 
    		客户名：<%= order.getCustomerName() %><br /><br />
    		客户电话：<%= order.getCustomerTel() %><br /><br /><br /><br /><br /><br /><br /><br />
    </div>
    </body>
</html>