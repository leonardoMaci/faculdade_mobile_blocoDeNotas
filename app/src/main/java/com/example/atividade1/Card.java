package com.example.atividade1;

import androidx.annotation.NonNull;

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

    public Card() {
    }

    public Card(int id, String title, String description, boolean pinnedCard, boolean marker, Date timestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pinnedCard = pinnedCard;
        this.marker = marker;
        this.timestamp = timestamp;
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

    public static Card builder() {
        List<Card> cards = new ArrayList<>();

        Card card = new Card(
                1,
                "Titulo Exmplo",
                "Descrição exemplo",
                true,
                true,
                new Date(2023,04,1));

        return card;
    }

    public String getFormatedDate(Date data){
        LocalDate localDate = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        DateTimeFormatter formatterOutput = DateTimeFormatter.ofPattern("d MMM", new Locale("pt", "BR"));
        String formattedDate = localDate.format(formatterOutput);

        return  formattedDate;
    }
}
