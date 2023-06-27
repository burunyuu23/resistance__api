package com.dnlkk.resistance.util;

import com.dnlkk.resistance.objects.graph.Graph;
import com.dnlkk.resistance.objects.graph.ResistorMatrixWeightedGraph;
import com.dnlkk.resistance.objects.resistor.Resistor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static void regexGraph(String input, ResistorMatrixWeightedGraph graph) {
        Pattern fromToPattern = Pattern.compile("\\d*-\\d*");
        Pattern resistorPattern = Pattern.compile("R\\d*:\\d*");

        for (String node : input.split(";")) {
            String[] nodeParts = node.split("=");

            Matcher matcher = fromToPattern.matcher(nodeParts[0]);
            String fromTo = "";

            if (matcher.find()) {
                fromTo = matcher.group();
            }

            int from = Integer.parseInt(fromTo.split("-")[0]);
            int to = Integer.parseInt(fromTo.split("-")[1]);

            matcher = resistorPattern.matcher(nodeParts[1]);
            while (matcher.find()) {
                String[] resistorData = matcher.group().split(":");
                Resistor resistor = new Resistor(resistorData[0], Integer.parseInt(resistorData[1]));
                graph.addEdge(from, to, resistor);
            }
        }
    }
}
