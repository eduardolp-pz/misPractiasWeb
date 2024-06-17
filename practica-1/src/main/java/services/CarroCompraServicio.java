package services;

import encapsulation.CarroCompra;

import java.util.ArrayList;
import java.util.List;

public class CarroCompraServicio {
  private static CarroCompraServicio instance;
  private List<CarroCompra> listaCarroCompra = new ArrayList<>();

  private CarroCompraServicio(List<CarroCompra> listaCarroCompra) {
    this.listaCarroCompra = listaCarroCompra;
  }

  private CarroCompraServicio() {
  }

  public static CarroCompraServicio getInstance() {
    if (instance == null) {
      instance = new CarroCompraServicio();
    }
    return instance;
  }

  public List<CarroCompra> getListaCarroCompra() {
    return listaCarroCompra;
  }

  public void setListaCarroCompra(List<CarroCompra> listaCarroCompra) {
    this.listaCarroCompra = listaCarroCompra;
  }

  public void addCarroCompra(CarroCompra carroCompra) {
    listaCarroCompra.add(carroCompra);
  }

  public CarroCompra getCarroCompra(long id) {
    for (CarroCompra cc : listaCarroCompra) {
      if (cc.getId() == id) {
        return cc;
      }
    }
    return null;
  }

  public boolean deleteCarroCompra(long id) {
    for (CarroCompra cc : listaCarroCompra) {
      if (cc.getId() == id){
        listaCarroCompra.remove(cc);
        return true;
      }
    }
    return false;
  }

  public CarroCompra newCarroCompra() {
    return new CarroCompra();
  }
}
