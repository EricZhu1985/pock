package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pockorder.domain.Order;

@Repository
public interface OrderMapper {
	
	public static String ORDERBY_ORDER_TIME = "ORDER_TIME";
	public static String ORDERBY_ORDER_RECORD_TIME = "ORDER_RECORD_TIME";
	/**
	 * Select Order By Primary Key
	 * @param orderID
	 * @return
	 */
	public Order selectByPrimaryKey(@Param("orderID") String orderID);
	/**
	 * Insert Order
	 * @param o
	 * @return
	 */
	public int insert(Order order);
	/**
	 * Update Order
	 * @param o
	 * @return
	 */
	public int update(Order order);
	/**
	 * Select orders on index page
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
	public List<Order> indexSelect(@Param("startDate")String startDate,
			@Param("endDate")String endDate,
			@Param("content")String content,
			@Param("customerTel")String customerTel,
			@Param("customerWx")String customerWx,
			@Param("branchID")Integer branchID,
			@Param("finished")Integer finished,
			@Param("orderBy")String orderBy);
	/**
	 * Get latest weijujuNo
	 * @return
	 */
	public String selectMaxWeijujuNo();
	/**
	 * Get maximum orderNo of specified date
	 * @param orderDate
	 * @return
	 */
	public Integer selectCurrentOrderNo(@Param("orderDate") String orderDate);
	/**
	 * Update finished column of order
	 * @param ID
	 * @param finished
	 * @return
	 */
	public int updateFinished(@Param("orderID") String orderID, @Param("finished") String finished);
	/**
	 * Delete order. Actually, it will only change deleteFlag column of the data.
	 * @param orderID
	 * @return
	 */
	public int delete(@Param("orderID") String orderID);
	/**
	 * Update remark column of order
	 * @param ID
	 * @param finished
	 * @return
	 */
	public int updateRemark(@Param("orderID") String orderID, @Param("remark") String remark);
	
	public List<Order> selectByDateAndTel(@Param("orderDate") String orderDate, @Param("customerTel") String customerTel);
	/**
	 * Update column `PAID` of table `ORDER`
	 * @param orderID
	 * @param paid
	 * @return
	 */
	public int updatePaid(@Param("orderID") String orderID, @Param("paid") Integer paid);
	/**
	 * Update column `DELIVER_FLAG` of table `ORDER`
	 * @param orderID
	 * @param deliverFlag
	 * @return
	 */
	public int updateDeliverFlag(@Param("orderID") String orderID, @Param("deliverFlag") Integer deliverFlag);
	/**
	 * Update column `HASBONUSPOINT` of table `ORDER`
	 * @param orderID
	 * @param hasBonusPoint
	 * @return
	 */
	public int updateHasBonusPoint(@Param("orderID") String orderID, @Param("hasBonusPoint") Integer hasBonusPoint);
}
