package services;

import encapsulation.VentasProductos;
import util.BaseServiceDatabase;

import java.util.ArrayList;
import java.util.List;

public class VentasProductosServicio extends BaseServiceDatabase<VentasProductos> {
  private static VentasProductosServicio instance;

  public static VentasProductosServicio getInstance() {
    if (instance == null) {
      instance = new VentasProductosServicio();
    }
    return instance;
  }

  public VentasProductosServicio() {
    super(VentasProductos.class);
  }

  public List<VentasProductos> getListaVentasProductos() {
      return this.dbFindAll();
    }

  public void addVentasProductos(VentasProductos ventasProductos) {
    this.dbCreate(ventasProductos);
  }

}
