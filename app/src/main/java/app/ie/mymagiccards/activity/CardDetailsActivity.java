package app.ie.mymagiccards.activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import app.ie.mymagiccards.R;
import app.ie.mymagiccards.models.Cards;
import app.ie.mymagiccards.utils.Constants;


public class CardDetailsActivity extends AppCompatActivity {

    private Cards        cards;
    private TextView     cardName, cardManaCost, cardType,
                         cardColor, cardText, cardRarity,
                         cardSet;
    private ImageView    cardImage;
    private RequestQueue queue;
    private String       cardid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        Slide slide = new Slide(Gravity.RIGHT);
        getWindow().setReenterTransition(slide);

        queue = Volley.newRequestQueue(this);

        cards = (Cards) getIntent().getSerializableExtra("card");
        cardid = cards.getCardID();

        setupUi();
        getCardDetails(cardid);

    }

    private void getCardDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.SINGLE_CARD + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject singleCardInfo = response.getJSONObject("card");
                    cardName.setText("Name: " + singleCardInfo.getString("name"));
                    cardColor.setText("Colour: " + singleCardInfo.optString("colors"));
                    cardType.setText("Type: " + singleCardInfo.getString("type"));
                    cardManaCost.setText("Mana Cost " + singleCardInfo.optString("manaCost"));
                    cardRarity.setText("Rarity: " + singleCardInfo.getString("rarity"));
                    cardSet.setText("Set: " + singleCardInfo.getString("setName"));
                    cardText.setText("Text: " + singleCardInfo.optString("text"));
                    if(!singleCardInfo.optString("imageUrl").equals("")){
                        Glide.with(getApplicationContext())
                                .load(singleCardInfo.getString("imageUrl"))
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


    private void setupUi() {
        cardName = findViewById(R.id.cardNameDetails);
        cardManaCost = findViewById(R.id.manaCost);
        cardType = findViewById(R.id.cardTypeDetails);
        cardColor = findViewById(R.id.colorDetailsId);
        cardRarity = findViewById(R.id.cardRarityDetails);
        cardText = findViewById(R.id.cardTextDetails);
        cardSet = findViewById(R.id.setCardDetails);
        cardImage = findViewById(R.id.cardImageDetail);
    }
}