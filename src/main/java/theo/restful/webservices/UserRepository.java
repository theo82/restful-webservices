package theo.restful.webservices;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import theo.restful.webservices.ui.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {

}
