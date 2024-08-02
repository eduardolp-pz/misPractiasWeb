package encapsulation;

import java.math.BigDecimal;
import java.util.*;

public class CarroCompra {
  private long id;
  private List<Producto> listaProductos;

  public CarroCompra(List<Producto> listaProductos, long id) {
    this.listaProductos = listaProductos;
    this.id = id;
  }

  public CarroCompra() {
    this.id = 1;
    this.listaProductos = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public List<Producto> getListaProductos() {
    return listaProductos;
  }

  public void setListaProductos(List<Producto> listaProductos) {
    this.listaProductos = listaProductos;
  }

  public int getCount() {
    Set<Producto> productosDiferentes = new HashSet<>(listaProductos);
    return productosDiferentes.size();
  }

  public void addProducto(Producto producto) {
    listaProductos.add(producto);
  }

  public Map<Producto, AbstractMap.SimpleEntry<Integer, BigDecimal>> getResumenProductos() {
    Map<Producto, AbstractMap.SimpleEntry<Integer, BigDecimal>> resumen = new HashMap<>();
    for (Producto producto : listaProductos) {
      if (resumen.containsKey(producto)) {
        AbstractMap.SimpleEntry<Integer, BigDecimal> entry = resumen.get(producto);
        Integer newQuantity = entry.getKey() + 1;
        BigDecimal newTotal = entry.getValue().add(producto.getPrecio());
        // Update the map with a new entry
        resumen.put(producto, new AbstractMap.SimpleEntry<>(newQuantity, newTotal));
      } else {
        resumen.put(producto, new AbstractMap.SimpleEntry<>(1, producto.getPrecio()));
      }
    }
    return resumen;
  }

}
