package nasch.prestabanco_backend.repositories;

import nasch.prestabanco_backend.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    /**
     * Retrieves a loan based on the applicant's RUT (unique identifier).
     * @param rut String - the RUT of the applicant.
     * @return LoanEntity - the loan associated with the given RUT.
     */
    public LoanEntity findByRut(String rut);

    /**
     * Retrieves a loan based on its state.
     * @param state String - the current state of the loan.
     * @return LoanEntity - the loan associated with the given state.
     */
    public LoanEntity findByState(String state);
}


