package com.aeroways.ragnarok.aeroways.UI.flight_booking;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.JsonPlaces;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Places;
import com.aeroways.ragnarok.aeroways.utils.CalendarUtils;
import com.aeroways.ragnarok.aeroways.utils.PlacesUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlightSearchFragment extends Fragment implements NumberPicker.OnValueChangeListener {
/*
    private List<FlightBookingEntry> flightBookingEntries = new ArrayList<FlightBookingEntry>();
    private TextView test;
*/

    RadioButton r11,r12,r21,r22,r23;
    DatePickerDialog.OnDateSetListener onDateSetListener,onDateSetListener2;
    LinearLayout l_depart,l_retour,box1,box2,box3;
    TextView d1,y1,d2,y2,a_source,a_dest,p_source,p_dest,n1,n2,n3;
    SpinnerDialog spinnerDialog,spinnerDialog2;
    ArrayList<String> airportNames;
    JsonPlaces depart,arrivee;
    Button button;

    // Attributes to be sent
    String s_source,s_destination,s_dd,s_da,s_adults,s_children,s_infants,cabinClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        n1 = getActivity().findViewById(R.id.n1);
        n2 = getActivity().findViewById(R.id.n2);
        n3 = getActivity().findViewById(R.id.n3);

        box1 = getActivity().findViewById(R.id.box1);
        box2 = getActivity().findViewById(R.id.box2);
        box3 = getActivity().findViewById(R.id.box3);

        box1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showN1();
            }
        });
        box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showN2();
            }
        });
        box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showN3();
            }
        });

        l_depart=getActivity().findViewById(R.id.l_depart);
        l_retour=getActivity().findViewById(R.id.l_retour);

        r11 = getActivity().findViewById(R.id.r11);
        r12 = getActivity().findViewById(R.id.r12);
        r21 = getActivity().findViewById(R.id.r21);
        r22 = getActivity().findViewById(R.id.r22);
        r23 = getActivity().findViewById(R.id.r23);

        r11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               r11.setChecked(true);
               r12.setChecked(false);
               l_retour.setClickable(true);
               l_retour.setVisibility(View.VISIBLE);

            }
        });

        r12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                r12.setChecked(true);
                r11.setChecked(false);
                l_retour.setClickable(false);
                l_retour.setVisibility(View.GONE);
            }
        });

        cabinClass = "economy";

        r21.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                r21.setChecked(true);
                r22.setChecked(false);
                r23.setChecked(false);
                cabinClass = "economy";
            }
        });

        r22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                r22.setChecked(true);
                r21.setChecked(false);
                r23.setChecked(false);
                cabinClass = "business";
            }
        });

        r23.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                r23.setChecked(true);
                r22.setChecked(false);
                r21.setChecked(false);
                cabinClass = "first";
            }
        });


        l_depart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        s_dd = "2019-09-20";
        s_da = "2019-09-22";

        d1=getActivity().findViewById(R.id.d1);
        y1=getActivity().findViewById(R.id.y1);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String sYear = String.valueOf(year);
                String sMonth;
                String sDay;
                if (dayOfMonth<10) sDay="0"+String.valueOf(dayOfMonth); else sDay=String.valueOf(dayOfMonth);
                if (month<10) sMonth="0"+String.valueOf(month); else sMonth=String.valueOf(month);

                d1.setText(sDay + " " + CalendarUtils.monthNumberToMonthName(month));
                y1.setText(sYear);
                s_dd=sYear+"-"+sMonth+"-"+sDay;
            }
        };

        l_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),android.R.style.Theme_Holo_Light_Dialog_MinWidth,onDateSetListener2,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        d2=getActivity().findViewById(R.id.d2);
        y2=getActivity().findViewById(R.id.y2);

        onDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String sYear = String.valueOf(year);
                String sMonth;
                String sDay;
                if (dayOfMonth<10) sDay="0"+String.valueOf(dayOfMonth); else sDay=String.valueOf(dayOfMonth);
                if (month<10) sMonth="0"+String.valueOf(month); else sMonth=String.valueOf(month);
                d2.setText(sDay + " " + CalendarUtils.monthNumberToMonthName(month));
                y2.setText(sYear);
                s_da=sYear+"-"+sMonth+"-"+sDay;
            }
        };

        a_source = getActivity().findViewById(R.id.a_source);
        a_dest = getActivity().findViewById(R.id.a_dest);

        p_source = getActivity().findViewById(R.id.p_source);
        p_dest = getActivity().findViewById(R.id.p_dest);

        final JsonPlaces[] jsonPlaces = PlacesUtils.getFromJson(getContext());
        airportNames = PlacesUtils.getAirportNames(jsonPlaces);

        depart = PlacesUtils.getJsonPlaceByAirportName("Tunis - Carthage",jsonPlaces);
        arrivee = PlacesUtils.getJsonPlaceByAirportName("Paris - Charles de Gaulle",jsonPlaces);


        spinnerDialog = new SpinnerDialog(getActivity(),airportNames,"Choisir l'aéroport de départ");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                depart = PlacesUtils.getJsonPlaceByAirportName(item,jsonPlaces);
                String c = depart.getCode().substring(0, arrivee.getCode().length() - 4);
                a_source.setText(c);
                p_source.setText(depart.getPays());

            }
        });

        a_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
            }
        });

        spinnerDialog2 = new SpinnerDialog(getActivity(),airportNames,"Choisir l'aéroport d'arrivée");
        spinnerDialog2.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                arrivee = PlacesUtils.getJsonPlaceByAirportName(item,jsonPlaces);
                String c = arrivee.getCode().substring(0, arrivee.getCode().length() - 4);
                a_dest.setText(c);
                p_dest.setText(arrivee.getPays());
            }
        });

        a_dest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog2.showSpinerDialog();
            }
        });

        button = getActivity().findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_source = depart.getCode();
                s_destination = arrivee.getCode();
                s_adults = n1.getText().toString();
                s_children = n2.getText().toString();
                s_infants = n3.getText().toString();

                if (!(CalendarUtils.compareDate(s_dd,s_da))) {
                    Toast.makeText(getActivity(), "Veuillez vérifier les dates de départ et d'arrivée",
                            Toast.LENGTH_SHORT).show();
                } else if ((Integer.parseInt(s_adults)+Integer.parseInt(s_children)+Integer.parseInt(s_infants))>6){
                    Toast.makeText(getActivity(), "Vous pouvez réserver des tickets pour 6 personnes au maximum",
                            Toast.LENGTH_SHORT).show();
                } else if ((Integer.parseInt(s_adults) == 0) && (Integer.parseInt(s_infants) > 0 )){
                    Toast.makeText(getActivity(), "Les bébés ne peuvent pas voyager sans adultes",
                            Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(s_adults)*2 < Integer.parseInt(s_infants)){
                    Toast.makeText(getActivity(), "Un adulte peut accompagner au maximum 2 bébés",
                            Toast.LENGTH_SHORT).show();
                } else { // Cas normal

                    if (r11.isChecked()){ // Vol aller retour



                    } else if (r12.isChecked()){ // Vol aller simple



                    }

                }

            }
        });


