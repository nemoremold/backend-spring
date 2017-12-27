package org.latheild.user.service;

import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.UserErrorCode;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.dao.UserRepository;
import org.latheild.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public boolean isEmailUsed(String email) {
        if (userRepository.countByEmail(email) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (isEmailUsed(registerDTO.getEmail())) {
            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassword());
            return user;
        } else {
            throw new AppBusinessException(
                    UserErrorCode.EmailExist, String.format("Email %s has already been used.",
                    registerDTO.getEmail())
            );
        }
    }
}
