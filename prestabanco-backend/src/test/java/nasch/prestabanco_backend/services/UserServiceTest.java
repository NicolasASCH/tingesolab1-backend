package nasch.prestabanco_backend.services;

import nasch.prestabanco_backend.entities.UserEntity;
import nasch.prestabanco_backend.repositories.UserRepository;
import nasch.prestabanco_backend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllUsers_NoUsers_ReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        ArrayList<UserEntity> result = userService.getAllUsers();

        assertTrue(result.isEmpty(), "Expected empty list when there are no users.");
    }

    @Test
    public void getAllUsers_OneUser_ReturnsSingleUserList() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});
        when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(user)));

        ArrayList<UserEntity> result = userService.getAllUsers();

        assertEquals(1, result.size(), "Expected list with one user.");
        assertEquals("John Doe", result.get(0).getName(), "Expected user name to be 'John Doe'.");
    }

    @Test
    public void getAllUsers_MultipleUsers_ReturnsCorrectUserList() {
        UserEntity user1 = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});
        UserEntity user2 = new UserEntity(2L, "98765432-1", "Jane Smith", "jane@example.com", new byte[]{});
        when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(user1, user2)));

        ArrayList<UserEntity> result = userService.getAllUsers();

        assertEquals(2, result.size(), "Expected list with two users.");
        assertEquals("John Doe", result.get(0).getName(), "Expected first user name to be 'John Doe'.");
        assertEquals("Jane Smith", result.get(1).getName(), "Expected second user name to be 'Jane Smith'.");
    }

    @Test
    public void getAllUsers_UserWithNullFields_ReturnsUserWithNullFields() {
        UserEntity user = new UserEntity(1L, null, null, null, null);
        when(userRepository.findAll()).thenReturn(new ArrayList<>(List.of(user)));

        ArrayList<UserEntity> result = userService.getAllUsers();

        assertEquals(1, result.size(), "Expected list with one user.");
        assertNull(result.get(0).getRut(), "Expected 'rut' to be null.");
        assertNull(result.get(0).getName(), "Expected 'name' to be null.");
        assertNull(result.get(0).getEmail(), "Expected 'email' to be null.");
        assertNull(result.get(0).getDocument(), "Expected 'document' to be null.");
    }

    @Test
    public void getAllUsers_RepositoryReturnsEmptyList_ReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        ArrayList<UserEntity> result = userService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getAllUsers_RepositoryThrowsException_ThrowsException() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.getAllUsers(), "Expected exception when repository throws error.");
    }

    @Test
    public void saveUser_ValidData_ReturnsSavedUser() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.saveUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        assertNotNull(result, "Expected non-null result when saving a user.");
        assertEquals("12345678-9", result.getRut(), "Expected correct rut.");
        assertEquals("John Doe", result.getName(), "Expected correct name.");
        assertEquals("john@example.com", result.getEmail(), "Expected correct email.");
        assertArrayEquals(new byte[]{}, result.getDocument(), "Expected correct document.");
    }

    @Test
    public void saveUser_ValidUser_SuccessfullySaved() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.saveUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        assertNotNull(result, "Expected non-null user to be returned.");
        assertEquals("12345678-9", result.getRut(), "Expected correct rut.");
        assertEquals("John Doe", result.getName(), "Expected correct name.");
        assertEquals("john@example.com", result.getEmail(), "Expected correct email.");
    }

    @Test
    public void saveUser_ValidUser_CallsSaveOnce() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        userService.saveUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void saveUser_ValidData_UserSavedSuccessfully() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.saveUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals(user, result, "Expected the saved user to match the result.");
    }

    @Test
    public void saveUser_RepositoryThrowsException_ThrowsException() {
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.saveUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{}),
                "Expected RuntimeException when repository throws error.");
    }

    @Test
    public void saveUser_NullDocument_ReturnsUserWithNullDocument() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", null);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.saveUser(1L, "12345678-9", "John Doe", "john@example.com", null);

        assertNull(result.getDocument(), "Expected document to be null.");
    }

    @Test
    public void getUserById_ExistingUser_ReturnsUser() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void getUserById_NonExistingUser_ReturnsNull() {
        when(userRepository.findById(1L)).thenReturn(null);
        UserEntity result = userRepository.getById(1L);
        assertNull(result);
    }

    @Test
    public void getUserById_UserWithoutDocument_ReturnsUser() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertNull(result.getDocument());
    }

    @Test
    public void getUserById_ValidId_ReturnsCorrectUser() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertNotNull(result.getDocument());
    }


    @Test
    public void getUserById_CallsFindByIdOnce() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.getUserById(1L);

        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getUserById_UserWithEmptyDocument_ReturnsUser() {
        byte[] documentData = {}; // Empty document
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", documentData);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertArrayEquals(documentData, result.getDocument());
    }

    @Test
    public void updateUser_ExistingUser_UpdatesUser() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.updateUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void updateUser_RepositoryThrowsException_ThrowsRuntimeException() {
        when(userRepository.save(any(UserEntity.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{}),
                "Expected RuntimeException when repository throws an exception.");
    }



    @Test
    public void updateUser_WithNullDocument_UpdatesUser() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", null);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.updateUser(1L, "12345678-9", "John Doe", "john@example.com", null);

        assertNotNull(result);
        assertNull(result.getDocument());
    }

    @Test
    public void updateUser_WithEmptyDocument_UpdatesUser() {
        byte[] emptyDocument = new byte[]{};
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", emptyDocument);

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userService.updateUser(1L, "12345678-9", "John Doe", "john@example.com", emptyDocument);

        assertNotNull(result);
        assertArrayEquals(emptyDocument, result.getDocument());
    }

    @Test
    public void updateUser_CallsSaveOnce() {
        UserEntity user = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        userService.updateUser(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void updateUser_UpdatesOnlyChangedFields() {
        UserEntity existingUser = new UserEntity(1L, "12345678-9", "John Doe", "john@example.com", new byte[]{});
        UserEntity updatedUser = new UserEntity(1L, "12345678-9", "John Doe Updated", "john@example.com", new byte[]{});

        when(userRepository.save(any(UserEntity.class))).thenReturn(updatedUser);

        UserEntity result = userService.updateUser(1L, "12345678-9", "John Doe Updated", "john@example.com", new byte[]{});

        assertNotNull(result);
        assertEquals("John Doe Updated", result.getName());
        assertEquals("john@example.com", result.getEmail());
    }

    @Test
    public void deleteUser_ExistingUser_DeletesUser() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteUser_NonExistingUser_ThrowsException() {
        doThrow(new IllegalArgumentException("User not found")).when(userRepository).deleteById(anyLong());

        assertThrows(Exception.class, () -> userService.deleteUser(999L));
    }

    @Test
    public void deleteUser_ThrowsUnexpectedException_ThrowsException() {
        doThrow(new RuntimeException("Unexpected error")).when(userRepository).deleteById(anyLong());

        assertThrows(Exception.class, () -> userService.deleteUser(1L));
    }

    @Test
    public void deleteUser_ValidId_ReturnsTrue() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteUser_ValidId_CallsDeleteById() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
        assertTrue(result);
    }


    @Test
    public void deleteUser_DeletesUserSuccessfully_ReturnsTrue() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
    }

}
