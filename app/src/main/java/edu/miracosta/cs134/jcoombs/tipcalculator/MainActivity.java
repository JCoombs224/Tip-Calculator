package edu.miracosta.cs134.jcoombs.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import edu.miracosta.cs134.jcoombs.tipcalculator.model.Bill;

public class MainActivity extends AppCompatActivity {

    // Instance Variables
    // Bridges the View and the Model
    private Bill currentBill;

    private EditText amountEditText;
    private TextView percentTextView;
    private SeekBar percentSeekBar;
    private TextView tipTextView;
    private TextView totalTextView;

    // Instance variables to format output (currency and percent)
    NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.getDefault());
    NumberFormat percent = NumberFormat.getPercentInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Wire up the instance variables (initialize)
        currentBill = new Bill();
        amountEditText = findViewById(R.id.amountEditText);
        percentTextView = findViewById(R.id.percentTextView);
        percentSeekBar = findViewById(R.id.percentSeekBar);
        tipTextView = findViewById(R.id.tipTextView);
        totalTextView = findViewById(R.id.totalTextView);

        // Let's set the current tip percentage
        currentBill.setTipPercent(percentSeekBar.getProgress() / 100.0);

        // Implement the interface for EditText
        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                // Read the input from the amountEditText and store in the currentBill
                try {
                    double amount = Double.parseDouble(amountEditText.getText().toString());
                    // Store the amount in the bill
                    currentBill.setAmount(amount);
                } catch (NumberFormatException e)
                {
                    currentBill.setAmount(0.0);
                }

                calculateBill();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        percentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                double progress = percentSeekBar.getProgress() / 100.0;
                // Update the tip percent
                currentBill.setTipPercent(progress);
                // Update the view
                percentTextView.setText(percent.format(currentBill.getTipPercent()));

                calculateBill();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void calculateBill()
    {
        // Update the tipTextView and the totalTextView
        tipTextView.setText(currency.format(currentBill.getTipAmount()));
        totalTextView.setText(currency.format(currentBill.getTotalAmount()));
    }

}
