package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.message.*;
import com.example.demo.node.Movie;
import com.example.demo.node.Person;
import com.example.demo.repository.ACTED_INRepository;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.request.Document;
import com.example.demo.request.Query;
import com.example.demo.request.QueryStr;
import org.neo4j.driver.internal.value.RelationshipValue;
import org.neo4j.driver.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin("*")
@RestController
public class Controller {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ACTED_INRepository acted_inRepository;

    private final DataHelper dataHelper = new DataHelper();

    private final List<String> allNodesType = Arrays.asList("Event", "Location", "Object", "Organization", "Other", "Person", "Time");

    private final List<String> allEdgesType = Arrays.asList("COREF", "INTRA_CAUSE", "INTER_CAUSE");

    private final List<String> edgesColor = Arrays.asList("rgb(255,166,0)", "rgb(60,171,0)", "rgb(174,130,231)", "rgb(237,121,246)", "rgb(36,121,236)", "rgb(253,125,127)");

    private Graph tempGraph;

    private List<Node> tempEvents = new ArrayList<>();

    private Node getNodeById(Long id){
        Optional<Person> personOptional = personRepository.findById(id);
        Optional<Movie> movieOptional = movieRepository.findById(id);
        Node node = new Node();
        Map<String, Object> properties = new HashMap<>();
        Map<String, Integer> relations = new HashMap<>();
        if(personOptional.isPresent()){
            Person person = personOptional.get();
            node.setId(person.getId());
            node.setGroup(allNodesType.indexOf("Person"));
            node.setLabel(person.getName());
            properties.put("name", person.getName());
            properties.put("born", person.getBorn());
            List<String> edges = personRepository.findAllEdgesType(person.getName());
            for(String type: allEdgesType){
                int count = Collections.frequency(edges, type);
                if(count != 0) relations.put(type, count);
            }
        }else{
            Movie movie = movieOptional.get();
            node.setId(movie.getId());
            node.setGroup(allNodesType.indexOf("Movie"));
            node.setLabel(movie.getTitle());
            properties.put("title", movie.getTitle());
            properties.put("released", movie.getReleased());
            properties.put("tagline", movie.getTagline());
            List<String> edges = movieRepository.findAllEdgesType(movie.getTitle());
            for(String type: allEdgesType){
                int count = Collections.frequency(edges, type);
                if(count != 0) relations.put(type, count);
            }
        }
        node.setProperties(properties);
        node.setRelation_num(relations);
        return node;
    }

    @PostMapping("/test")
    public Map<String, String> test(){
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("name", "John");
        map.put("born", "1998");
        return map;
    }

    @PostMapping("/api/querySearchPerson")
    public List<Nodename> querySearchPerson(@RequestBody QueryStr queryStr){
        List<Nodename> results = new ArrayList<>();
        List<Person> persons = personRepository.fuzzyFindByName(".*" + queryStr.getQuerystr() + ".*");
        for(Person person: persons)
            results.add(new Nodename(person.getName(), "Person"));
        return results;
    }

    @PostMapping("/api/querySearchMovie")
    public List<Nodename> querySearchMovie(@RequestBody QueryStr queryStr){
        List<Nodename> results = new ArrayList<>();
        List<Movie> movies = movieRepository.fuzzyFindByTitle(".*" + queryStr.getQuerystr() + ".*");
        for (Movie movie: movies)
            results.add(new Nodename(movie.getTitle(), "Movie"));
        return results;
    }

    @PostMapping("/api/searchDetailPerson")
    public DetailData searchDetailPerson(@RequestBody QueryStr queryStr){
        DetailData result = new DetailData();
        Optional<Person> personOptional = personRepository.findByName(queryStr.getQuerystr());
        List<TableData> attributes = new ArrayList<>();
        List<TableData> relations = new ArrayList<>();
        if(personOptional.isPresent()) {
            Person person = personOptional.get();
            attributes.add(new TableData("name", person.getName()));
            attributes.add(new TableData("born", person.getBorn().toString()));
            List<String> edges = personRepository.findAllEdgesType(queryStr.getQuerystr());
            for(String type: allEdgesType){
                int count = Collections.frequency(edges, type);
                if(count != 0) relations.add(new TableData(type, Integer.toString(count)));
            }
        }
        result.setAttributes(attributes);
        result.setRelations(relations);
        return result;
    }

    @PostMapping("/api/searchDetailMovie")
    public DetailData searchDetailMovie(@RequestBody QueryStr queryStr){
        DetailData result = new DetailData();
        Optional<Movie> movieOptional = movieRepository.findByTitle(queryStr.getQuerystr());
        List<TableData> attributes = new ArrayList<>();
        List<TableData> relations = new ArrayList<>();
        if(movieOptional.isPresent()){
            Movie movie = movieOptional.get();
            attributes.add(new TableData("title", movie.getTitle()));
            attributes.add(new TableData("released", movie.getReleased().toString()));
            attributes.add(new TableData("tagline", movie.getTagline()));
            List<String> edges = movieRepository.findAllEdgesType(queryStr.getQuerystr());
            for(String type: allEdgesType){
                int count = Collections.frequency(edges, type);
                if(count != 0) relations.add(new TableData(type, Integer.toString(count)));
            }
        }
        result.setAttributes(attributes);
        result.setRelations(relations);
        return result;
    }

