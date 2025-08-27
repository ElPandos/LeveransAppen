package officea.leveransapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        TextView cityHeader = (TextView) findViewById(R.id.header_order);
        final String country = getIntent().getStringExtra("COUNTRY_NAME");
        final String city = getIntent().getStringExtra("CITY_NAME");
        final String restaurant = getIntent().getStringExtra("RESTAURANT_NAME");
        cityHeader.setText(" > " + country + " > " + city + " > " + restaurant);

        final Button btnTime = (Button) findViewById(R.id.buttonTime);
        btnTime.setText(btnTime.getText() + ", 0 MIN");

        SeekBar seekBarOrder = (SeekBar) findViewById(R.id.seekBarOrder);
        //seekBarTime.setMax(100);

        seekBarOrder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                btnTime.setText(((String)btnTime.getText()).split(",")[0] + ", " + String.valueOf(progress) + " MIN");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ListView lviewOrder = (ListView) findViewById(R.id.lviewOrder);
        String[] orderItems = {"1", "2", "3"};
        ArrayList<String> orderArrayList = new ArrayList<>(Arrays.asList(orderItems));
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, orderArrayList);
        lviewOrder.setAdapter(orderAdapter);

        lviewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order = (String) lviewOrder.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), DeliveryActivity.class);
                intent.putExtra("COUNTRT_NAME", country);
                intent.putExtra("CITY_NAME", city);
                intent.putExtra("RESTAURANT_NAME", restaurant);
                intent.putExtra("DELIVERY_ORDER", order);
                startActivity(intent);
            }
        });
    }
}