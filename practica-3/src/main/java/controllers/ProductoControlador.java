package controllers;

import encapsulation.Producto;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.ProductoServicio;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoControlador {
  private final static ProductoServicio productoServicio = ProductoServicio.getInstance();

  public static void listarProductos(@NotNull Context ctx) throws Exception {
    List<Producto> listaProductos = productoServicio.getListaProductos();
    Map<String, Object> listaProductosMap = new HashMap<>();
    listaProductosMap.put("productos", listaProductos);
    ctx.render("/public/templates/comprar.html", listaProductosMap);
  }

  public static void listarProductosAdmin(@NotNull Context ctx) throws Exception {
    List<Producto> listaProductos = productoServicio.getListaProductos();
    Map<String, Object> listaProductosMap = new HashMap<>();
    listaProductosMap.put("productos", listaProductos);
    listaProductosMap.put("titulo", "Crear Producto");
    listaProductosMap.put("boton", "Crear Producto");
    ctx.render("/public/templates/administrar-productos.html", listaProductosMap);
  }

  public static void listarProductosEditar(@NotNull Context ctx) throws Exception {
    List<Producto> listaProductos = productoServicio.getListaProductos();
    int id = Integer.parseInt(ctx.pathParam("id"));
    Producto producto = productoServicio.getProducto(id);
    Map<String, Object> listaProductosMap = new HashMap<>();
    listaProductosMap.put("productos", listaProductos);
    listaProductosMap.put("producto", producto);
    listaProductosMap.put("titulo", "Editar Producto");
    listaProductosMap.put("boton", "Editar Producto");
    listaProductosMap.put("nombre", producto.getNombre());
    listaProductosMap.put("precio", producto.getPrecio());

    ctx.render("/public/templates/administrar-productos.html", listaProductosMap);
  }

  public static void eliminarProducto(@NotNull Context ctx) throws Exception {
    int idProducto = Integer.parseInt(ctx.pathParam("id"));
    productoServicio.deleteProducto(idProducto);
    ctx.redirect("/productos");
  }

  public static void agregarProducto(@NotNull Context context) {
    String nombre = context.formParam("nombre");
    String precio = context.formParam("precio");
    assert precio != null;
    BigDecimal precioDecimal = new BigDecimal(precio);

    Producto producto = new Producto( nombre, precioDecimal);
    productoServicio.addProducto(producto);
    context.redirect("/productos");
  }

  public static void editarProducto(@NotNull Context context) {
    System.out.println("Editar Producto");
    int id = Integer.parseInt(context.pathParam("id"));
    Producto producto = productoServicio.getProducto(id);
    String nombre = context.formParam("nombre");
    String precio = context.formParam("precio");
    assert precio != null;
    BigDecimal precioDecimal = new BigDecimal(Integer.parseInt(precio));

    producto.setNombre(nombre);
    producto.setPrecio(precioDecimal);
    context.redirect("/productos");
  }
}
