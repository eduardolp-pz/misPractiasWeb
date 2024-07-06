package encapsulation;
import jakarta.persistence.*;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Producto implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String nombre;
  private BigDecimal precio;
  private String descripcion;
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Foto> fotos;
  @OneToMany(cascade = CascadeType.REMOVE,fetch = FetchType.EAGER)
  private List<Comentario> comentarios;

  @ManyToMany(mappedBy = "listaProductos")
  private List<VentasProductos> ventasProductosList;

  public Producto(String nombre, BigDecimal precio, String descripcion, List<Foto> fotos) {
    this.nombre = nombre;
    this.precio = precio;
    this.descripcion = descripcion;
    this.fotos = fotos;
    this.comentarios = new ArrayList<>();
  }

  public Producto() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public List<Foto> getFotos() {
    return fotos;
  }

  public void setFotos(List<Foto> fotos) {
    this.fotos = fotos;
  }

  public List<Comentario> getComentarios() {
    return comentarios;
  }

  public void setComentarios(List<Comentario> comentarios) {
    this.comentarios = comentarios;
  }
}
