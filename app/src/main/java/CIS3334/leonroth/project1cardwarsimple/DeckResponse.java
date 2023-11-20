package CIS3334.leonroth.project1cardwarsimple;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Represents a response object for a deck of cards or portion thereof,
 * at https://www.deckofcardsapi.com/ including details such as success,
 * deck ID, shuffle status, remaining cards, and a list of cards.
 */
public class DeckResponse {

    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("deck_id")
    @Expose
    private String deck_id;

    @SerializedName("shuffled")
    @Expose
    private boolean shuffled;

    @SerializedName("remaining")
    @Expose
    private Integer remaining;

    @SerializedName("cards")
    @Expose
    private List<Card> cards;


    /**
     * Indicates whether the deck creation or operation was successful.
     *
     * @return `true` if the operation was successful, `false` otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets the success status of the deck operation.
     *
     * @param success The success status to set.
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * Gets the unique identifier for the deck.
     *
     * @return The deck's unique identifier.
     */
    public String getDeck_id() {
        return deck_id;
    }

    /**
     * Sets the unique identifier for the deck.
     *
     * @param deck_id The deck's unique identifier to set.
     */
    public void setDeck_id(String deck_id) {
        this.deck_id = deck_id;
    }

    /**
     * Checks if the deck has been shuffled.
     *
     * @return `true` if the deck has been shuffled, `false` otherwise.
     */
    public boolean isShuffled() {
        return shuffled;
    }

    /**
     * Sets the shuffle status of the deck.
     *
     * @param shuffled The shuffle status to set.
     */
    public void setShuffled(boolean shuffled) {
        this.shuffled = shuffled;
    }

    /**
     * Gets the number of remaining cards in the deck.
     *
     * @return The number of remaining cards.
     */
    public Integer getRemaining() {
        return remaining;
    }


    /**
     * Sets the number of remaining cards in the deck.
     *
     * @param remaining The number of remaining cards to set.
     */
    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    /**
     * Gets a list of cards in the deck.
     *
     * @return A list of `Card` objects in the deck.
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Sets the list of cards in the deck.
     *
     * @param cards The list of `Card` objects to set.
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }



}
