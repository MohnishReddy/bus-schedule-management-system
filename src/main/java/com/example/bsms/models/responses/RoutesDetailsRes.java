package com.example.bsms.models.responses;

import com.example.bsms.models.BusStopDetails;
import com.example.bsms.models.RouteDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoutesDetailsRes {
    @JsonProperty("routes_list")
    private List<SingleRouteDetails> routesList;

    public void setBusStopList(List<RouteDetails> routeDetailsList) {
        List<SingleRouteDetails> singleRouteDetailsList = new ArrayList<>();
        for(RouteDetails routeDetails: routeDetailsList) {
            List<String> busStopNameList = new ArrayList<>();
            for(BusStopDetails busStopDetails: routeDetails.getBusStopDetailsList()) {
                busStopNameList.add(busStopDetails.getName());
            }

            singleRouteDetailsList.add(new SingleRouteDetails() {{
                setRouteName(routeDetails.getName());
                setCity(routeDetails.getCity());
                setState(routeDetails.getState());
                setCountry(routeDetails.getCountry());
                setPinCode(routeDetails.getPinCode());
                setBusStopNameList(busStopNameList);
            }});
        }
        this.routesList = singleRouteDetailsList;
    }
}

@Data
class SingleRouteDetails {
    @JsonProperty("route_name")
    private String routeName;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("pin_code")
    private String pinCode;

    @JsonProperty("country")
    private String country;

    @JsonProperty("bus_stop_names_list")
    private List<String> busStopNameList;

}
