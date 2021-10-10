package com.dev.projet_annuaire;

import android.widget.ImageView;
import android.widget.TextView;

public class Utilisateur {
    int id_user;
    int filiale_id;
    String nom_user;
    String prenom_user;
    String email_user;
    String password_user;
    String tel_user;
    String role;
    String photo;
    String nom_filiale;
    String num_user;
    String num_fav;
    String key_cnx;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getFiliale_id() {
        return filiale_id;
    }

    public void setFiliale_id(int filiale_id) {
        this.filiale_id = filiale_id;
    }

    public String getNom_user() {
        return nom_user;
    }

    public void setNom_user(String nom_user) {
        this.nom_user = nom_user;
    }

    public String getPrenom_user() {
        return prenom_user;
    }

    public void setPrenom_user(String prenom_user) {
        this.prenom_user = prenom_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getTel_user() {
        return tel_user;
    }

    public void setTel_user(String tel_user) {
        this.tel_user = tel_user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNom_filiale() {
        return nom_filiale;
    }

    public void setNom_filiale(String nom_filiale) {
        this.nom_filiale = nom_filiale;
    }

    public String getNum_user() {
        return num_user;
    }

    public void setNum_user(String num_user) {
        this.num_user = num_user;
    }

    public String getNum_fav() {
        return num_fav;
    }

    public void setNum_fav(String num_fav) {
        this.num_fav = num_fav;
    }

    public String getKey_cnx() {
        return key_cnx;
    }

    public void setKey_cnx(String key_cnx) {
        this.key_cnx = key_cnx;
    }

    public Utilisateur(int id_user, int filiale_id, String nom_user, String prenom_user, String email_user, String password_user, String tel_user, String photo, String nom_filiale) {
        this.id_user = id_user;
        this.filiale_id = filiale_id;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.tel_user = tel_user;
        this.photo = photo;
        this.nom_filiale = nom_filiale;
    }
   public Utilisateur(int id_user, String nom_user, String prenom_user, String email_user, String password_user, String tel_user, String photo,String key_cnx,int filiale_id,String nom_filiale){
        this.id_user = id_user;
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.tel_user = tel_user;
        this.photo = photo;
        this.key_cnx = key_cnx;
        this.filiale_id = filiale_id;
        this.nom_filiale = nom_filiale;
    }
    public Utilisateur(String nom_user, String prenom_user, String email_user, String password_user, String tel_user, String photo_user, String nom_filiale,String key_cnx){
        this.nom_user = nom_user;
        this.prenom_user = prenom_user;
        this.email_user = email_user;
        this.password_user = password_user;
        this.tel_user = tel_user;
        this.photo = photo_user;
        this.key_cnx = key_cnx;
        this.nom_filiale = nom_filiale;
    }
}
