package com.muratkeremkara.travelbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.muratkeremkara.travelbook.R;
import com.muratkeremkara.travelbook.CustomAdapter;
import com.muratkeremkara.travelbook.Place;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    ArrayList<Place> placeList = new ArrayList<>();
    ListView listView;
    CustomAdapter customAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {// menu açıldığında anda olacaklar
        MenuInflater menuInflater = getMenuInflater(); // herhangi bir layoutu kodla ilişkili hale getirmek için inflater kullanılır
        menuInflater.inflate(R.menu.add_places,menu); // hangi xml dosyasını menuyle bağlayacağını gösteriyor     layout = sayfa düzeni
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {// menu seçildikten sonra olacaklar yani menuye tıkladığımızda karşımıza çıkacaklar

        if (item.getItemId() == R.id.add_place) {
            Intent intent = new Intent(this,MapsActivity.class); // aktiviteler arası geçiş yapmak için intent kullanılır
            intent.putExtra("info","new");// info diye bir anahtar kelimeye new diye bir değer kaydedip öyle yoluyoruz
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        getData();
    }


    public void getData() {// SQl de veri tabanını çekmek için kullanılıyor
        customAdapter = new CustomAdapter(this,placeList);

        try {

            database = this.openOrCreateDatabase("Places",MODE_PRIVATE,null);
            Cursor cursor = database.rawQuery("SELECT * FROM places",null);

            int nameIx = cursor.getColumnIndex("name");
            int latitudeIx = cursor.getColumnIndex("latitude");
            int longitudeIx = cursor.getColumnIndex("longitude");

            while (cursor.moveToNext()) {

                String nameFromDatabase = cursor.getString(nameIx);
                String latitudeFromDatabase = cursor.getString(latitudeIx);
                String longitudeFromDatabase = cursor.getString(longitudeIx);

                Double latitude = Double.parseDouble(latitudeFromDatabase);
                Double longitude = Double.parseDouble(longitudeFromDatabase);

                Place place = new Place(nameFromDatabase,latitude,longitude);

                System.out.println(place.name);

                placeList.add(place);

            }
            customAdapter.notifyDataSetChanged();
            cursor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("info","old");
                intent.putExtra("place",placeList.get(i));
                startActivity(intent);
            }
        });

    }


}