package theo.restful.webservices.service;

import theo.restful.webservices.shared.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAddresses(String userId);
    AddressDTO getAddress(String addressId);
    AddressDTO updateAddress(String userId, String addressId, AddressDTO address);

}
