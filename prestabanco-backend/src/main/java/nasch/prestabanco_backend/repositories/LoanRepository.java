package nasch.prestabanco_backend.repositories;

import nasch.prestabanco_backend.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    public LoanEntity findByRut(String rut);
    public LoanEntity findByState(String state);
}

