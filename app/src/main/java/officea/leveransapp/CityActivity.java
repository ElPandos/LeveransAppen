package officea.leveransapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class CityActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);

        TextView cityHeader = (TextView) findViewById(R.id.header_city);
        final String country = getIntent().getStringExtra("COUNTRY_NAME");
        cityHeader.setText(" > " + country + " > " + cityHeader.getText());

        final ListView lviewCity = (ListView) findViewById(R.id.lviewCity);
        String[] cityItems = {"d","e","f"};
        ArrayList<String> cityArrayList = new ArrayList<>(Arrays.asList(cityItems));
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cityArrayList);
        lviewCity.setAdapter(cityAdapter);

        lviewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String city = (String) lviewCity.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                intent.putExtra("COUNTRY_NAME", country);
                intent.putExtra("CITY_NAME", city);
                startActivity(intent);
            }
        });
    }

}