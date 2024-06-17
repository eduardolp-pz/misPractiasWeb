package encapsulation;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {
  @Id
  private String usuario;
  private String name;
  private String password;
  private Boolean isAdmin;

  public Usuario(String usuario, String name, String password) {
    this.usuario = usuario;
    this.name = name;
    this.password = password;
    this.isAdmin = false;
  }

  public Usuario(String usuario, String name, String password, Boolean isAdmin) {
    this.usuario = usuario;
    this.name = name;
    this.password = password;
    this.isAdmin = isAdmin;
  }

  public Usuario() {
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(Boolean isAdmin) {
    this.isAdmin = isAdmin;
  }
}
