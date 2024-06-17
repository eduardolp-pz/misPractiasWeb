package services;

import encapsulation.Usuario;
import org.jasypt.util.text.BasicTextEncryptor;
import util.BaseServiceDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsuarioServicio extends BaseServiceDatabase<Usuario> {
  private static UsuarioServicio instance;
  private final BasicTextEncryptor textEncryptor;

  private UsuarioServicio() {
    super(Usuario.class);
    Usuario usuario = new Usuario("admin", "Administrador", "admin", true);

    Usuario adminExiste = this.getUsuario(usuario.getUsuario());
    if (adminExiste == null) this.dbCreate(usuario);

    textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPassword("jsadkfaksldjfklasdjflkajsd");
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

}
