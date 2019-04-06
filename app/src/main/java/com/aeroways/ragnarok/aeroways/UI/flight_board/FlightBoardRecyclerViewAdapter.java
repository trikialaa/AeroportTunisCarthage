package com.aeroways.ragnarok.aeroways.UI.flight_board;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.R;
import java.util.List;

public class FlightBoardRecyclerViewAdapter extends RecyclerView.Adapter<FlightBoardRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<FlightBoardEntry> fbe;

    public FlightBoardRecyclerViewAdapter(Context context,List<FlightBoardEntry> fbe){
        this.context=context;
        this.fbe=fbe;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flight_board,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FlightBoardEntry flightBoardEntry =fbe.get(position);
        holder.time.setText(flightBoardEntry.getTime());
        holder.place.setText(flightBoardEntry.getPlace());
        holder.compagny.setText(flightBoardEntry.getCompany());
        holder.number.setText(flightBoardEntry.getNumber());
        holder.status.setText(flightBoardEntry.getStatus());
    }

    @Override
    public int getItemCount() {
        return fbe.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time,place,compagny,number,status;

        public ViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.textView15);
            place = itemView.findViewById(R.id.textView16);
            compagny = itemView.findViewById(R.id.textView17);
            number = itemView.findViewById(R.id.textView18);
            status = itemView.findViewById(R.id.textView19);


        }

    }
}
