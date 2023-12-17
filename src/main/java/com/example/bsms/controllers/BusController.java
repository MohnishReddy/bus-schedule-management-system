package com.example.bsms.controllers;

import com.example.bsms.annotations.ForAdminOnly;
import com.example.bsms.constants.BusTypeConstants;
import com.example.bsms.exceptions.BusNotFoundException;
import com.example.bsms.exceptions.NumberPlateAlreadyExistsException;
import com.example.bsms.models.BusDetails;
import com.example.bsms.models.requests.AddBusDetailsReq;
import com.example.bsms.models.responses.BasicResponse;
import com.example.bsms.models.responses.BusDetailsRes;
import com.example.bsms.services.BusService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bus")
public class BusController {

    @Autowired
    private BusService busService;

    @ForAdminOnly
    @PostMapping("/add")
    public ResponseEntity<?> addBusDetails(HttpServletRequest request, @RequestBody AddBusDetailsReq addBusDetailsReq) {
        if(addBusDetailsReq.getBusNumber() == null || addBusDetailsReq.getNumberPlate() == null)
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("Bus number/ Number plate cannot be empty");
            }}, HttpStatus.BAD_REQUEST);

        if(addBusDetailsReq.getBusType() == null || !BusTypeConstants.isValidBusType(addBusDetailsReq.getBusType()))
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("invalid bus type provided");
            }}, HttpStatus.BAD_REQUEST);
        try {
            BusDetails busDetails = new BusDetails() {{
                setBusNumber(addBusDetailsReq.getBusNumber());
                setNumberPlate(addBusDetailsReq.getNumberPlate());
                setBusType(addBusDetailsReq.getBusType());
            }};
            busService.upsertBusDetails(busDetails);
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
            setMessage("Bus detail added");
        }}, HttpStatus.CREATED);
    }

    @GetMapping("/{busNumber}")
    public ResponseEntity<?> getBusDetail(HttpServletRequest request, @PathVariable String busNumber) {
        List<BusDetails> busDetailsList;
        try {
            busDetailsList = busService.getBusDetailsFromBusNumber(busNumber);
        } catch (BusNotFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("No buses found with number "+busNumber);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BusDetailsRes busDetailsRes = new BusDetailsRes();
        busDetailsRes.setBusNumber(busNumber);
        busDetailsRes.setBusList(busDetailsList);
        return new ResponseEntity<>(busDetailsRes, HttpStatus.OK);
    }

    @ForAdminOnly
    @GetMapping("/{numberPlate}")
    public ResponseEntity<?> getBusDetailFromNumberPlate(HttpServletRequest request, @PathVariable String numberPlate) {
        BusDetails busDetails;
        try {
            busDetails = busService.getBusDetailsFromNumberPlate(numberPlate);
        } catch (BusNotFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage("No buses found with number "+numberPlate);
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        BusDetailsRes busDetailsRes = new BusDetailsRes();
        busDetailsRes.setBusNumber(busDetailsRes.getBusNumber());
        busDetailsRes.setBusList(Arrays.asList(new BusDetails[]{busDetails}));
        return new ResponseEntity<>(busDetailsRes, HttpStatus.OK);
    }

    @ForAdminOnly
    @DeleteMapping("/{numberPlate}")
    public ResponseEntity<?> deleteBusWithNumberPlate(HttpServletRequest request, @PathVariable String numberPlate) {
        try {
            busService.deleteBusDetailsFromNumberPlate(numberPlate);
        } catch (BusNotFoundException e) {
            return new ResponseEntity<>(new BasicResponse() {{
                setErrMessage(e.ErrMessage);
            }}, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new BasicResponse(){{
                setErrMessage(e.getMessage());
            }}, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(new BasicResponse() {{
            setMessage("Bus with number plate "+numberPlate+" deleted!");
        }}, HttpStatus.OK);
    }
}
