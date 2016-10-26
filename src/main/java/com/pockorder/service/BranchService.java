package com.pockorder.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pockorder.dao.BranchMapper;
import com.pockorder.domain.Branch;

@Service
public class BranchService {

	@Autowired
	private BranchMapper branchMapper;

	public Branch getBranchByID(String branchID) {
		return branchMapper.getBranchByID(branchID);
	}

	public List<Branch> getAllBranch() {
		return branchMapper.getAllBranch();
	}
	
}
