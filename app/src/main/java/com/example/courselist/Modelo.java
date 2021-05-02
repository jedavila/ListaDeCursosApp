package com.example.courselist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modelo {

    private HashMap<Course, List<section>> cursosMap;
    private static Modelo ourInstance = new Modelo();
    public static Modelo getInstance() {
        return ourInstance;
    }

    private Modelo() {
        this.cursosMap = new HashMap<>();;
    }

    public HashMap<Course, List<section>> getCursosMap() {
        return cursosMap;
    }

    public void addCursosMap(Course course, ArrayList lista) {
        this.cursosMap.put(course,lista);
    }

    public void setCursosMap(HashMap<Course, List<section>> cursosMap) {
        this.cursosMap = cursosMap;
    }
}
