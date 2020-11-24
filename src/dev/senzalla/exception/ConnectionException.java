package dev.senzalla.exception;

import javax.swing.*;

public class ConnectionException extends RuntimeException {
    public ConnectionException(String msg) {
        JOptionPane.showMessageDialog(
                new JFrame(),
                msg,
                "Erro no Servidor",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
