package com.cts.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.vendor.model.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer>{

}
