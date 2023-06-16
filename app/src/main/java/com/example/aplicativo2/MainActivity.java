package com.example.aplicativo2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    CardAdpter cardAdpter;
    Boolean deleteCard = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardAdpter = new CardAdpter(new ArrayList<>(CardDAO.getCards(this)));

        RecyclerView rv = findViewById(R.id.recyclerView);
        rv.setAdapter(cardAdpter);

        findViewById(R.id.btnAddM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Card card = Card.builder();
                CardDAO.insert(Activity_trash.this, card);
                cardAdpter.getCards().add(card);
                cardAdpter.notifyItemInserted(0);
                rv.scrollToPosition(0);*/
                Intent i = new Intent(MainActivity.this, CadastroActivity.class);
                i.putExtra("action", "insert");
                startActivity(i);
            }
        });

        findViewById(R.id.btnLanguage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.btn_title_language);

                builder.setPositiveButton("Português", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setAppLanguage("pt");
                        recreate();
                    }
                });

                builder.setNegativeButton("Inglês", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setAppLanguage("en");
                        recreate();
                    }
                });

                builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        findViewById(R.id.btnDarkMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
        });

        findViewById(R.id.btnLightMode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        });

        ItemTouchHelper helperThouch = new ItemTouchHelper(new ItemTouchHandler(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT));

        helperThouch.attachToRecyclerView(rv);
    }

    private class ItemTouchHandler extends ItemTouchHelper.SimpleCallback {

        public ItemTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return super.isLongPressDragEnabled();
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            List<Card> cards = CardDAO.getCards(recyclerView.getContext());
            Card card = cards.get(viewHolder.getAdapterPosition());

            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            if (card.isPinnedCard()) {
                return false;
            }

            Collections.swap(cards, from, to);
            cardAdpter.notifyItemMoved(from, to);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Card card = cardAdpter.getCards().get(viewHolder.getAdapterPosition());
            delete(card, viewHolder);
        }

        private void delete(Card card, RecyclerView.ViewHolder viewHolder) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(viewHolder.itemView.getContext());
            alerta.setTitle(getApplicationContext().getString(R.string.deleteTitle));
            alerta.setIcon(android.R.drawable.ic_dialog_alert);
            String title = card.getTitle().substring(0, Math.min(card.getTitle().length(), 20));
            alerta.setMessage(
                    "                    "+getApplicationContext().getString(R.string.deleteMSG)+"                    " +
                    "       "+getApplicationContext().getString(R.string.tv_title)+": "+title+"?");
            alerta.setNeutralButton(getApplicationContext().getString(R.string.negative), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cardAdpter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            });

            alerta.setPositiveButton(getApplicationContext().getString(R.string.positive), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    CardDAO.delete(MainActivity.this, card.getId() );
                    cardAdpter.getCards().remove(viewHolder.getAdapterPosition());
                    cardAdpter.notifyItemRemoved(viewHolder.getAdapterPosition());
                }
            });
            alerta.show();
        }
    }
    private void setAppLanguage(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}