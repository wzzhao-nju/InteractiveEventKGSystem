package com.example.demo.message;

import java.util.List;

public class Graph {

    private List<Node> nodes;

    private List<Edge> edges;

    public Graph() {}

    public Graph(List<Node> nodes, List<Edge> edges){
        this.nodes = nodes;
        this.edges = edges;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
