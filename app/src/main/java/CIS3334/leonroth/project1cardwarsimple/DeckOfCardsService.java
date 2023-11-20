package CIS3334.leonroth.project1cardwarsimple;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * The `DeckOfCardsService` interface defines methods for interacting with a deck of cards
 * at https://www.deckofcardsapi.com/ using Retrofit.
 */
public interface DeckOfCardsService {

    /**
     * Creates a new deck of cards and shuffles it.
     *
     * @return A Retrofit `Call` object that can be used to make the API request and receive a `DeckResponse`.
     */
    @GET("new/shuffle/?deck_count=1")
    Call<DeckResponse> newDeck();

    /**
     * Draws half of the deck, which is 26 cards, from the specified deck.
     *
     * @param deck_id The ID of the deck from which cards should be drawn.
     * @return A Retrofit `Call` object that can be used to make the API request and receive a `DeckResponse`.
     */
    @GET("{deck_id}/draw/?count=26")
    Call<DeckResponse> drawHalfDeck(@Path("deck_id") String deck_id);
}
