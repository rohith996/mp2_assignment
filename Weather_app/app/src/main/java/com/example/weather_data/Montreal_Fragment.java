package com.example.weather_data;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Montreal_Fragment extends Fragment
{

    ArrayList<ConsolidateWeather> consolidatedWeather;
    ArrayList<Source> sources;
    public Montreal_Fragment() {
        // Required empty public constructor
    }

    TextView city,min,max,actual,humi,predic,daytxt1,daytxt2,daytxt3,daytxt4,daytxt5;
    ImageButton dayimg1,dayimg2,dayimg3,dayimg4,dayimg5;
    View root;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_montreal, container, false);
        return root;
    }
    public void onResume() {
        super.onResume();
    }
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        city=root.findViewById(R.id.m_city);
        min=root.findViewById(R.id.m_min);
        max=root.findViewById(R.id.m_max);
        actual=root.findViewById(R.id.m_actual);
        humi=root.findViewById(R.id.m_humidity);
        predic=root.findViewById(R.id.m_predic);
        daytxt1=root.findViewById(R.id.m_day1);
        daytxt2=root.findViewById(R.id.m_day2);
        daytxt3=root.findViewById(R.id.m_day3);
        daytxt4=root.findViewById(R.id.m_day4);
        daytxt5=root.findViewById(R.id.m_day5);
        getWeatherData();
    }

    private void getWeatherData()
    {

        GetDataServiceInterface service =RetrofitClientInstance.getRetrofitInstance().create(GetDataServiceInterface.class);

        Call<Weather> call = service.getWeather(getArguments().getInt("geoid"));

        call.enqueue(new Callback<Weather>() {
            public void onResponse(Call<Weather> call, Response<Weather> response) {


                Weather weather = response.body();
                sources = new ArrayList<>(weather.getSources());
                Parent parent = weather.getParent();

                consolidatedWeather = new ArrayList<>(weather.getConsolidatedWeather());

                bundle = new Bundle();

                bundle.putString("bbc_url", sources.get(0).getUrl() + getArguments().getInt("bbcid"));
                bundle.putString("city", weather.getTitle());

                Date date = new Date();
                String date1 = consolidatedWeather.get(0).getApplicableDate();
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("Date: " + date);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM");
                String today = simpleDateFormat.format(date);
                System.out.println("Date: " + today);


                city.setText(weather.getTitle() + ",\n" + parent.getTitle() + " " + today);
                actual.setText(String.format("%.2f", consolidatedWeather.get(0).getTheTemp()) + "°C");
                //f_min_max_temp.setText(String.format("%.2f",consolidatedWeather.get(0).getMaxTemp()) + "°" + "/" + String.format("%.2f",consolidatedWeather.get(0).getMinTemp()) + "°");
                min.setText(String.format("%.2f", consolidatedWeather.get(0).getMinTemp()) + "°");
                max.setText(String.format("%.2f", consolidatedWeather.get(0).getMaxTemp()) + "°");
                humi.setText(consolidatedWeather.get(0).getHumidity().toString() + "%");
                predic.setText(consolidatedWeather.get(0).getPredictability().toString() + "%");
                Picasso.get().load(getWeatherImage(consolidatedWeather.get(0).getWeatherStateAbbr())).into(dayimg1);


            }

            public String getWeatherImage(String weatherStateAbbr) {
                return "https://www.metaweather.com/static/img/weather/png/" + weatherStateAbbr + ".png";
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }


        });
    }
}
