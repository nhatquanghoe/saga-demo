package com.quangho.designpattern.saga.demo.code_delivery.repository;

import com.quangho.designpattern.saga.demo.code_delivery.model.CodeDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeDeliveryRepository extends JpaRepository<CodeDelivery, Integer> {
    CodeDelivery findById(int id);
}
