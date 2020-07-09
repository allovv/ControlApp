package com.springapp.services;

import com.springapp.entities.Roles;
import com.springapp.entities.UserEntity;
import com.springapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserRepoService {
    //-------------------------------------------------------------
    @Autowired
    UserRepository userRepository;
    @Autowired
    FolderRepoService folderRepoService;
    @Autowired
    PasswordEncoder passwordEncoder;

    //-------------------------------------------------------------

    public boolean registerUser(UserEntity userEntity) {
        //проверка при регистрации по email TODO: регистрация по username
        UserEntity userEntityDB = userRepository.findByUsername(userEntity.getUsername());

        if (userEntityDB != null) {
            return false;
        }

        //кодирование пароля
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        //установка ролей TODO: по умолчанию все просто USER
        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.USER);
        userEntity.setRoles(roles);

        //сохранение пользователя
        userRepository.save(userEntity);
        return true;
    }

    public boolean registerAdmin() { //TODO: временный метод для добавления администратора
        if (userRepository.findByUsername("admin") != null) {
            return false;
        }
        UserEntity userEntity = new UserEntity("admin", "admin", "admin@admin.com");

        //кодирование пароля
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        //установка ролей
        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ADMIN);
        userEntity.setRoles(roles);

        //сохранение пользователя
        userRepository.save(userEntity);
        return true;
    }

    //delete --------------------------------------------
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        folderRepoService.deleteAllByCreatorId(id);
    }

    public void deleteAll() {
        userRepository.deleteAll();
        folderRepoService.deleteAll();
    }

    //save --------------------------------------------
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    //find --------------------------------------------
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(new UserEntity(" ", " ", " ")); //TODO: если пользователя нет в БД, возвр. пустой
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

}
