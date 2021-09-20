package com.moqi.scheduleminiprogrambackend.controller;


import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.service.MessageService;
import com.moqi.scheduleminiprogrambackend.vo.MessageZoneVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RequestMapping("/message")
@RestController
public class MessageController {

    @Resource
    MessageService messageService;

    @PostMapping("/createMessageZone")
    public JSONObject createMessageZone(@RequestParam(name = "skey") String skey,
                                        @RequestParam(name = "topic") String topic){
        return messageService.createMessageZone(skey,topic);
    }

    @GetMapping("/getMessageZone")
    public JSONObject getMessageZone(@RequestParam(name = "pageIndex") int pageIndex,
                                     @RequestParam(name = "pageSize") int pageSize){
        return messageService.getMessageZone(pageIndex,pageSize);
    }

    @GetMapping("/getMessageZoneBySkey")
    public JSONObject getMessageZoneBySkey(@RequestParam(name = "skey") String skey){
        return messageService.getMessageZoneBySkey(skey);
    }

    @PostMapping("/createMessage")
    public JSONObject createMessage(@RequestParam(name = "skey") String skey,
                                    @RequestParam(name = "content") String content,
                                    @RequestParam(name = "zoneId") int zoneId){
        return messageService.createMessage(skey,content,zoneId);
    }

    @GetMapping("/getMessageByZoneId")
    public JSONObject getMessageByZoneId(@RequestParam(name = "skey") String skey,
                                         @RequestParam(name = "zoneId") int zoneId){
        return messageService.getMessageByZoneId(skey,zoneId);
    }

    @GetMapping("/getMessageZoneByUserId")
    public List<MessageZoneVO> getMessageZoneByUserId(@RequestParam(name = "userId")int userId){
        return messageService.getMessageZoneByUserId(userId);
    }

}
