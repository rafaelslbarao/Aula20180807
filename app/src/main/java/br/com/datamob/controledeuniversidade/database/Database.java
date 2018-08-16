package br.com.datamob.controledeuniversidade.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "bancoDeDados";
    private static SQLiteDatabase instance;

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getInstance(Context context)
    {
        if(instance == null)
            instance = new Database(context, DATABASE_NAME, null, 1).getWritableDatabase();

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table Cidade " +
                "(codigo INTEGER " +
                ", nome TEXT not null" +
                ", estado TEXT not null" +
                ", primary key (codigo))");

        db.execSQL("create table Universidade " +
                "(codigo INTEGER " +
                ", nome TEXT not null" +
                ", cidade INTEGER" +
                ", primary key (codigo)" +
                ", foreign key (cidade) references Cidade(codigo))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
