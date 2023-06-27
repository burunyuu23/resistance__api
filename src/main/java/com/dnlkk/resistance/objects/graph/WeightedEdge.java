package com.dnlkk.resistance.objects.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WeightedEdge<T> {
    private int to;
    private List<T> weight;

    public int to(){
        return getTo();
    }

    public List<T> weight(){
        return getWeight();
    }
}
