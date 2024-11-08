package nasch.prestabanco_backend.services;

import nasch.prestabanco_backend.entities.UserEntity;
import nasch.prestabanco_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository; // Injects the UserRepository dependency to access database operations.

    /**
     * Retrieves all users from the database.
     * @return ArrayList<UserEntity> - a list of all users in the database.
     */
    public ArrayList<UserEntity> getAllUsers() {
        return (ArrayList<UserEntity>) userRepository.findAll();
    }

    /**
     * Saves a new user in the database.
     * @param id Long - unique identifier of the user.
     * @param rut String - unique identification number for the user.
     * @param name String - name of the user.
     * @param email String - email address of the user.
     * @param documentData byte[] - binary data for user documents.
     * @return UserEntity - the saved user entity.
     */
    public UserEntity saveUser(Long id, String rut, String name, String email, byte[] documentData){
        UserEntity user = new UserEntity(id, rut, name, email, documentData);
        return userRepository.save(user);
    }

    /**
     * Finds and retrieves a user by their ID.
     * @param id Long - the unique identifier of the user.
     * @return UserEntity - the user entity if found.
     */
    public UserEntity getUserById(Long id){
        return userRepository.findById(id).get();
    }

    /**
     * Updates an existing user in the database.
     * @param id Long - unique identifier of the user.
     * @param rut String - unique identification number for the user.
     * @param name String - name of the user.
     * @param email String - email address of the user.
     * @param documentData byte[] - binary data for user documents.
     * @return UserEntity - the updated user entity.
     */
    public UserEntity updateUser(Long id, String rut, String name, String email, byte[] documentData) {
        UserEntity user = new UserEntity(id, rut, name, email, documentData);
        return userRepository.save(user);
    }

    /**
     * Deletes a user by their ID.
     * @param id Long - the unique identifier of the user to delete.
     * @return boolean - true if deletion was successful, otherwise throws an exception.
     * @throws Exception - throws if there is an issue with deletion.
     */
    public boolean deleteUser(Long id) throws Exception {
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}