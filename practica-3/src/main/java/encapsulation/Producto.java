package encapsulation;
import jakarta.persistence.*;
import java.io.Serializable;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Producto implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String nombre;
  private BigDecimal precio;

  @ManyToMany(mappedBy = "listaProductos")
  private List<VentasProductos> ventasProductosList;

  public Producto(String nombre, BigDecimal precio) {
    this.nombre = nombre;
    this.precio = precio;
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
}
