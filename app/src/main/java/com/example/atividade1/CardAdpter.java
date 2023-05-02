package com.example.atividade1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdpter extends RecyclerView.Adapter<CardAdpter.CardViewHolder>{

    private final List<Card> cards;

    public CardAdpter(List<Card> cards) {
        this.cards = cards;
    }
    public List<Card> getCards() {
        return cards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);

        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.bind(card);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


    class CardViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle, txtDesc, txtDate;
        CardView cardView;

        ImageView pin;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Card card = cards.get(getAdapterPosition());
                    Context context = itemView.getContext();
                    Intent i = new Intent(context, CadastroActivity.class);
                    i.putExtra("edit", card);
                    context.startActivity(i);
                }
            });

            txtTitle = itemView.findViewById(R.id.txtUser);
            txtDesc =  itemView.findViewById(R.id.txtDesc);
            txtDate = itemView.findViewById(R.id.txtDate);
            cardView = itemView.findViewById(R.id.cardView);
            pin = itemView.findViewById(R.id.ivPin);
        }

        public void bind(Card card) {
            txtTitle.setText(card.getTitle());
            txtDesc.setText(card.getDescription());
            txtDate.setText(card.getFormatedDate(card.getTimestamp()));

            if(!card.isPinnedCard())
                pin.setVisibility(View.INVISIBLE);

            if(card.isMarker())
                cardView.setCardBackgroundColor(itemView.getContext().getColor(R.color.purple_200));
        }
    }
}
