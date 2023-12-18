package com.example.bsms.services;

import com.example.bsms.models.RouteDetails;
import com.example.bsms.models.requests.AddRouteReq;

import java.util.List;

public interface RoutesService {
    void addRoute(AddRouteReq addRouteReq) throws Exception;
    RouteDetails getRouteDetailsFromId(int routeId) throws Exception;
    RouteDetails getRouteDetailsFromName(String routeName) throws Exception;
    void updateRouteDetails(AddRouteReq updateRouteReq) throws Exception;
    List<String> getBusStopsList(String routeName) throws Exception;
    void deleteRoute(String routeName) throws Exception;
}
