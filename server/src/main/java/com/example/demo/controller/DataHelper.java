package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.message.Doc;
import com.example.demo.message.Document;
import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    private String document;

    private JSONObject graph;

    private JSONObject graphWithPosition;

    private List<Document> documentTimeline;

    private List<Doc> docList;

    public DataHelper() {
        this.documentTimelineInit();
        this.docListInit();
    }

    public void documentTimelineInit() {
        List<Document> documents = new ArrayList<>();
        /*
        documents.add(new Document(1L, "Cheeks returns as Sixers' fixer Jim O'Brien was shown the door after a single tumultuous season", "2005-05-24", ""));
        documents.add(new Document(2L, "Wladimir Klitschko retains belts", "2012-07-07", ""));
        documents.add(new Document(3L, "Three die in bank during Greek riots", "2010-05-05", ""));
        documents.add(new Document(4L, "Navy foils Somali pirate attack off Gulf of Aden", "2011-11-11", ""));
        documents.add(new Document(5L, "Waitrose fire in Wellington town centre tackled", "2013-07-21", ""));
        documents.add(new Document(6L, "Brooklyn Neighborhood Is 'A Powder Keg' After Teen Killed By Police", "2013-05-14", ""));
        */
        documents.add(new Document(1L, "1", "2005-05-24"));
        documents.add(new Document(2L, "2", "2012-07-07"));
        documents.add(new Document(3L, "3", "2010-05-05"));
        documents.add(new Document(4L, "4", "2011-11-11"));
        documents.add(new Document(5L, "5", "2013-07-21"));
        documents.add(new Document(6L, "6", "2013-05-14"));
        this.documentTimeline = documents;
    }

    public void docListInit() {
        List<Doc> docList = new ArrayList<>();
        docList.add(new Doc("5_1", "Cheeks returns as Sixers' fixer Jim O'Brien was shown the door after a single tumultuous season", "2005-05-24"));
        docList.add(new Doc("7_1", "Wladimir Klitschko retains belts", "2012-07-07"));
        docList.add(new Doc("8_3", "Three die in bank during Greek riots", "2010-05-05"));
        docList.add(new Doc("12_1", "Navy foils Somali pirate attack off Gulf of Aden", "2011-11-11"));
        docList.add(new Doc("14_1", "Waitrose fire in Wellington town centre tackled", "2013-07-21"));
        docList.add(new Doc("19_1", "Brooklyn Neighborhood Is 'A Powder Keg' After Teen Killed By Police", "2013-05-14"));
        this.docList = docList;
    }

    public String getDocument() {
        return document;
    }

    public JSONObject getGraph() {
        return graph;
    }

    public JSONObject getGraphWithPosition() {
        return graphWithPosition;
    }

    public List<Document> getDocumentTimeline() {
        return documentTimeline;
    }

    public List<Doc> getDocList() {
        return docList;
    }

    public void setDocument(String document) {
        this.document = document;
        readWithPosition();
        readWithoutPosition();
    }

    public void readWithoutPosition() {
        try{
            File jsonFile = ResourceUtils.getFile("classpath:static/" + this.document + ".json");
            String jsonString = FileUtils.readFileToString(jsonFile, "UTF-8");
            graph = JSON.parseObject(jsonString);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readWithPosition() {
        try{
            File jsonFile = ResourceUtils.getFile("classpath:static/new_" + this.document + ".json");
            String jsonString = FileUtils.readFileToString(jsonFile, "UTF-8");
            graphWithPosition = JSON.parseObject(jsonString);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String readOriginalText() {
        String text = "";
        try {
            File originalFile = ResourceUtils.getFile("classpath:static/original_" + this.document + ".txt");
            FileInputStream in = new FileInputStream(originalFile);
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            in.close();
            text = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e){
            e.printStackTrace();
        }
        return text;
    }
}
