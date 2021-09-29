package com.cts.proceedtobuy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.proceedtobuy.model.CustomerWishlist;

@Repository
public interface CustomerWishlistRepository extends JpaRepository<CustomerWishlist, Integer>{

	public List<CustomerWishlist> findByCustomerName(String customerName);
}
