package ru.job4j.carsales.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.job4j.carsales.model.Car;
import ru.job4j.carsales.model.Engine;
import ru.job4j.carsales.model.Transmission;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "j_announcement")
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@RequiredArgsConstructor(staticName = "of")
public class AnnouncementDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "announcement_id")
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "tm_id")
    private Transmission transmission;

    @NonNull
    private String color;

    @NonNull
    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean active = true;

    @NonNull
    private Long price;

    @NonNull
    private Long run;

    @NonNull
    private String location;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "Asia/Novosibirsk")
    private Date created = new Date(System.currentTimeMillis());

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "Asia/Novosibirsk")
    private Date updated = new Date(System.currentTimeMillis());

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="photos", joinColumns=@JoinColumn(name="announcement_id"))
    @Column(name="photo")
    private List<String> photos = new ArrayList<>();

    @NonNull
    @Column(name="account_id")
    private Long owner;
}
