package services;

import encapsulation.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServicio {
  private static UsuarioServicio instance;
  private List<Usuario> listaUsuarios = new ArrayList<>();

  private UsuarioServicio() {
    Usuario usuario = new Usuario("admin", "admin", "admin", true);
    listaUsuarios.add(usuario);
  }

  public static UsuarioServicio getInstance() {
    if (instance == null) {
      instance = new UsuarioServicio();
    }
    return instance;
  }

  public List<Usuario> getListaUsuarios() {
    return listaUsuarios;
  }

  public void setListaUsuarios(List<Usuario> listaUsuarios) {
    this.listaUsuarios = listaUsuarios;
  }

  public void addUsuario(Usuario usuario) {
    listaUsuarios.add(usuario);
  }

  public Usuario getUsuario(String usuario) {
    for (Usuario u : listaUsuarios) {
      if (u.getUsuario().equals(usuario)) {
        return u;
      }
    }
    return null;
  }

  public boolean deleteUsuario(String usuario) {
    for (Usuario u : listaUsuarios) {
      if (u.getUsuario().equals(usuario)) {
        listaUsuarios.remove(u);
        return true;
      }
    }
    return false;
  }

  public boolean updateUsuario(Usuario usuario) {
    for (Usuario u : listaUsuarios) {
      if (u.getUsuario().equals(usuario.getUsuario())) {
        u.setName(usuario.getName());
        u.setPassword(usuario.getPassword());
        return true;
      }
    }
    return false;
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
