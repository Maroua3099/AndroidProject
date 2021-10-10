package com.dev.projet_annuaire;

public class Setting {
    public int id_set;
    public String set_appel;
    public String set_msg;
    public String set_wtp;
    public String set_teams;
    public int utili_id;

    public int getId_set() {
        return id_set;
    }

    public void setId_set(int id_set) {
        this.id_set = id_set;
    }

    public String getSet_appel() {
        return set_appel;
    }

    public void setSet_appel(String set_appel) {
        this.set_appel = set_appel;
    }

    public String getSet_msg() {
        return set_msg;
    }

    public void setSet_msg(String set_msg) {
        this.set_msg = set_msg;
    }

    public String getSet_wtp() {
        return set_wtp;
    }

    public void setSet_wtp(String set_wtp) {
        this.set_wtp = set_wtp;
    }

    public String getSet_teams() {
        return set_teams;
    }

    public void setSet_teams(String set_teams) {
        this.set_teams = set_teams;
    }

    public int getUtili_id() {
        return utili_id;
    }

    public void setUtili_id(int utili_id) {
        this.utili_id = utili_id;
    }

    public Setting(int id_set, String set_appel, String set_msg, String set_wtp, String set_teams, int utili_id) {
        this.id_set = id_set;
        this.set_appel = set_appel;
        this.set_msg = set_msg;
        this.set_wtp = set_wtp;
        this.set_teams = set_teams;
        this.utili_id = utili_id;
    }
}
