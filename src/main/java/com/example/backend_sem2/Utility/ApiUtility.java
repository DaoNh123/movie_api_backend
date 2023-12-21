package com.example.backend_sem2.Utility;

import java.util.List;

public class ApiUtility {
    public static String getIdFromEndpointString (String endpointString){
        List<String> split = List.of(endpointString.split("/"));
        return split.get(split.size() - 1);
    }
}
