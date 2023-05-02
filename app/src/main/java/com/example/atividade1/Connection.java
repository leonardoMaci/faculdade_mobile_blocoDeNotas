package com.example.atividade1;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Connection extends SQLiteOpenHelper {

    private static final String NOME_BD = "bd-appCard";

    private static final int VERSAO_BD = 2;

    public Connection(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cards ( " +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                " title TEXT NOT NULL ," +
                " description TEXT NOT NULL ," +
                " marker BOOL NOT NULL DEFAULT 0 ," +
                " pinnedCard BOOL NOT NULL DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE cards ADD COLUMN  timeStamp DATETIME DEFAULT CURRENT_TIMESTAMP");
        }
    }
}
