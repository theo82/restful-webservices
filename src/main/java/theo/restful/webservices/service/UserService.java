package theo.restful.webservices.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import theo.restful.webservices.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
}
