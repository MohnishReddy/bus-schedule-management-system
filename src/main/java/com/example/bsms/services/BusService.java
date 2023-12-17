package com.example.bsms.services;

import com.example.bsms.models.BusDetails;

import java.util.List;

public interface BusService {
    BusDetails getBusDetails(int busId) throws Exception;
    List<BusDetails> getBusDetailsFromBusNumber(String busNumber) throws Exception;
    BusDetails getBusDetailsFromNumberPlate(String numberPlate) throws Exception;
    void upsertBusDetails(BusDetails busDetails) throws Exception;
    void deleteBusDetailsFromNumberPlate(String numberPlate) throws Exception;
    void deleteAllBusesWithBusNumber(String busNumber) throws Exception;
}
