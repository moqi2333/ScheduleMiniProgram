package com.moqi.scheduleminiprogrambackend.mapperService;

import com.moqi.scheduleminiprogrambackend.po.Message;

import java.util.List;

public interface MessageMapper {

    Integer insert(Message message);

    List<Message> getMessageByZoneId(Integer id);

    Integer updateStatusByZoneId(Integer zoneId,Integer type);

    Integer countNewMessage(Integer zoneId,Integer type);
}
