package theo.restful.webservices.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theo.restful.webservices.io.repositories.AddressRepository;
import theo.restful.webservices.io.repositories.UserRepository;
import theo.restful.webservices.service.AddressService;
import theo.restful.webservices.shared.dto.AddressDTO;
import theo.restful.webservices.ui.entity.AddressEntity;
import theo.restful.webservices.ui.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDTO> getAddresses(String userId) {

        List<AddressDTO> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);


        if(userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);

        for(AddressEntity addressEntity : addresses){
            returnValue.add(modelMapper.map(addressEntity, AddressDTO.class));
        }

        return returnValue;
    }
}
