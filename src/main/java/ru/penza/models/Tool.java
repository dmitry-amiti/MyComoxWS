package ru.penza.models;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"values"})
@Builder
@Entity
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double criticalValue;
    private String unit;

    @OneToMany(mappedBy = "tool")
    private List<Value> values;
}
