package encapsulation;

import java.util.Date;
import java.util.List;

public class VentasProductos {
  private long id;
  private Date fechaCompra;
  private String nombreCliente;
  private List<Producto> listaProductos;

  public VentasProductos(long id, Date fechaCompra, String nombreCliente, List<Producto> listaProductos) {
    this.id = id;
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
