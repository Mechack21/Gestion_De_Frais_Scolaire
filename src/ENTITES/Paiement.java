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
public class Paiement {

    String Matr, CdeF, Dte, devise;
    int num, mntant;

    public String getMatr() {
        return Matr;
    }

    public void setMatr(String Matr) {
        this.Matr = Matr;
    }

    public String getCdeF() {
        return CdeF;
    }

    public void setCdeF(String CdeF) {
        this.CdeF = CdeF;
    }

    public String getDte() {
        return Dte;
    }

    public void setDte(String Dte) {
        this.Dte = Dte;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMntant() {
        return mntant;
    }

    public void setMntant(int mntant) {
        this.mntant = mntant;
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
            String req = "insert into PAIEMENT(Matricule,CodeF,Montant,Devise,DateP) values(?,?,?,?,?)";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getMatr());
            pst.setString(2, getCdeF());
            pst.setInt(3, getMntant());
            pst.setString(4, getDevise());
            pst.setString(5, getDte());
            pst.executeUpdate();
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }
    }

    public void Modifier() {
        try {
            String req = "update PAIEMENT set Matricule=?,CodeF=?,Montant=?,Devise=?,DateP=? where Num=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setString(1, getMatr());
            pst.setString(2, getCdeF());
            pst.setInt(3, getMntant());
            pst.setString(4, getDevise());
            pst.setString(5, getDte());
            pst.setInt(6, getNum());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void Supprimer() {
        try {
            String req = "delete from PAIEMENT where Num=?";
            pst = cnx.meconnecter.prepareStatement(req);
            pst.setInt(1, getNum());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Paiement.class.getName()).log(Level.SEVERE, null, ex.getMessage());
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
                data[n][0] = Resultat.getInt(1);
                data[n][1] = Resultat.getString(2);
                data[n][2] = Resultat.getString(3);
                data[n][3] = Resultat.getInt(4);
                data[n][4] = Resultat.getString(5);
                data[n][5] = Resultat.getString(6);
                n++;

            }
            String titre[] = {"Code", "Eleve", "Intutilé Frais", "Montant", "Devise", "Date"};

            grille.setModel(new DefaultTableModel(data, titre));
        } catch (SQLException ex) {
            Logger.getLogger(Paiement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
