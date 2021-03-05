package ru.job4j.carsales.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "j_account")
@Data
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode()
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "account_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role = Role.of("USER");

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "Asia/Novosibirsk")
    private Date created = new Date(System.currentTimeMillis());

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd HH:mm:ss", timezone = "Asia/Novosibirsk")
    private Date updated = new Date(System.currentTimeMillis());

//    @OneToMany(mappedBy = "account")
//    private List<Announcement> announcements = new ArrayList<>();

    @NonNull
    private String name;

    @NonNull
    @Column(unique = true)
    private String email;

    @NonNull
    private String password;

    @NonNull
    @Column(unique = true)
    private Long telephone;
    private String userPic;
    private String location;
}
