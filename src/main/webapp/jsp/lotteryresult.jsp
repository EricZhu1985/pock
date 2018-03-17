<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pockorder.constant.*,com.pockorder.domain.*" %>
<% LotteryTerm lt = (LotteryTerm) request.getAttribute("lotteryTerm"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
        <div style="text-align:center;color:#FF0000">
            各位吃货们，PieceOfCake抽奖活动又来啦！每两星期抽一次（下次抽奖时间12月08日），每次抽出一名幸运儿，机会多多，PieceOfCake诚邀您的参与！


参与步骤：

1. 在 *朋*友*圈* 分*享*我们家的蛋糕，写出对我们家蛋糕的食评，必须晒出是PieceOfCake蛋糕小姐的哦

 

2. 发送截图至公众号pocake2013 领取抽奖号码



​ 

本期奖品： 水果忌廉蛋糕一磅



抽奖日期：	2017-11-10 ~ 2017-11-24
中奖号码：	2016020839
领奖时间：	不限
活动奖品：	生日蛋糕一磅（168元）
中奖须知：	需领取奖品的亲，请提前致电我们预留哦，联系电话18127132896。
最终解释权归佛山市南海区蛋糕小姐蛋糕店
            <h1>抽奖公告</h1>
            <table style="align:center;margin:0 auto;text-align:left">
                <tr><td style="width:5em">抽奖日期：</td><td><%= lt.getLotteryStart() %>&nbsp;~&nbsp;<%= lt.getLotteryEnd().substring(0, 10) %></td></tr>
                <tr><td>中奖号码：</td><td><%= lt.getPrizedLottery().getLotteryNo() %></td></tr>
                <tr><td>领奖时间：</td><td>不限</td></tr>
                <tr><td>活动奖品：</td><td>榴莲忌廉蛋糕一磅</td></tr>
                <tr><td>中奖须知：</td><td>需领取奖品的亲，请提前致电我们预留哦，联系电话18127132896。</td></tr>
                <tr><td style="text-align:left;padding-top:20px;font-size: 0.8em" colspan="2">最终解释权归佛山市南海区蛋糕小姐蛋糕店</td></tr>
            </table>
        </div>
</body>
</html>