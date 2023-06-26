package com.dnlkk.resistance.objects.graph;

import com.dnlkk.resistance.exceptions.VertexNotFoundException;
import com.dnlkk.resistance.objects.resistor.Resistor;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public interface WeightedGraph<T> extends Graph{

    void addEdge(int v1, int v2, T weight);
    void removeEdge(int v1, int v2, T weight);

    Iterable<WeightedEdge<T>> adjacencyWithWeights(int v);

    default List<T> getWeight(int v1, int v2){
        return StreamSupport.stream(adjacencyWithWeights(v1).spliterator(), false)
                .filter(v -> v.to() == v2)
                .findFirst()
                .orElseThrow(VertexNotFoundException::new)
                .weight();
    }
}

