package serviços;

public class SendGridConfig {

    public static final String API_KEY = System.getenv("SENDGRID_API_KEY");
    public static final String REMETENTE = System.getenv("SENDGRID_REMETENTE");

    static {
        if (API_KEY == null || REMETENTE == null) {
            throw new RuntimeException("Variáveis de ambiente SENDGRID_API_KEY ou SENDGRID_REMETENTE não definidas!");
        }
    }
}
