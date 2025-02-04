package tests;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail {
    public static void main(String[] args) throws MailosaurException, IOException {
        getEmailBodyData();
    }

    public static void getEmailBodyData() throws MailosaurException, IOException {
        String apiKey = "4hvwMgKIOKVPK7X7F8SH32rRXMrEsjSl";
        String serverId = "un8wyobg";
        String serverDomain = "un8wyobg.mailosaur.net";

        MailosaurClient mailosaurClient = new MailosaurClient(apiKey);

        MessageSearchParams params = new MessageSearchParams();
        params.withServer(serverId);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo("thing-rope@" + serverDomain);

        Message message = mailosaurClient.messages().get(params, criteria);

        System.out.println("Email Subject is: " +message.subject());
        System.out.println("Recipient Name: " +message.to().get(0).name());

        //System.out.println("Body: " +message.text().body());
        String emailBody = message.text().body();

        Pattern pattern = Pattern.compile("Client Id:\\s*(\\S+)");
        Matcher matcher = pattern.matcher(emailBody);

        if (matcher.find()) {
            String clientId = matcher.group(1);
            System.out.println("Extracted Client Id: " + clientId);
        }
        else {
            System.out.println("Client Id not found.");
        }

        Link link = message.text().links().get(0);

        String loginUrl = link.href();
        System.out.println("Login Url is: " +loginUrl);
    }
}
