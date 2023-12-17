package com.example.bsms.controllers;

import com.example.bsms.annotations.ForAdminOnly;
import com.example.bsms.exceptions.BusNotFoundException;
import com.example.bsms.exceptions.NumberPlateAlreadyExistsException;
import com.example.bsms.models.BusStopDetails;
import com.example.bsms.models.requests.AddBusStopReq;
import com.example.bsms.models.responses.BasicResponse;
import com.example.bsms.models.responses.BusStopDetailsRes;
import com.example.bsms.services.BusStopService;
import com.example.bsms.utils.ReflectionUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/bus-stop")
public class BusStopController {

    @Autowired
    private BusStopService busStopService;

    @ForAdminOnly
    @PostMapping("/add")
    public ResponseEntity<?> addBusDetails(HttpServletRequest request, @RequestBody AddBusStopReq addBusStopReq) {
        if (addBusStopReq.getName() == null || addBusStopReq.getCity() == null ||
                addBusStopReq.getCountry() == null || addBusStopReq.getState() == null ||
                addBusStopReq.getPinCode() == null || addBusStopReq.getLatitude() == null ||
                addBusStopReq.getLongitude() == null)
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Fields cannot be empty!");
            }}, HttpStatus.BAD_REQUEST);
        try {
            busStopService.addBusStop(addBusStopReq);
        } catch (NumberPlateAlreadyExistsException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("New Bus stop added!");
        }}, HttpStatus.CREATED);
    }

    @GetMapping("/{busStopName}")
    public ResponseEntity<?> getBusStopDetails(@PathVariable String busStopName) {
        BusStopDetailsRes busStopDetailsRes;
        try {
            BusStopDetails busStopDetails = busStopService.getBusStopDetailsFromName(busStopName);
            busStopDetailsRes = new BusStopDetailsRes() {{
                setBusStopList(Arrays.asList(new BusStopDetails[]{busStopDetails}));
            }};
        } catch (BusNotFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(busStopDetailsRes, HttpStatus.OK);
    }

    @ForAdminOnly
    @PutMapping("/{busStopName}")
    public ResponseEntity<?> updateBusStopDetails(HttpServletRequest request, @PathVariable String busStopName, @RequestBody AddBusStopReq updateBusStopReq) {
        try {
            BusStopDetails busStopDetails = new BusStopDetails();
            ReflectionUtils.mapProperties(updateBusStopReq, busStopDetails, true);
            busStopService.updateBusDetails(busStopName, busStopDetails);
        } catch (NumberPlateAlreadyExistsException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("Bus stop "+busStopName+" updated!");
        }}, HttpStatus.OK);
    }

    @ForAdminOnly
    @DeleteMapping("/{busStopName}")
    public ResponseEntity<?> deleteBusStop(HttpServletRequest request, @PathVariable String busStopName) {
        try {
            busStopService.deleteBusStop(busStopName);
        } catch (BusNotFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("Bus stop "+busStopName+" deleted!");
        }}, HttpStatus.OK);
    }

}
