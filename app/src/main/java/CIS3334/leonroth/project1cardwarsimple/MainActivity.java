package CIS3334.leonroth.project1cardwarsimple;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

/**
 * The main activity for the Card War game application.
 */
public class MainActivity extends AppCompatActivity {
    ImageView imageViewPlayerCard;
    ImageView imageViewOpponentCard;
    Button buttonPlay;
    FloatingActionButton floatingActionButtonHelp;
    FloatingActionButton floatingActionButtonEnd;
    TextView textViewPlayerStack;
    TextView textViewOpponentStack;
    MainViewModel mainViewModel;
    TextView textViewStatus;
    Picasso picasso;

    /**
     * Called when the activity is first created. Initializes the UI components, ViewModel, and Picasso for image loading.
     *
     * @param savedInstanceState The previously saved instance state, if available.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        imageViewPlayerCard = findViewById(R.id.imageViewPlayerCard);
        imageViewOpponentCard = findViewById(R.id.imageViewOpponentCard);
        textViewPlayerStack = findViewById(R.id.textViewPlayerStack);
        textViewOpponentStack = findViewById(R.id.textViewOpponentStack);
        textViewStatus = findViewById(R.id.textViewStatus);

        setupButtonPlay();
        setupFloatingActionButtonHelp();
        setupFloatingActionButtonEnd();

        picasso = Picasso.get();
        //picasso.setLoggingEnabled(true);
        //picasso.setIndicatorsEnabled(true);
    }


    /**
     * Sets up the "Play" button's click event handler. When clicked, it triggers a round of the card game.
     */
    private void setupButtonPlay() {
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonPlay.setEnabled(false);
                String[] onButtonPlayClick = mainViewModel.onButtonPlayClick();

                Drawable cardback = ContextCompat.getDrawable(MainActivity.this, R.drawable.cardback);

                picasso.load(onButtonPlayClick[1]).fit().placeholder(cardback).into(imageViewPlayerCard);
                picasso.load(onButtonPlayClick[2]).fit().placeholder(cardback).into(imageViewOpponentCard);

                if(onButtonPlayClick[0].equals("0")){
                    textViewStatus.setText("This hand is a tie.");
                }
                else if(onButtonPlayClick[0].equals("1")){
                    textViewStatus.setText("Player wins this hand.");
                }
                else if(onButtonPlayClick[0].equals("-1")){
                    textViewStatus.setText("Opponent wins this hand.");
                }
                else if(onButtonPlayClick[0].equals("9")){
                    textViewStatus.setText("Problem with comparing cards.");
                }
                else{
                    textViewStatus.setText("Another problem.");
                    Log.d("3334",(onButtonPlayClick[0].getClass()+"***"+onButtonPlayClick[0]+"***"+onButtonPlayClick[1])+"***"+onButtonPlayClick[2]);
                }

                textViewPlayerStack.setText(mainViewModel.getPlayerCardListSize().toString());
                textViewOpponentStack.setText(mainViewModel.getOpponentCardListSize().toString());

                if(mainViewModel.getPlayerCardListSize() > 0 && mainViewModel.getOpponentCardListSize() >0){
                    buttonPlay.setEnabled(true);
                }
                else{
                    endGameDialog();
                }

            }
        });
    }

    /**
     * Sets up the "Help" floating action button's click event handler.
     * When clicked, it opens the help dialog.
     */
    private void setupFloatingActionButtonHelp() {
        floatingActionButtonHelp = findViewById(R.id.floatingActionButtonHelp);
        floatingActionButtonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpDialog();
            }
        });
    }

    /**
     * Sets up the "End" floating action button's click event handler.
     * When clicked, it opens the end game dialog.
     */
    private void setupFloatingActionButtonEnd() {
        floatingActionButtonEnd = findViewById(R.id.floatingActionButtonEnd);
        floatingActionButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endGameDialog();
            }
        });
    }

    /**
     * Displays an end game dialog with a message based on the game results.
     * Allows the user to start a new game or exit the app.
     */
    private void endGameDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Game Over");
        if(mainViewModel.getPlayerCardListSize() == 0){
            builder.setMessage("Opponent wins!\nPlay Again?");
        }
        else if(mainViewModel.getOpponentCardListSize() == 0){
            builder.setMessage("Player wins!\nPlay Again?");
        }
        else{
            builder.setMessage("Game ended without a winner.\nPlay Again?");
        }
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            mainViewModel.getNewDeck();
            mainViewModel.newGame();
            textViewStatus.setText("New Game");

            Drawable cardback = ContextCompat.getDrawable(MainActivity.this, R.drawable.cardback);
            picasso.load(R.drawable.cardback).fit().placeholder(cardback).into(imageViewPlayerCard);
            picasso.load(R.drawable.cardback).fit().placeholder(cardback).into(imageViewOpponentCard);

            buttonPlay.setEnabled(true);

            textViewPlayerStack.setText("26");
            textViewOpponentStack.setText("26");
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
        });

        builder.create().show();
    }

    /**
     * Displays a help dialog with credits.
     */
    private void helpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Help");

        String message = "";
        message +=        "This is a simplified version of the card game War.";
        message += "\n" + " ";
        message += "\n" + "Each player starts with half the deck (26 cards).";
        message += "\n" + "Press the 'Play next hand' button to deal a card from the player's and the opponent's stacks.";
        message += "\n" + " ";
        message += "\n" + "The high card wins. Whoever wins gets to put their own card and the other played card on the bottom of their stack.";
        message += "\n" + " ";
        message += "\n" + "Aces are high.";
        message += "\n" + " ";
        message += "\n" + "On a tie each player takes back their own card to the bottom of their stack.";
        message += "\n" + " ";
        message += "\n" + "The game is over when either stack runs out of cards.";
        message += "\n" + " ";
        message += "\n" + "This uses Deck of Cards API at https://www.deckofcardsapi.com/";
        message += "\n" + "by Chase Roberts.";
        message += "\n" + " ";
        message += "\n" + "This application by Leon Roth.";

        builder.setMessage(message);

        builder.setCancelable(true);

        builder.setNegativeButton("Close",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // if this button is clicked, just closes the dialog
                dialog.cancel();
            }
        });

        builder.create().show();
    }
}