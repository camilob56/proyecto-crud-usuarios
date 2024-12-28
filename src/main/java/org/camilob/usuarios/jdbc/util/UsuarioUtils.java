package org.camilob.usuarios.jdbc.util;

import org.camilob.usuarios.jdbc.modelo.Usuario;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioUtils {

    private static final UsuarioUtils INSTANCE = new UsuarioUtils();

    private UsuarioUtils() {} // Constructor privado

    public static UsuarioUtils getInstance() {
        return INSTANCE;
    }

    public Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }

    public static Long solicitarId(String mensaje) {
        while (true) {
            String inputId = JOptionPane.showInputDialog(mensaje);

            if (inputId == null || inputId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un ID.");
            } else {
                try {
                    return Long.parseLong(inputId);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "ID no válido. Por favor ingrese un número válido.");
                }
            }
        }
    }

}
