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
public class Classes {

    String cdeClasse, libelle, cdeOption;

    public String getCdeClasse() {
        return cdeClasse;
    }

    public void setCdeClasse(String cdeClasse) {
        this.cdeClasse = cdeClasse;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCdeOption() {
        return cdeOption;
    }

    public void setCdeOption(String cdeOption) {
        this.cdeOption = cdeOption;
    }
    connexion cnx = new connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into CLASSES(CodeClass,Libelle,CodeOpt) values(?,?,?)";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getCdeClasse());
            pst.setString(2, getLibelle());
            pst.setString(3, getCdeOption());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());;
        }
    }

    public void Modifier() {
        try {
            String req = "update CLASSES set Libelle=?,CodeOpt=? where CodeClass=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getLibelle());
            pst.setString(2, getCdeOption());
            pst.setString(3, getCdeClasse());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Classes.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Supprimer() {
        try {
            String req = "delete from CLASSES where CodeClass=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getCdeClasse());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Charger(JComboBox cmbx) {
        try {
            String req = "select Libelle from CLASSES";
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

    public String IDclasse(String Libclasse) {
        String id = "";
        try {
            String req = "select CodeClass from CLASSES where Libelle=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, Libclasse);
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
            Object data[][] = new Object[n][3];
            n=0;
            while (Resultat.next()) {                
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                data[n][2] = Resultat.getString(3);
                n++;
            }
            String titre[] = {"Code","Libelle","Options"};
            
            grille.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Classes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
