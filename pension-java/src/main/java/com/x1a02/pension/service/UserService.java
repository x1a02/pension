package com.x1a02.pension.service;

import com.x1a02.pension.entity.User;
import java.util.List;

public interface UserService {

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Long id);

    User getUserById(Long id);

    User getUserByUsername(String username);

    List<User> getAllUsers();

    List<User> getStaffUsers();

    List<User> getFamilyUsers();

    User login(String username, String password);
}
