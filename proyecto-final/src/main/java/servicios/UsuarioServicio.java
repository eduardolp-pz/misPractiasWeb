package servicios;

import Util.MongoDb;
import dev.morphia.query.filters.Filters;
import entidades.Usuario;

import java.util.List;

public class UsuarioServicio extends MongoDb<Usuario> {
  private static UsuarioServicio instancia;

  private UsuarioServicio() {
    super(Usuario.class);
  }

  public static UsuarioServicio getInstancia() {
    if (instancia == null) {
      instancia = new UsuarioServicio();
    }
    return instancia;
  }

  public Usuario crear(String nombreUsuario, String nombre, String clave, boolean administrador, boolean encuestador) {
    Usuario usuario = new Usuario();
    usuario.setUsuario(nombreUsuario);
    usuario.setNombre(nombre);
    usuario.setClave(clave);
    usuario.setAdministrador(administrador);
    usuario.setEncuestador(encuestador);
    usuario.setActivo(true);

    this.createDb(usuario);
    return usuario;
  }

  public Usuario modificar(String nombreUsuario, String nombre, String clave, boolean administrador, boolean encuestador) {
    Usuario usuario = this.findByUsername(nombreUsuario);
    usuario.setNombre(nombre);
    usuario.setClave(clave);
    usuario.setAdministrador(administrador);
    usuario.setEncuestador(encuestador);

    this.updateDb(usuario);
    return usuario;
  }

  public void desactivar(String nombreUsuario) {
    Usuario usuario = this.findByUsername(nombreUsuario);
    usuario.setActivo(false);
    this.updateDb(usuario);
  }

  public Usuario findByUsername(String username) {
    return findDb().filter(Filters.eq("username", username)).first();
  }

  public boolean checkPassword(String username, String password) {
    Usuario usuario = this.findByUsername(username);
    return usuario != null && usuario.getClave().equals(password);
  }

}