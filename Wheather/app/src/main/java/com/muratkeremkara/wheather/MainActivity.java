package com.muratkeremkara.wheather;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private TextView txt_Sehir, txt_Sicaklik, txt_Weather, txt_Aciklama;
    private Button buton;
    private EditText editText;

    String sehir;
    //tanımlamalarımızı yaptık.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_Sehir = findViewById(R.id.txt_sehir);
        txt_Aciklama = (TextView) findViewById(R.id.txt_aciklama);
        txt_Sicaklik = (TextView) findViewById(R.id.txt_sicaklik);
        txt_Weather = (TextView) findViewById(R.id.txt_weather);
        buton = (Button) findViewById(R.id.buton);
        editText = (EditText) findViewById(R.id.editText);

//arayüzdeki componentlere atadık
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonParse jsonParse = new JsonParse();
                sehir = String.valueOf(editText.getText());//edit textten veriyi sehir adlı değişkene atadık
                new JsonParse().execute();//jsonParse AsynTask metodumuzu çalıştırdık.

            }
        });
    }

    //AsynTask olayını başka bir yazıda açıklayacağım inşallah ama internetten araştırabilirsiniz çok güzel anlatan siteler var.
    protected class JsonParse extends AsyncTask<Void, Void, Void> {
        String result_main ="";
        String result_description = "";
        String result_icon = "";
        int result_temp;
        String result_city;
        Bitmap bitImage;
        @Override
        protected Void doInBackground(Void... params) {
            String result="";
            try {
                URL weather_url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+sehir+"&appid=df7def57b01a2d28ee6391c1fd0e19ca");//Url'mizi aldık
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(weather_url.openStream()));//url'yi okuyacak bufferReader'a gönderdik
                String line = null;
                while((line = bufferedReader.readLine()) != null){//satırları tek tek aldık ve ekledik
                    result += line;
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(result);//string ifadeye çevirdik
                JSONArray jsonArray = jsonObject.getJSONArray("weather");//şimdi jsona bakarsanız weather isimli bir dizi var o diziyi aldık
                JSONObject jsonObject_weather = jsonArray.getJSONObject(0);//ilk indexi aldık
                result_main = jsonObject_weather.getString("main");//ilk indexin main adlı değişkenini çektik
                result_description = jsonObject_weather.getString("description");
                result_icon = jsonObject_weather.getString("icon");
//tek tek işimize yarayacakları aldık

                JSONObject jsonObject_main = jsonObject.getJSONObject("main");//main diye son kısımlarda bir değişken var onuda aldık
                Double temp = jsonObject_main.getDouble("temp");//main'in içinden sıcaklığı aldık

                result_city = jsonObject.getString("name");//en sondaki kısımdan city ismini aldık

                result_temp = (int) (temp-273);//Kelvin olduğu için Celcius'a çevirdik

                URL icon_url = new URL("http://openweathermap.org/img/w/"+result_icon+".png");//resim dosyasını burada saklıyor api adresimiz
                bitImage = BitmapFactory.decodeStream(icon_url.openConnection().getInputStream());//Android'de image olarak kullanamadığımız için bitmap formatına çevirdik

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            txt_Sicaklik.setText(String.valueOf(result_temp));
            txt_Weather.setText(result_main);
            txt_Sehir.setText(result_city);
            txt_Aciklama.setText(result_description);
            super.onPostExecute(aVoid);

//tek tek gerekli olan kısımlara yerleştirdik aldığımız verileri
        }
    }
}