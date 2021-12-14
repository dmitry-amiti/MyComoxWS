package ru.penza.models;

import lombok.*;
import ru.penza.forms.EngineForm;

import javax.persistence.*;
import java.util.List;

@Data
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"values"})
@Builder
@Entity
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "engine")
    private List<Value> values;


    public static Engine from(EngineForm form) {
        return Engine.builder()
                .name(form.getName())
                .build();
    }
}
