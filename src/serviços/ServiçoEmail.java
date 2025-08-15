package serviços;

import com.sendgrid.*;

import java.io.IOException;

public class ServiçoEmail {

    public void enviarEmail(String to, String subject, String content){
        Email from = new Email(SendGridConfig.remetente);
        Email toEmail = new Email(to);
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, toEmail, emailContent);

        SendGrid sg = new SendGrid(SendGridConfig.API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Status: " + response.getStatusCode());
            System.out.println("Corpo: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
