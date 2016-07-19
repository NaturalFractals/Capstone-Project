package com.developer.jc.newsreporter.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Parses URL of article web page to retrieve story content
 */
public class WebParser {
    private String baseUrl;
    private ArrayList<String> list = new ArrayList<>();

    public WebParser(String url) {
        this.baseUrl = url;
    }

    public ArrayList<String> extractParagraphs(String urlPath) throws IOException{
        Elements elements = Jsoup.connect(baseUrl + urlPath)
                .get()
                .select("p");

        for(Element e: elements) {
            if(e.hasClass("p-block"))
                list.add(e.text());
        }
        return list;
    }

 }
