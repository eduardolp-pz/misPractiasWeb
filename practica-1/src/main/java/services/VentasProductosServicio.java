package services;

import encapsulation.VentasProductos;

import java.util.ArrayList;
import java.util.List;

public class VentasProductosServicio {
  private static VentasProductosServicio instance;
  private List<VentasProductos> listaVentasProductos = new ArrayList<>();

  public static VentasProductosServicio getInstance() {
    if (instance == null) {
      instance = new VentasProductosServicio();
    }
    return instance;
  }

  public VentasProductosServicio(List<VentasProductos> listaVentasProductos) {
    this.listaVentasProductos = listaVentasProductos;
  }

  public VentasProductosServicio() {
  }

  public List<VentasProductos> getListaVentasProductos() {
      return listaVentasProductos;
    }

  public void setListaVentasProductos(List<VentasProductos> listaVentasProductos) {
    this.listaVentasProductos = listaVentasProductos;
  }

  public void addVentasProductos(VentasProductos ventasProductos) {
    listaVentasProductos.add(ventasProductos);
  }

  public VentasProductos getVentasProductos(String nombre) {
    for (VentasProductos vp : listaVentasProductos) {
      if (vp.getNombreCliente().equals(nombre)) {
        return vp;
      }
    }
    return null;
  }

  public boolean deleteVentasProductos(String nombre) {
    for (VentasProductos vp : listaVentasProductos) {
      if (vp.getNombreCliente().equals(nombre)) {
        listaVentasProductos.remove(vp);
        return true;
      }
    }
    return false;
  }

  public boolean updateVentasProductos(VentasProductos ventasProductos) {
    for (VentasProductos vp : listaVentasProductos) {
      if (vp.getId() == ventasProductos.getId()) {
        vp.setListaProductos(ventasProductos.getListaProductos());
        return true;
      }
    }
    return false;
  }
}
