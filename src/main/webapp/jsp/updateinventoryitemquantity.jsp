<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pockorder.constant.*,com.pockorder.domain.*" %>
<% InventoryItem ii = (InventoryItem) request.getAttribute("inventoryItem"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改数量</title>
</head>
<body>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="keywords" content="jquery,ui,easy,easyui,web">
    <meta name="description" content="easyui help you build your web page easily!">
    <meta name="viewport" content="width=device-width" />
    <title>蛋糕小姐蛋糕店</title>
</head>
    <body>
        <div style="text-align:center;">
        <% if(ii == null) { %>
        无此物品
        <% } else { %>
        <form method="POST" action="updateinventoryitemquantity.do" style="font-size:20pt">
        	<input type="hidden" name="inventoryItemId" value="<%= ii.getInventoryItemId() %>"/>
            <table style="align:center;margin:0 auto;text-align:left;">
                <tr><td>名称：</td><td><%= ii.getName() %></td></tr>
                <tr><td>规格：</td><td><%= ii.getUnit() %></td></tr>
                <tr><td>备注：</td><td><%= ii.getMemo() %></td></tr>
                <tr><td>数量：</td><td><%= ii.getQuantity() %></td></tr>
            </table>
            <br/><br/>新数量：<br/><input type="text" name="quantity" style="font-size:15pt"><br/><input style="font-size:20pt" type="submit" value="确认">
        </form>
        <% } %>
        </div>
</body>
</html>