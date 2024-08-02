package controllers;

import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

public class BaseController {
  public static void getHome(@NotNull Context ctx) {
    ctx.render("/public/templates/index.html");
  }

  public static void getLogged(@NotNull Context ctx) {
    ctx.render("/public/templates/logged.html");
  }

  public static void getLoggedAfter(@NotNull Context ctx) {
    ctx.redirect("/comprar");
  }
}