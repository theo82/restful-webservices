package theo.restful.webservices.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import theo.restful.webservices.ui.entity.AddressEntity;
import theo.restful.webservices.ui.entity.UserEntity;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {

        List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
        AddressEntity findByAddressId(String addressId);

}
