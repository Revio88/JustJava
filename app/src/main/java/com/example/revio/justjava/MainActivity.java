package com.example.revio.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import javax.security.auth.Subject;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int price = 5;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (quantity < 10) {
            quantity += 1;
        } else {
            Toast.makeText(this, "Too much men!", Toast.LENGTH_SHORT).show();
        }
        price = quantity * 5;
        display(quantity);
    }

    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity -= 1;
        } else {
            Toast.makeText(this, "0 coffee aren't enough!", Toast.LENGTH_SHORT).show();
        }
        price = quantity * 5;
        display(quantity);
    }


    /**
     * This method change hasWhippedCream variable when whipped_cream_checkbox is clicked
     */
    public void whippedCream(View view) {
        hasWhippedCream = ((CheckBox) view).isChecked();
        Log.v("MainActivity", "The whipped cream state is: " + hasWhippedCream);
    }

    /**
     * This method change hasChocolate variable when chocolate_checkbox is clicked
     */
    public void chocolate(View view) {
        hasChocolate = ((CheckBox) view).isChecked();
        Log.v("MainActivity", "The chocolate state is: " + hasChocolate);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int pricePerCup = calculatePricePerCup(hasWhippedCream, hasChocolate);
        int price = quantity * pricePerCup;
        EditText nameEditText = (EditText) findViewById(R.id.name_editText);
        name = nameEditText.getText().toString();
        String priceMessage = createOrderSummary(price);
        sendMail(priceMessage);
    }


    /**
     * this method return a message
     */
    private String createOrderSummary (int price) {
        return "Name: "+ name + "\nAdded whipped cream? "+ hasWhippedCream + "\nAdded chocolate? " +
                hasChocolate +"\nQuantity: " + quantity + "\nTotal: " + price + "\n" +
                getString(R.string.thank_you);
    }

    private void sendMail(String priceMessage) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                "f.reviati@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your order");
        emailIntent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private int calculatePricePerCup(boolean hasWhippedCream, boolean hasChocolate) {
        int cupPrice = 5;
        if (hasWhippedCream){
            cupPrice += 1;
        }
        if (hasChocolate){
            cupPrice += 2;
        }
        return cupPrice;
    }

}