package org.camilob.usuarios.jdbc.repositorio;

import java.util.List;

public interface Repositorio<T> {

    void actualizar(T t);

    void eliminar(Long id);

    T crear(T t);

    List<T> listar();

    T porId(Long id);

    void salir();

}
