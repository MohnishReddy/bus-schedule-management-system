package com.example.bsms.services;

import com.example.bsms.models.ScheduleDetails;
import com.example.bsms.models.requests.AddScheduleReq;

public interface ScheduleService {
    void addSchedule(AddScheduleReq addScheduleReq) throws Exception;
    ScheduleDetails getSchedule(String busName, String routeName) throws Exception;
    void updateSchedule(AddScheduleReq updateScheduleReq) throws Exception;
    void deleteSchedule(String busName, String routeName) throws Exception;
}
