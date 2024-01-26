package com.example.backend_sem2.security;

public class Endpoints {
//    public static final String FRONT_END_HOST = "http://127.0.0.1:5500";
    public static final String FRONT_END_HOST = "*";
    public static final String[] PUBLIC_GET_ENDPOINTS = {
            "/api/**"
    };
    public static final String[] PUBLIC_POST_ENDPOINTS = {
            "/api/**"
    };

//    public static final String[] PUBLIC_PUT_ENDPOINTS = {
//            "/api/**"
//    };
//    public static final String[] PUBLIC_DELETE_ENDPOINTS = {
//            "/api/**"
//    };

    public static final String[] ADMIN_GET_ENDPOINTS = {
            "/api/admin/**"
    };
    public static final String[] ADMIN_POST_ENDPOINTS = {
            "/api/admin/**"
    };
        public static final String[] ADMIN_PUT_ENDPOINTS = {
            "/api/admin/**"
    };
    public static final String[] ADMIN_DELETE_ENDPOINTS = {
            "/api/admin/**"
    };

    public static final String[] USER_GET_ENDPOINTS = {
            "/api/users/**"
    };
    public static final String[] USER_POST_ENDPOINTS = {
            "/api/users/**"
    };
    public static final String[] USER_PUT_ENDPOINTS = {
            "/api/users/**"
    };
    public static final String[] USER_DELETE_ENDPOINTS = {
            "/api/users/**"
    };
}
