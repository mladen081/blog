package u.blog.service;

import u.blog.dto.RegistrationDto;
import u.blog.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    User findByEmail(String email);
}
