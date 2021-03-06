package app.ie.mymagiccards.models;

import java.io.Serializable;

/**
 * Created by Eoin on 24/02/2018.
 */

public class Cards implements Serializable {
    private static final long id = 1L;

    //Set up variables for the card model
    private String name;
    private String manaCost;
    private String color;
    private String type;
    private String rarity;
    private String cardText;
    private String imageUrl;
    private String cardID;




    //An empty constructor
    public Cards() {
    }

    public Cards(String name, String manaCost, String color, String type, String rarity, String cardText, String imageUrl, String cardID) {
        this.name = name;
        this.manaCost = manaCost;
        this.color = color;
        this.type = type;
        this.rarity = rarity;
        this.cardText = cardText;
        this.imageUrl = imageUrl;
        this.cardID = cardID;
    }

    //Getter and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getCardText() {
        return cardText;
    }

    public void setCardText(String cardText) {
        this.cardText =  cardText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    @Override
    public String toString() {
        return "Cards{" +
                "name='" + name + '\'' +
                ", manaCost='" + manaCost + '\'' +
                ", color='" + color + '\'' +
                ", type='" + type + '\'' +
                ", rarity='" + rarity + '\'' +
                ", cardText='" + cardText + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", cardID='" + cardID + '\'' +
                '}';
    }
}
