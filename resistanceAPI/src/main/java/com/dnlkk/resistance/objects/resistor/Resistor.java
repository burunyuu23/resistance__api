package com.dnlkk.resistance.objects.resistor;

import lombok.Data;

public record Resistor(String name, Integer resistance) {
    @Override
    public String toString() {
        return String.format("(%s=%d)", name, resistance);
    }
}
