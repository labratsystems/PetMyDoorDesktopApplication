/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PetMyDoorDesktopApplicationMVC.model.domain;

import java.io.Serializable;

/**
 *
 * @author Lab Rat Systems
 */
public class Porta implements Serializable{
    private int idPorta;
    private String localizacao;
    private String login;

    public int getIdPorta() {
        return idPorta;
    }

    public void setIdPorta(int idPorta) {
        this.idPorta = idPorta;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
