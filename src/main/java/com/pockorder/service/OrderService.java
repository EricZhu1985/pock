package com.pockorder.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pockorder.constant.OrderConst;
import com.pockorder.constant.PaymentConst;
import com.pockorder.dao.OrderMapper;
import com.pockorder.dao.PaymentMapper;
import com.pockorder.domain.Member;
import com.pockorder.domain.Order;
import com.pockorder.domain.Payment;
import com.pockorder.exception.BusiException;
import com.pockorder.exception.DataNotFoundException;

@Service
@Transactional 
public class OrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private MemberService memberService;

	@Autowired
	private PaymentMapper paymentMapper;
	
	public static String ORDER_NO_FLAG_FALSE = "no";
	public static String ORDER_NO_FLAG_TRUE = "yes";
	/**
	 * Get user by name and password, password will be encode by MD5 algorithm
	 * @param name
	 * @param password
	 * @return
	 */
	public List<Order> getIndexOrders(String startDate, String endDate, String content, String customerTel,
			String customerWx, Integer branchID, Integer finished, String orderBy) {
		if(orderBy == null || orderBy.equals("")) {
			orderBy = OrderMapper.ORDERBY_ORDER_TIME;
		}
		if(finished != null && finished == -1) {
			finished = null;
		}
		if(branchID != null && branchID == -1) {
			branchID = null;
		}
		return orderMapper.indexSelect(startDate, endDate, content, customerTel, customerWx, branchID, finished, orderBy);
	}
	/**
	 * Get latest weijuju order no in weijuju.com
	 * @return
	 */
	public String getLatestWeijujuNo() {
		return orderMapper.selectMaxWeijujuNo();
	}
	/**
	 * Add order
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
	 * @return
	 */
	public Order addOrder(String orderDate, String orderTime, Integer price, String content,
			Integer paid, String paymentAccountID, String customerWx, String customerName, String customerTel,
			String recorder, String memo, String weijujuNo, String branchID,
			String orderNoFlag) {
		
		if(branchID.equals("")) {
			branchID = null;
		}
		
		Order order = new Order();
		String orderNo = getOrderNoWithFlag(orderDate, orderNoFlag);
		order.setOrderNo(orderNo);
		
		
		order.setOrderDate(orderDate);
		order.setOrderTime(orderTime);
		order.setPrice(price);
		order.setContent(content);
		//can not update this column directly while you can update it by pay order
		//order.setPaid(paid);
		order.setRecorder(recorder);
		order.setMemo(memo);
		order.setWeijujuNo(weijujuNo);

		order.setBranchID(branchID);
		
		order.setCustomerName(customerName);
		order.setCustomerTel(customerTel);
		order.setCustomerWx(customerWx);
		
		orderMapper.insert(order);
		
		if(paid > 0) {
			this.payOrder(order.getOrderID(), paid, paymentAccountID);
		}
		
		return order;
	}
	
	public Payment payOrder(String orderID, Integer paid, String paymentAccountID) {

		Payment payment = new Payment();
		payment.setMemo("");
		payment.setPaid(paid);
		payment.setAccountID(paymentAccountID);
		payment.setPaymentTypeID(PaymentConst.PAYMENT_TYPE_ID_ORDER);

		paymentMapper.insert(payment);
		paymentMapper.insertPaymentOrder(payment.getPaymentID(), orderID);
		Integer orderPaid = paymentMapper.getOrderPaid(orderID);
		orderMapper.updatePaid(orderID, orderPaid);
		return payment;
	}
	/**
	 * Generate order no
	 * @param orderDate
	 * @param orderNoFlag
	 * @return
	 */
	public String getOrderNoWithFlag(String orderDate, String orderNoFlag) {
		if(orderNoFlag.equals(ORDER_NO_FLAG_FALSE)) {
			return "0";
		}
		return getOrderNo(orderDate);
		
	}
	/**
	 * Generate order no
	 * @param orderDate
	 * @return
	 */
	public String getOrderNo(String orderDate) {

		Integer orderNo = orderMapper.selectCurrentOrderNo(orderDate);
		if(orderNo == null || orderNo <= 0) {
			orderNo = 999;
		}
		orderNo = orderNo - 1;
		return orderNo.toString();
	}
	/**
	 * Update finished column by order ID
	 * @param ID
	 * @param finished
	 * @return
	 */
	/*public int updateFinished(String orderID, String finished) {
		return orderMapper.updateFinished(orderID, finished);
	}*/
	
	public int payAndFinish(String orderID, Integer paid, String paymentAccountID, String memberTel, Float bonusPoint) throws BusiException {
		Order order = getOrder(orderID);
		if(order.getFinished().equals(OrderConst.FINISHED_YES)) {
			throw new BusiException("该订单已取，不可重复取单！");
		}
		if(paid > 0) {
			payOrder(orderID, paid, paymentAccountID);
		}
		
		if(order.getHasBonusPoint() == Order.HASBONUSPOINT_NO && memberTel != null) {
			Member member;
			try {
				member = memberService.getMemberByTel(memberTel);
			} catch (DataNotFoundException e) {
				member = memberService.insert(memberTel);
			}
			orderMapper.updateHasBonusPoint(orderID, Order.HASBONUSPOINT_YES);
			memberService.updateBonusPoint(member.getMemberID(), bonusPoint / 10, order.getContent());
		}
		
		return orderMapper.updateFinished(orderID, OrderConst.FINISHED_YES);
	}
	
	public int updateFinished(String orderID, String finished) {
		return orderMapper.updateFinished(orderID, finished);
	}
	
	public int updateOrder(String orderID, String orderDate, String orderTime, Integer price, String content,
			String customerWx, String customerName, String customerTel, String recorder,
			String deliverAddress, String memo, String weijujuNo, String branchID, String orderNoFlag) {

		if(branchID.equals("")) {
			branchID = null;
		}
		
		Order order = new Order();
		order.setOrderID(orderID);
		order.setOrderDate(orderDate);
		order.setOrderTime(orderTime);
		order.setPrice(price);
		order.setContent(content);
		//can not update this column directly while you can update it by pay order
		//order.setPaid(paid);
		order.setRecorder(recorder);
		order.setDeliverAddress(deliverAddress);
		order.setMemo(memo);
		order.setWeijujuNo(weijujuNo);
		
		order.setCustomerName(customerName);
		order.setCustomerTel(customerTel);
		order.setCustomerWx(customerWx);
		
		order.setBranchID(branchID);
		
		return updateOrder(order, orderNoFlag);
	}
	/**
	 * Update Order
	 * @param o
	 * @return
	 */
	public int updateOrder(Order order, String orderNoFlag) {
		Order oldOrder = orderMapper.selectByPrimaryKey(order.getOrderID());
		//判断订单日期是否有改变
		if(!order.getOrderDate().equals(oldOrder.getOrderDate())) {
			//如果改变根据新日期生成新订单编号
			order.setOrderNo(getOrderNoWithFlag(order.getOrderDate(), orderNoFlag));
		} else {
			//如果没改，判断原本ID是否大于0
			if(Integer.parseInt(oldOrder.getOrderNo()) > 0) {
				//如果大于0，用原有OrderNo
				order.setOrderNo(oldOrder.getOrderNo());
			} else {
				order.setOrderNo(getOrderNoWithFlag(order.getOrderDate(), orderNoFlag));
			}
		}
		
		return orderMapper.update(order);
	}
	/**
	 * 用ID获取订单
	 * @param ID
	 * @return
	 */
	public Order getOrder(String orderID) {
		return orderMapper.selectByPrimaryKey(orderID);
	}
	/**
	 * 
	 * @param orderID
	 * @return
	 */
	public int delete(String orderID) {
		return orderMapper.delete(orderID);
	}
	/**
	 * Update remark column by order ID
	 * @param ID
	 * @param finished
	 * @return
	 */
	public int updateRemark(String orderID, String remark) {
		return orderMapper.updateRemark(orderID, remark);
	}
	
	public boolean testRepeatOrder(String orderDate, String tel) {
		if(orderMapper.selectByDateAndTel(orderDate, tel).size() > 1) {
			return true;
		}
		return false;
	}
	
	public int updateDeliverFlag(String orderID, Integer paid) {
		return orderMapper.updateDeliverFlag(orderID, paid);
	}
}
