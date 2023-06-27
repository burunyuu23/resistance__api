package com.dnlkk.resistance.objects.resistor;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Resistor {
    private String name;
    private int resistance;

    @Override
    public String toString() {
        return String.format("(%s:%d)", name, resistance);
    }
}
