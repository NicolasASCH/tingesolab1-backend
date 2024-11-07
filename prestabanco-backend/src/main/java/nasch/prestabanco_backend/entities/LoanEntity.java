package nasch.prestabanco_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String type;
    private long property_price;
    private long amount;
    private int term;
    private float interest_rate;
    private long income;
    private int working_time;
    private int age;
    private String state;

    @Lob
    @Column(name = "document1")
    private byte[] document1;

    @Lob
    @Column(name = "document2")
    private byte[] document2;

    @Lob
    @Column(name = "document3")
    private byte[] document3;

    @Lob
    @Column(name = "document4")
    private byte[] document4;
}