    @PostMapping("/api/getOneNode")
    public Node getOneNode(@RequestBody QueryStr queryStr){
        Optional<Person> personOptional = personRepository.findByName(queryStr.getQuerystr());
        Optional<Movie> movieOptional = movieRepository.findByTitle(queryStr.getQuerystr());
        Node node = new Node();
        HashMap<String, Object> properties = new HashMap<>();
        if(personOptional.isPresent()){
            node.setId(personOptional.get().getId());
            node.setGroup(allNodesType.indexOf("Person"));
            node.setLabel(personOptional.get().getName());
            properties.put("name", personOptional.get().getName());
            properties.put("born", personOptional.get().getBorn());
            node.setProperties(properties);
            node = this.getNodeById(personOptional.get().getId());
        }else if(movieOptional.isPresent()){
            node.setId(movieOptional.get().getId());
            node.setGroup(allNodesType.indexOf("Movie"));
            node.setLabel(movieOptional.get().getTitle());
            properties.put("title", movieOptional.get().getTitle());
            properties.put("released", movieOptional.get().getReleased());
            properties.put("tagline", movieOptional.get().getTagline());
            node.setProperties(properties);
            node = this.getNodeById(movieOptional.get().getId());
        }else{
            return new Node((long) -1, -1, "Error!", new HashMap<>(), new HashMap<>());
        }
        return node;
    }

    @PostMapping("/api/getEdges")
    public Graph getEdges(@RequestBody Query query){
        Long nodeid = (long)-1;
        List<RelationshipValue> results = new ArrayList<>();
        if(query.getType().equals("Person")){
            Optional<Person> personOptional = personRepository.findByName(query.getString());
            if(personOptional.isPresent()) {
                nodeid = personOptional.get().getId();
                results = personRepository.findAllEdges(query.getString());
            }
        }else{
            Optional<Movie> movieOptional = movieRepository.findByTitle(query.getString());
            if(movieOptional.isPresent()){
                nodeid = movieOptional.get().getId();
                results = movieRepository.findAllEdges(query.getString());
            }
        }
        List<Node> nodes = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        for(RelationshipValue result: results){
            Relationship r = result.asRelationship();
            Color c = new Color(edgesColor.get(allEdgesType.indexOf(r.type())));
            if(result.asMap().isEmpty())
                edges.add(new Edge(r.id(), r.startNodeId(), r.endNodeId(), r.type(), c, new HashMap<>()));
            else
                edges.add(new Edge(r.id(), r.startNodeId(), r.endNodeId(), r.type(), c, result.asMap()));
            if(r.startNodeId() != nodeid)
                nodes.add(getNodeById(r.startNodeId()));
            if(r.endNodeId() != nodeid)
                nodes.add(getNodeById(r.endNodeId()));
        }
        return new Graph(nodes, edges);
    }

    @PostMapping("/api/getDocumentList")
    public List<Doc> getDocumentList() {
        return dataHelper.getDocList();
    }

    @PostMapping("/api/setDocument")
    public void setDocument(@RequestBody Document document) {
        // TODO
        dataHelper.setDocument(document.getDocument());

    }

    @PostMapping("/api/searchDetail")
    public Node searchDetail(@RequestBody Query query) {
        Graph graph = dataHelper.getGraph().toJavaObject(Graph.class);
        for(Node node: graph.getNodes()){
            if(node.getLabel().equals(query.getString()) && allNodesType.get(node.getGroup()).equals(query.getType()))
                return node;
        }
        return new Node();
    }

    @PostMapping("/api/query")
    public List<Nodename> query(@RequestBody Query query){
        JSONObject object = dataHelper.getGraph();
        Graph graph = object.toJavaObject(Graph.class);
        List<Node> nodes = graph.getNodes();
        String queryString = query.getString();
        String type = query.getType();
        List<Nodename> results = new ArrayList<>();
        for(Node node: nodes){
            if(allNodesType.get(node.getGroup()).equals(type) && node.getLabel().contains(queryString)){
                Nodename nodename = new Nodename(node.getLabel(), type);
                results.add(nodename);
            }
        }
        return results;
    }

    @PostMapping("/api/getDocument")
    public JSONObject getDocument() {
        return dataHelper.getGraph();
    }

    @PostMapping("/api/getDocumentWithPosition")
    public JSONObject getDocumentWithPosition() {
        return dataHelper.getGraphWithPosition();
    }

    @PostMapping("/api/getOriginalText")
    public OriginalText getOriginalText() {
        String text = dataHelper.readOriginalText();
        OriginalText originalText = new OriginalText();
        originalText.setText(text);
        return originalText;
    }

    @PostMapping("/api/saveGraph")
    public void saveGraph(@RequestBody Graph graph) {
        this.tempGraph = graph;
        for(Node node: graph.getNodes()) {
            if(node.getGroup() == 0)
                this.tempEvents.add(node);
        }
    }

    @PostMapping("/api/getGraph")
    public Graph getGraph() {
        return tempGraph;
    }

    @PostMapping("/api/getEventTimeline")
    public List<Event> getEventTimeline() {
        List<Event> events = new ArrayList<>();
        for(Node node: tempEvents){
            Long id = node.getId();
            String content = node.getLabel();
            Map<String, Object> properties = node.getProperties();
            String start = "";
            String end = "";
            if(properties.containsKey("start"))
                start = (String) properties.remove("start");
            if(properties.containsKey("end"))
                end = (String) properties.remove("end");
            Event event = new Event(id, content, start, end, properties);
            events.add(event);
        }
        return events;
    }

    @PostMapping("/api/getDocumentTimeline")
    public List<com.example.demo.message.Document> getDocumentTimeline() {
        return dataHelper.getDocumentTimeline();
    }
}
