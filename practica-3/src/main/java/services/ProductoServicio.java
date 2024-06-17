package services;

import encapsulation.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import util.BaseServiceDatabase;

import java.math.BigDecimal;
import java.util.*;

public class ProductoServicio extends BaseServiceDatabase<Producto> {
  private static ProductoServicio instance;

  private ProductoServicio() {
    super(Producto.class);
    Producto[] productos = {
      new Producto("Razer Viper Ultimate", new BigDecimal(3500)),
      new Producto("Steel Series Artics Nova 7P", new BigDecimal(5000)),
      new Producto("Logitech G Pro X", new BigDecimal(2500)),
      new Producto("Corsair K95 RGB Platinum", new BigDecimal(3000)),
      new Producto("Razer Black Widow Elite", new BigDecimal(2000))
    };

    for (Producto producto : productos) {
      Producto productoExistente = this.getProductoPorNombre(producto.getNombre());
      if (productoExistente == null)
        this.addProducto(producto);
    }
  }

  private Producto getProductoPorNombre(String nombre) {
    try (EntityManager em = getEntityManager()) {
      Query query = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = :nombre");
      query.setParameter("nombre", nombre);
      return (Producto) query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  public static ProductoServicio getInstance() {
    if (instance == null) {
      instance = new ProductoServicio();
    }
    return instance;
  }

  public List<Producto> getListaProductos() {
    return this.dbFindAll();
  }

  public void addProducto(Producto producto) {
    this.dbCreate(producto);
  }

  public Producto getProducto(int id) {
    return this.dbFind(id);
  }

  public Producto getByName(String name) {
    for (Producto p : this.getListaProductos()) {
      if (p.getNombre().equals(name)) {
        return p;
      }
    }
    return null;
  }

  public boolean deleteProducto(int id) {
    for (Producto p : this.getListaProductos()) {
      if (p.getId() == id) {
        this.getListaProductos().remove(p);
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
