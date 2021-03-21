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
public class Eleves {

    String matricle, nom, Postnom, Sexe, Adress, Cdeclass;

    public String getMatricle() {
        return matricle;
    }

    public void setMatricle(String matricle) {
        this.matricle = matricle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPostnom() {
        return Postnom;
    }

    public void setPostnom(String Postnom) {
        this.Postnom = Postnom;
    }

    public String getSexe() {
        return Sexe;
    }

    public void setSexe(String Sexe) {
        this.Sexe = Sexe;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String Adress) {
        this.Adress = Adress;
    }

    public String getCdeclass() {
        return Cdeclass;
    }

    public void setCdeclass(String Cdeclass) {
        this.Cdeclass = Cdeclass;
    }

    connexion cnx = new connexion();
    PreparedStatement pst;
    ResultSet Resultat;

    public void Ajouter() {
        try {
            String req = "insert into ELEVES(Matricule,Nom,Postnom,Sexe,Adresse,CodeClass) values(?,?,?,?,?,?)";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getMatricle());
            pst.setString(2, getNom());
            pst.setString(3, getPostnom());
            pst.setString(4, getSexe());
            pst.setString(5, getAdress());
            pst.setString(6, getCdeclass());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Eleves.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Modifier() {
        try {
            String req = "update ELEVES set Nom=?,Postnom=?,Sexe=?,Adresse=?,CodeClass=? where Matricule=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getNom());
            pst.setString(2, getPostnom());
            pst.setString(3, getSexe());
            pst.setString(4, getAdress());
            pst.setString(5, getCdeclass());
            pst.setString(6, getMatricle());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Eleves.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Supprimer() {
        try {
            String req = "delete from ELEVES where Matricule=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getMatricle());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Eleves.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

    public void Charger(JComboBox cmbx) {
        try {
            String req = "select Nom from ELEVES";
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

    public String IDeleve(String Libeleve) {
        String id = "";
        try {
            String req = "select Matricule from ELEVES where Nom=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, Libeleve);
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
            Object data[][] = new Object[n][6];
            n = 0;
            while (Resultat.next()) {
                data[n][0] = Resultat.getString(1);
                data[n][1] = Resultat.getString(2);
                data[n][2] = Resultat.getString(3);
                data[n][3] = Resultat.getString(4);
                data[n][4] = Resultat.getString(5);
                data[n][5] = Resultat.getString(6);
                n++;

            }
            String titre[] = {"Code", "Nom", "Postnom", "Sexe", "Adresse", "Classes"};

            grille.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Eleves.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
