package com.example.diploma.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "data")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @Id
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "masut_pressure")
    private double masutPresure;

    @Column(name = "masut_consumption")
    private double masutConsumtion;

    @Column(name = "steam_capacity")
    private double steamCapacity;
}
