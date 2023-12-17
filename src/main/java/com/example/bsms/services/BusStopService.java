package com.example.bsms.services;

import com.example.bsms.models.BusStopDetails;
import com.example.bsms.models.BusStopFilters;
import com.example.bsms.models.requests.AddBusStopReq;

import java.util.List;

public interface BusStopService {
    void addBusStop(AddBusStopReq addBusStopReq) throws Exception;
    BusStopDetails getBusStopDetailsFromId(int busStopId) throws Exception;
    BusStopDetails getBusStopDetailsFromName(String busStopName) throws Exception;
    List<BusStopDetails> getBusDetails(BusStopFilters busStopFilters) throws Exception;
    void updateBusDetails(String busStopName, BusStopDetails busStopDetails) throws Exception;
    void deleteBusStop(String busStopName) throws Exception;
}
