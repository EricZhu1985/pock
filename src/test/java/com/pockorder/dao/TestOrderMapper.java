package com.pockorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.Order;

public class TestOrderMapper extends BaseTest {

	@Autowired
	private OrderMapper mapper;
	
	@Test
	public void testSelectByPrimaryKey() {
		Order order = mapper.selectByPrimaryKey("2");
		Assert.assertEquals("998", order.getOrderNo());
		Assert.assertEquals("2016-09-20", order.getOrderDate());
		Assert.assertEquals("12:00:00", order.getOrderTime());
		Assert.assertEquals("test", order.getContent());
		Assert.assertEquals(168, order.getPrice().intValue());
		Assert.assertEquals(0, order.getPaid().intValue());
		Assert.assertEquals("微信01", order.getCustomerWx());
		Assert.assertEquals("111", order.getCustomerTel());
		Assert.assertEquals("光", order.getRecorder());
		Assert.assertEquals("998", order.getOrderNo());
		Assert.assertEquals("1", order.getBranch().getBranchID());
	}

	@Test
	public void testInsert() {
		Order order = new Order();
		order.setOrderDate("2016-09-21");
		order.setOrderNo("888");
		order.setPrice(228);
		Assert.assertEquals(1, mapper.insert(order));
		Assert.assertNotNull(order.getOrderID());
	}

	@Test
	public void testUpdate() {
		Order order = mapper.selectByPrimaryKey("1");
		order.setContent("修改后");
		Assert.assertEquals(1, mapper.update(order));
		Order order2 = mapper.selectByPrimaryKey("1");
		Assert.assertEquals("修改后", order2.getContent());
	}

	@Test
	public void testIndexSelect() {
		List<Order> orderList = mapper.indexSelect(null, null, null, null, null, null, null, null);
		Assert.assertEquals(3, orderList.size());
	}

	@Test
	public void testSelectMaxWeijujuNo() {
		String weijujuNo = mapper.selectMaxWeijujuNo();
		Assert.assertEquals("000002", weijujuNo);
	}

	@Test
	public void testSelectCurrentOrderNo() {
		Integer orderNo = mapper.selectCurrentOrderNo("2016-09-20");
		Assert.assertEquals(997, orderNo.intValue());
	}

	@Test
	public void testUpdateFinished() {
		Assert.assertEquals(1, mapper.updateFinished("1", "1"));
	}

	@Test
	public void testDelete() {
		Assert.assertEquals(0, mapper.delete("666"));
		Assert.assertEquals(1, mapper.delete("1"));
	}

	@Test
	public void testUpdateRemark() {
		Assert.assertEquals(1, mapper.updateRemark("1", "1"));
	}

	@Test
	public void testSelectByDateAndTel() {
		List<Order> list = mapper.selectByDateAndTel("2016-09-20", "111");
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testUpdatePaid() {
		Assert.assertEquals(1, mapper.updatePaid("1", 50));
	}

	@Test
	public void testUpdateDeliverFlag() {
		Assert.assertEquals(1, mapper.updateDeliverFlag("1", 1));
	}

}
