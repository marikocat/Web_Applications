package com.my.util;

import com.my.domain.Tariff;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UtilFunctionsTest {
    @ParameterizedTest
    @CsvSource(value = {"135.0,125.0", "250.0,350.0"})
    void testFindTotalCost1(double x, double y) {
        List<Tariff> tariffList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            tariffList.add(new Tariff());
        }
        tariffList.get(0).setCost(x);
        tariffList.get(1).setCost(y);
        double expectedResult = (x + y) * 0.8;
        double actualResult = UtilFunctions.findTotalCost(tariffList);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"135.0,125.0,75.0", "250.0,350.0,150.0"})
    void testFindTotalCost2(double x, double y, double z) {
        List<Tariff> tariffList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tariffList.add(new Tariff());
        }
        tariffList.get(0).setCost(x);
        tariffList.get(1).setCost(y);
        tariffList.get(2).setCost(z);
        double expectedResult = (x + y + z) * 0.7;
        double actualResult = UtilFunctions.findTotalCost(tariffList);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"135.0,125.0,75.0,100.0", "250.0,350.0,200.0,150.0"})
    void testFindTotalCost3(double x, double y, double z, double n) {
        List<Tariff> tariffList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            tariffList.add(new Tariff());
        }
        tariffList.get(0).setCost(x);
        tariffList.get(1).setCost(y);
        tariffList.get(2).setCost(z);
        tariffList.get(3).setCost(n);
        double expectedResult = (x + y + z + n) * 0.6;
        double actualResult = UtilFunctions.findTotalCost(tariffList);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    void getStartDate() {
        String expectedDate = "2023-12-12";
        String actualDate = UtilFunctions.getStartDate().toString();
        Assertions.assertEquals(expectedDate, actualDate);
    }

    @Test
    void getEndDate() {
        String expectedDate = "2023-12-31";
        String actualDate = UtilFunctions.getEndDate().toString();
        Assertions.assertEquals(expectedDate, actualDate);
    }

    @Test
    void getCurrentDate() {
        String expectedDate = "12.12.2023 Tuesday";
        String actualDate = UtilFunctions.getCurrentDate();
        Assertions.assertEquals(expectedDate, actualDate);
    }
}