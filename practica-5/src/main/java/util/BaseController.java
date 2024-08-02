package util;

import io.javalin.Javalin;

public abstract class BaseController {

  protected static Javalin app;

  public BaseController(Javalin app){
    BaseController.app = app;
  }

  abstract public void applyRoutes();
}
