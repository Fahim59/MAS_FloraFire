package tests;

import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Mail {
    public static void main(String[] args) throws MailosaurException, IOException {
        getEmailBodyData();
    }

    public static void getEmailBodyData() throws MailosaurException, IOException {
        String apiKey = "cHZ59nRRyktWAp7Wj3E1Sneh1rNHhrKl";
        String serverId = "hufctr7p";
        String serverDomain = "hufctr7p.mailosaur.net";

        MailosaurClient mailosaurClient = new MailosaurClient(apiKey);

        MessageSearchParams params = new MessageSearchParams();
        params.withServer(serverId);

        SearchCriteria criteria = new SearchCriteria();
        criteria.withSentTo("armandina@" + serverDomain);

        Message message = mailosaurClient.messages().get(params, criteria);

        System.out.println("Email Subject is: " +message.subject());
        System.out.println("Recipient Name: " +message.to().get(0).name());

        //System.out.println("Body: " +message.text().body());
        //String emailBody = message.text().body();

        //System.out.println(message.html().body());
        String emailBody = message.html().body();
//
//        Pattern pattern = Pattern.compile("Client Id:\\s*(\\S+)");
//        Matcher matcher = pattern.matcher(emailBody);
//
//        if (matcher.find()) {
//            String clientId = matcher.group(1);
//            System.out.println("Extracted Client Id: " + clientId);
//        }
//        else {
//            System.out.println("Client Id not found.");
//        }

//        Link link = message.text().links().get(0);
//
//        String loginUrl = link.href();
//        System.out.println("Login Url is: " +loginUrl);

        Document doc = Jsoup.parse(emailBody);
        Element loginElement = doc.selectFirst("strong:contains(Login URL)");

        Element clientId = doc.selectFirst("strong:contains(Client Id)");

        if (loginElement != null) {
            String loginUrl = loginElement.parent().ownText().trim();
            System.out.println("Login URL: " + loginUrl);
        } else {
            System.out.println("No Login URL found.");
        }

        if (clientId != null) {
            String id = clientId.parent().ownText().trim();
            System.out.println("Client Id: " + id);
        } else {
            System.out.println("No Id found.");
        }
    }
}
