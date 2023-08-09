package com.quangho.designpattern.saga.demo.sim_card_data_package.repository;

import com.quangho.designpattern.saga.demo.sim_card_data_package.model.DataPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataPackageRepository extends JpaRepository<DataPackage, Integer> {
    DataPackage findById(int id);
}
