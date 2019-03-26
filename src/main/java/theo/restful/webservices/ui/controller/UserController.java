package theo.restful.webservices.ui.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import theo.restful.webservices.service.AddressService;
import theo.restful.webservices.service.UserService;
import theo.restful.webservices.shared.dto.AddressDTO;
import theo.restful.webservices.shared.dto.UserDto;
import theo.restful.webservices.ui.model.request.AddressesRequestModel;
import theo.restful.webservices.ui.model.request.UsersDetailsRequestModel;
import theo.restful.webservices.ui.model.response.AddressesRest;
import theo.restful.webservices.ui.model.response.OperationStatusModel;
import theo.restful.webservices.ui.model.response.RequestOperationStatus;
import theo.restful.webservices.ui.model.response.UserRest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressService addressesService;


    @GetMapping(path = "/{id}",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
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
    //http://localhost:8080/restful-webservices/users/id/addresses
    @GetMapping(path = "/{id}/addresses",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
    public List<AddressesRest> getUserAddresses(@PathVariable String id){

        List<AddressesRest> returnValue = new ArrayList<>();

        List<AddressDTO> addressesDTO = addressesService.getAddresses(id);

        if(addressesDTO != null && !addressesDTO.isEmpty()){
            Type listType = new TypeToken<List<AddressesRest>>(){}.getType();
            returnValue = new ModelMapper().map(addressesDTO, listType);
        }

        return returnValue;
    }

    @GetMapping(path="/{userId}/addresses/{addressId}",
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE} )
    public AddressesRest getUserAddress(@PathVariable String userId, @PathVariable String addressId){

        AddressDTO addressesDto = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();

        Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, addressId)).withSelfRel();
        Link userLink = linkTo(methodOn(UserController.class).getUser(userId)).withRel("user");
        Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");

        AddressesRest addressesRestModel = modelMapper.map(addressesDto,AddressesRest.class);

        addressesRestModel.add(addressLink);
        addressesRestModel.add(userLink);
        addressesRestModel.add(addressesLink);


        return addressesRestModel;
    }

    @PutMapping(path="/{userId}/addresses/{addressId}",
            consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AddressesRest updateAddress(@PathVariable String userId, @PathVariable String addressId, @RequestBody AddressesRequestModel addressDetails){

          AddressesRest returnValue = new AddressesRest();

          if(addressDetails.getCity().isEmpty()
                  || addressDetails.getCountry().isEmpty()
                  || addressDetails.getPostalCode().isEmpty()
                  || addressDetails.getStreetName().isEmpty()
                  || addressDetails.getType().isEmpty()) throw new NullPointerException("The object is null");

          AddressDTO addressDto = new AddressDTO();
          BeanUtils.copyProperties(addressDetails, addressDto);

          AddressDTO updatedAddress = addressService.updateAddress(userId, addressId, addressDto);
          BeanUtils.copyProperties(updatedAddress,returnValue);

          return returnValue;
    }


}
