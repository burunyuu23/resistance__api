package com.dnlkk.resistance.objects.graph;

import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public interface Graph<T> {
    int vertexCount();
    int edgeCount();

    void addEdge(int v1, int v2);
    void removeEdge(int v1, int v2);

    void removeVertex(int v);

    Iterable<Integer> adjacency(int v);

    default boolean isAdj(int v1, int v2){
        return StreamSupport.stream(adjacency(v1).spliterator(), false)
                .anyMatch(v -> v == v2);
    }

    String bfs(Consumer<T> visitor);
}
