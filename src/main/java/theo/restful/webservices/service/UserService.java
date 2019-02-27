package theo.restful.webservices.service;

import theo.restful.webservices.shared.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto user);
}
