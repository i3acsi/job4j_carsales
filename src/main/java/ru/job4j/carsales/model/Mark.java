package ru.job4j.carsales.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.job4j.carsales.dto.ModelDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "j_mark")
@Data
@NoArgsConstructor
@EqualsAndHashCode()
@RequiredArgsConstructor(staticName = "of")
@ToString(of = {"id", "name"})
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mark_id")
    private Long id;

    @NonNull
    @Column(unique = true, updatable = false)
    @EqualsAndHashCode.Include
    private String name;

    @OneToMany(mappedBy = "mark")
    @JsonIgnore
    private List<ModelDto> models = new ArrayList<>();
}
