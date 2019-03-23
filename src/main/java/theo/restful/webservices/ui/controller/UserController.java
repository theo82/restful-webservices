package theo.restful.webservices.ui.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import theo.restful.webservices.service.UserService;
import theo.restful.webservices.shared.dto.UserDto;
import theo.restful.webservices.ui.model.request.UsersDetailsRequestModel;
import theo.restful.webservices.ui.model.response.OperationStatusModel;
import theo.restful.webservices.ui.model.response.RequestOperationStatus;
import theo.restful.webservices.ui.model.response.UserRest;

import java.util.ArrayList;
import java.util.List;

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

        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The object is null");

//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetails,userDto);

        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createUser,returnValue);
        returnValue = modelMapper.map(createUser, UserRest.class);

        return returnValue;
    }


    @PutMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest updateUser(@PathVariable String id, @RequestBody UsersDetailsRequestModel userDetails){

        UserRest returnValue = new UserRest();

        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The object is null");

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails,userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser,returnValue);

        return returnValue;
    }

    @DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public OperationStatusModel deleteUser(@PathVariable String id){

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;

    }

    @GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public List<UserRest> getUsers(@RequestParam(value="page", defaultValue = "0") int page,
                                   @RequestParam(value="limit", defaultValue = "25") int limit){

        List<UserRest> returnValue = new ArrayList<>();

        List<UserDto> users = userService.getUsers(page,limit);

        for(UserDto userDto : users){
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

}
