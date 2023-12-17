package com.example.bsms.models.responses;

import com.example.bsms.models.BusStopDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BusStopDetailsRes {
    @JsonProperty("bus_stops_list")
    private List<SingleBusStopDetails> busStopList;

    public void setBusStopList(List<BusStopDetails> busStopList) {
        List<SingleBusStopDetails> busStopDetailsList = new ArrayList<>();
        for(BusStopDetails busStopDetails: busStopList) {
            busStopDetailsList.add(new SingleBusStopDetails() {{
                setBusStopName(busStopDetails.getName());
                setCity(busStopDetails.getCity());
                setState(busStopDetails.getState());
                setCountry(busStopDetails.getCountry());
                setPinCode(busStopDetails.getPinCode());
                setLatitude(busStopDetails.getLatitude());
                setLongitude(busStopDetails.getLongitude());
                setIsActive(busStopDetails.getIsActive());
            }});
        }
        this.busStopList = busStopDetailsList;
    }
}

@Data
class SingleBusStopDetails {
    @JsonProperty("bus_stop_name")
    private String busStopName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("latitude")
    private Float latitude;

    @JsonProperty("longitude")
    private Float longitude;

    @JsonProperty("is_active")
    private Boolean isActive;

}
