/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ENTITES;

import CONNEXION.connexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MECHACK
 */
public class Frais {

    String codeFrais, Libelle, devise;
    int Montant;

    public String getCodeFrais() {
        return codeFrais;
    }

    public void setCodeFrais(String codeFrais) {
        this.codeFrais = codeFrais;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String Libelle) {
        this.Libelle = Libelle;
    }

    public int getMontant() {
        return Montant;
    }

    public void setMontant(int Montant) {
        this.Montant = Montant;
    }

    public String getDevise() {
        return devise;
    }

    public void setDevise(String devise) {
        this.devise = devise;
    }

    connexion cnx = new connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into FRAIS(CodeF,Libelle,Montant,Devise) values(?,?,?,?)";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getCodeFrais());
            pst.setString(2, getLibelle());
            pst.setInt(3, getMontant());
            pst.setString(4, getDevise());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Frais.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Modifier() {
        try {
            String req = "update FRAIS set Libelle=?,Montant=?,Devise=? where CodeF=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getLibelle());
            pst.setInt(2, getMontant());
            pst.setString(3, getDevise());
            pst.setString(4, getCodeFrais());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Frais.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Supprimer() {
        try {
            String req = "delete from  FRAIS where CodeF=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getCodeFrais());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Frais.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Charger(JComboBox cmbx) {
        try {
            String req = "select Libelle from FRAIS";
            cmbx.setModel(new DefaultComboBoxModel(new String[]{""}));
            pst = cnx.meconnecter.prepareStatement(req);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {
                cmbx.addItem(Resultat.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Erreur de CHARGEMENT du combobox : " + e.getMessage());
        }
    }

    public String IDfrais(String Libfrais) {
        String id = "";
        try {
            String req = "select CodeF from FRAIS where Libelle=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, Libfrais);
            Resultat = pst.executeQuery();
            while (Resultat.next()) {
                id = Resultat.getString(1);
            }
            return id;
        } catch (SQLException e) {
            System.out.println("Erreur d'identifiant" + e.getMessage());
        }
        return id;
    }

    public void Remplir(JTable grille, String req) {
        try {
            pst = cnx.meconnecter.prepareStatement(req);
            Resultat = pst.executeQuery();
            int n = 0;
            while (Resultat.next()) {
                n++;
            }
            pst = cnx.meconnecter.prepareStatement(req);
            Resultat = pst.executeQuery();
            Object data[][] = new Object[n][4];
            n = 0;
            while (Resultat.next()) {
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                data[n][2] = Resultat.getInt(3);
                data[n][3] = Resultat.getString(4);
                n++;
            }
            String titre[] = {"Code", "Libelle", "Montant", "Devise"};

            grille.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Frais.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
