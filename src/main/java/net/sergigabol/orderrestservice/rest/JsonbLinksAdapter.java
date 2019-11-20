/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sergigabol.orderrestservice.rest;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.bind.adapter.JsonbAdapter;
import javax.ws.rs.core.Link;

/**
 *
 * @author gabalca
 */
public class JsonbLinksAdapter implements JsonbAdapter<List<Link>, JsonArray>{

    @Override
    public JsonArray adaptToJson(List<Link> orgnl) throws Exception {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for(Link link:orgnl){
            arrBuilder.add(
                    Json.createObjectBuilder()
                            .add("rel", link.getRel())
                            .add("href", link.getUri().toString())
                            .add("type", link.getType())
                            .build()
            );
        }
        return arrBuilder.build();
    }

    @Override
    public List<Link> adaptFromJson(JsonArray adptd) throws Exception {
        return new ArrayList<>();
    }
    
}
