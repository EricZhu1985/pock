package com.pockorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.Branch;


public class TestBranchMapper extends BaseTest {

	@Autowired
	private BranchMapper mapper;
	
	@Test
	public void testGetAllBranch() {
		List<Branch> branchList = mapper.getAllBranch();
		Assert.assertEquals(3, branchList.size());
		
		Branch branch = branchList.get(1);
		Assert.assertEquals("佛山雅居乐店", branch.getBranchName());
	}

	@Test
	public void testGetBranchByID() {
		Branch branch = mapper.getBranchByID("2");
		Assert.assertEquals("佛山雅居乐店", branch.getBranchName());
	}

}
