package services;

import entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServicio {

  private static UsuarioServicio instance;
  private List<Usuario> listaUsuarios = new ArrayList<>();

  private UsuarioServicio() {
    Usuario usuario = new Usuario("admin", "admin");
    listaUsuarios.add(usuario);
  }

  public static UsuarioServicio getInstance() {
    if (instance == null) {
      instance = new UsuarioServicio();
    }
    return instance;
  }

  public Usuario login(String usuario, String password) {
    for (Usuario u : listaUsuarios) {
      if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
        return u;
      }
    }
    return null;
  }
}
