package controllers;

import encapsulation.CarroCompra;
import encapsulation.Producto;
import encapsulation.VentasProductos;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import services.ProductoServicio;
import services.VentasProductosServicio;

import java.math.BigDecimal;
import java.util.*;

public class VentaProductosControlador {
  private final static VentasProductosServicio ventasProductosServicio = VentasProductosServicio.getInstance();
  private final static ProductoServicio productoServicio = ProductoServicio.getInstance();

  public static void comprarProductos(@NotNull Context context) {
    String nombre = context.formParam("cliente");
    CarroCompra carroCompra = context.sessionAttribute("carroCompra");
    assert carroCompra != null;
    long nextId = VentasProductosServicio.getInstance().getListaVentasProductos().size() + 1;
    Date currentDate = new Date();
    VentasProductos nuevaVenta = new VentasProductos(nextId, currentDate, nombre, carroCompra.getListaProductos());
    ventasProductosServicio.addVentasProductos(nuevaVenta);

    context.sessionAttribute("carroCompra", null);
    context.sessionAttribute("totalCarroCompra", 0);
    context.redirect("/comprar");
  }

  public static void listarVentasProductos(@NotNull Context context) {
    List<VentasProductos> ventasProductos = ventasProductosServicio.getListaVentasProductos();
    Map<String, Object> ventasModelo = new HashMap<>();

    List<Map<String, Object>> listaVentasModelo = new ArrayList<>();

    for(VentasProductos vp : ventasProductos){
      Map<String, Object> ventaModelo = new HashMap<>();
      Map<Producto, AbstractMap.SimpleEntry<Integer, BigDecimal>> resumenProductos = productoServicio.getResumenProductos(vp.getListaProductos());
      ventaModelo.put("productos", resumenProductos);
      ventaModelo.put("fecha", vp.getFechaCompra());
      ventaModelo.put("cliente", vp.getNombreCliente());
      listaVentasModelo.add(ventaModelo);
    }
    ventasModelo.put("listaVentasModelo", listaVentasModelo);

    context.render("/public/templates/ventas-realizadas.html", ventasModelo);
  }
}
