package nasch.prestabanco_backend.repositories;

import nasch.prestabanco_backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    /**
     * Retrieves a user based on their RUT (unique identifier).
     * @param rut String - the RUT of the user.
     * @return UserEntity - the user associated with the given RUT.
     */
    public UserEntity findByRut(String rut);
}
