package com.example.bsms.services;

import com.example.bsms.models.RouteDetails;

import java.util.List;

public interface RoutesService {
    void addRoute(RouteDetails routeDetails) throws Exception;
    RouteDetails getRouteDetailsFromId(int routeId) throws Exception;
    RouteDetails getRouteDetailsFromName(String routeName) throws Exception;
    void updateRouteDetails(RouteDetails updateRouteDetails) throws Exception;
    List<String> getBusStopsList(String routeName) throws Exception;
    void deleteRoute(String routeName) throws Exception;
}
