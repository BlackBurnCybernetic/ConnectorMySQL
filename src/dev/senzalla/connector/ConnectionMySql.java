package dev.senzalla.connector;

import dev.senzalla.exception.ConnecctionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author Bomsalvez Freitas
 * @e-mail bomsalvez@gmail.com
 * @github github.com/Bomsalvez
 */
public class ConnectionMySql {

    private static final File DIR = new File(System.getProperty("user.dir") + "/config/database.properties");
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String SERVER = config("server");
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + config("nameDB");
    private static final String USER = config("user");
    private static final String PASS = config("pass");
    private static boolean trow;

    private static String config(String info) {
        try {
            FileInputStream input = new FileInputStream(DIR);
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty(info);
        } catch (IOException e) {
            trow = true;
            return null;
        }
    }

    public static Connection getConnection() {
        if (trow) {
            new FrmConnectorCreate().setVisible(true);
        } else {
            try {
                Class.forName(DRIVER);
                return DriverManager.getConnection(URL, USER, PASS);
            } catch (ClassNotFoundException | SQLException ex) {
                throw new ConnecctionException("Houve um Problema com sua Conexão!");
            }
        }
        return null;
    }

    public static void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            throw new ConnecctionException("Erro ao fechar a conexão com o Banco de Dados: ");
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt) {
        closeConnection(con);
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao fechar a conexão com o Banco de Dados: ");
        }
    }

    public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {
        closeConnection(con, stmt);
        try {

            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            throw new ConnecctionException("Erro ao fechar a conexão com o Banco de Dados: ");
        }
    }

}
