package com.example.backend_sem2.utility;

import java.util.List;

public class ApiUtility {
    /*  This method using for Rapid API */
    public static String getIdFromEndpointString (String endpointString){
        List<String> split = List.of(endpointString.split("/"));
        return split.get(split.size() - 1);
    }
}
