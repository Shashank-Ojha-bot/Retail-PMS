package com.cts.vendor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.vendor.model.Vendor;
import com.cts.vendor.model.VendorStock;

@Repository
public interface VendorStockRepository extends JpaRepository<VendorStock, Integer>{

	@Query("select vs.vendor from VendorStock vs where vs.productId=?1 and vs.stockInHand>0")
	public List<Vendor> getVendorsWithId(int productId);
}
