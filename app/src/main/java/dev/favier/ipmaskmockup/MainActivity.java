package dev.favier.ipmaskmockup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main activity compute all ip subnet ranges
 *
 * @author Maxime Favier
 */
public class MainActivity extends AppCompatActivity {

    /**
     * All widgets variables
     */
    EditText oct1textView;
    EditText oct2TextView;
    EditText oct3TextView;
    EditText oct4TextView;
    EditText sm1textView;
    EditText sm2textView;
    EditText sm3textView;
    EditText sm4textView;
    EditText ipclasseditText;
    Button calculateButton;
    ListView listview;

    /**
     * list + SimpleAdapter for the listView
     */
    SimpleAdapter simpleAdapter;
    ArrayList<Map<String, String>> itemsIp;

    /**
     * ip & sm related variable
     */
    int oct1;
    int oct2;
    int oct3;
    int oct4;
    int sm1, sm2, sm3, sm4;
    char ipclass;

    /**
     * Load activity layout
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupControls();
    }

    /**
     * Initialisation of widgets and set eventListener for button
     */
    public void SetupControls() {
        oct1textView = findViewById(R.id.oct1textView);
        // applied filter 0-255
        oct1textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        oct2TextView = findViewById(R.id.oct2textView);
        oct2TextView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        oct3TextView = findViewById(R.id.oct3textView);
        oct3TextView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        oct4TextView = findViewById(R.id.oct4textView);
        oct4TextView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        sm1textView = findViewById(R.id.sm1textView);
        sm1textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        sm2textView = findViewById(R.id.sm2textView);
        sm2textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        sm3textView = findViewById(R.id.sm3textView);
        sm3textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});
        sm4textView = findViewById(R.id.sm4textView);
        sm4textView.setFilters(new InputFilter[]{new InputFilterMinMax("0", "255")});

        ipclasseditText = findViewById(R.id.ipclasseditText);

        // add event listener for button
        calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculate();
            }
        });

        // setup listview
        listview = findViewById(R.id.ipListView);
        itemsIp = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this, itemsIp, android.R.layout.simple_list_item_2,
                new String[]{"title", "descr"}, new int[]{android.R.id.text1, android.R.id.text2});
        listview.setAdapter(simpleAdapter);

        Calculate();
    }

    /**
     * compute ip range
     */
    public void Calculate() {
        // get numbers from edit text's
        oct1 = Integer.parseInt(oct1textView.getText().toString());
        oct2 = Integer.parseInt(oct2TextView.getText().toString());
        oct3 = Integer.parseInt(oct3TextView.getText().toString());
        oct4 = Integer.parseInt(oct4TextView.getText().toString());
        sm1 = Integer.parseInt(sm1textView.getText().toString());
        sm2 = Integer.parseInt(sm2textView.getText().toString());
        sm3 = Integer.parseInt(sm3textView.getText().toString());
        sm4 = Integer.parseInt(sm4textView.getText().toString());

        // clear list
        itemsIp.clear();

        // get ip class
        ipclass = WhatClass(oct1);

        int offset = -1;
        int host =  -1;

        if (InvertBits(sm4) < 255 || (sm3 == 255 && sm2 ==255 && sm1 == 255 && sm4 == 0)) {
            offset = InvertBits(sm4);
            host = offset - 2;

            if(ipclass == 'C') {
                for (int j = 0; j <= 255; j += offset) {
                    Map<String, String> ipObj = new HashMap<String, String>();
                    ipObj.put("title", "Subnet: " + oct1 + "." + oct2 + "." + oct3 + "." + j + "\t broadcast: " + oct1 + "." + oct2 + "." + oct3 + "." + (j + offset));
                    ipObj.put("descr", oct1 + "." + oct2 + "." + oct3 + "." + (j + 1) + " - " + oct1 + "." + oct2 + "." + oct3 + "." + (j + offset - 1));
                    itemsIp.add(ipObj);
                    j++;
                }
            }

            if(ipclass == 'B'){
                for(int k=0; k<=255; k++) {
                    for (int j = 0; j <= 255; j += offset) {
                        Map<String, String> ipObj = new HashMap<String, String>();
                        ipObj.put("title", "Subnet: " + oct1 + "." + oct2 + "." + (oct3 +k)  + "." + j + "\t broadcast: " + oct1 + "." + oct2 + "." + (oct3 +k) + "." + (j + offset));
                        ipObj.put("descr", oct1 + "." + oct2 + "." + (oct3 +k) + "." + (j + 1) + " - " + oct1 + "." + oct2 + "." + (oct3 +k) + "." + (j + offset - 1));
                        itemsIp.add(ipObj);
                        j++;
                    }
                }
            }

            if(ipclass == 'A'){
                for(int l=0; l<=255; l++) {
                    for (int k = 0; k <= 255; k++) {
                        for (int j = 0; j <= 255; j += offset) {
                            Map<String, String> ipObj = new HashMap<String, String>();
                            ipObj.put("title", "Subnet: " + oct1 + "." + (oct2+l) + "." + (oct3 + k) + "." + j + "\t broadcast: " + oct1 + "." + (oct2+l) + "." + (oct3 + k) + "." + (j + offset));
                            ipObj.put("descr", oct1 + "." + (oct2+l) + "." + (oct3 + k) + "." + (j + 1) + " - " + oct1 + "." + (oct2+l) + "." + (oct3 + k) + "." + (j + offset - 1));
                            itemsIp.add(ipObj);
                            j++;
                        }
                    }
                }
            }
        } else if (InvertBits(sm3) < 255 || (sm3 == 0 && sm2 == 255 && sm1 ==255)) {
            offset = InvertBits(sm3);
            host = offset*255 -2;
            if(ipclass == 'B') {
                for (int j = 0; j <= 255; j += offset) {
                    //System.out.println(j);
                    Map<String, String> ipObj = new HashMap<String, String>();
                    ipObj.put("title", "Subnet: " + oct1 + "." + oct2 + "." + j + "." + 0 + "\t broadcast: " + oct1 + "." + oct2 + "." + (j + offset) + "." + 255);
                    ipObj.put("descr", oct1 + "." + oct2 + "." + j + "." + 1 + " - " + oct1 + "." + oct2 + "." + (j + offset) + "." + 254);
                    itemsIp.add(ipObj);
                    j++;
                }
            }
            if (ipclass == 'A'){
                for(int l=0; l<=255; l++) {
                    for (int j = 0; j <= 255; j += offset) {
                        //System.out.println(j);
                        Map<String, String> ipObj = new HashMap<String, String>();
                        ipObj.put("title", "Subnet: " + oct1 + "." + (oct2+l) + "." + j + "." + 0 + "\t broadcast: " + oct1 + "." + (oct2+l) + "." + (j + offset) + "." + 255);
                        ipObj.put("descr", oct1 + "." + (oct2+l) + "." + j + "." + 1 + " - " + oct1 + "." + (oct2+l) + "." + (j + offset) + "." + 254);
                        itemsIp.add(ipObj);
                        j++;
                    }
                }
            }
        } else if (InvertBits(sm2) < 255 || (sm2 == 0 && sm1 ==255)) {
            offset = InvertBits(sm2);
            host = offset *255 *255 -2;
            for (int j = 0; j <= 255; j += offset) {
                Map<String, String> ipObj = new HashMap<String, String>();
                ipObj.put("title", "Subnet: " + oct1 + "." + j + "." + 0 + "." + 0 + "\t broadcast: " + oct1 + "." + (j + offset) + "." + 255 + "." + 255);
                ipObj.put("descr", oct1 + "." + j + "." + 0 + "." + 1 + " - " + oct1 + "." + (j + offset) + "." + 255 + "." + 254);
                itemsIp.add(ipObj);
                j++;
            }

        } else {
            // rest of the classes
            Log.w("Subnet", "Not implemanted");
        }

        ipclasseditText.setText(String.valueOf(ipclass) + " - " + itemsIp.size() + " subnet" + " - " + (host) + " hosts/sub");

        // display subnet range
        simpleAdapter.notifyDataSetChanged();
    }

    /**
     * get the ip class
     *
     * @param firstOctet the first octet of the ip address
     * @return ip class
     */
    public static char WhatClass(int firstOctet) {
        if ((firstOctet >= 1) && (firstOctet <= 126))    // A
        {
            return 'A';
        } else if ((firstOctet >= 128) && (firstOctet <= 191))    //B
        {
            return 'B';
        } else if ((firstOctet >= 192) && (firstOctet <= 223))  //C
        {
            return 'C';
        } else {
            return '?';
        }
    }

    /**
     * flip bits
     *
     * @param value value to flip bits
     * @return value with flipped bits
     */
    public static int InvertBits(int value) {
        int val = value;
        val = ~val & 0xff;
        // Ones complement and get rid of the other bits
        // by using binary AND
        // 0xff (hex) = 255 (decimal)
        return val;
    }

    /**
     * Initialize the top menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Listen for menu click and start Help & About activity
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Help:
                Intent intent = new Intent(getBaseContext(), Help.class);
                startActivity(intent);
                return true;
            case R.id.About:
                Intent intent1 = new Intent(getBaseContext(), About.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
