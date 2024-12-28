package org.camilob.usuarios.jdbc;
import org.camilob.usuarios.jdbc.modelo.Usuario;
import org.camilob.usuarios.jdbc.repositorio.Repositorio;
import org.camilob.usuarios.jdbc.repositorio.UsuarioRepositorioImpl;
import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.camilob.usuarios.jdbc.util.UsuarioUtils.solicitarId;

public class EjemploAplicacion {
    public static void main(String[] args) {

        Repositorio<Usuario> repositorio = new UsuarioRepositorioImpl();
        int opcionIndice = 0;

        Map<String, Integer> operaciones = new LinkedHashMap<>();
        operaciones.put("Actualizar", 1);
        operaciones.put("Buscar por id", 2);
        operaciones.put("Eliminar", 3);
        operaciones.put("Agregar", 4);
        operaciones.put("Listar", 5);
        operaciones.put("Salir", 6);

        Object[] opArreglo = operaciones.keySet().toArray();

        while (true) {
            Object opcion = JOptionPane.showInputDialog(null,
                    "Seleccione una operación",
                    "Mantenedor de Usuarios",
                    JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
                continue;
            }

            opcionIndice = operaciones.get(opcion.toString());

            switch (opcionIndice) {
                case 1:
                    Long idEditar = solicitarId("Ingrese el ID del usuario que desea editar:");
                    Usuario usuarioEncontrado = repositorio.porId(idEditar);

                    if (usuarioEncontrado != null) {
                        String username = JOptionPane.showInputDialog("Ingrese el username del usuario (actual: " + usuarioEncontrado.getUsername() + "):");
                        String password = JOptionPane.showInputDialog("Ingrese la contraseña del usuario (actual: " + usuarioEncontrado.getPassword() + "):");
                        String email = JOptionPane.showInputDialog("Ingrese el email del usuario (actual: " + usuarioEncontrado.getEmail() + "):");

                        if (username != null && password != null && email != null && !username.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
                            usuarioEncontrado.setUsername(username);
                            usuarioEncontrado.setPassword(password);
                            usuarioEncontrado.setEmail(email);
                            repositorio.actualizar(usuarioEncontrado);
                        } else {
                            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID " + idEditar);
                    }
                    break;

                case 2:
                    Long idBuscar = solicitarId("Ingrese el ID del usuario que desea buscar:");
                    usuarioEncontrado = repositorio.porId(idBuscar);

                    if (usuarioEncontrado != null) {
                        JOptionPane.showMessageDialog(null, "Usuario encontrado:\n" + usuarioEncontrado.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un usuario con el ID " + idBuscar);
                    }
                    break;

                case 3:
                    Long idEliminar = solicitarId("Ingrese el ID del usuario que desea eliminar:");
                    repositorio.eliminar(idEliminar);
                    JOptionPane.showMessageDialog(null, "Usuario con ID " + idEliminar + " ha sido eliminado.");
                    break;

                case 4:
                    String username = JOptionPane.showInputDialog("Ingrese el ussername del usuario:");
                    String password = JOptionPane.showInputDialog("Ingrese la contraseña del usuario:");
                    String email = JOptionPane.showInputDialog("Ingrese el email del usuario:");

                    if (username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
                        break;
                    }

                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setUsername(username);
                    nuevoUsuario.setPassword(password);
                    nuevoUsuario.setEmail(email);
                    Usuario usuarioCreado = repositorio.crear(nuevoUsuario);
                    if (usuarioCreado != null) {
                        JOptionPane.showMessageDialog(null, "Usuario creado con éxito. ID generado: " + usuarioCreado.getId());
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al crear el usuario.");
                    }
                    break;

                case 5:
                    List<Usuario> usuarios = repositorio.listar();
                    StringBuilder listaUsuarios = new StringBuilder();

                    for (Usuario usuario : usuarios) {
                        listaUsuarios.append(usuario.toString()).append("\n");
                    }

                    if (!listaUsuarios.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Lista de usuarios:\n" + listaUsuarios.toString());
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay usuarios registrados.");
                    }
                    break;

                case 6:
                    repositorio.salir();
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Operación no válida.");
                    break;
            }
        }
    }
}
