package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity{

    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox= (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox= (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate= chocolateCheckBox.isChecked();
        EditText nameField= (EditText)findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        int price= calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage=createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for"+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    /**
     * Calculates the price of the order.
     *
     * @param %quantity is the number of cups of coffee ordered
     * the price for whipped Cream is $1 and the price for chocolate is $2
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice =5;
        if(addWhippedCream){
            basePrice+=1;
        }
        if(addChocolate){
            basePrice+=2;
        }
        return quantity*basePrice;
    }
    /**
     * This method is called when the plus button is clicked.
     */

    private String createOrderSummary(String name,int priceOfOrder,boolean addWhippedCream,boolean addChocolate){
        String priceMessage="Name "+name;
        priceMessage+="\nAdd Whipped Cream? "+addWhippedCream;
        priceMessage+="\nAdd  Chocolate? "+addChocolate;
        priceMessage=priceMessage+ "\nQuantity:" + quantity;
        priceMessage=priceMessage+ "\nTotal: $"+priceOfOrder;
        priceMessage=priceMessage+"\n" +getString(R.string.thank_you);
        return priceMessage;
    }
    public void increment(View view) {
        if(quantity==100){
            Toast.makeText(this,"You can not have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity+1;
        displayQuantity(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view){
        if(quantity==1){
            Toast.makeText(this,"You can not have less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

}