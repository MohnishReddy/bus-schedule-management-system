package com.example.bsms.models.responses;

import com.example.bsms.models.BusDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusDetailsRes {
    @JsonProperty("bus_number")
    private String busNumber;

    @JsonProperty("bus_list")
    private List<SingleBusDetails> busList;

    public void setBusList(List<BusDetails> busDetailList) {
        List<SingleBusDetails> singleBusDetailList = new ArrayList<>();
        for(BusDetails busDetails: busDetailList) {
            singleBusDetailList.add(new SingleBusDetails() {{
                setNumberPlate(busDetails.getNumberPlate());
                setBusType(busDetails.getBusType());
            }});
        }
        this.busList = singleBusDetailList;
    }
}

@Data
class SingleBusDetails {
    @JsonProperty("number_plate")
    private String numberPlate;

    @JsonProperty("bus_type")
    private String busType;
}
