package org.camilob.usuarios.jdbc.repositorio;

import org.camilob.usuarios.jdbc.modelo.Usuario;
import org.camilob.usuarios.jdbc.util.ConexionBaseDatos;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.camilob.usuarios.jdbc.util.UsuarioUtils;

public class UsuarioRepositorioImpl implements Repositorio<Usuario>{

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getInstance();
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET username = ?, password = ?, email = ? WHERE id = ?";
        try (Connection conexion = getConnection();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());
            stmt.setLong(4, usuario.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario con el ID " + usuario.getId() + " para actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario.");
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conexion = getConnection();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setLong(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Usuario crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios(username, password, email) VALUES(?, ?, ?)";
        try (Connection conexion = getConnection();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, usuario.getUsername());
                stmt.setString(2, usuario.getPassword());
                stmt.setString(3, usuario.getEmail());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId((long) rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while (rs.next()) {
                Usuario u = UsuarioUtils.getInstance().mapearUsuario(rs);
                usuarios.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) {
        Usuario usuario = null;

        try (PreparedStatement stmt = getConnection()
                .prepareStatement("SELECT * FROM usuarios WHERE id = ?")){
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = UsuarioUtils.getInstance().mapearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void salir() {
        try {
            if (getConnection() != null && !getConnection().isClosed()) {
                getConnection().close();
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al cerrar la conexión.");
        }
    }

}
