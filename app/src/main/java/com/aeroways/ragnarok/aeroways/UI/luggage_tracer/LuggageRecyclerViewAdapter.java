package com.aeroways.ragnarok.aeroways.UI.luggage_tracer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.Entities.FlightBoardEntry;
import com.aeroways.ragnarok.aeroways.Entities.Luggage;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.flight_board.FlightBoardRecyclerViewAdapter;
import com.aeroways.ragnarok.aeroways.utils.BitmapUtils;

import java.util.List;

public class LuggageRecyclerViewAdapter extends RecyclerView.Adapter<LuggageRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Luggage> luggageList;

    public LuggageRecyclerViewAdapter(Context context,List<Luggage> luggageList){
        this.context=context;
        this.luggageList=luggageList;
    }

    @NonNull
    @Override
    public LuggageRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_luggage,parent,false);
        LuggageRecyclerViewAdapter.ViewHolder viewHolder = new LuggageRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LuggageRecyclerViewAdapter.ViewHolder holder, int position) {
        Luggage luggage =luggageList.get(position);
        holder.id.setText("Id : "+luggage.getId());
        holder.type.setText("Type : "+luggage.getType());
        holder.couleur.setText("Couleur : "+luggage.getColor());
        holder.poids.setText("Poids : "+luggage.getWeight()+" Kg");
        holder.status.setText(luggage.getStatus());
        holder.lastseen.setText(luggage.getLastSeen());
        BitmapUtils.loadImageFromURL(luggage.getPicUrl(),holder.pic);
    }

    @Override
    public int getItemCount() {
        return luggageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id,type,couleur,poids,status,lastseen;
        ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.textView15);
            type = itemView.findViewById(R.id.textView17);
            couleur = itemView.findViewById(R.id.textView2);
            poids = itemView.findViewById(R.id.textView3);
            status = itemView.findViewById(R.id.textView9);
            lastseen = itemView.findViewById(R.id.textView12);
            pic = itemView.findViewById(R.id.imageView2);


        }

    }
}
