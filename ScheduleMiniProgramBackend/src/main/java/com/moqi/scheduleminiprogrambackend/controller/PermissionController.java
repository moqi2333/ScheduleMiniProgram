package com.moqi.scheduleminiprogrambackend.controller;


import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.service.PermissionService;
import com.moqi.scheduleminiprogrambackend.vo.PermissionVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    PermissionService permissionService;

    @GetMapping("/getPermission")
    public PermissionVO getPermission(@RequestParam(name = "skey") String skey){
        return permissionService.getPermission(skey);
    }

    @PostMapping("/increaseAppointmentCount")
    public JSONObject increaseAppointmentCount(@RequestParam(name = "skey") String skey){
        return permissionService.increaseAppointmentCount(skey);
    }

    @PostMapping("/increaseStatusCount")
    public JSONObject increaseStatusCount(@RequestParam(name = "skey") String skey){
        return permissionService.increaseStatusCount(skey);
    }

    @PostMapping("/increaseCancelCount")
    public JSONObject increaseCancelCount(@RequestParam(name = "skey") String skey){
        return permissionService.increaseCancelCount(skey);
    }

    @PostMapping("/increaseMessageCount")
    public JSONObject increaseMessageCount(@RequestParam(name = "skey") String skey){
        return permissionService.increaseMessageCount(skey);
    }
}
