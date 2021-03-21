/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package CONNEXION;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author MECHACK
 */
public class connexion {
     public Connection meconnecter;
    String chemin = "jdbc:sqlserver://PEDRO\\MUTOKASERVER:1433;databaseName=GestionFrais";
    String utilisateur = "sa";
    String motDePasse = "123456";

    public connexion() {
        try {
            meconnecter = DriverManager.getConnection(chemin, utilisateur, motDePasse);
        } catch (SQLException ex) {
            Logger.getLogger(connexion.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }

}
