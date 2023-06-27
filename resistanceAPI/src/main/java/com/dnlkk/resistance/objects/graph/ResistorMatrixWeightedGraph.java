package com.dnlkk.resistance.objects.graph;

import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@ToString
public class ResistorMatrixWeightedGraph implements WeightedGraph<Resistor>{

    private final List<List<List<Resistor>>> adjacencyMatrix;
    private int vertexCount;
    private int edgeCount;

    public ResistorMatrixWeightedGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacencyMatrix = IntStream.range(0, vertexCount)
                .mapToObj(lvl1 -> {
                    var arr = new ArrayList<List<Resistor>>(vertexCount);
                    IntStream.range(0, vertexCount)
                            .forEach(lvl2 -> arr.add(new ArrayList<>()));
                    return arr;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int vertexCount() {
        return this.getVertexCount();
    }

    @Override
    public int edgeCount() {
        return this.getEdgeCount();
    }

    @Override
    public Iterable<Integer> adjacency(int v) {
        return null;
    }

    @Override
    public boolean isAdj(int v1, int v2) {
        return WeightedGraph.super.isAdj(v1, v2);
    }


    @Override
    public void addEdge(int v1, int v2) {
    }

    /**
     * [[],[],[]]    [ [],  [], [z],  []]
     * [[],[],[]] => [ [],  [], [], [w]]
     * [[],[],[]]    [[z],  [], [],  []]
     *               [ [], [w], [],  []]
     *
     */
    @Override
    public void addEdge(int v1, int v2, Resistor resistor) {
        if (v2 >= this.vertexCount()) {
            this.vertexCount++;

            List<List<Resistor>> listLVL1 = new ArrayList<>();
            for (int indexLVL2 = 0; indexLVL2 < this.vertexCount; indexLVL2++) {
                listLVL1.add(new ArrayList<>());
                if (indexLVL2 < this.vertexCount - 1)
                    this.adjacencyMatrix.get(indexLVL2).add(new ArrayList<>());
            }
            this.adjacencyMatrix.add(listLVL1);
        }
        this.adjacencyMatrix.get(v1).get(v2).add(resistor);
        this.adjacencyMatrix.get(v2).get(v1).add(resistor);
        this.edgeCount++;

        renameResistors();
    }

    @Override
    public void removeEdge(int v1, int v2, Resistor resistor) {
        this.adjacencyMatrix.get(v1).get(v2).remove(resistor);
        this.adjacencyMatrix.get(v2).get(v1).remove(resistor);
        this.edgeCount--;

        renameResistors();
    }

    @Override
    public void removeEdge(int v1, int v2) {
        int size = this.adjacencyMatrix.get(v1).get(v2).size();
        this.adjacencyMatrix.get(v1).get(v2).clear();
        this.adjacencyMatrix.get(v2).get(v1).clear();
        this.edgeCount -= size;

        renameResistors();
    }
    /**
     * [[],[],[]]    [ [],  [], [z],  `[]`]
     * [[],[],[]] => [ [],  [], [],  `[w]`]
     * [[],[],[]]    [[z],  [], [],   `[]`]
     *               `[ [], [w], [],   []]`
     *
     */
    @Override
    public void removeVertex(int v) {
        this.adjacencyMatrix.remove(v);
        int size = 0;
        for (int i = 0; i < this.vertexCount() - 1; i++) {
            if (i != v)
                size += this.adjacencyMatrix.get(i).remove(v).size();
        }
        this.vertexCount--;
        this.edgeCount -= size;

        renameResistors();
    }

    @Override
    public Iterable<WeightedEdge<Resistor>> adjacencyWithWeights(int v) {
        return new Iterable<>() {

            WeightedEdge<Resistor> nextAdj = null;

            @Override
            public Iterator<WeightedEdge<Resistor>> iterator() {
                for (int i = 0; i < vertexCount(); i++) {
                    if (!adjacencyMatrix.get(v).get(i).isEmpty()) {
                        nextAdj = new WeightedEdge<>(i, adjacencyMatrix.get(v).get(i));
                        break;
                    }
                }

                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public WeightedEdge<Resistor> next() {
                        WeightedEdge<Resistor> result = nextAdj;
                        nextAdj = null;
                        for (int i = result.to() + 1; i < vertexCount(); i++) {
                            if (!adjacencyMatrix.get(v).get(i).isEmpty()) {
                                nextAdj = new WeightedEdge<>(i, adjacencyMatrix.get(v).get(i));
                                break;
                            }
                        }
                        return result;
                    }
                };
            }

            @Override
            public void forEach(Consumer<? super WeightedEdge<Resistor>> action) {
                Iterable.super.forEach(action);
            }

            @Override
            public Spliterator<WeightedEdge<Resistor>> spliterator() {
                return Iterable.super.spliterator();
            }
        };
    }

    public void renameResistors() {
        int counter = 1;
        for (WeightedEdge<Resistor> v : this.adjacencyWithWeights(1)) {
            for (Resistor resistor : v.getWeight()) {
                resistor.setName(String.format("R%d", counter));
                counter++;
            }
        }
    }

    @Override
    public List<Resistor> getWeight(int v1, int v2) {
        return WeightedGraph.super.getWeight(v1, v2);
    }
}
