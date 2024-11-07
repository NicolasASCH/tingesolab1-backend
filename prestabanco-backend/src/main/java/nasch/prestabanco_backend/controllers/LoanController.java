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
public class    LoanController {
    @Autowired
    LoanService loanService;

    @GetMapping("/")
    public ResponseEntity<List<LoanEntity>> listLoan() {
        List<LoanEntity> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{state}")
    public ResponseEntity<LoanEntity> getLoanState(@PathVariable String state) {
        LoanEntity loan = loanService.getByState(state);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/{rut}")
    public ResponseEntity<LoanEntity> getLoanRut(@PathVariable String rut) {
        LoanEntity loan = loanService.getByRut(rut);
        return ResponseEntity.ok(loan);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLoanById(@PathVariable Long id) throws Exception {
        var isDeleted = loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/simulation")
    public ResponseEntity<Double> simulateCredit(@RequestParam("amount") long amount,
                                                 @RequestParam("interest_rate") float interest_rate,
                                                 @RequestParam("term") int term) {
        return ResponseEntity.ok(loanService.mortgageCreditSimulation(amount, interest_rate, term));
    }

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
