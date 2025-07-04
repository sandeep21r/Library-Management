package com.LibraryApi.LibraryManagement.Controller;


import com.LibraryApi.LibraryManagement.Request.UserTenantRequest;
import com.LibraryApi.LibraryManagement.Response.APIResponse;
import com.LibraryApi.LibraryManagement.Request.UserRequest;
import com.LibraryApi.LibraryManagement.Response.UserResponse;
import com.LibraryApi.LibraryManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/library/user")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/registerUser")
    public ResponseEntity<APIResponse<UserResponse>> registerUser(@RequestBody UserRequest userRequest){
            UserResponse userResponse = userService.registerUser(userRequest);
            APIResponse<UserResponse> apiResponse = new APIResponse<>(true,"User Successfully Register To Application",userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponse<Map<String,String>>>  login(@RequestBody UserTenantRequest userTenantRequest){
        String token = userService.login(userTenantRequest);
        Map<String,String> resp = new HashMap<>();
        resp.put("Access Token",token);
        APIResponse<Map<String,String>> apiResponse = new APIResponse<>(true,"User Successfully Login",resp);
        return   new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<APIResponse<List<UserResponse>>> listUser(){
        List<UserResponse> userEntityList = userService.findAllUser();
        APIResponse<List<UserResponse>> apiResponse=new APIResponse<>(true,"Successfully Find all user",userEntityList);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserResponse>> getUser(@PathVariable("id") UUID id){
        UserResponse userResponse = userService.getUser(id);
        APIResponse<UserResponse> apiResponse=new APIResponse<>(true,"Successfully Find user",userResponse);
        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
    }



//    @PostMapping("/registerToTenant")
//    public  ResponseEntity<APIResponse<UserTenantResponse>> registerTotTenant(@RequestBody UserTenantRequest userTenantRequest){
//        UserTenantResponse userTenantResponse = userRoleTenantService.registerToTenant(userTenantRequest);
//        APIResponse<UserTenantResponse> apiResponse = new APIResponse<>(true,"User Successfully Register To Tenant",userTenantResponse);
//        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
//    }


//    @GetMapping("/userTenant/list")
//    public ResponseEntity<APIResponse< List<UserTenantResponse> >>  getUserTenant(){
//        List<UserTenantResponse> userTenantResponses = userService.getUserTenant();
//        APIResponse<  List<UserTenantResponse> > apiResponse=new APIResponse<>(true,"Successfully Find user",userTenantResponses);
//        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
//    }
//    @GetMapping("/userTenant/{id}")
//    public ResponseEntity<APIResponse< UserTenantResponse> >  getUserTenantByID(@PathVariable("id") UUID id){
//       UserTenantResponse userTenantResponses = userService.getUserTenantByID(id);
//        APIResponse< UserTenantResponse > apiResponse=new APIResponse<>(true,"Successfully Find user",userTenantResponses);
//        return new ResponseEntity<>(apiResponse,HttpStatus.FOUND);
//    }
//

 }
