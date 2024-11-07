package nasch.prestabanco_backend.services;

import nasch.prestabanco_backend.entities.UserEntity;
import nasch.prestabanco_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ArrayList<UserEntity> getAllUsers() {
        return (ArrayList<UserEntity>) userRepository.findAll();
    }

    public UserEntity saveUser(Long id, String rut, String name, String email, byte[] documentData){
        UserEntity user = new UserEntity(id, rut, name, email, documentData);

        return userRepository.save(user);
    }

    public UserEntity getUserById(Long id){
        return userRepository.findById(id).get();
    }

    public UserEntity updateUser(Long id, String rut, String name, String email, byte[] documentData) {
        UserEntity user = new UserEntity(id, rut, name, email, documentData);

        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) throws Exception {
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
