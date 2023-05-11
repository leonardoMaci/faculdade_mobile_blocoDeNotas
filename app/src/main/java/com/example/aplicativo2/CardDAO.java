package com.example.aplicativo2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CardDAO {

    public static void insert(Context context, Card card) {
        Connection conn = new Connection(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ContentValues valores = new ContentValues();
        valores.put("title", card.getTitle() );
        valores.put("description", card.getDescription() );
        valores.put("pinnedCard", card.isPinnedCard() );
        valores.put("marker", card.isMarker() );
        valores.put("timeStamp", sdf.format(Calendar.getInstance().getTime()));

        db.insert("cards", null, valores);
    }

    public static void edit(Context context, Card card){
        Connection conn = new Connection(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("title", card.getTitle() );
        valores.put("description", card.getDescription() );
        valores.put("pinnedCard", card.isPinnedCard() );
        valores.put("marker", card.isMarker() );
        //valores.put("timeStamp", card.getTimestamp());

        db.update("cards", valores ,
                " id = " + card.getId(), null  );
    }

    public static void delete(Context context, int idCard){
        SQLiteDatabase db = new Connection(context).getWritableDatabase();

        db.delete("cards",
                " id = " + idCard, null  );
    }

    public static List<Card> getCards(Context context){
        Connection conn = new Connection(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        List<Card> lista = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT id, title, description, pinnedCard, marker, timeStamp FROM cards ORDER BY id and pinnedCard desc",
                null);
        if( cursor != null && cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                Card card = mappingData(cursor);
                lista.add( card );
            }while ( cursor.moveToNext() );
        }
        return lista;
    }

    public static Card getCardById(Context context, int idAluno){
        SQLiteDatabase db = new Connection(context).getReadableDatabase();
        Card card = null;
        Cursor cursor = db.rawQuery("SELECT id, title, description, pinnedCard, marker FROM cards " +
                " WHERE id = " + idAluno, null);
        if( cursor != null && cursor.getCount() > 0 ){
            cursor.moveToFirst();
            card = new Card();
            card.setId( cursor.getInt( 0 )  );
            card.setTitle( cursor.getString( 1 )  );
            card.setDescription( cursor.getString( 2 )  );
            card.setPinnedCard( cursor.getInt( 3 ) == 1 );
            card.setMarker( cursor.getInt( 4 ) == 1 );
        }
        return card;
    }

    private static Card mappingData(Cursor cursor){

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String StringDate = cursor.getString( 5);
        Date date;

        try {
            java.util.Date parseDate = formatDate.parse(StringDate);
            date = new Date(parseDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Card card = new Card();
        card.setId( cursor.getInt( 0 )  );
        card.setTitle( cursor.getString( 1 )  );
        card.setDescription( cursor.getString( 2 )  );
        card.setPinnedCard( cursor.getInt( 3 ) == 1 );
        card.setMarker( cursor.getInt( 4 ) == 1 );
        card.setTimestamp(date);

        return card;
    }
}
