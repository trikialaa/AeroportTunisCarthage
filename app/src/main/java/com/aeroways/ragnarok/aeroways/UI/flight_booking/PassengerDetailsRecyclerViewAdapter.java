package com.aeroways.ragnarok.aeroways.UI.flight_booking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.R;

import static android.view.View.GONE;

public class PassengerDetailsRecyclerViewAdapter extends RecyclerView.Adapter<PassengerDetailsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private int adults;
    private int children;
    private int infants;


    public  PassengerDetailsRecyclerViewAdapter(Context context, int adults, int children, int infants){
        this.context=context;
        this.children=children;
        this.adults=adults;
        this.infants=infants;
    }

    @NonNull
    @Override
    public PassengerDetailsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_voyageur,parent,false);
        PassengerDetailsRecyclerViewAdapter.ViewHolder viewHolder = new PassengerDetailsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerDetailsRecyclerViewAdapter.ViewHolder holder, int position) {
        if (adults>0){
            if (position < adults) holder.textView.setText("Informations de l'adulte N° "+(position+1));
            else if ((position >= adults) && (position < adults+children )) holder.textView.setText("Informations de l'enfant "+(position-adults+1));
            else {
                holder.email.setVisibility(GONE);
                holder.tel.setVisibility(GONE);
                holder.textView.setText("Informations du bébé "+(position-adults-children+1));
            }
        } else {
            holder.textView.setText("Informations de l'enfant "+(position+1));
        }
    }

    @Override
    public int getItemCount() {
        return adults+children+infants;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        EditText editText1,editText2,editText3,editText4,editText5,editText6,editText7;
        TextView textView;
        LinearLayout email,tel;

        public ViewHolder(View itemView) {
            super(itemView);

            editText1 = itemView.findViewById(R.id.editText1);
            editText2 = itemView.findViewById(R.id.editText2);
            editText3 = itemView.findViewById(R.id.editText3);
            editText4 = itemView.findViewById(R.id.editText4);
            editText5 = itemView.findViewById(R.id.editText5);
            editText6 = itemView.findViewById(R.id.editText6);
            editText7 = itemView.findViewById(R.id.editText7);
            textView = itemView.findViewById(R.id.textView28);
            email = itemView.findViewById(R.id.email);
            tel = itemView.findViewById(R.id.tel);


        }
    }
}
