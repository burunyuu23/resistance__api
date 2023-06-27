package com.dnlkk.resistance.objects.graph;

import com.dnlkk.resistance.objects.resistor.Resistor;

public interface ResistorWeightedGraph extends WeightedGraph<Resistor>{
    void removeEdge(int v1, int v2, String resistorName);
}
