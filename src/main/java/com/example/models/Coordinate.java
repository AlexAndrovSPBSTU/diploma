package com.example.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    public LocalDateTime time;
    public double y;
}
