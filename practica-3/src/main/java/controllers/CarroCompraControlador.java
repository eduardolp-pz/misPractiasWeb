package controllers;

import encapsulation.CarroCompra;
import encapsulation.Producto;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.CarroCompraServicio;
import services.ProductoServicio;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CarroCompraControlador {
  private final static  CarroCompraServicio carroCompraServicio = CarroCompraServicio.getInstance();
  private final static ProductoServicio productoServicio = ProductoServicio.getInstance();


  public static void setearCarroCompraSesion(@NotNull Context ctx) throws Exception {
    if(ctx.sessionAttribute("carroCompra") != null) return;

    ctx.sessionAttribute("carroCompra", carroCompraServicio.newCarroCompra());
    ctx.sessionAttribute("totalCarroCompra", 0);
  }

  public static void agregarProductoAlCarro(@NotNull Context ctx) throws Exception {
    int idProducto = Integer.parseInt(ctx.pathParam("id"));
    int cantidad = Integer.parseInt(Objects.requireNonNull(ctx.formParam("cantidad")));
    Producto producto = productoServicio.getProducto(idProducto);
    CarroCompra carroCompra = ctx.sessionAttribute("carroCompra");

    assert carroCompra != null;
    for (int i = 0; i < cantidad; i++)
      carroCompra.addProducto(producto);

    ctx.sessionAttribute("carroCompra", carroCompra);
    ctx.sessionAttribute("totalCarroCompra", carroCompra.getCount());
    ctx.redirect("/comprar");
  }

  public static void listarCarroCompra(@NotNull Context context) {
    CarroCompra carroCompra = context.sessionAttribute("carroCompra");
    assert carroCompra != null;
    Map<Producto, AbstractMap.SimpleEntry<Integer, BigDecimal>> resumenProductos = carroCompra.getResumenProductos();
    Map<String, Object> carroCompraModelo = new HashMap<>();
    carroCompraModelo.put("resumenProductos", resumenProductos);

    BigDecimal precioTotal = BigDecimal.ZERO;
    for(AbstractMap.SimpleEntry<Integer, BigDecimal> entry : resumenProductos.values()){
      precioTotal = precioTotal.add(entry.getValue());
    }
    carroCompraModelo.put("precioTotal", precioTotal);

    context.render("/public/templates/carrito-compra.html", carroCompraModelo);
  }

  public static void eliminarProductoDelCarro(@NotNull Context context) {
    String nombreProducto = (context.pathParam("name"));
    Producto producto = productoServicio.getByName(nombreProducto);
    CarroCompra carroCompra = context.sessionAttribute("carroCompra");
    assert carroCompra != null;
    carroCompra.getListaProductos().remove(producto);
    context.sessionAttribute("carroCompra", carroCompra);
    context.sessionAttribute("totalCarroCompra", carroCompra.getCount());
    context.redirect("/carrito");
  }

  public static void eliminarTodoDelCarro(@NotNull Context context) {
    CarroCompra carroCompra = context.sessionAttribute("carroCompra");
    assert carroCompra != null;
    carroCompra.getListaProductos().clear();
    context.sessionAttribute("carroCompra", carroCompra);
    context.sessionAttribute("totalCarroCompra", carroCompra.getCount());
    context.redirect("/carrito");
  }
}
