package theo.restful.webservices.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import theo.restful.webservices.service.UserService;
import theo.restful.webservices.shared.dto.UserDto;
import theo.restful.webservices.ui.model.request.UsersDetailsRequestModel;
import theo.restful.webservices.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping
    public String getUser(){
        return "get user was called";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UsersDetailsRequestModel userDetails){


        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(userDto,createUser);

        return returnValue;
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
