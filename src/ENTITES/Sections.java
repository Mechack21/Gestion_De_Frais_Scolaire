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
public class Sections {
 String cdeSect,Libelle;

    public String getCdeSect() {
        return cdeSect;
    }

    public void setCdeSect(String cdeSect) {
        this.cdeSect = cdeSect;
    }

    public String getLibelle() {
        return Libelle;
    }

    public void setLibelle(String Libelle) {
        this.Libelle = Libelle;
    }
  connexion cnx = new connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into SECTIONS(CodeSect,Libelle) values(?,?)";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getCdeSect());
            pst.setString(2, getLibelle());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Sections.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Modifier() {
        try {
            String req = "update SECTIONS set Libelle=? where CodeSect=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getLibelle());
            pst.setString(2, getCdeSect());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Sections.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Supprimer() {
        try {
            String req = "delete from SECTIONS where CodeSect=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getCdeSect());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Charger(JComboBox cmbx) {
        try {
            String req = "select Libelle from SECTIONS ";
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

    public String IDsection(String Libsection) {
        String id = "";
        try {
            String req = "select CodeSect from SECTIONS where Libelle=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, Libsection);
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
    public void Remplir(JTable grille,String req){
           try {
            pst = cnx.meconnecter.prepareStatement(req);
            Resultat=pst.executeQuery();
            int n = 0;
            while (Resultat.next()) {                
                n++;
            }
            pst = cnx.meconnecter.prepareStatement(req);
            Resultat=pst.executeQuery();
            Object data[][] = new Object[n][2];
            n=0;
            while (Resultat.next()) {                
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                n++;
            }
            String titre[] = {"Code Section","Libelle"};
            
            grille.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Sections.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
