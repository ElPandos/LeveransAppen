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

public class RestaurantActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        TextView restaurantHeader = (TextView) findViewById(R.id.header_restaurant);
        final String country = getIntent().getStringExtra("COUNTRY_NAME");
        final String city = getIntent().getStringExtra("CITY_NAME");
        restaurantHeader.setText(" > " + country + " > " + city + " > " + restaurantHeader.getText());

        final ListView lviewRestaurant = (ListView) findViewById(R.id.lviewRestaurant);
        String[] cityItems = {"g", "h", "i"};
        ArrayList<String> restaurantArrayList = new ArrayList<>(Arrays.asList(cityItems));
        ArrayAdapter<String> restaurantAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, restaurantArrayList);
        lviewRestaurant.setAdapter(restaurantAdapter);

        lviewRestaurant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String restaurant = (String) lviewRestaurant.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("COUNTRY_NAME", country);
                intent.putExtra("CITY_NAME", city);
                intent.putExtra("RESTAURANT_NAME", restaurant);
                startActivity(intent);
            }
        });
    }
}