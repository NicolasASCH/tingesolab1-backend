package nasch.prestabanco_backend.controllers;

import nasch.prestabanco_backend.entities.UserEntity;
import nasch.prestabanco_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService; // Injects the UserService dependency to access user-related operations.

    /**
     * Retrieves a list of all users.
     * @return ResponseEntity<List<UserEntity>> - an HTTP response containing the list of all users.
     */
    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> listUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a specific user by their ID.
     * @param id Long - unique identifier of the user.
     * @return ResponseEntity<UserEntity> - an HTTP response containing the user entity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserId(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Saves a new user to the system.
     * @param rut String - unique identification number for the user.
     * @param name String - name of the user.
     * @param email String - email address of the user.
     * @param document MultipartFile - optional document file associated with the user.
     * @return ResponseEntity<UserEntity> - an HTTP response containing the saved user entity.
     * @throws IOException - if there is an error reading the document file.
     */
    @PostMapping("/")
    public ResponseEntity<UserEntity> saveUser(@RequestParam("rut") String rut,
                                               @RequestParam("name") String name,
                                               @RequestParam("email") String email,
                                               @RequestParam(value = "document", required = false) MultipartFile document) throws IOException {
        byte[] documentData = document.getBytes();

        UserEntity userNew = userService.saveUser(null, rut, name, email, documentData);
        return ResponseEntity.ok(userNew);
    }

    /**
     * Updates an existing user's details.
     * @param id Long - unique identifier of the user.
     * @param rut String - unique identification number for the user.
     * @param name String - name of the user.
     * @param email String - email address of the user.
     * @param document MultipartFile - optional document file associated with the user.
     * @return ResponseEntity<UserEntity> - an HTTP response containing the updated user entity.
     * @throws IOException - if there is an error reading the document file.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id,
                                                 @RequestParam("rut") String rut,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("email") String email,
                                                 @RequestParam(value = "document", required = false) MultipartFile document) throws IOException {
        byte[] documentData = document != null ? document.getBytes() : null;

        UserEntity userUpdated = userService.updateUser(id, rut, name, email, documentData);
        return ResponseEntity.ok(userUpdated);
    }

    /**
     * Deletes a user by their ID.
     * @param id Long - unique identifier of the user to delete.
     * @return ResponseEntity<Boolean> - an HTTP response with no content if deletion was successful.
     * @throws Exception - if there is an issue with deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id) throws Exception {
        var isDeleted = userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}