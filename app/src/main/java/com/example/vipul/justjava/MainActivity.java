package com.example.vipul.justjava;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.content.Intent;
        import android.net.Uri;

        import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
     int quantity=0;

    public void increment(View view){
        if(quantity==100)
        {
            Toast.makeText(this,"You cannot order more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            quantity += 1;
            display(quantity);
        }
    }

    public void decrement(View view){
        if(quantity==1)
        {
            Toast.makeText(this,"You cannot order less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            quantity -= 1;
            display(quantity);
        }
    }
    /**
     * This method is called when the order button is clicked.
     */


    public void submitOrder(View view) {

        CheckBox checkbox_whip = (CheckBox) findViewById(R.id.checkbox_whip);
        Boolean haswhip = checkbox_whip.isChecked();
        CheckBox checkBox_choc = (CheckBox) findViewById(R.id.checkbox_choc);
        Boolean haschoc = checkBox_choc.isChecked();
        EditText name = (EditText) findViewById(R.id.name);
        String value = name.getText().toString();
        display(quantity);
        int price = calculateprice(quantity, haswhip, haschoc);
        String orderSummary = createOrderSummary(price, haswhip, haschoc, value);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
        displayMessage(orderSummary);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummary = (TextView) findViewById(R.id.order_summary);
        orderSummary.setText(message);
    }

    private int calculateprice(int quantity,Boolean whip,Boolean choc) {
        int baseprice=5;
        if(whip)
        {baseprice=baseprice+1;}
        if(choc)
        {
            baseprice=baseprice+2;
        }

        return quantity*baseprice;
    }

    private String createOrderSummary(int price,Boolean whip,Boolean choc,String val) {
        return "Name: " + val +"\nHas Whip Cream ? "+whip+
                "\nHas Chocolate cream ? "+choc+
                "\nQuantity: "+quantity+"\nTotal: $"+price+"\nThank You";
    }
}

