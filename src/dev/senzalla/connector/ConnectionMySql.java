/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.senzalla.connector;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Black Burn Cybernetic
 * @e-mail blackburncyber@gmail.com
 * @github github.com/BlackBurnCybernetic
 */
public class ConnectionMySql {

    private static final Path DIR = Paths.get(System.getProperty("user.dir") + "/config/");
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String SERVER = config(DIR + "/server.txt");
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + config(DIR + "/nameDB.txt");
    private static final String USER = config(DIR + "/user.txt");
    private static final String PASS = config(DIR + "/pass.txt");

    private static String config(String info) {
        try {
            FileInputStream dir = new FileInputStream(info);
            InputStreamReader input = new InputStreamReader(dir);
            BufferedReader br = new BufferedReader(input);
            return br.readLine();
        } catch (IOException ex) {
            return null;
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar a conexão com o Banco de Dados: " + ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {

        closeConnection(con);

        try {

            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar a conexão com o Banco de Dados: " + ex);
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {

        closeConnection(con, stmt);

        try {

            if (rs != null) {
                rs.close();
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar a conexão com o Banco de Dados: " + ex);
        }
    }
}
