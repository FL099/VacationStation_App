package com.example.vacationstation;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.here.olp.network.HttpClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.EditTextPreference;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public ListAdapter mAdapter;
    private boolean bool_new = false;
    private static final String KEY_TEXT = "resultText";
    public static List<MemoryItem> lst_memories = new LinkedList<MemoryItem>();
    public static List<String> lst_places = new LinkedList<String>();
    public static  String credentials = "";

    /*MemoryItemDbHelper dbHelper;
    SQLiteDatabase db;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView list = findViewById(R.id.rv_list); //?
        //lst_memories = generateContent();
        //storeList(lst_memories);

        /*dbHelper = new MemoryItemDbHelper(this);
        db = dbHelper.getWritableDatabase();*/

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String user = prefs.getString("username", "u1");
        String passwd = prefs.getString("password", "p1");


        getCredentials(user, passwd);

        lst_memories = generateContent();

        //TODO: auskommentieren

        //loadWebResult("u1");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_overview, R.id.navigation_add, R.id.navigation_map, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_TEXT)) {

            }
        }
    }

    private void getCredentials (String user, String password) {
        CredentialsGetter credentialsGetter = new CredentialsGetter(user, password);
        new Thread(credentialsGetter).start();
    }



    class CredentialsGetter implements Runnable {

        URL url;
        String user;
        String pwd;

        CredentialsGetter(String us, String pw) {
            this.user = us;
            this.pwd =pw;
            try {
                url = new URL("https://localhost:11080/api/login/");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {

            try {
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);

                String out = "{\"username\":\"" + user + "\",\"password\":\"" + pwd + "\"}";
                int length = out.length();

                http.setFixedLengthStreamingMode(length);
                http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                http.connect();
                try (OutputStream os = http.getOutputStream()) {
                    os.write(out.getBytes(StandardCharsets.UTF_8));
                }

                InputStream is = http.getInputStream();
                credentials = is.toString();

            /*HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.addRequestProperty("username", user);
            urlConnection.addRequestProperty("passwort", password);

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            String out2 = "";


            if (scanner.hasNext()) {

                JSONObject root = new JSONObject(scanner.next());
                JSONArray results = root.getJSONArray("memories");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject result = results.getJSONObject(i);

                    MemoryItem mc = new MemoryItem(result);
                    lst_memories.add(mc);
                    out += mc.getMemoryItemData();


                }


            }

        */
            } catch (IOException e) { //| JSONException
                e.printStackTrace();
            }


        }
    }

    private void loadWebResult(String user) {

        WebRunnable webRunnable = new WebRunnable("https://api.vacationstation.com/" + user);
        new Thread(webRunnable).start();

    }

    class WebRunnable implements Runnable {

        URL url;

        WebRunnable(String url) {
            try {
                this.url = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            Handler mainHandler = new Handler(Looper.getMainLooper());



            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream in = urlConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");



                String out = "";


                if (scanner.hasNext()) {

                    JSONObject root = new JSONObject(scanner.next());
                    JSONArray results = root.getJSONArray("memories");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject result = results.getJSONObject(i);

                        MemoryItem mc = new MemoryItem(result);
                        lst_memories.add(mc);
                        out += mc.getMemoryItemData();


                    }

                    String finalOut = out;
                    //mainHandler.post(() -> outView.setText(finalOut));
                }


            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            //mainHandler.post(() -> loadBtn.setEnabled(true));
        }
    }

    public static List<MemoryItem> generateContent(){
        LinkedList<MemoryItem> list = new LinkedList<MemoryItem>();
        try{

            JSONObject root = new JSONObject("{\n" +
                    "\"places\": [\n" +
                    "{\n" +
                    "\"id\": 1234,\n" +
                    "\"name\": \"Paris\",\n" +
                    "\"contents\": [\n" +
                    "{\n" +
                    "\"filename\": \"bild1.png\",\n" +
                    "\"coordinates\": \"40.858370 N, 2.294481 E\",\n" +
                    "\"datetime\": \"12:00:01 1.1.2021\",\n" +
                    "\"tags\": [\"historic\", \"france\"],\n" +
                    "\"comment\":\"stunning\"\n" +
                    "},\n" +
                    "{\n" +
                    "\"filename\": \"bild2.png\",\n" +
                    "\"coordinates\": \"48.858370 N, 5.294481 W\",\n" +
                    "\"datetime\": \"13:00:01 1.1.2021\",\n" +
                    "\"tags\": [\"historic\", \"france\"],\n" +
                    "\"comment\":\"stunning\"\n" +
                    "}\n" +
                    "]\n" +
                    "},\n" +
                    "{\n" +
                    "\"id\": 1235,\n" +
                    "\"name\": \"Rom\",\n" +
                    "\"contents\": [\n" +
                    "{\n" +
                    "\"filename\": \"here_car.png\",\n" +
                    "\"coordinates\": \"1.858370 S, 2.294481 E\",\n" +
                    "\"datetime\": \"12:00:01 1.1.2021\",\n" +
                    "\"tags\": [\"historic\", \"italy\"],\n" +
                    "\"comment\":\"stunning\"\n" +
                    "}\n" +
                    "]\n" +
                    "}\n" +
                    "]\n" +
                    "}");
            JSONArray results = root.getJSONArray("places");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                JSONArray place = result.getJSONArray("contents");
                lst_places.add(result.getString("name"));

                for (int t = 0; t < place.length(); t++) {
                    JSONObject result2 = place.getJSONObject(t);
                    MemoryItem mc = new MemoryItem(result2);
                    list.add(mc);
                }
            }
        } catch ( JSONException e) {
            e.printStackTrace();
        }
        catch (Exception f){
            f.printStackTrace();
        }
        return list;
    }

    /*public List<MemoryItem> getContentFromDB() {
        List<MemoryItem> data = new LinkedList<>();

        //SELECT TOP(100) name, type, weight FROM tb_pokemon
        // WHERE type = 'ELECTRIC' and weight < x

        String[] projection = {
                BaseColumns._ID,
                MemoryItemContractClass.MemoryEntry.COLUMN_NAME_NAME,
                MemoryItemContractClass.MemoryEntry.COLUMN_NAME_TAGS,
                MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COMMENT,
                MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLAT,
                MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLON,
                MemoryItemContractClass.MemoryEntry.COLUMN_NAME_IMGPATH

        };

        String sortOrder = MemoryItemContractClass.MemoryEntry.COLUMN_NAME_TAGS + " DESC"; //TODO: auf date ändern

        Cursor cursor = db.query(
                MemoryItemContractClass.MemoryEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        while (cursor.moveToNext()){
            data.add(new MemoryItem(
                    cursor.getString(cursor.getColumnIndexOrThrow(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_TAGS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COMMENT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLAT)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLON)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_IMGPATH))

            ));
        }
        cursor.close();
        return data;
    }*/

    /*private void storeList(List<MemoryItem> lst_memories) {

        dbHelper = new MemoryItemDbHelper(this);
        db = dbHelper.getWritableDatabase();


        Cursor cursor = db.query(
                MemoryItemContractClass.MemoryEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        int amnt = cursor.getCount();

        if(amnt == 0){

            for(MemoryItem it : lst_memories){
                ContentValues values = new ContentValues();
                values.put(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_NAME, it.getName());
                values.put(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_TAGS, it.getTags());
                values.put(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COMMENT, it.getComment());
                values.put(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLAT, it.getCoordLat());
                values.put(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_COORDSLON, it.getCoordLon());
                values.put(MemoryItemContractClass.MemoryEntry.COLUMN_NAME_IMGPATH, it.getImgPath());

                 db.insert(MemoryItemContractClass.MemoryEntry.TABLE_NAME, null, values);
            }
        }
    }*/

    /*private List<MemoryItem> generateContent() {
        List<MemoryItem> data = new LinkedList<>();
        data.add(new MemoryItem("vienna1", "austria, flower, summer", "awesome",48.190498, 16.400408,  "card1.png"));
        data.add(new MemoryItem("vienna2", "austria, ancient, summer", "awesome", 48.190998, 16.420408, "card2.png"));
        data.add(new MemoryItem("vienna5", "austria, old, summer", "awesome", 48.190098, 16.395408, "card3.png"));
        data.add(new MemoryItem("vienna10", "austria, flower, summer", "awesome", 48.191018, 16.401408, "card2.png"));
        return data;
    }*/
}