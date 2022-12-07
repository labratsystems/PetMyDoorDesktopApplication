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
public class Passagem implements Serializable{
    private int idPorta;
    private String idTag;
    private String dataHoraPassagem;
    private String direcao;
    
    public int getIdPorta() {
        return idPorta;
    }

    public void setIdPorta(int idPorta) {
        this.idPorta = idPorta;
    }

    public String getIdTag() {
        return idTag;
    }

    public void setIdTag(String idTag) {
        this.idTag = idTag;
    }

    public String getDataHoraPassagem() {
        return dataHoraPassagem;
    }

    public void setDataHoraPassagem(String dataHoraPassagem) {
        this.dataHoraPassagem = dataHoraPassagem;
    }

    public String getDirecao() {
        return direcao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }
}
