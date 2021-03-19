package ru.job4j.carsales.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "j_transmission",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"automatic", "drive_type"})})
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@RequiredArgsConstructor(staticName = "of")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "tm_id")
    private Long id;

    @NonNull
    private Boolean automatic;

    @NonNull
    @Column(name = "drive_type")
    private String driveType;

    @Override
    public String toString() {
        String tm = (automatic) ? ("автомат ") : ("механика ");
        String drive = "";
        if ("FWD".equals(driveType))
            drive = ", передний привод";
        else if ("RWD".equals(driveType))
            drive =  ", задний привод";
        else if ("4WD".equals(driveType))
            drive =  ", полный привод";
        return tm + drive;
    }
}
