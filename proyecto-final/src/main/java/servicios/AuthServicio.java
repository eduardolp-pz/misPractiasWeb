package servicios;
import org.jasypt.util.text.BasicTextEncryptor;

public class AuthServicio {
  private static AuthServicio instancia;
  private final BasicTextEncryptor textEncryptor;

  private AuthServicio() {
    textEncryptor = new BasicTextEncryptor();
    textEncryptor.setPasswordCharArray("esqueleto-hueso".toCharArray());
  }

  public static AuthServicio getInstancia() {
    if (instancia == null) {
      instancia = new AuthServicio();
    }
    return instancia;
  }

  public String encryptText(String text){
    return textEncryptor.encrypt(text);
  }

  public String decryptText(String encryptedText){
    return textEncryptor.decrypt(encryptedText);
  }
}
