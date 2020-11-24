package dev.senzalla.exception;

import javax.swing.*;

public class ConnecctionException extends RuntimeException {
    public ConnecctionException(String msg) {
        JOptionPane.showMessageDialog(
                new JFrame(),
                msg,
                "Erro no Servidor",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
