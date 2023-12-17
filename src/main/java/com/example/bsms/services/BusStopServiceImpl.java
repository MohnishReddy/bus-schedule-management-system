package com.example.bsms.services;

import com.example.bsms.exceptions.BusNotFoundException;
import com.example.bsms.exceptions.NumberPlateAlreadyExistsException;
import com.example.bsms.models.BusStopDetails;
import com.example.bsms.models.BusStopFilters;
import com.example.bsms.models.entities.BusStops;
import com.example.bsms.models.requests.AddBusStopReq;
import com.example.bsms.repositories.BusStopsRepository;
import com.example.bsms.utils.ReflectionUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BusStopServiceImpl implements BusStopService {

    @Autowired
    private BusStopsRepository busStopsRepository;

    @Override
    public void addBusStop(AddBusStopReq addBusStopReq) throws Exception {
        Optional<BusStops> busStopsOpt = busStopsRepository.findOneByName(addBusStopReq.getName());
        if(busStopsOpt.isPresent())
            throw new NumberPlateAlreadyExistsException();
        BusStopDetails busStopDetails = new BusStopDetails();
        ReflectionUtils.mapProperties(busStopDetails, addBusStopReq, false);
        busStopsRepository.save(mapBusStopModelToEntity(busStopDetails));
    }

    @Override
    public BusStopDetails getBusStopDetailsFromId(int busStopId) throws Exception {
        Optional<BusStops> busStopsOpt = busStopsRepository.findById(busStopId);
        if(busStopsOpt.isEmpty())
            throw new BusNotFoundException();
        BusStopDetails busStopDetails = new BusStopDetails();
        ReflectionUtils.mapProperties(busStopDetails, busStopsOpt.get(), true);
        return busStopDetails;
    }

    @Override
    public BusStopDetails getBusStopDetailsFromName(String busStopName) throws Exception {
        Optional<BusStops> busStopsOpt = busStopsRepository.findOneByName(busStopName);
        if(busStopsOpt.isEmpty())
            throw new BusNotFoundException();
        BusStopDetails busStopDetails = new BusStopDetails();
        ReflectionUtils.mapProperties(busStopDetails, busStopsOpt.get(), true);
        return busStopDetails;
    }

    @Override
    public List<BusStopDetails> getBusDetails(BusStopFilters busStopFilters) throws Exception {
        return null;
    }

    @Override
    public void updateBusDetails(String busStopName, BusStopDetails updateBusStopDetails) throws Exception {
        Optional<BusStops> busStopsOpt = busStopsRepository.findOneByName(busStopName);
        if(busStopsOpt.isEmpty())
            throw new BusNotFoundException();
        BusStops updateBusStopDetail = mapBusStopModelToEntity(updateBusStopDetails);
        BusStops originalBusStops = busStopsOpt.get();
        ReflectionUtils.mapProperties(updateBusStopDetail, originalBusStops, false);
        busStopsRepository.save(originalBusStops);
    }

    @Override
    public void deleteBusStop(String busStopName) throws Exception {
        Optional<BusStops> busStopsOpt = busStopsRepository.findOneByName(busStopName);
        if(busStopsOpt.isEmpty())
            throw new BusNotFoundException();
        busStopsRepository.deleteById(busStopsOpt.get().getId());
    }

    private BusStopDetails mapBusStopEntityToModel(BusStops busStop) throws Exception {
        BusStopDetails busStopDetails = new BusStopDetails();
        ReflectionUtils.mapProperties(busStopDetails, busStop, true);
        return busStopDetails;
    }

    private BusStops mapBusStopModelToEntity(BusStopDetails busStopDetails) throws Exception {
        BusStops busStops = new BusStops();
        ReflectionUtils.mapProperties(busStops, busStopDetails, true);
        return busStops;
    }
}
