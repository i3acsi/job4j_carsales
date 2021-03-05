package ru.job4j.carsales.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.job4j.carsales.model.Mark;

import javax.persistence.*;

@Entity
@Table(name = "j_model")
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@RequiredArgsConstructor(staticName = "of")
@ToString(of = {"id", "name", "mark"})
public class ModelDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "model_id")
    private Long id;
    @NonNull
    private String name;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "mark_id")
    private Mark mark;
}
