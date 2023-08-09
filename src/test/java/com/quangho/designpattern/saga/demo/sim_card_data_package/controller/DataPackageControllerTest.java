package com.quangho.designpattern.saga.demo.sim_card_data_package.controller;

import com.quangho.designpattern.saga.demo.sim_card_data_package.model.DataPackage;
import com.quangho.designpattern.saga.demo.sim_card_data_package.service.DataPackageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class DataPackageControllerTest {
    @InjectMocks
    DataPackageController dataPackageController;

    @Mock
    DataPackageService dataPackageService;

    @Test
    void testGetDataPackages() {
        DataPackage dataPackage1 = new DataPackage("Data package 1", "Data package 1 desc", 49.99f, 6);
        DataPackage dataPackage2 = new DataPackage("Data package 2", "Data package 2 desc", 89.99f, 12);
        List dataPackages = new ArrayList();
        dataPackages.add(dataPackage1);
        dataPackages.add(dataPackage2);
        when(dataPackageService.getDataPackages()).thenReturn(dataPackages);

        ResponseEntity<List<DataPackage>> responseEntity = dataPackageController.getDataPackages();
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().size()).isEqualTo(2);
        assertThat(responseEntity.getBody().get(0).getPackageName()).isEqualTo("Data package 1");
        assertThat(responseEntity.getBody().get(1).getPackageName()).isEqualTo("Data package 2");
    }

    @Test
    void testCreateDataPackage() {
        DataPackage dataPackage = new DataPackage("Data package 1", "Data package 1 desc", 49.99f, 6);
        when(dataPackageService.createDataPackage(any(DataPackage.class))).thenReturn(dataPackage);

        ResponseEntity<DataPackage> responseEntity = dataPackageController.createDataPackage(dataPackage);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getPackageName()).isEqualTo("Data package 1");
    }


    @Test
    void testGetDataPackageById() {
        DataPackage dataPackage = new DataPackage("Data package 1", "Data package 1 desc", 49.99f, 6);
        when(dataPackageService.getDataPackageById(any(Integer.class))).thenReturn(dataPackage);

        ResponseEntity<DataPackage> responseEntity = dataPackageController.getDataPackageById(0);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody().getId()).isEqualTo(0);
    }
}