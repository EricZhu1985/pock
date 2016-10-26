package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pockorder.domain.Branch;

@Repository
public interface BranchMapper {

	public List<Branch> getAllBranch();

	public Branch getBranchByID(@Param("branchID")String branchID);
}
