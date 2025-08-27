package officea.leveransapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.Arrays;

import officea.leveransapp.Common.Update;

public class CountryActivity extends Activity {

    public static Handler messageHandler;

    private final Update update = new Update();
    private Button btnUpdate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        Log.i("Test", "Large Memory Limit " + activityManager.getLargeMemoryClass() + " MB");
        Log.i("Test", " Memory Limit  " + activityManager.getMemoryClass() + " MB");

        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); // Tar bort actionbaren
        setContentView(R.layout.country);

        btnUpdate = (Button) findViewById(R.id.btnUpdate); // special since we have to write to it
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    /*
                    btnUpdate.setEnabled(false);
                    Intent iUpdate = new Intent(getApplicationContext(), UpdateService.class);
                    iUpdate.putExtra("MESSENGER", new Messenger(VendorActivity.messageHandler));
                    iUpdate.putExtra("USER", "officea.se");
                    iUpdate.putExtra("PASS", "nk5mn6ch97yNmyy");
                    iUpdate.putExtra("URL", "ftp.officea.se");
                    iUpdate.putExtra("PORT", "21");
                    iUpdate.putExtra("DL_DIR", "/pizzaappen/");
                    iUpdate.putExtra("FILENAME", "app.apk");
                    startService(iUpdate);
                    */

                    btnUpdate.setEnabled(false);

                    update.setContext(getApplicationContext());
                    update.setup("officea.se", "nk5mn6ch97yNmyy", "ftp.officea.se", 21, "/leveransappen/", "app.apk");
                    update.execute();

                    new Thread() {
                        public void run() {
                            while (update.isRunning()) {
                                try {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            String sPercent = Float.toString(update.getPercent()) + "%";
                                            btnUpdate.setText(sPercent);
                                        }
                                    });
                                    Thread.sleep(10);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (!update.isUpdated()) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        btnUpdate.setEnabled(true);
                                        btnUpdate.setText("Updatera");
                                    }
                                });
                            }
                        }
                    }.start();

                } catch (Exception e) {
                    Log.d("Update exception: ", e.getMessage());
                }
            }
        });

        final ListView lviewCountry = (ListView) findViewById(R.id.lviewCountry);
        String[] countryItems = {"a", "b", "c"};
        ArrayList<String> countryArrayList = new ArrayList<>(Arrays.asList(countryItems));
        final ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countryArrayList);
        lviewCountry.setAdapter(countryAdapter);

        lviewCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String country = (String) lviewCountry.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), CityActivity.class);
                intent.putExtra("COUNTRY_NAME", country);
                startActivity(intent);
            }
        });

        Log.d("Google API version: ", Integer.toString(GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE));

        SQLiteDatabase db;
        db = openOrCreateDatabase("orders", MODE_PRIVATE, null);
        try {
            Cursor c = db.rawQuery("SELECT uname, pword FROM credentials;", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                int col = c.getColumnIndex("uname");
                int col2 = c.getColumnIndex("pword");
                //Intent i = new Intent(context, AuthenticationService.class);
                // i.putExtra("uName", uNameS);
                //i.putExtra("pWord", pWordS);
                //i.putExtra("MESSENGER", new Messenger(CountryActivity.messageHandler));
                //startService(i);
            } else {
                Log.d("auto login", "no data");
            }
        } catch (Exception e) {
            Log.d("auto login error", e.getMessage());
        }
        db.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message message) {
            int state = message.arg1;
//            switch (General.MessageStatus.values()[state]) {
//                case OPEN:
//                    Intent intent = new Intent(MainActivity.this, VendorActivity.class);
//                    intent.putExtra("uName", MainActivity.uNameS);
//                    intent.putExtra("pWord", MainActivity.pWordS);
//                    intent.putExtra("open", false);
//                    startActivity(intent);
//                    break;
//                case LOGIN_INVALID:
//                    Toast.makeText(getApplicationContext(), "Invalid login", Toast.LENGTH_LONG).show();
//                    break;
//                case CONNECTION_ERROR:
//                    iTriedToLogin++;
//                    if (iTriedToLogin < 20000) {
//                        Intent i = new Intent(context, AuthenticationService.class);
//                        i.putExtra("uName", uNameS);
//                        i.putExtra("pWord", pWordS);
//                        i.putExtra("MESSENGER", new Messenger(MainActivity.messageHandler));
//                        startService(i);
//                    } else {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                        builder.setMessage("Could not contact server").setTitle("Connection Error");
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                    }
//                    break;
//            }
        }
    }
}
