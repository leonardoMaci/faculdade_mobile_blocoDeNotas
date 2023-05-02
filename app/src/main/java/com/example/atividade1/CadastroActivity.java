package com.example.atividade1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class CadastroActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private CheckBox cbPinnedCard, cbMarker;
    private Button btnSave, btnCancel;

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        cbPinnedCard = findViewById(R.id.cbPinnedCard);
        cbMarker = findViewById(R.id.cbMarker);
    btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        Card itemSelected = (Card) getIntent().getSerializableExtra("edit");

        if (itemSelected != null) {
            btnSave.setText("Update");
            etTitle.setText(itemSelected.getTitle());
            etDescription.setText(itemSelected.getDescription());
            cbPinnedCard.setChecked(itemSelected.isPinnedCard());
            cbMarker.setChecked(itemSelected.isMarker());
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemSelected == null)
                    salvar();
                else
                    update(itemSelected);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CadastroActivity.this, Activity_trash.class);
                i.putExtra("action", "cancel");
                startActivity(i);
            }
        });
    }

    private void salvar() {

        String title = etTitle.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this,
                    "O campo title deve ser preenchido!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            card = new Card();
            card.setTitle(etTitle.getText().toString());
            card.setDescription(etDescription.getText().toString());
            card.setPinnedCard(cbPinnedCard.isChecked());
            card.setMarker(cbMarker.isChecked());
            CardDAO.insert(this, card);

            etTitle.setText("");
            etDescription.setText("");
            cbPinnedCard.setChecked(false);
            cbMarker.setChecked(false);
            card = null;

            Intent i = new Intent(CadastroActivity.this, Activity_trash.class);
            i.putExtra("action", "cancel");
            startActivity(i);
        }
    }

    private void update(Card card){
        String title = etTitle.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this,
                    "O campo title deve ser preenchido!",
                    Toast.LENGTH_LONG
            ).show();
        } else {
            card.setTitle(etTitle.getText().toString());
            card.setDescription(etDescription.getText().toString());
            card.setPinnedCard(cbPinnedCard.isChecked());
            card.setMarker(cbMarker.isChecked());

            CardDAO.edit(this, card);

            etTitle.setText("");
            etDescription.setText("");
            cbPinnedCard.setChecked(false);
            cbMarker.setChecked(false);
            card = null;

            Intent i = new Intent(CadastroActivity.this, Activity_trash.class);
            i.putExtra("action", "cancel");
            startActivity(i);
        }
    }
}