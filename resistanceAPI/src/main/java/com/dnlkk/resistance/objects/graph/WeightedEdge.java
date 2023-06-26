package com.dnlkk.resistance.objects.graph;

import lombok.Data;

import java.util.List;

public record WeightedEdge<T>(int to, List<T> weight) {
}
