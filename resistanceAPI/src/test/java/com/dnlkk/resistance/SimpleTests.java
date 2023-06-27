package com.dnlkk.resistance;

import com.dnlkk.resistance.exceptions.VertexNotFoundException;
import com.dnlkk.resistance.objects.graph.WeightedEdge;
import com.dnlkk.resistance.objects.graph.WeightedGraph;
import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        private String matchedGraphInfo(String input) {
            String result = "";

            Pattern regex = Pattern.compile("\\[(...)*]*]");
            Matcher matcher = regex.matcher(input);

            if (matcher.find()) {
                result = matcher.group(); // Получение сматченной строки
            }
            return result;
        }
    }

    @Nested
    @Getter
    class GraphCreateTest {
        private int vertexCount = 3;
        private int edgeCount = 0;
        private WeightedGraph<Resistor> graph = new ResistorMatrixWeightedGraph(vertexCount);

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
        void testGraphAddNewEdge(){
            System.out.println(new Object() {}
                    .getClass()
                    .getEnclosingMethod()
                    .getName());
            recreateGraph(3);

            graph.addEdge(0,3,new Resistor("R1", 1));
            edgeCount++;
            vertexCount++;

            new GraphBaseTest(this).testGraph(
                    "{0-3=[(R1=1)]};"
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
                    "{0-1=[(R1=1), (R2=2), (R3=3), (R4=4)]};{0-2=[(R5=5)]};{0-3=[(R6=6), (R7=7), (R8=8)]};"
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

            assertEquals("[(R1=1), (R2=2)]", graph.getWeight(0, 1).toString());

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1=1), (R2=2)]};{1-2=[(R3=3)]};"
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
            assertEquals("[(R3=3)]", ans.toString());

            ans = graph.getWeight(0, 1);
            System.out.println(ans);
            assertEquals("[(R1=1), (R2=2)]", graph.getWeight(0, 1).toString());

            graph.removeEdge(0, 1, new Resistor("R1", 1));
            edgeCount--;

            ans = graph.getWeight(0, 1);
            System.out.println(ans);
            assertEquals("[(R1=2)]", graph.getWeight(0, 1).toString());

            graph.removeEdge(1,2,new Resistor("R2", 3));
            edgeCount--;

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
                    "{0-1=[(R1=2)]};"
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
            assertEquals("[(R1=1), (R2=2)]", ans.toString());

            graph.removeEdge(0, 1);
            edgeCount -= 2;

            try {
                ans = graph.getWeight(0, 1);
                System.out.println(ans);
                assertEquals("[(R1=1), (R2=2)]", ans.toString());
            } catch (VertexNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Something went wrong!");
            }

            new GraphBaseTest(this).testGraph(
                    "{1-2=[(R1=3)]};"
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

            assertEquals("{0-1=[(R1=1), (R2=2)]};{1-2=[(R3=3)]};", graphInfo);

            new GraphBaseTest(this).testGraph(
                    "{0-1=[(R1=1), (R2=2)]};{1-2=[(R3=3)]};"
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
                    "{0-1=[(R1=1), (R2=2)]};{0-3=[(R3=4)]};{1-2=[(R4=3)]};"
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

            new GraphBaseTest(this).testGraph(
                    "{0-2=[(R1=1)]};"
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
                    "{0-1=[(R1=1), (R2=2), (R3=3), (R4=4)]};{0-2=[(R5=5)]};"
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
