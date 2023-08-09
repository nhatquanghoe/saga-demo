package com.quangho.designpattern.saga.demo.sim_card_data_package.service;

import com.quangho.designpattern.saga.demo.sim_card_data_package.model.DataPackage;
import com.quangho.designpattern.saga.demo.sim_card_data_package.repository.DataPackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataPackageService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataPackageRepository dataPackageRepository;

    public DataPackage createDataPackage(DataPackage dataPackage) {
        try {
            DataPackage savedDataPackage = dataPackageRepository.save(dataPackage);
            logger.info("Created data package with id: " + savedDataPackage.getId());
            return dataPackage;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public DataPackage getDataPackageById(int dataPackageId) {
        DataPackage dataPackage = dataPackageRepository.findById(dataPackageId);

        if (dataPackage == null) {
            throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
        } else {
            logger.info("Found data package with id: " + dataPackageId);
            return dataPackage;
        }
    }

    public List<DataPackage> getDataPackages() {
        List<DataPackage> dataPackages = dataPackageRepository.findAll();
        logger.info("Number of data packages found: " + dataPackages.size());
        return dataPackages;
    }

    public DataPackage updateDataPackage(int dataPackageId, DataPackage dataPackage) {
        try {
            DataPackage dataPackageExist = dataPackageRepository.findById(dataPackageId);

            if (dataPackageExist == null) {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }

            logger.info("Updated data package with id: " + dataPackageId);
            dataPackageExist = dataPackageRepository.save(dataPackage);
            return dataPackageExist;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }

    public DataPackage deleteDataPackage(int dataPackageId) {
        try {
            DataPackage dataPackageExist = dataPackageRepository.findById(dataPackageId);

            if (dataPackageExist == null) {
                throw new RuntimeException(HttpStatus.NOT_FOUND.toString());
            }

            dataPackageRepository.deleteById(dataPackageId);
            logger.info("Deleted data package with id: " + dataPackageId);
            return dataPackageExist;
        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        }
    }
}
