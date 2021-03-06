package app.ie.mymagiccards.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.ie.mymagiccards.R;
import app.ie.mymagiccards.models.Cards;
import app.ie.mymagiccards.models.Decks;
import app.ie.mymagiccards.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;

import static maes.tech.intentanim.CustomIntent.customType;


public class CardDetailsActivity extends AppCompatActivity {

    //Set up variables
    private DatabaseReference   decks;
    private FirebaseDatabase    database;
    private FirebaseUser        user;
    private FirebaseAuth        mAuth;
    private Cards               cards;
    private RequestQueue        queue;
    private String              cardid;
    private Decks               deck;

    @BindView(R.id.cardNameDetails)TextView  cardName;
    @BindView(R.id.cardImageDetail)ImageView cardImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        decks = database.getReference().child(user.getUid()).child("Deck");
        decks.keepSynced(true);
        deck = new Decks();

        Slide slide = new Slide(Gravity.RIGHT);
        getWindow().setReenterTransition(slide);
        queue = Volley.newRequestQueue(this);

        //get the card id from the intent
        cards = (Cards) getIntent().getSerializableExtra("card");
        cardid = cards.getCardID();
        getCardDetails(cardid);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and show the items.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle menu clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
                break;
            case R.id.add:
                Log.i("Deck Name", deck.getCardImage());
                Log.i("Deck Image", deck.getCardImage());
                addCardToDeck(deck.getCardName(), deck.getCardImage());
                break;
            case R.id.signout:
                if(mAuth != null && user != null){
                    mAuth.signOut();
                    startActivity(new Intent(CardDetailsActivity.this, MainMenu.class));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCardToDeck(String cardName, String imageUrl) {

                DatabaseReference newDeck = decks.push();
                Map<String, String> dataToSave = new HashMap<>();
                dataToSave.put("cardName", cardName);
                dataToSave.put("cardImage", imageUrl);
                dataToSave.put("userid", user.getUid());
                newDeck.getRef().child("").setValue(dataToSave);

                startActivity(new Intent(CardDetailsActivity.this, MainActivity.class));
                customType(CardDetailsActivity.this,"up-to-bottom");
                finish();
    }


    private void getCardDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.SINGLE_CARD + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    String cardName = response.getString("name");
                    Log.i("Tag",cardName);
                    if(!cardName.equals("")){
                        deck.setCardName(cardName);
                    }
                    JSONObject singleCardInfo = response.getJSONObject("image_uris");
                    if(!singleCardInfo.optString("large").equals("")){
                        deck.setCardImage(singleCardInfo.optString("large"));
                        Glide.with(getApplicationContext())
                                .load(singleCardInfo.getString("large"))
                                .into(cardImage);
                    }else{
                        Glide.with(getApplicationContext())
                                .load(R.drawable.cardback)
                                .into(cardImage);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: ", error.getMessage());

            }
        });

        queue.add(jsonObjectRequest);
    }



}
