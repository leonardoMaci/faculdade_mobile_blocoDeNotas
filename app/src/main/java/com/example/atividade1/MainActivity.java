package com.example.atividade1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;

    private ListView lvCards;

    private List<Card> listCards;
    private MyListAdapter adapter;

    private ActivityResultLauncher<Card> myLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCards = findViewById(R.id.lvCards);
        btnAdd = findViewById(R.id.btnAdd);

        lvCards.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                delete(position);
                return true;
            }
        });

        lvCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit(position);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CadastroActivity.class);
                i.putExtra("action", "insert");
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.carregarCards();
    }

    private void carregarCards() {
        String[] emptyList = {"Empty list!"};
        listCards = CardDAO.getCards(this);


        if(listCards.isEmpty())
            adapter = new MyListAdapter(this, emptyList);
        else
            adapter = new MyListAdapter(this, listCards);

        lvCards.setAdapter(adapter);
        lvCards.setEnabled(true);
    }


    private void delete(int index) {
        Card card = listCards.get( index );
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Delete");
        alerta.setIcon(android.R.drawable.ic_dialog_alert);
        String title = card.getTitle().substring(0, Math.min(card.getTitle().length(), 20));
        alerta.setMessage(
                "                    Confirm deletion                    " +
                "       Title: "+title +"? ");
        alerta.setNeutralButton("No", null);
        alerta.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CardDAO.delete(MainActivity.this, card.getId() );
                carregarCards();
            }
        });
        alerta.show();
    }

    private void edit(int index){
        Card card = listCards.get(index);

        Intent i = new Intent(MainActivity.this, CadastroActivity.class);
        i.putExtra("edit", card);
        startActivity(i);
    }

    public class MyListAdapter extends ArrayAdapter {

        private boolean emptyList;
        public MyListAdapter(Context context, List<Card> items) {
            super(context, R.layout.list_item, items);
            emptyList = false;
        }

        public MyListAdapter(Context context, String[] msg) {
            super(context, R.layout.list_item, msg);
            emptyList = true;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                itemView = inflater.inflate(R.layout.list_item, parent, false);
            }

            /*if (!emptyList) {
                Card card = (Card) getItem(position);

                TextView textView = itemView.findViewById(R.id.list_item_text);
                textView.setText(position+1 +" - " + card.getTitle());

                CheckBox checkBox = itemView.findViewById(R.id.cb_list_item);
                checkBox.setChecked(card.isMarker());

            } else {
                if(card.isPinnedCard())
                    itemView.setBackgroundColor(getContext().getColor(R.color.purple_200));

                String msg = (String) getItem(position);

                TextView textView = itemView.findViewById(R.id.list_item_text);
                textView.setText(msg);
                lvCards.setEnabled(false);

                CheckBox checkBox = itemView.findViewById(R.id.cb_list_item);
                checkBox.setVisibility(View.INVISIBLE);
            }*/

            return itemView;
        }
    }
}