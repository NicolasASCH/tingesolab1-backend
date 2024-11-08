package nasch.prestabanco_backend.services;

import nasch.prestabanco_backend.entities.LoanEntity;
import nasch.prestabanco_backend.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository; // Injects LoanRepository to perform database operations.

    /**
     * Retrieves all loans from the database.
     * @return ArrayList<LoanEntity> - a list of all loans in the database.
     */
    public ArrayList<LoanEntity> getAllLoans() {
        return (ArrayList<LoanEntity>) loanRepository.findAll();
    }

    /**
     * Finds and retrieves a loan by the user's RUT (unique ID).
     * @param rut String - unique identification number of the user.
     * @return LoanEntity - the loan entity if found.
     */
    public LoanEntity getByRut(String rut) {
        return loanRepository.findByRut(rut);
    }

    /**
     * Finds and retrieves a loan by its state.
     * @param state String - the state of the loan.
     * @return LoanEntity - the loan entity if found.
     */
    public LoanEntity getByState(String state) {
        return loanRepository.findByState(state);
    }

    /**
     * Saves a new loan in the database.
     * @param id Long - unique identifier of the loan.
     * @param rut String - unique identification number of the user.
     * @param type String - type of the loan.
     * @param property_price long - price of the property associated with the loan.
     * @param amount long - loan amount.
     * @param term int - loan term in years.
     * @param interest_rate float - interest rate for the loan.
     * @param income long - income of the user.
     * @param working_time int - working time of the user in years.
     * @param age int - age of the user.
     * @param state String - state of the loan.
     * @param document1Data, document2Data, document3Data, document4Data byte[] - binary data for loan documents.
     * @return LoanEntity - the saved loan entity.
     */
    public LoanEntity saveLoan(Long id, String rut, String type, long property_price, long amount, int term,
                               float interest_rate, long income, int working_time, int age, String state,
                               byte[] document1Data, byte[] document2Data, byte[] document3Data, byte[] document4Data) {
        LoanEntity loan = new LoanEntity(id, rut, type, property_price, amount, term,
                interest_rate, income, working_time, age, state, document1Data, document2Data, document3Data, document4Data);

        return loanRepository.save(loan);
    }

    /**
     * Updates an existing loan in the database.
     * @param id Long - unique identifier of the loan.
     * @param rut String - unique identification number of the user.
     * @param type String - type of the loan.
     * @param property_price long - price of the property associated with the loan.
     * @param amount long - loan amount.
     * @param term int - loan term in years.
     * @param interest_rate float - interest rate for the loan.
     * @param income long - income of the user.
     * @param working_time int - working time of the user in years.
     * @param age int - age of the user.
     * @param state String - state of the loan.
     * @param document1Data, document2Data, document3Data, document4Data byte[] - binary data for loan documents.
     * @return LoanEntity - the updated loan entity.
     */
    public LoanEntity updateLoan(Long id, String rut, String type, long property_price, long amount, int term,
                                 float interest_rate, long income, int working_time, int age, String state,
                                 byte[] document1Data, byte[] document2Data, byte[] document3Data, byte[] document4Data) {
        LoanEntity loan = new LoanEntity(id, rut, type, property_price, amount, term,
                interest_rate, income, working_time, age, state, document1Data, document2Data, document3Data, document4Data);

        return loanRepository.save(loan);
    }

    /**
     * Deletes a loan by its ID.
     * @param id Long - unique identifier of the loan to delete.
     * @return boolean - true if deletion was successful, otherwise throws an exception.
     * @throws Exception - throws if there is an issue with deletion.
     */
    public boolean deleteLoan(Long id) throws Exception {
        try {
            loanRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * Simulates a mortgage credit calculation based on the loan amount, interest rate, and term.
     * @param amount long - loan amount.
     * @param interest_rate float - annual interest rate.
     * @param term int - loan term in years.
     * @return double - calculated monthly mortgage payment.
     */
    public double mortgageCreditSimulation(long amount, float interest_rate, int term) {
        float r = (interest_rate / 12) / 100;
        int n = term * 12;

        return amount * ((r * Math.pow((1 + r), n)) / (Math.pow((1 + r), n) - 1));
    }

    /**
     * Calculates the total cost of the loan, including mortgage payments, insurance, and administrative fees.
     * @param amount long - loan amount.
     * @param interest_rate float - annual interest rate.
     * @param term int - loan term in years.
     * @param desgravament float - desgravament (life insurance) rate.
     * @param admin_com_por float - administrative commission percentage.
     * @param secure int... - optional costs for additional insurance policies.
     * @return double - total monthly cost of the loan.
     */
    public double totalCostCalculation(long amount, float interest_rate, int term, float desgravament, float admin_com_por, int... secure) {
        double monthly_fee = mortgageCreditSimulation(amount, interest_rate, term);

        float sec_desgravament = amount * desgravament;

        double admin_com = amount * admin_com_por;

        double monthly_cost = monthly_fee + sec_desgravament;
        for (int i : secure) monthly_cost += i;

        return monthly_cost + admin_com;
    }
}