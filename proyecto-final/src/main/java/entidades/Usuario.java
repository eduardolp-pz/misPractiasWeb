package entidades;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("usuarios")
public class Usuario {
  @Id
  ObjectId id;
  private String usuario;
  private String nombre;
  private String correo;
  private String clave;
  private boolean administrador;
  private boolean encuestador;
  private boolean activo;

  public Usuario() {
  }

  public Usuario(String usuario, String nombre, String correo, String clave, boolean administrador, boolean encuestador, boolean activo) {
    this.usuario = usuario;
    this.nombre = nombre;
    this.correo = correo;
    this.clave = clave;
    this.administrador = administrador;
    this.encuestador = encuestador;
    this.activo = activo;
  }

  public Usuario(String usuario, String nombre, String correo, String clave, boolean administrador) {
    this.usuario = usuario;
    this.nombre = nombre;
    this.correo = correo;
    this.clave = clave;
    this.administrador = administrador;
    this.encuestador = false;
    this.activo = true;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public boolean isAdministrador() {
    return administrador;
  }

  public void setAdministrador(boolean administrador) {
    this.administrador = administrador;
  }

  public boolean isEncuestador() {
    return encuestador;
  }

  public void setEncuestador(boolean encuestador) {
    this.encuestador = encuestador;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
