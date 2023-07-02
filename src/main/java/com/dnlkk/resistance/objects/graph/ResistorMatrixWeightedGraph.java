package com.dnlkk.resistance.objects.graph;

import com.dnlkk.resistance.exceptions.errors_404.ResistorNotFoundException;
import com.dnlkk.resistance.objects.resistor.Resistor;
import com.dnlkk.resistance.util.RegexUtil;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class ResistorMatrixWeightedGraph implements ResistorWeightedGraph {

    private final List<List<List<Resistor>>> adjacencyMatrix;
    private int vertexCount;
    private int edgeCount;

    public ResistorMatrixWeightedGraph(String graph) {
        this(1);
        RegexUtil.regexGraph(graph, this);
    }

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
        return new Iterable<Integer>() {

            Integer nextAdj = null;

            @Override
            public Iterator<Integer> iterator() {
                for (int i = 0; i < vertexCount(); i++) {
                    if (!adjacencyMatrix.get(v).get(i).isEmpty()) {
                        nextAdj = i;
                        break;
                    }
                }

                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return nextAdj != null;
                    }

                    @Override
                    public Integer next() {
                        Integer result = nextAdj;
                        nextAdj = null;
                        for (int i = result + 1; i < vertexCount(); i++) {
                            if (!adjacencyMatrix.get(v).get(i).isEmpty()) {
                                nextAdj = i;
                                break;
                            }
                        }
                        return result;
                    }
                };
            }

            @Override
            public void forEach(Consumer<? super Integer> action) {
                Iterable.super.forEach(action);
            }

            @Override
            public Spliterator<Integer> spliterator() {
                return Iterable.super.spliterator();
            }
        };
    }

    @Override
    public boolean isAdj(int v1, int v2) {
        return ResistorWeightedGraph.super.isAdj(v1, v2);
    }


    @Override
    public void addEdge(int v1, int v2) {
    }

    /**
     * [[],[],[]]    [ [],  [], [z],  []]
     * [[],[],[]] => [ [],  [], [], [w]]
     * [[],[],[]]    [[z],  [], [],  []]
     * [ [], [w], [],  []]
     */
    @Override
    public void addEdge(int v1, int v2, Resistor resistor) {
        while (v2 >= this.vertexCount()) {
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

        rename();
    }

    @Override
    public void removeEdge(int v1, int v2, Resistor resistor) {
        this.adjacencyMatrix.get(v1).get(v2).remove(resistor);
        this.adjacencyMatrix.get(v2).get(v1).remove(resistor);
        this.edgeCount--;

        rename();
    }


    @Override
    public void removeEdge(int v1, int v2, String resistorName) {
        Resistor resistor = this.adjacencyMatrix.get(v1).get(v2).stream()
                .filter(r -> r.getName().equals(resistorName))
                .findFirst()
                .orElseThrow(ResistorNotFoundException::new);

        this.removeEdge(v1, v2, resistor);
    }

    @Override
    public void removeEdge(int v1, int v2) {
        int size = this.adjacencyMatrix.get(v1).get(v2).size();
        this.adjacencyMatrix.get(v1).get(v2).clear();
        this.adjacencyMatrix.get(v2).get(v1).clear();
        this.edgeCount -= size;

        rename();
    }

    /**
     * [[],[],[]]    [  [],  [], [z],  ~[]~]
     * [[],[],[]] => [  [],  [],  [], ~[w]~]
     * [[],[],[]]    [ [z],  [],  [], ~[]~]
     * ~[ [], [w],  [],  []]~
     */
    @Override
    public void removeVertex(int v) {
        this.adjacencyMatrix.remove(v);
        int size = 0;
        for (int i = 0; i < this.vertexCount() - 1; i++) {
            size += this.adjacencyMatrix.get(i).remove(v).size();
        }
        this.vertexCount--;
        this.edgeCount -= size;

        rename();
    }

    private void rename() {
        renameResistors();
        renameVertex();
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
        var ref = new Object() {
            int counter = 1;
        };

        bfs(resistor -> {
            resistor.setName(String.format("R%d", ref.counter));
            ref.counter++;
        });
    }

    public void renameVertex() {
        for (int i = 0; i < vertexCount(); i++) {
            boolean isEmpty = true;
            for (int j = 0; j < vertexCount(); j++) {
                isEmpty = this.adjacencyMatrix.get(i).get(j).isEmpty();
                if (!isEmpty)
                    break;
            }
            if (isEmpty)
                this.removeVertex(i);
        }
    }

    @Override
    public List<Resistor> getWeight(int v1, int v2) {
        return ResistorWeightedGraph.super.getWeight(v1, v2);
    }

    @Override
    public String bfs(Consumer<Resistor> visitor) {
        StringBuilder stringBuilder = new StringBuilder();
        int from = 0;

        boolean[][] visited = new boolean[this.vertexCount()][this.vertexCount()];
        Queue<Integer> queue = new LinkedList<>();

        for (int range = from; range < this.vertexCount(); range++) {
            queue.add(range);
        }

        visited[from][from] = true;
        while (queue.size() > 0) {
            Integer curr = queue.remove();
            for (WeightedEdge<Resistor> weResistor : this.adjacencyWithWeights(curr)) {
                if (!visited[weResistor.to()][curr]) {
                    queue.add(weResistor.to());

                    if (visitor != null)
                        for (Resistor resistor : weResistor.weight()) {
                            visitor.accept(resistor);
                        }

                    // Я слишком туп, чтобы придумать как одновременно делать консюмера в одном случае с резистором,
                    // а в другом с двумя элементами: кур и V
                    // А возможно еще и слишком ленив
                    // ヽ(๑˘︶˘๑)ノ https://i.imgur.com/XtnTpp9.jpg
                    // Извините за картинку, самому стыдно
                    stringBuilder.append(String.format("{%d-%d=%s};", curr, weResistor.to(), weResistor.weight()));

                    visited[weResistor.to()][curr] = true;
                    visited[curr][weResistor.to()] = true;
                }
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return bfs(null);
    }

}