/*        test = getActivity().findViewById(R.id.textView13);
        new FlightSearchUpdater().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); */
    }

/*
    public class FlightSearchUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                flightBookingEntries = DoubleFlightBookingService.getFlightBookingEntries("TUN-sky","CDG-sky","2019-04-06","2019-04-12");
            } catch (Exception e) {
                Log.e("MyApp","Background failed");
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("MyApp","Starting post execute");
            super.onPostExecute(aVoid);
            String s="";
            for (int i=0;i<flightBookingEntries.size();i++) s+= flightBookingEntries.get(i).getPrice()+"\n";
            test.setText(s);
        }
    }
*/
    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
    }

    public void showN1()
    {
        final Dialog d = new Dialog(getContext());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.number_dialog);
        Button b1 = d.findViewById(R.id.button1);
        final NumberPicker np = d.findViewById(R.id.numberPicker1);
        np.setMaxValue(5);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                n1.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        d.show();
    }

    public void showN2()
    {
        final Dialog d = new Dialog(getContext());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.number_dialog);
        Button b1 = d.findViewById(R.id.button1);
        final NumberPicker np = d.findViewById(R.id.numberPicker1);
        np.setMaxValue(5);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                n2.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        d.show();
    }

    public void showN3()
    {
        final Dialog d = new Dialog(getContext());
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.number_dialog);
        Button b1 = d.findViewById(R.id.button1);
        final NumberPicker np = d.findViewById(R.id.numberPicker1);
        np.setMaxValue(5);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                n3.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });
        d.show();
    }
}
