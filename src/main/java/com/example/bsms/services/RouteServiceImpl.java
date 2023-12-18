package com.example.bsms.services;

import com.example.bsms.exceptions.BusStopNotFoundException;
import com.example.bsms.exceptions.NoSuchRouteFoundException;
import com.example.bsms.exceptions.RouteAlreadyExistsException;
import com.example.bsms.models.BusStopDetails;
import com.example.bsms.models.RouteDetails;
import com.example.bsms.models.entities.BusStops;
import com.example.bsms.models.entities.Routes;
import com.example.bsms.models.requests.AddRouteReq;
import com.example.bsms.repositories.BusStopsRepository;
import com.example.bsms.repositories.RoutesRepository;
import com.example.bsms.utils.ReflectionUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RouteServiceImpl implements RoutesService {

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private BusStopsRepository busStopsRepository;

    @Override
    public void addRoute(AddRouteReq addRouteReq) throws Exception {
        Optional<Routes> routesOpt = routesRepository.findOneByName(addRouteReq.getName());

        if(routesOpt.isPresent())
            throw new RouteAlreadyExistsException();

        Routes routes = new Routes();
        ReflectionUtils.mapProperties(addRouteReq, routes, true);

        Set<BusStops> busStopsSet = new HashSet<>();
        for(String busStopName: addRouteReq.getBusStopNamesList()) {
            Optional<BusStops> busStopOpt = busStopsRepository.findOneByName(busStopName);
            if( busStopOpt.isEmpty())
                throw new BusStopNotFoundException();
            busStopsSet.add(busStopOpt.get());
        }
        routes.setBusStops(busStopsSet);
        routesRepository.save(routes);
    }

    @Override
    public RouteDetails getRouteDetailsFromId(int routeId) throws Exception {
        Optional<Routes> routesOpt = routesRepository.findById(routeId);

        if(routesOpt.isEmpty())
            throw new NoSuchRouteFoundException();

        RouteDetails routeDetails = new RouteDetails();
        ReflectionUtils.mapProperties(routesOpt.get(), routeDetails, true);
        return routeDetails;
    }

    @Override
    public RouteDetails getRouteDetailsFromName(String routeName) throws Exception {
        Optional<Routes> routesOpt = routesRepository.findOneByName(routeName);

        if(routesOpt.isEmpty())
            throw new NoSuchRouteFoundException();

        RouteDetails routeDetails = new RouteDetails();
        ReflectionUtils.mapProperties(routesOpt.get(), routeDetails, true);
        routeDetails.setBusStopDetailsList(convertBusStopSetToList(routesOpt.get().getBusStops()));
        return routeDetails;
    }

    @Override
    public void updateRouteDetails(AddRouteReq updateRouteReq) throws Exception {
        Optional<Routes> routesE = routesRepository.findOneByName(updateRouteReq.getName());

        if(routesE.isEmpty())
            throw new NoSuchRouteFoundException();

        Routes routes = new Routes();
        ReflectionUtils.mapProperties(updateRouteReq, routes, true);
        if(updateRouteReq.getBusStopNamesList() != null && !updateRouteReq.getBusStopNamesList().isEmpty()) {
            Set<BusStops> busStopsSet = new HashSet<>();
            for(String busStopName: updateRouteReq.getBusStopNamesList()) {
                Optional<BusStops> busStopOpt = busStopsRepository.findOneByName(busStopName);
                if( busStopOpt.isEmpty())
                    throw new BusStopNotFoundException();
                busStopsSet.add(busStopOpt.get());
            }
            routes.setBusStops(busStopsSet);
        }

        routesRepository.save(routes);
    }

    @Override
    public List<String> getBusStopsList(String routeName) throws Exception {
        Optional<Routes> routesOpt = routesRepository.findOneByName(routeName);

        if(routesOpt.isEmpty())
            throw new NoSuchRouteFoundException();

        List<String> busStopList = new ArrayList<>();
        for(BusStopDetails busStopDetails: convertBusStopSetToList(routesOpt.get().getBusStops())) {
            busStopList.add(busStopDetails.getName());
        }
        return busStopList;
    }

    @Override
    public void deleteRoute(String routeName) throws Exception {
        Optional<Routes> routesOpt = routesRepository.findOneByName(routeName);

        if(routesOpt.isEmpty())
            throw new NoSuchRouteFoundException();

        routesRepository.deleteById(routesOpt.get().getId());
    }

    private Set<BusStops> convertBusStopListToSet(List<BusStopDetails> busStopDetailsList) throws Exception {
        Set<BusStops> busStopsSet = new HashSet<>();
        for(BusStopDetails busStopsDetails: busStopDetailsList) {
            BusStops busStop = new BusStops();
            ReflectionUtils.mapProperties(busStopsDetails, busStop, true);
            busStopsSet.add(busStop);
        }
        return busStopsSet;
    }

    private List<BusStopDetails> convertBusStopSetToList(Set<BusStops> busStopsSet) throws Exception {
        List<BusStopDetails> stopDetailsList = new ArrayList<>();
        for(BusStops busStop: busStopsSet.stream().toList()) {
            BusStopDetails busStopDetails = new BusStopDetails();
            ReflectionUtils.mapProperties(busStop, busStopDetails, true);
            stopDetailsList.add(busStopDetails);
        }
        return stopDetailsList;
    }
}
