package com.moqi.scheduleminiprogrambackend.mapperService;

import com.moqi.scheduleminiprogrambackend.po.MessageZone;

import java.util.List;

public interface MessageZoneMapper {

    Integer insert(MessageZone messageZone);

    List<MessageZone> getAllMessageZone();

    List<MessageZone> getMessageZoneByOpenId(String openId);

    MessageZone getMessageZoneById(Integer id);

    List<MessageZone> getMessageZoneByOpenIds(List<String> openId);
}
