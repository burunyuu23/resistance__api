package com.dnlkk.resistance.objects.graph;

import com.dnlkk.resistance.exceptions.errors_404.VertexNotFoundException;

import java.util.List;
import java.util.stream.StreamSupport;

public interface WeightedGraph<T> extends Graph<T>{

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

