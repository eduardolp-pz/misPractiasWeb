package services;

import encapsulation.Usuario;
import org.eclipse.jetty.websocket.api.Session;
import org.jasypt.util.text.BasicTextEncryptor;
import util.BaseServiceDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UsuarioServicio extends BaseServiceDatabase<Usuario> {
  private static UsuarioServicio instance;
  private final BasicTextEncryptor textEncryptor;
  private final Set<String> usuariosConectados;
  private final Set<Session> sessions = new HashSet<>();

  private UsuarioServicio() {
    super(Usuario.class);
    textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPassword("jsadkfaksldjfklasdjflkajsd");
    usuariosConectados = new HashSet<>();
  }

  public Usuario getUsuario(String usuario){
    return this.dbFind(usuario);
  }

  public static UsuarioServicio getInstance() {
    if (instance == null) {
      instance = new UsuarioServicio();
    }
    return instance;
  }

  public String encryptText(String text){
    return textEncryptor.encrypt(text);
  }

  public String decryptText(String encryptedText){
    return textEncryptor.decrypt(encryptedText);
  }

  public List<Usuario> getListaUsuarios() {
    return this.dbFindAll();
  }


  public Usuario login(String usuario, String password) {
    for (Usuario u : this.getListaUsuarios()) {
      if (u.getUsuario().equals(usuario) && u.getPassword().equals(password)) {
        return u;
      }
    }
    return null;
  }

  public void agregarUsuarioConectado(String username) {
    usuariosConectados.add(username);
  }

  public void eliminarUsuarioConectado(String username) {
    usuariosConectados.remove(username);
  }

  public int getNumeroUsuariosConectados() {
    return usuariosConectados.size();
  }

  public void agregarSesion(Session session) {
    sessions.add(session);
  }

  public void eliminarSesion(Session session) {
    sessions.remove(session);
  }

  public void notificarEliminacionComentario() {
    sessions.forEach(session -> {
      session.getRemote().sendString("deleted", null);
    });
  }

  public void notificarActualizacionVentas() {
    sessions.forEach(session -> {
      session.getRemote().sendString("updated", null);
    });
  }

}
