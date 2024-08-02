package encapsulation;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class VentasProductos implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private Date fechaCompra;
  private String nombreCliente;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "VentasProductos_Producto",
    joinColumns = @JoinColumn(name = "ventasProductos_id"),
    inverseJoinColumns = @JoinColumn(name = "producto_id")
  )
  private List<Producto> listaProductos;

  public VentasProductos(Date fechaCompra, String nombreCliente, List<Producto> listaProductos) {
    this.fechaCompra = fechaCompra;
    this.nombreCliente = nombreCliente;
    this.listaProductos = listaProductos;
  }

  public VentasProductos() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getFechaCompra() {
    return fechaCompra;
  }

  public void setFechaCompra(Date fechaCompra) {
    this.fechaCompra = fechaCompra;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public List<Producto> getListaProductos() {
    return listaProductos;
  }

  public void setListaProductos(List<Producto> listaProductos) {
    this.listaProductos = listaProductos;
  }
}
