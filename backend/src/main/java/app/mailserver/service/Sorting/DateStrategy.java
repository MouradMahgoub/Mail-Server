package app.mailserver.service.Sorting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import app.mailserver.models.MailModel;

public class DateStrategy implements SortingStrategy {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<MailModel> sort(List<MailModel> emails) {
        List<MailModel> sortedEmails = new ArrayList<>(emails);
        sortedEmails.sort(Comparator.comparing(mail -> toLocalDateTime(((MailModel) mail).getDate())));
        return sortedEmails;
    }

    private LocalDateTime toLocalDateTime(String dateString) {
        try {
            return LocalDateTime.parse(dateString, formatter);
        } catch (Exception e) {
           
            return LocalDateTime.MIN; 
        }
    }
}
