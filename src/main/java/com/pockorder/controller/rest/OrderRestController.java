package com.pockorder.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pockorder.annotation.Logged;
import com.pockorder.constant.OrderConst;
import com.pockorder.domain.Order;
import com.pockorder.exception.BusiException;
import com.pockorder.service.OrderService;
import com.pockorder.view.IndexView;
import com.pockorder.view.MsgResult;

@RestController
@RequestMapping("/order")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@Autowired  
	private HttpSession session;  
	/**
	 * 主页订单列表
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @param content
	 * @param customerTel
	 * @param customerWx
	 * @param branchID
	 * @param finished
	 * @param orderBy
	 * @return
	 */
    @RequestMapping("/indexlist")
    public IndexView getIndexOrder(HttpSession session,
    		@RequestParam(value="startDate", required=false) String startDate,
    		@RequestParam(value="endDate", required=false) String endDate,
    		@RequestParam(value="content", required=false) String content,
    		@RequestParam(value="customerTel", required=false) String customerTel,
    		@RequestParam(value="customerWx", required=false) String customerWx,
    		@RequestParam(value="branchID", required=false) Integer branchID,
    		@RequestParam(value="finished", required=false) Integer finished,
    		@RequestParam(value="orderBy", required=false) String orderBy) {
    	
    	List<Order> list = orderService.getIndexOrders(startDate, endDate, content, customerTel,
    			customerWx, branchID, finished, orderBy);
    	IndexView view = new IndexView();
    	int totalPrice = 0;
    	for(int i = 0; i < list.size(); i++) {
    		Order o = list.get(i);
    		totalPrice += o.getPrice();
    	}
    	view.setRows(list);
    	view.setWeijujuNo(orderService.getLatestWeijujuNo());
    	view.setTotalPrice(totalPrice);
    	System.out.println(session.getId() + "===============" + session.getMaxInactiveInterval());
        return view;
    }
    /**
     * 增加订单
     * @param session
     * @param orderDate
     * @param orderTime
     * @param price
     * @param content
     * @param paid
     * @param customerWx
     * @param customerName
     * @param customerTel
     * @param recorder
     * @param deliverAddress
     * @param weijujuNo
     * @param branchID
     * @param orderNoFlag
     * @param repeatCount
     * @return
     */
    @RequestMapping("/add")
    @Logged
    public Order addNewOrder(HttpSession session,
    		@RequestParam(value="orderDate", required=true) String orderDate,
    		@RequestParam(value="orderTime", required=false) String orderTime,
    		@RequestParam(value="price", required=false, defaultValue="0") Integer price,
    		@RequestParam(value="content", required=false) String content,
    		@RequestParam(value="paid", required=false, defaultValue="0") Integer paid,
    		@RequestParam(value="paymentAccountID", required=true) String paymentAccountID,
    		@RequestParam(value="customerWx", required=false) String customerWx,
    		@RequestParam(value="customerName", required=false) String customerName,
    		@RequestParam(value="customerTel", required=false) String customerTel,
    		@RequestParam(value="recorder", required=false) String recorder,
    		@RequestParam(value="memo", required=false) String memo,
    		@RequestParam(value="weijujuNo", required=false) String weijujuNo,
    		@RequestParam(value="branchID", required=false) String branchID,
    		@RequestParam(value="orderNoFlag", required=false, defaultValue="yes") String orderNoFlag,
    		@RequestParam(value="repeatCount", required=false, defaultValue="1") Integer repeatCount) {
    	
    	if(repeatCount < 1) {
    		repeatCount = 1;
    	}
    	
    	Order ret = null;
    	for(int i = 0; i < repeatCount; i++) {
    		ret = orderService.addOrder(orderDate, orderTime, price, content, paid, paymentAccountID, customerWx,
    				customerName, customerTel, recorder, memo, weijujuNo, branchID, orderNoFlag);
    	}
    	
    	return ret;
    }
    /**
     * 取货订单
     * @param session
     * @param ID
     * @param finished
     * @return
     */
    @RequestMapping("/payAndFinish")
    @Logged
    public MsgResult payAndFinish(HttpSession session,
    		@RequestParam(value="orderID") String orderID,
    		@RequestParam(value="paid") Integer paid,
    		@RequestParam(value="paymentAccountID") String paymentAccountID,
    		@RequestParam(value="memberTel", required=false) String memberTel,
    		@RequestParam(value="bonusPoint", defaultValue="0") Float bonusPoint) {
    	
    	MsgResult msg = new MsgResult();
    	try {
    		if(orderService.payAndFinish(orderID, paid, paymentAccountID, memberTel, bonusPoint) >= 1) {
	    		msg.setMsg("修改成功！");
	    	} else {
	    		msg.setErrMsg("修改失败，数据不存在！");
	    	}
    	} catch(BusiException e) {
    		msg.setErrMsg(e.getMessage());
    	}
    	return msg;
    }

    @RequestMapping("/pay")
    @Logged
    public MsgResult pay(HttpSession session,
    		@RequestParam(value="orderID") String orderID,
    		@RequestParam(value="paid") Integer paid,
    		@RequestParam(value="paymentAccountID") String paymentAccountID) {
    	
    	MsgResult msg = new MsgResult();
		if(paid > 0) {
			orderService.payOrder(orderID, paid, paymentAccountID);
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败，不可零支付！");
    	}
    	return msg;
    }

    @RequestMapping("/deliver")
    @Logged
    public MsgResult deliverOrder(
    		@RequestParam(value="orderID") String orderID,
    		@RequestParam(value="deliverFlag") Integer deliverFlag) {

		String[] orderIDs = orderID.split(",");
    	MsgResult msg = new MsgResult();
    	for(int i = 0; i < orderIDs.length; i++) {
    		orderService.updateDeliverFlag(orderIDs[i], deliverFlag);
    	}
		msg.setMsg("修改成功！");
		return msg;
    }
    
    @RequestMapping("/unfinish")
    @Logged
    public MsgResult unfinish(HttpSession session,
    		@RequestParam(value="orderID") String orderID) {
    	
    	MsgResult msg = new MsgResult();
		if(orderService.updateFinished(orderID, OrderConst.FINISHED_NO) >= 1) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败，数据不存在！");
    	}
    	return msg;
    }
    /**
     * 修改订单
     * @param session
     * @param ID
     * @param orderDate
     * @param orderTime
     * @param price
     * @param content
     * @param paid
     * @param customerWx
     * @param customerName
     * @param customerTel
     * @param recorder
     * @param deliverAddress
     * @param weijujuNo
     * @param memo
     * @param branchID
     * @param orderNoFlag
     * @return
     */
    @RequestMapping("/update")
    @Logged
    public MsgResult updateOrder(HttpSession session,
    		@RequestParam(value="orderID", required=true) String orderID,
    		@RequestParam(value="orderDate", required=true) String orderDate,
    		@RequestParam(value="orderTime", required=false) String orderTime,
    		@RequestParam(value="price", required=false) Integer price,
    		@RequestParam(value="content", required=false) String content,
    		@RequestParam(value="customerWx", required=false) String customerWx,
    		@RequestParam(value="customerName", required=false) String customerName,
    		@RequestParam(value="customerTel", required=false) String customerTel,
    		@RequestParam(value="recorder", required=false) String recorder,
    		@RequestParam(value="deliverAddress", required=false) String deliverAddress,
    		@RequestParam(value="weijujuNo", required=false) String weijujuNo,
    		@RequestParam(value="memo", required=false) String memo,
    		@RequestParam(value="branchID", required=false) String branchID,
    		@RequestParam(value="orderNoFlag", required=false, defaultValue="yes") String orderNoFlag) {
    	
    	MsgResult msg = new MsgResult();
    	if(orderService.updateOrder(orderID, orderDate, orderTime, price,
    			content, customerWx, customerName, customerTel, recorder,
    			deliverAddress, memo, weijujuNo, branchID, orderNoFlag) >= 1) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setMsg("修改失败，数据不存在！");
    	}
    	return msg;
    }
    /**
     * 通过ID获取订单
     * @param ID
     * @return
     */
    @RequestMapping("/detail")
    public Order getOrder(@RequestParam(value="orderID", required=true) String orderID) {
    	return orderService.getOrder(orderID);
    }
    /**
     * 删除订单
     * @param session
     * @param ID
     * @param finished
     * @return
     */
    @RequestMapping("/delete")
    @Logged
    public MsgResult delete(@RequestParam(value="orderID") String orderID) {
    	
    	MsgResult msg = new MsgResult();
    	if(orderService.delete(orderID) >= 1) {
    		msg.setMsg("删除成功！");
    	} else {
    		msg.setErrMsg("删除失败，数据不存在！");
    	}
    	return msg;
    }
    /**
     * 标记订单
     * @param session
     * @param ID
     * @param finished
     * @return
     */
    @RequestMapping("/remark")
    public MsgResult remark(@RequestParam(value="orderID") String orderID,
    		@RequestParam(value="remark") String remark) {
    	
    	MsgResult msg = new MsgResult();
    	if(orderService.updateRemark(orderID, remark) >= 1) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败，数据不存在！");
    	}
    	return msg;
    }

    @RequestMapping("/testrepeatorder")
	public boolean testRepeatOrder(@RequestParam(value="orderDate") String orderDate,
    		@RequestParam(value="tel") String tel) {
		if(tel != null && !tel.equals("") && orderService.testRepeatOrder(orderDate, tel)) {
			return true;
		}
		return false;
	}
}
