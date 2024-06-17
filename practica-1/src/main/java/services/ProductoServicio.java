package services;

import encapsulation.Producto;

import java.math.BigDecimal;
import java.util.*;

public class ProductoServicio {
  private static ProductoServicio instance;
  private List<Producto> listaProductos = new ArrayList<>();

  private ProductoServicio() {
    Producto producto = new Producto(1, "Razer Viper Ultimate", new BigDecimal(3500));
    listaProductos.add(producto);
    Producto producto2 = new Producto(2, "Steel Series Artics Nova 7P", new BigDecimal(5000));
    listaProductos.add(producto2);
    Producto producto3 = new Producto(3, "Logitech G Pro X", new BigDecimal(2500));
    listaProductos.add(producto3);
    Producto producto4 = new Producto(4, "Corsair K95 RGB Platinum", new BigDecimal(3000));
    listaProductos.add(producto4);
    Producto producto5 = new Producto(5, "Razer Black Widow Elite", new BigDecimal(2000));
    listaProductos.add(producto5);
  }

  public static ProductoServicio getInstance() {
    if (instance == null) {
      instance = new ProductoServicio();
    }
    return instance;
  }

  public List<Producto> getListaProductos() {
    return listaProductos;
  }

  public void setListaProductos(List<Producto> listaProductos) {
    this.listaProductos = listaProductos;
  }

  public void addProducto(Producto producto) {
    listaProductos.add(producto);
  }

  public Producto getProducto(int id) {
    for (Producto p : listaProductos) {
      if (p.getId() == id) {
        return p;
      }
    }
    return null;
  }

  public Producto getByName(String name) {
    for (Producto p : listaProductos) {
      if (p.getNombre().equals(name)) {
        return p;
      }
    }
    return null;
  }

  public boolean deleteProducto(int id) {
    for (Producto p : listaProductos) {
      if (p.getId() == id) {
        listaProductos.remove(p);
        return true;
      }
    }
    return false;
  }

  public boolean updateProducto(Producto producto) {
    for (Producto p : listaProductos) {
      if (p.getNombre().equals(producto.getNombre())) {
        p.setPrecio(producto.getPrecio());
        return true;
      }
    }
    return false;
  }

  public Map<Producto, AbstractMap.SimpleEntry<Integer, BigDecimal>> getResumenProductos(List<Producto> productos) {
    Map<Producto, AbstractMap.SimpleEntry<Integer, BigDecimal>> resumen = new HashMap<>();
    for (Producto producto : productos) {
      if (resumen.containsKey(producto)) {
        AbstractMap.SimpleEntry<Integer, BigDecimal> entry = resumen.get(producto.getPrecio());
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
