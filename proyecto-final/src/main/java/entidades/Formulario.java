package entidades;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import org.bson.types.ObjectId;

@Entity("formularios")
public class Formulario {
  @Id
  ObjectId id;
  private String nombre;
  private String sector;
  private String nivelEducacion;
  @Reference
  private Usuario usuario;
  @Reference
  private Coordenadas coordenadas;

  public Formulario() {
  }

public Formulario(String nombre, String sector, String nivelEducacion, Usuario usuario, Coordenadas coordenadas) {
    this.nombre = nombre;
    this.sector = sector;
    this.nivelEducacion = nivelEducacion;
    this.usuario = usuario;
    this.coordenadas = coordenadas;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public String getNivelEducacion() {
    return nivelEducacion;
  }

  public void setNivelEducacion(String nivelEducacion) {
    this.nivelEducacion = nivelEducacion;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Coordenadas getCoordenadas() {
    return coordenadas;
  }

  public void setCoordenadas(Coordenadas coordenadas) {
    this.coordenadas = coordenadas;
  }
}
