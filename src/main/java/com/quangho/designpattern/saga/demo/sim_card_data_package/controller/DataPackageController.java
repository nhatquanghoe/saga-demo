package com.quangho.designpattern.saga.demo.sim_card_data_package.controller;

import com.quangho.designpattern.saga.demo.sim_card_data_package.model.DataPackage;
import com.quangho.designpattern.saga.demo.sim_card_data_package.service.DataPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DataPackageController {
    private static final Logger logger = LoggerFactory.getLogger(DataPackageController.class);

    @Autowired
    private DataPackageService dataPackageService;

    @GetMapping("/data-packages")
    public ResponseEntity<List<DataPackage>> getDataPackages() {
        logger.info("Received request to find all data packages");
        return ResponseEntity.status(HttpStatus.OK).body(dataPackageService.getDataPackages());
    }

    @PostMapping("/data-packages")
    public ResponseEntity<DataPackage> createDataPackage(@Valid @RequestBody DataPackage dataPackage) {
        logger.info("Received request to create a new data package");
        return ResponseEntity.status(HttpStatus.OK).body(dataPackageService.createDataPackage(dataPackage));
    }

    @GetMapping("/data-packages/{id}")
    public ResponseEntity<DataPackage> getDataPackageById(@PathVariable("id") int dataPackageId) {
        logger.info("Received request to find data package by Id: {}", dataPackageId);
        return ResponseEntity.status(HttpStatus.OK).body(dataPackageService.getDataPackageById(dataPackageId));
    }

    @PutMapping("/data-packages/{id}")
    public ResponseEntity<DataPackage> updateDataPackage(@PathVariable("id") int dataPackageId, @Valid @RequestBody DataPackage dataPackage) {
        logger.info("Received request to update data package by Id: {}", dataPackageId);
        return ResponseEntity.status(HttpStatus.OK).body(dataPackageService.updateDataPackage(dataPackageId, dataPackage));
    }

    @DeleteMapping("/data-packages/{id}")
    public ResponseEntity<DataPackage> deleteDataPackage(@PathVariable("id") int dataPackageId) {
        logger.info("Received request to delete data package by Id: {}", dataPackageId);
        return ResponseEntity.status(HttpStatus.OK).body(dataPackageService.deleteDataPackage(dataPackageId));
    }
}
