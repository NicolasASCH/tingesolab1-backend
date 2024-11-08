package nasch.prestabanco_backend.controllers;

import nasch.prestabanco_backend.entities.LoanEntity;
import nasch.prestabanco_backend.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin("*")
public class LoanController {
    @Autowired
    LoanService loanService; // Injects the LoanService dependency to access loan-related operations.

    /**
     * Retrieves a list of all loans.
     * @return ResponseEntity<List<LoanEntity>> - an HTTP response containing the list of all loans.
     */
    @GetMapping("/")
    public ResponseEntity<List<LoanEntity>> listLoan() {
        List<LoanEntity> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    /**
     * Retrieves a loan by its state.
     * @param state String - the current state of the loan.
     * @return ResponseEntity<LoanEntity> - an HTTP response containing the loan entity.
     */
    @GetMapping("/{state}")
    public ResponseEntity<LoanEntity> getLoanState(@PathVariable String state) {
        LoanEntity loan = loanService.getByState(state);
        return ResponseEntity.ok(loan);
    }

    /**
     * Retrieves a loan by the applicant's RUT (unique ID number).
     * @param rut String - the RUT of the applicant.
     * @return ResponseEntity<LoanEntity> - an HTTP response containing the loan entity.
     */
    @GetMapping("/{rut}")
    public ResponseEntity<LoanEntity> getLoanRut(@PathVariable String rut) {
        LoanEntity loan = loanService.getByRut(rut);
        return ResponseEntity.ok(loan);
    }

    /**
     * Saves a new loan to the system.
     * @param rut String - the RUT of the applicant.
     * @param type String - type of loan.
     * @param property_price long - price of the property involved in the loan.
     * @param amount long - loan amount.
     * @param term int - term of the loan in years.
     * @param interest_rate float - interest rate of the loan.
     * @param income long - applicant's income.
     * @param working_time int - years applicant has been employed.
     * @param age int - applicant's age.
     * @param state String - state of the loan application.
     * @param document1, document2, document3, document4 MultipartFile - optional supporting documents.
     * @return ResponseEntity<LoanEntity> - an HTTP response containing the saved loan entity.
     * @throws IOException - if there is an error reading the document files.
     */
    @PostMapping("/")
    public ResponseEntity<LoanEntity> saveLoan(@RequestParam("rut") String rut,
                                               @RequestParam("type") String type,
                                               @RequestParam("property_price") long property_price,
                                               @RequestParam("amount") long amount,
                                               @RequestParam("term") int term,
                                               @RequestParam("interest_rate") float interest_rate,
                                               @RequestParam("income") long income,
                                               @RequestParam("working_time") int working_time,
                                               @RequestParam("age") int age,
                                               @RequestParam("state") String state,
                                               @RequestParam(value = "document1", required = false) MultipartFile document1,
                                               @RequestParam(value = "document2", required = false) MultipartFile document2,
                                               @RequestParam(value = "document3", required = false) MultipartFile document3,
                                               @RequestParam(value = "document4", required = false) MultipartFile document4) throws IOException {
        byte[] document1Data = document1 != null ? document1.getBytes() : null;
        byte[] document2Data = document2 != null ? document2.getBytes() : null;
        byte[] document3Data = document3 != null ? document3.getBytes() : null;
        byte[] document4Data = document4 != null ? document4.getBytes() : null;

        LoanEntity loanNew = loanService.saveLoan(null, rut, type, property_price, amount, term,
                interest_rate, income, working_time, age, state, document1Data, document2Data, document3Data, document4Data);
        return ResponseEntity.ok(loanNew);
    }

    /**
     * Updates an existing loan with new details.
     * @param id Long - unique identifier of the loan.
     * @param rut, type, property_price, amount, term, interest_rate, income, working_time, age, state - new loan details.
     * @param document1, document2, document3, document4 MultipartFile - optional updated supporting documents.
     * @return ResponseEntity<LoanEntity> - an HTTP response containing the updated loan entity.
     * @throws IOException - if there is an error reading the document files.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoanEntity> updateUser(@PathVariable Long id,
                                                 @RequestParam("rut") String rut,
                                                 @RequestParam("type") String type,
                                                 @RequestParam("property_price") long property_price,
                                                 @RequestParam("amount") long amount,
                                                 @RequestParam("term") int term,
                                                 @RequestParam("interest_rate") float interest_rate,
                                                 @RequestParam("income") long income,
                                                 @RequestParam("working_time") int working_time,
                                                 @RequestParam("age") int age,
                                                 @RequestParam("state") String state,
                                                 @RequestParam(value = "document1", required = false) MultipartFile document1,
                                                 @RequestParam(value = "document2", required = false) MultipartFile document2,
                                                 @RequestParam(value = "document3", required = false) MultipartFile document3,
                                                 @RequestParam(value = "document4", required = false) MultipartFile document4) throws IOException {
        byte[] document1Data = document1 != null ? document1.getBytes() : null;
        byte[] document2Data = document2 != null ? document2.getBytes() : null;
        byte[] document3Data = document3 != null ? document3.getBytes() : null;
        byte[] document4Data = document4 != null ? document4.getBytes() : null;

        LoanEntity loanUpdated = loanService.updateLoan(id, rut, type, property_price, amount, term,
                interest_rate, income, working_time, age, state, document1Data, document2Data, document3Data, document4Data);
        return ResponseEntity.ok(loanUpdated);
    }

    /**
     * Deletes a loan by its ID.
     * @param id Long - unique identifier of the loan to delete.
     * @return ResponseEntity<Boolean> - an HTTP response with no content if deletion was successful.
     * @throws Exception - if there is an issue with deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLoanById(@PathVariable Long id) throws Exception {
        var isDeleted = loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Simulates a mortgage credit calculation based on the amount, interest rate, and term.
     * @param amount long - loan amount.
     * @param interest_rate float - annual interest rate.
     * @param term int - term in years.
     * @return ResponseEntity<Double> - an HTTP response containing the monthly payment.
     */
    @PostMapping("/simulation")
    public ResponseEntity<Double> simulateCredit(@RequestParam("amount") long amount,
                                                 @RequestParam("interest_rate") float interest_rate,
                                                 @RequestParam("term") int term) {
        return ResponseEntity.ok(loanService.mortgageCreditSimulation(amount, interest_rate, term));
    }

    /**
     * Calculates the total cost of the loan including additional charges.
     * @param amount long - loan amount.
     * @param interest_rate float - annual interest rate.
     * @param term int - term in years.
     * @param desgravament float - insurance cost as a percentage of the amount.
     * @param admin_cor_por float - administration fee as a percentage of the amount.
     * @param secure int... - optional additional costs.
     * @return ResponseEntity<Double> - an HTTP response containing the total monthly cost.
     */
    @PostMapping("/total_cost")
    public ResponseEntity<Double> costCalculation(@PathVariable long amount,
                                                  @PathVariable float interest_rate,
                                                  @PathVariable int term,
                                                  @PathVariable float desgravament,
                                                  @PathVariable float admin_cor_por,
                                                  @RequestParam(required = false) int... secure) {
        return ResponseEntity.ok(loanService.totalCostCalculation(amount, interest_rate, term, desgravament, admin_cor_por, secure));
    }
}

