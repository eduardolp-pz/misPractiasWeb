package services;

import encapsulation.CarroCompra;

import java.util.ArrayList;
import java.util.List;

public class CarroCompraServicio {
  private static CarroCompraServicio instance;

  public static CarroCompraServicio getInstance() {
    if (instance == null) {
      instance = new CarroCompraServicio();
    }
    return instance;
  }

  private CarroCompraServicio() {
  }

  public CarroCompra newCarroCompra() {
    return new CarroCompra();
  }
}
