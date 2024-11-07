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
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> listUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserId(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserEntity> saveUser(@RequestParam("rut") String rut,
                                               @RequestParam("name") String name,
                                               @RequestParam("email") String email,
                                               @RequestParam(value = "document", required = false) MultipartFile document) throws IOException {
        byte[] documentData = document.getBytes();

        UserEntity userNew = userService.saveUser(null, rut, name, email, documentData);
        return ResponseEntity.ok(userNew);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable Long id) throws Exception {
        var isDeleted = userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
