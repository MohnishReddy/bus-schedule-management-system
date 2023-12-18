package com.example.bsms.controllers;

import com.example.bsms.annotations.ForAdminOnly;
import com.example.bsms.exceptions.NoSuchRouteFoundException;
import com.example.bsms.exceptions.RouteAlreadyExistsException;
import com.example.bsms.models.RouteDetails;
import com.example.bsms.models.requests.AddRouteReq;
import com.example.bsms.models.responses.BasicResponse;
import com.example.bsms.models.responses.BusStopListRes;
import com.example.bsms.models.responses.RoutesDetailsRes;
import com.example.bsms.services.RoutesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/routes")
public class RoutesController {

    @Autowired
    private RoutesService routesService;

    @ForAdminOnly
    @PostMapping("")
    public ResponseEntity<?> addRoute(@RequestBody AddRouteReq addRouteReq) {
        if (addRouteReq.getName() == null || addRouteReq.getCity() == null || addRouteReq.getCountry() == null ||
                addRouteReq.getState() == null || addRouteReq.getPinCode() == null ||
                addRouteReq.getBusStopNamesList() == null || addRouteReq.getBusStopNamesList().isEmpty())
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Fields cannot be empty!");
            }}, HttpStatus.BAD_REQUEST);
        try {
            routesService.addRoute(addRouteReq);
        } catch (RouteAlreadyExistsException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("New Route added!");
        }}, HttpStatus.CREATED);
    }

    @GetMapping("/{routeName}")
    public ResponseEntity<?> getRouteFromName(@PathVariable String routeName) {
        RoutesDetailsRes routesDetailsRes = new RoutesDetailsRes();
        try {
            RouteDetails routeDetails = routesService.getRouteDetailsFromName(routeName);
            routesDetailsRes.setRoutesList(Arrays.asList(new RouteDetails[]{routeDetails}));
        } catch (NoSuchRouteFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(routesDetailsRes, HttpStatus.OK);
    }

    @GetMapping("/{routeName")
    public ResponseEntity<?> getAllBusStopsInRoute(@PathVariable String routeName) {
        BusStopListRes busStopListRes = new BusStopListRes();
        try {
            List<String> busStopList = routesService.getBusStopsList(routeName);
            busStopListRes.setBusStopList(busStopList);
        } catch (NoSuchRouteFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(busStopListRes, HttpStatus.OK);
    }

    @ForAdminOnly
    @PostMapping("/{routeName}")
    public ResponseEntity<?> updateRoute(@PathVariable String routeName, @RequestBody AddRouteReq updateRouteReq) {
        try {
            updateRouteReq.setName(routeName);
            routesService.updateRouteDetails(updateRouteReq);
        } catch (NoSuchRouteFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("Route updated!");
        }}, HttpStatus.OK);
    }

    @ForAdminOnly
    @DeleteMapping("/{routeName")
    public ResponseEntity<?> deleteRoute(@PathVariable String routeName) {
        try {
            routesService.deleteRoute(routeName);
        } catch (NoSuchRouteFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("Route "+routeName+" deleted!");
        }}, HttpStatus.OK);
    }
}
