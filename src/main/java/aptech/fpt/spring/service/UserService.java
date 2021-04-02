package aptech.fpt.spring.service;


import aptech.fpt.spring.entity.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
