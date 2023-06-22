package com.example.aplicativo2;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Card implements Serializable {

    private int id;
    private String title;
    private String description;
    private boolean pinnedCard;
    private boolean marker;
    private Date timestamp;

    private String category;

    public Card() {
    }

    public Card(int id, String title, String description, boolean pinnedCard, boolean marker, Date timestamp, String category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pinnedCard = pinnedCard;
        this.marker = marker;
        this.timestamp = timestamp;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPinnedCard() {
        return pinnedCard;
    }

    public void setPinnedCard(boolean pinnedCard) {
        this.pinnedCard = pinnedCard;
    }

    public boolean isMarker() {
        return marker;
    }

    public void setMarker(boolean marker) {
        this.marker = marker;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public static Card builder() {
        List<Card> cards = new ArrayList<>();

        Card card = new Card(
                1,
                "Titulo Exmplo",
                "Descrição exemplo",
                true,
                true,
                new Date(2023,04,1),
                "Todos");

        return card;
    }

    public String getFormatedDate(Date data){
        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("d MMM", new Locale("pt", "BR"));
        String formattedDate = localDate.format(formatterOutput);

        return  formattedDate;
    }
}
