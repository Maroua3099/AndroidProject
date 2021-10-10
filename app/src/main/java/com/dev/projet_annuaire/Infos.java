package com.dev.projet_annuaire;

public class Infos {
    int id_info;
    String nom_info,activite_info,lieu_info,directeur_info,logo_info,locali_info;

    public int getId_info() {
        return id_info;
    }

    public void setId_info(int id_info) {
        this.id_info = id_info;
    }

    public String getNom_info() {
        return nom_info;
    }

    public void setNom_info(String nom_info) {
        this.nom_info = nom_info;
    }

    public String getActivite_info() {
        return activite_info;
    }

    public void setActivite_info(String activite_info) {
        this.activite_info = activite_info;
    }

    public String getLieu_info() {
        return lieu_info;
    }

    public void setLieu_info(String lieu_info) {
        this.lieu_info = lieu_info;
    }

    public String getDirecteur_info() {
        return directeur_info;
    }

    public void setDirecteur_info(String directeur_info) {
        this.directeur_info = directeur_info;
    }

    public String getLogo_info() {
        return logo_info;
    }

    public void setLogo_info(String logo_info) {
        this.logo_info = logo_info;
    }

    public String getLocali_info() {
        return locali_info;
    }

    public void setLocali_info(String locali_info) {
        this.locali_info = locali_info;
    }

    public Infos(int id_info, String nom_info, String activite_info, String lieu_info, String directeur_info, String logo_info, String locali_info) {
        this.id_info = id_info;
        this.nom_info = nom_info;
        this.activite_info = activite_info;
        this.lieu_info = lieu_info;
        this.directeur_info = directeur_info;
        this.logo_info = logo_info;
        this.locali_info = locali_info;
    }
}
