package br.com.datamob.controledeuniversidade.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "bancoDeDados";
    private static SQLiteDatabase instace;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getInstace(Context context)
    {
        if(instace == null)
            instace = new Database(context, DATABASE_NAME, null, 1).getWritableDatabase();

        return  instace;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table Universidade " +
                "(codigo INTEGER, " +
                "nome TEXT, " +
                "cidade TEXT" +
                ", primary key (codigo))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
