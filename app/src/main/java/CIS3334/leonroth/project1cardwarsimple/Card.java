package CIS3334.leonroth.project1cardwarsimple;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Arrays;

/**
 * Represents a playing card with properties such as image, value, suit, and code.
 */
public class Card  {
    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("value")
    @Expose
    private String value;

    @SerializedName("suit")
    @Expose
    private String suit;

    @SerializedName("code")
    @Expose
    private String code;

    private Integer rank;

    static final String[] RANKING = {"","","2","3","4","5","6","7","8","9","10","JACK","QUEEN","KING","ACE"};

    /**
     * Constructs a new Card with the specified properties.
     *
     * @param image The image representation of the card.
     * @param value The face value or rank of the card.
     * @param suit The suit of the card (e.g., Hearts, Diamonds, Spades, Clubs).
     * @param code The unique code identifying the card.
     */
    public Card(String image, String value, String suit, String code) {
        this.image = image;
        this.value = value;
        this.suit = suit;
        this.code = code;
        this.rank = findRank(value);
    }

    /**
     * Gets the rank of the card compared to other cards.
     *
     * @return The rank of the card as an Integer.
     */
    public Integer getRank() {
        this.rank = findRank(value);
        return this.rank;
    }

    /**
     * Sets the rank of the card.
     *
     * @param rank The rank to set.
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Calculates the rank of the card compared to other cards.
     *
     * @return The rank of the card as an Integer.
     */
    private Integer findRank(String value){
        return Arrays.asList(RANKING).indexOf(value);
    }

    /**
     * Gets the image representation of the card.
     *
     * @return The image representation of the card.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the image representation of the card.
     *
     * @param image The image representation to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the face value of the card.
     *
     * @return The face value of the card.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the face value or rank of the card.
     *
     * @param value The face value or rank to set.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the suit of the card.
     *
     * @return The suit of the card (e.g., Hearts, Diamonds, Spades, Clubs).
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Sets the suit of the card.
     *
     * @param suit The suit to set.
     */
    public void setSuit(String suit) {
        this.suit = suit;
    }

    /**
     * Gets the unique code identifying the card.
     *
     * @return The unique code identifying the card.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the unique code identifying the card.
     *
     * @param code The unique code to set.
     */
    public void setCode(String code) {
        this.code = code;
    }
}
