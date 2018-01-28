package com.me.prokawsar.learning;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;

import static android.R.id.message;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this,
                "ca-app-pub-3835778305944450~2602156381");

        mAdView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    int quantity=0;

    public void increment(View view){
        quantity++;
        display(quantity);
    }

    public void decrement(View view){
        quantity--;
        if(quantity<0) quantity=0;
        display(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        String orderMessage = createOrderSummary(price);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:")); // only email apps should handle this
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order Summary");
        emailIntent.putExtra(Intent.EXTRA_TEXT, orderMessage);

        if (emailIntent.resolveActivity(getPackageManager()) != null) {
          //  startActivity(emailIntent);
        }
//        displayMessage(orderMessage);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method calculate the price.
     */
    private int calculatePrice() {
        return quantity*5;
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
    /**
     * This method displays order summary on the screen.
     */
    private String createOrderSummary(int price) {
        EditText name = (EditText) findViewById(R.id.name_view);
        String orderMessage = "Name: " + name.getText().toString();
        orderMessage += "\nWhipped Cream? " + creamCheck();
        orderMessage += "\nQuantity: " + quantity;
        orderMessage +="\nTotal: " + NumberFormat.getCurrencyInstance().format(price);
        orderMessage += "\nThank You";


        return orderMessage;
    }
    /**
     * This method displays order summary on the screen.
     */
    private String creamCheck() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
        if(checkBox.isChecked()){
            return "True";
        }
        return "False";
    }
}
