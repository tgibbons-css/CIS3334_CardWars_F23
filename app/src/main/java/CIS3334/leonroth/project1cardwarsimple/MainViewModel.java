package CIS3334.leonroth.project1cardwarsimple;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * View Model class for the Card War game application.
 * This class contains game logic and data management.
 */
public class MainViewModel extends AndroidViewModel {

    private Card playerCard;
    private Card opponentCard;
    private List<Card> playerCardList = new ArrayList<>();
    private List<Card> opponentCardList = new ArrayList<>();
    private String deck_id;
    private Retrofit retrofit;
    private DeckOfCardsService deckOfCardsService;
    final static String BASEURL = "https://www.deckofcardsapi.com/api/deck/";


    /**
     * Constructor for the MainViewModel class.
     *
     * @param application The Android application instance.
     */
    public MainViewModel (Application application) {
        super(application);
        connect();
        newGame();
    }

    /**
     * Handles the "Play" button click event. It pulls cards, compares them, and updates the game state.
     *
     * @return An array of strings containing game results and card image URLs.
     */
    public String[] onButtonPlayClick(){
        String[] onButtonOutput = new String[3];

        pullTopCard(playerCardList);
        pullTopCard(opponentCardList);

        onButtonOutput[0] = String.valueOf(compareCards());
        onButtonOutput[1] = playerCard.getImage();
        onButtonOutput[2] = opponentCard.getImage();

        if(compareCards() == 1){
            cardToList(playerCardList,playerCard);
            cardToList(playerCardList,opponentCard);
            playerCard = null;
            opponentCard = null;
        }
        else if(compareCards() == -1){
            cardToList(opponentCardList,playerCard);
            cardToList(opponentCardList,opponentCard);
            playerCard = null;
            opponentCard = null;
        }
        else if(compareCards() == 0){
            cardToList(playerCardList,playerCard);
            cardToList(opponentCardList,opponentCard);
            playerCard = null;
            opponentCard = null;
        }

        return onButtonOutput;
    }

    /**
     * Initializes the Retrofit components for API calls.
     */
    public void connect() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        if (deckOfCardsService == null) {
            deckOfCardsService = retrofit.create(DeckOfCardsService.class);
        }
    }

    /**
     * Requests and retrieves a new deck of cards from the API.
     *
     * @return The unique identifier of the newly created deck.
     */
    public String getNewDeck(){
        Call<DeckResponse> call = deckOfCardsService.newDeck();
        call.enqueue(new Callback<DeckResponse>() {
            @Override
            public void onResponse(Call<DeckResponse> call, Response<DeckResponse> response) {
                if (response.isSuccessful()) {
                    DeckResponse modal = response.body();
                    deck_id = modal.getDeck_id();
                    Log.d("3334",deck_id);
                }
            }

            @Override
            public void onFailure(Call<DeckResponse> call, Throwable t) {
            }
        });

    return deck_id;
    }

    /**
     * Fills a card list with half of the deck's cards. If the specified card list is empty,
     * it retrieves and populates it with cards from the deck.
     *
     * @param cardList The list to be populated with cards (e.g., playerCardList or opponentCardList).
     */
    public void fillOneCardList(List<Card> cardList ){
        Call<DeckResponse> call = deckOfCardsService.drawHalfDeck(deck_id);

        Log.d("3334", "Looking for card list");

        if(cardList == playerCardList && playerCardList.isEmpty()) {
            call.enqueue(new Callback<DeckResponse>() {
                @Override
                public void onResponse(Call<DeckResponse> call, Response<DeckResponse> response) {
                    if (response.isSuccessful()) {
                        DeckResponse modal = response.body();
                        Log.d("3334", modal.getCards().toString());
                        playerCardList = (List<Card>) ((ArrayList<Card>) modal.getCards()).clone();
                        Log.d("3334", "Player Card List Filled " + playerCardList.size());
                        Log.d("3334", modal.getRemaining() + " left in deck");
                    }
                }

                @Override
                public void onFailure(Call<DeckResponse> call, Throwable t) {
                    Log.d("3334", "List Fill Failed");
                }
            });
        }
        else if(opponentCardList.isEmpty()){
            call.enqueue(new Callback<DeckResponse>() {
                @Override
                public void onResponse(Call<DeckResponse> call, Response<DeckResponse> response) {
                    if (response.isSuccessful()) {
                        DeckResponse modal = response.body();
                        opponentCardList = (List<Card>) ((ArrayList<Card>) modal.getCards()).clone();
                        Log.d("3334", "Opponent Card List Filled " + opponentCardList.size());
                        Log.d("3334", modal.getRemaining() + " left in deck");
                    }
                }

                @Override
                public void onFailure(Call<DeckResponse> call, Throwable t) {
                    Log.d("3334", "List Fill Failed");
                }
            });
        }
    }

    /**
     * Initializes a new game by clearing card lists, retrieving a new deck, and populating the card lists.
     */
    public void newGame(){
        playerCardList.clear();
        opponentCardList.clear();

        getNewDeck();

        final Handler handler = new Handler();
        Runnable playerPopulate = new Runnable() {
            @Override
            public void run() {
                fillOneCardList(playerCardList);
                if(playerCardList.size() == 0) {
                    handler.postDelayed(this, 2000);
                }
            }
        };
        handler.post(playerPopulate);

        Runnable opponentPopulate = new Runnable() {
            @Override
            public void run() {
                fillOneCardList(opponentCardList);
                if(opponentCardList.size() == 0) {
                    handler.postDelayed(this, 2000);
                }
            }
        };
        handler.post(opponentPopulate);
    }

    /**
     * Pulls the top card from a card list.
     *
     * @param cardList The card list from which the top card will be pulled.
     * @return The top card.
     */
    public Card pullTopCard(List<Card> cardList){
        if(cardList == playerCardList && playerCardList.size() > 0) {
            playerCard = cardList.get(0);
            cardList.remove(0);
            return playerCard;
        }
        else if(cardList == opponentCardList && opponentCardList.size() >0){
            opponentCard = cardList.get(0);
            cardList.remove(0);
            return opponentCard;
        }
        else{
            return new Card("","","","");
        }
    }

    /**
     * Adds a card to a card list.
     *
     * @param cardList The card list to which the card will be added.
     * @param card     The card to add to the list.
     */
    public void cardToList(List<Card> cardList, Card card){
        cardList.add(card);
    }

    /**
     * Compares the ranks of the player's card and the opponent's card.
     *
     * @return 1 if the player wins, -1 if the opponent wins, 0 if it's a tie, 9 if there's an issue.
     */
    public Integer compareCards(){
        if(playerCard != null && opponentCard != null) {
            if (playerCard.getRank() > opponentCard.getRank()) {
                return 1;
            } else if (playerCard.getRank() < opponentCard.getRank()) {
                return -1;
            } else if (playerCard.getRank() == opponentCard.getRank()) {
                return 0;
            } else {
                return 9;
            }
        }
        else{
            return 9;
        }
    }

    /**
     * Retrieves the size of the player's card list.
     *
     * @return The size of the player's card list.
     */
    public Integer getPlayerCardListSize(){
        return playerCardList.size();
    }

    /**
     * Retrieves the size of the opponent's card list.
     *
     * @return The size of the opponent's card list.
     */
    public Integer getOpponentCardListSize(){
        return opponentCardList.size();
    }
}
