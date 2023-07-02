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

    default boolean isReachable(int v1, int v2){
        if (isAdj(v1, v2) || v1 == v2)
            return true;
        for (int v : adjacency(v1)) {
            if (v > v1){
                if (isAdj(v, v2))
                    return true;
                if (isReachable(v, v2))
                    return true;
            }
        }
        return false;
    }

    String bfs(Consumer<T> visitor);
}
