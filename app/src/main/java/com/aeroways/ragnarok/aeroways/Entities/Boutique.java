package com.aeroways.ragnarok.aeroways.Entities;

import java.util.Map;

public class Boutique {

    private String nom;
    private String picLink;
    private Map<String,Integer> evaluations;

    public Boutique() {
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public Map<String, Integer> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(Map<String, Integer> evaluations) {
        this.evaluations = evaluations;
    }
}
