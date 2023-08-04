/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
CREATE TABLE clientesC (
 nombre varchar(20) default NULL,
 apellido varchar(20) default NULL,
 dni int(8)default NULL,
 clave int(4) default NULL,
 saldo int(20) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 
*/
package ProyectoFinalSD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseBancoA {
    private final static String login = "root";
    private final static String password = "";
    private final static String url = "jdbc:mysql://localhost:3306/banco_a";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                System.out.println("Usted esta en linea con el Banco 'A'");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
