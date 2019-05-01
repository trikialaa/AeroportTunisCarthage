package com.aeroways.ragnarok.aeroways.UI.stores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.Entities.Boutique;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageRecyclerViewAdapter;
import com.aeroways.ragnarok.aeroways.utils.BitmapUtils;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import io.techery.properratingbar.ProperRatingBar;
import io.techery.properratingbar.RatingListener;


public class StoresRecyclerViewAdapter extends RecyclerView.Adapter<StoresRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Boutique> boutiques;
    DatabaseReference ref;

    public StoresRecyclerViewAdapter(Context context, ArrayList<Boutique> boutiques) {
        this.context = context;
        this.boutiques = boutiques;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference("boutiques");

    }

    @NonNull
    @Override
    public StoresRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store,parent,false);
        StoresRecyclerViewAdapter.ViewHolder viewHolder = new StoresRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoresRecyclerViewAdapter.ViewHolder holder, int position) {
        final Boutique boutique = boutiques.get(position);
        holder.nom.setText(boutique.getNom());
        BitmapUtils.loadImageFromURL(boutique.getPicLink(),holder.pic);

        if (boutique.getEvaluations().equals(null)){
            holder.properRatingBar.setRating(0);
            holder.evaluationVisiteurs.setText("Pas d'évaluations pour cette boutique");
            holder.monEvaluation.setText("Vous n'avez pas encore évalué cette boutique");
        } else {

            int nbrEvaluations = 0;
            int totalEvaluations = 0;
            for (String key: boutique.getEvaluations().keySet()){
                nbrEvaluations++;
                totalEvaluations+=boutique.getEvaluations().get(key);
            }

            float moyenneEvaluations = (float)totalEvaluations/nbrEvaluations;
            holder.evaluationVisiteurs.setText("Évaluation des visiteurs : "+ String.format("%.2f",moyenneEvaluations) +" / 5");
            if (boutique.getEvaluations().containsKey(SharedPreferencesUtils.loadUser((FragmentActivity)context).getId())){
                int myRating = boutique.getEvaluations().get(SharedPreferencesUtils.loadUser((FragmentActivity)context).getId());
                holder.properRatingBar.setRating(myRating);
                holder.monEvaluation.setText("Votre évaluation : "+myRating+" / 5");
            } else {
                holder.properRatingBar.setRating(0);
                holder.monEvaluation.setText("Vous n'avez pas encore évalué cette boutique");
            }
        }

        holder.properRatingBar.setListener(new RatingListener() {
            @Override
            public void onRatePicked(ProperRatingBar properRatingBar) {
                ref.child(boutique.getNom()).child("evaluations").child(SharedPreferencesUtils.loadUser((FragmentActivity)context).getId()).setValue(properRatingBar.getRating());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return boutiques.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView pic;
        TextView nom, evaluationVisiteurs,monEvaluation;
        ProperRatingBar properRatingBar;

        public ViewHolder(View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.imageView5);
            nom = itemView.findViewById(R.id.textView20);
            evaluationVisiteurs = itemView.findViewById(R.id.textView23);
            monEvaluation = itemView.findViewById(R.id.textView39);
            properRatingBar = itemView.findViewById(R.id.properRatingBar);
        }
    }
}
