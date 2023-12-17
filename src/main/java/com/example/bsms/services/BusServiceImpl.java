package com.example.bsms.services;

import com.example.bsms.exceptions.BusNotFoundException;
import com.example.bsms.exceptions.NumberPlateAlreadyExistsException;
import com.example.bsms.models.BusDetails;
import com.example.bsms.models.entities.Buses;
import com.example.bsms.repositories.BusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusesRepository busesRepository;

    @Override
    public BusDetails getBusDetails(int busId) throws Exception {
        Optional<Buses> busOpt = busesRepository.findById(busId);

        if(busOpt.isEmpty())
            throw new BusNotFoundException();

        return mapBusEntityToModel(busOpt.get());
    }

    @Override
    public List<BusDetails> getBusDetailsFromBusNumber(String busNumber) throws Exception {
        List<Buses> busList = busesRepository.findByBusNumber(busNumber);

        if(busList.isEmpty())
            throw new BusNotFoundException();

        List<BusDetails> busDetailsList = new ArrayList<>();
        for(Buses bus: busList) {
            busDetailsList.add(mapBusEntityToModel(bus));
        }

        return busDetailsList;
    }

    @Override
    public BusDetails getBusDetailsFromNumberPlate(String numberPlate) throws Exception {
        Optional<Buses> busOpt = busesRepository.findByNumberPlate(numberPlate);

        if(busOpt.isEmpty())
            throw new BusNotFoundException();

        return mapBusEntityToModel(busOpt.get());
    }

    @Override
    public void upsertBusDetails(BusDetails busDetails) throws Exception {
        Optional<Buses> busOpt = busesRepository.findByNumberPlate(busDetails.getBusNumber());

        if(busOpt.isPresent())
            throw new NumberPlateAlreadyExistsException();

        busesRepository.save(mapBusModelToEntity(busDetails));
    }

    @Override
    public void deleteBusDetailsFromNumberPlate(String numberPlate) throws Exception {
        Optional<Buses> busOpt = busesRepository.findByNumberPlate(numberPlate);

        if(busOpt.isEmpty())
            throw new BusNotFoundException();

        busesRepository.deleteById(busOpt.get().getId());
    }

    @Override
    public void deleteAllBusesWithBusNumber(String busNumber) throws Exception {
        busesRepository.deleteAllByBusNumber(busNumber);
    }

    private BusDetails mapBusEntityToModel(Buses bus) {
        BusDetails busDetails = new BusDetails();
        busDetails.setBusNumber(bus.getBusNumber());
        busDetails.setNumberPlate(bus.getNumberPlate());
        busDetails.setBusType(bus.getType());
        return busDetails;
    }

    private Buses mapBusModelToEntity(BusDetails busDetails) {
        Buses bus = new Buses();
        bus.setBusNumber(busDetails.getBusNumber());
        bus.setNumberPlate(busDetails.getNumberPlate());
        bus.setType(busDetails.getBusType());
        return bus;
    }
}
