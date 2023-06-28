package com.dnlkk.resistance;

import com.dnlkk.resistance.exceptions.VertexNotFoundException;
import com.dnlkk.resistance.objects.graph.*;
import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleTests {

    @Nested
    @AllArgsConstructor
    class GraphBaseTest {
        private final GraphCreateTest graphCreateTest;
        void testGraph(String graphInfo){
            testGraphVertex(graphCreateTest.getVertexCount());
            testGraphEdges(graphCreateTest.getEdgeCount());
            testGraphToString(graphInfo);
        }

        void testGraphVertex(int vertexCount) {
            assertEquals(vertexCount, graphCreateTest.getGraph().vertexCount());
        }

        void testGraphEdges(int edgeCount) {
            assertEquals(edgeCount, graphCreateTest.getGraph().edgeCount());
        }

        void testGraphToString(String graphInfo){
            String info = graphCreateTest.getGraph().toString();
            System.out.println(info);
            assertEquals(graphInfo, info);
        }
    }

    @Nested
    @Getter
    class GraphCreateTest {
        private int vertexCount = 3;
        private int edgeCount = 0;
        private ResistorWeightedGraph graph = new ResistorMatrixWeightedGraph(vertexCount);

        @Test
        void testGraphCreationThreeVertexToString(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            new GraphBaseTest(this).testGraph(
                    ""
            );
            System.out.println();
        }

        @Test
        void testGraphCreateFromString() {
            String input = "{0-1=[(R1:10)]};{1-2=[(R2:5), (R3:20)]};";

            Graph<Resistor> graph = new ResistorMatrixWeightedGraph(input);

            assertEquals(input, graph.toString());
        }

        @Test
        void testGraphAddNewEdge(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(2);

            graph.addEdge(0,3,new Resistor("R1", 1));
            edgeCount++;
            vertexCount = 2;

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphRemoveSequenceEdge(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1, new Resistor("R1", 1));
            graph.addEdge(0,1, new Resistor("R2", 2));
            graph.addEdge(0,1, new Resistor("R3", 3));
            graph.addEdge(1,0, new Resistor("R4", 4));
            graph.addEdge(0,2, new Resistor("R5", 5));
            graph.addEdge(0,3, new Resistor("R6", 6));
            graph.addEdge(0,3, new Resistor("R7", 7));
            graph.addEdge(3,0, new Resistor("R8", 8));
            graph.addEdge(1,2, new Resistor("R9", 9));
            edgeCount += 9;
            vertexCount = 4;

            graph.removeEdge(0, 3);
            vertexCount--;
            edgeCount -= 3;

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1), (R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{1-2=[(R6:9)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphAddSomeNewEdges(){
            System.out.println("testGraphAddSomeNewEdges");
            recreateGraph(3);

            graph.addEdge(0,1, new Resistor("R1", 1));
            graph.addEdge(0,1, new Resistor("R2", 2));
            graph.addEdge(0,1, new Resistor("R3", 3));
            graph.addEdge(1,0, new Resistor("R4", 4));
            graph.addEdge(0,2, new Resistor("R5", 5));
            graph.addEdge(0,3, new Resistor("R6", 6));
            graph.addEdge(0,3, new Resistor("R7", 7));
            graph.addEdge(3,0, new Resistor("R8", 8));
            edgeCount += 8;
            vertexCount++;

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1), (R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};{0-3=[(R6:6), (R7:7), (R8:8)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphGetResistor(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1,new Resistor("R1", 1));
            graph.addEdge(0,1,new Resistor("R2", 2));
            graph.addEdge(1,2,new Resistor("R3", 3));
            edgeCount+=3;

            assertEquals("[(R1:1), (R2:2)]", graph.getWeight(0, 1).toString());

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1), (R2:2)]};{1-2=[(R3:3)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphRemoveEdgeResistor(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1, new Resistor("R1", 1));
            graph.addEdge(0,1, new Resistor("R2", 2));
            graph.addEdge(1,2, new Resistor("R3", 3));
            edgeCount+=3;

            var ans = graph.getWeight(1, 2);
            System.out.println(ans);
            assertEquals("[(R3:3)]", ans.toString());

            ans = graph.getWeight(0, 1);
            System.out.println(ans);
            assertEquals("[(R1:1), (R2:2)]", graph.getWeight(0, 1).toString());

            graph.removeEdge(0, 1, new Resistor("R1", 1));
            edgeCount--;

            ans = graph.getWeight(0, 1);
            System.out.println(ans);
            assertEquals("[(R1:2)]", graph.getWeight(0, 1).toString());

            graph.removeEdge(1,2,new Resistor("R2", 3));
            edgeCount--;
            vertexCount--;

            try {
                ans = graph.getWeight(1, 2);
                System.out.println(ans);
                assertEquals("[(R2=3)]", ans.toString());
            } catch (VertexNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Something went wrong!");
            }

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:2)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphRemoveEdgeByResistorName(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1, new Resistor("R1", 1));
            graph.addEdge(0,1, new Resistor("R2", 2));
            graph.addEdge(1,2, new Resistor("R3", 3));
            edgeCount+=3;

            graph.removeEdge(0, 1, "R1");
            edgeCount--;

            var ans = graph.getWeight(0, 1);
            System.out.println(ans);
            assertEquals("[(R1:2)]", graph.getWeight(0, 1).toString());

            graph.removeEdge(0,1,"R1");
            edgeCount--;
            vertexCount--;

            try {
                ans = graph.getWeight(1, 2);
                System.out.println(ans);
                assertEquals("[(R2:3)]", ans.toString());
            } catch (VertexNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Something went wrong!");
            }

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:3)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphRemoveEdge(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1, new Resistor("R1", 1));
            graph.addEdge(0,1, new Resistor("R2", 2));
            graph.addEdge(1,2, new Resistor("R3", 3));
            edgeCount+=3;


            var ans = graph.getWeight(0, 1);
            System.out.println(ans);
            assertEquals("[(R1:1), (R2:2)]", ans.toString());

            graph.removeEdge(0, 1);
            edgeCount -= 2;
            vertexCount--;

            try {
                ans = graph.getWeight(0, 1);
                System.out.println(ans);
                assertEquals("[(R1:3)]", ans.toString());
            } catch (VertexNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Something went wrong!");
            }

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:3)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphIterator() {
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1,new Resistor("R1", 1));
            graph.addEdge(0,1,new Resistor("R2", 2));
            graph.addEdge(1,2,new Resistor("R3", 3));
            edgeCount+=3;

            String graphInfo = graph.toString();
            System.out.println(graphInfo);

            assertEquals("{0-1=[(R1:1), (R2:2)]};{1-2=[(R3:3)]};", graphInfo);

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1), (R2:2)]};{1-2=[(R3:3)]};"
            );
            System.out.println();
        }

        @Test
        void testGraphBfs(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1,new Resistor("R1", 1));
            graph.addEdge(0,1,new Resistor("R2", 2));
            graph.addEdge(1,2,new Resistor("R3", 3));
            graph.addEdge(0,3,new Resistor("R4", 4));
            vertexCount++;
            edgeCount+=4;

            System.out.println(graph.bfs(System.out::println));

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1), (R2:2)]};{0-3=[(R3:4)]};{1-2=[(R4:3)]};"
            );

            System.out.println();
        }

        @Test
        void testGraphAddEdge(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,2,new Resistor("R1", 1));
            edgeCount += 1;
            vertexCount--;

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1)]};"
            );
            System.out.println();
        }


        @Test
        void testGraphAddSomeEdges(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,1, new Resistor("R1", 1));
            graph.addEdge(0,1, new Resistor("R2", 2));
            graph.addEdge(0,1, new Resistor("R3", 3));
            graph.addEdge(1,0, new Resistor("R4", 4));
            graph.addEdge(0,2, new Resistor("R5", 5));
            edgeCount += 5;

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1:1), (R2:2), (R3:3), (R4:4)]};{0-2=[(R5:5)]};"
            );
            System.out.println();
        }

        private void recreateGraph(int vertexCount) {
            this.vertexCount = vertexCount;
            this.edgeCount = 0;
            this.graph = new ResistorMatrixWeightedGraph(vertexCount);
        }
    }
}
