package theo.restful.webservices.ui.controller;

import org.springframework.web.bind.annotation.*;
import theo.restful.webservices.ui.model.request.UsersDetailsRequestModel;
import theo.restful.webservices.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {


    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UsersDetailsRequestModel userDetails){
        return null;
    }

    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser(){
        return "delete user was called";
    }

}
