package dev.senzalla.connector;

import dev.senzalla.exception.ConnectionException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;

/**
 * @author Bomsalvez Freitas
 * @e-mail bomsalvez@gmail.com
 * @github github.com/Bomsalvez
 */
public class ConnectionMySql {

    public static void main(String[] args) {
        getConnection();
    }

    private static final Path DIR = Paths.get(System.getProperty("user.dir") + "/config/");
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String SERVER = config(DIR + "/server.properties");
    private static final String URL = "jdbc:mysql://" + SERVER + "/" + config(DIR + "/nameDB.properties");
    private static final String USER = config(DIR + "/user.properties");
    private static final String PASS = config(DIR + "/pass.properties");
    private static boolean trow;

    private static String config(String info) {
        try {
            FileInputStream dir = new FileInputStream(info);
            InputStreamReader input = new InputStreamReader(dir);
            BufferedReader br = new BufferedReader(input);
            return br.readLine();
        } catch (IOException ex) {
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
                throw new ConnectionException("Houve um Problema com sua Conexão!");
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
            throw new ConnectionException("Erro ao fechar a conexão com o Banco de Dados: ");
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
            throw new ConnectionException("Erro ao fechar a conexão com o Banco de Dados: ");
        }
    }

}
