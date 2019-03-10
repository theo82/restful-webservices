package theo.restful.webservices.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import theo.restful.webservices.service.UserService;
import theo.restful.webservices.shared.dto.UserDto;
import theo.restful.webservices.ui.model.request.UsersDetailsRequestModel;
import theo.restful.webservices.ui.model.response.ErrorMessages;
import theo.restful.webservices.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping(path = "/{id}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
    public UserRest getUser(@PathVariable String id){

        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UsersDetailsRequestModel userDetails) throws Exception{


        UserRest returnValue = new UserRest();

        if(userDetails.getFirstName().isEmpty()) throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser,returnValue);

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
