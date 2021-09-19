package com.moqi.scheduleminiprogrambackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.service.UserService;
import com.moqi.scheduleminiprogrambackend.vo.ResultVO;
import com.moqi.scheduleminiprogrambackend.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;


    @PostMapping("/login")
    public JSONObject login(@RequestParam String code){
        return userService.login(code);
    }


    @PostMapping("/certification")
    public JSONObject certification(@RequestParam String skey,
                                    @RequestParam String name,
                                    @RequestParam String studentId,
                                    @RequestParam(required = false) String avatar){
        return userService.certification(skey,name,studentId,avatar);
    }


    @GetMapping("/getInformation")
    public ResultVO<UserVO> getInformation(@RequestParam String skey){
        return userService.getInformation(skey);
    }

    @GetMapping("/getAllUsers")
    public JSONObject getAllUsers(){
        return userService.getAllUsers();
    }

}
