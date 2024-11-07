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
    private LoanRepository loanRepository;

    public ArrayList<LoanEntity> getAllLoans() {
        return (ArrayList<LoanEntity>) loanRepository.findAll();
    }

    public LoanEntity getByRut(String rut) {
        return loanRepository.findByRut(rut);
    }

    public LoanEntity getByState(String state) {
        return loanRepository.findByState(state);
    }

    public LoanEntity saveLoan(Long id, String rut, String type, long property_price, long amount, int term,
                               float interest_rate, long income, int working_time, int age, String state,
                               byte[] document1Data, byte[] document2Data, byte[] document3Data, byte[] document4Data) {
        LoanEntity loan = new LoanEntity(id, rut, type, property_price, amount, term,
                interest_rate, income, working_time, age, state, document1Data, document2Data, document3Data, document4Data);

        return loanRepository.save(loan);
    }

    public LoanEntity updateLoan(Long id, String rut, String type, long property_price, long amount, int term,
                                 float interest_rate, long income, int working_time, int age, String state,
                                 byte[] document1Data, byte[] document2Data, byte[] document3Data, byte[] document4Data) {
        LoanEntity loan = new LoanEntity(id, rut, type, property_price, amount, term,
                interest_rate, income, working_time, age, state, document1Data, document2Data, document3Data, document4Data);

        return loanRepository.save(loan);
    }

    public boolean deleteLoan(Long id) throws Exception {
        try {
            loanRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    public double mortgageCreditSimulation(long amount, float interest_rate, int term) {
        float r = (interest_rate / 12) / 100;
        int n = term * 12;

        return amount * ((r * Math.pow((1 + r), n)) / (Math.pow((1 + r), n) - 1));
    }

    public double totalCostCalculation(long amount, float interest_rate, int term, float desgravament, float admin_com_por, int... secure) {
        double monthly_fee = mortgageCreditSimulation(amount, interest_rate, term);

        float sec_desgravament = amount * desgravament;

        double admin_com = amount * admin_com_por;

        double monthly_cost = monthly_fee + sec_desgravament;
        for (int i : secure) monthly_cost += i;

        return monthly_cost + admin_com;
    }
}
