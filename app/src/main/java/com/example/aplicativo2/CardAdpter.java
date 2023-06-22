package com.example.aplicativo2;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardAdpter extends RecyclerView.Adapter<CardAdpter.CardViewHolder>{
    private List<Card> cards;
    private List<Card> cardsFilter;

    public CardAdpter(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
        this.cardsFilter = new ArrayList<>(cards);
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
        Card card = cardsFilter.get(position);
        holder.bind(card);
    }

    @Override
    public int getItemCount() {
        return cardsFilter.size();
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

            if(card.isPinnedCard())
                pin.setImageResource(R.drawable.baseline_star_24);
        }
    }

    public void filtrar(String texto) {

        if(texto.isEmpty() || texto.equals("") || texto == null)
            return;

        String filtro = "";
        cardsFilter.clear();

        if(!texto.toLowerCase().equals("todos") && !texto.toLowerCase().equals("favoritos")) {
            filtro = texto;
            texto = "textfilter";
        }

        switch (texto.toLowerCase()){
            case  "textfilter":
                for (Card item : cards) {
                    if (item.getCategory().toLowerCase().contains(filtro.toLowerCase())) {
                        cardsFilter.add(item);
                    }
                }
                break;
            case  "favoritos":
                cardsFilter.addAll(cards.stream().filter(item -> item.isPinnedCard()).collect(Collectors.toList()));
                break;
            default:
                cardsFilter.addAll(cards);
                break;
        }

        notifyDataSetChanged();
    }
}
