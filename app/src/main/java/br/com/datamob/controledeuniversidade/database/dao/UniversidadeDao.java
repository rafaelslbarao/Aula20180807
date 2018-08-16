package br.com.datamob.controledeuniversidade.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.datamob.controledeuniversidade.database.Database;
import br.com.datamob.controledeuniversidade.database.entity.UniversidadeEntity;

public class UniversidadeDao
{
    public static final String TABLE_NAME = "Universidade";
    public static final String[] COLUMNS_NAMES = new String[]{"codigo", "nome", "cidade"};

    private SQLiteDatabase db;

    public UniversidadeDao(Context context)
    {
        this.db = Database.getInstance(context);
    }

    public boolean insert(UniversidadeEntity... entities)
    {
        if (entities != null)
        {
            for (UniversidadeEntity entity : entities)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMNS_NAMES[0], entity.getCodigo());
                contentValues.put(COLUMNS_NAMES[1], entity.getNome());
                contentValues.put(COLUMNS_NAMES[2], entity.getCidade());
                db.insertOrThrow(TABLE_NAME, null, contentValues);
            }
        }

        return true;
    }

    public List<UniversidadeEntity> selectAll()
    {
        ArrayList<UniversidadeEntity> retorno = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, COLUMNS_NAMES, null, null, null, null,
                "upper(nome), upper(cidade)");

        if (cursor.moveToFirst())
        {
            do
            {
                retorno.add(getEntity(cursor));
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return retorno;
    }

    public UniversidadeEntity selectByCodigo(String codigo)
    {
        ArrayList<UniversidadeEntity> retorno = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, COLUMNS_NAMES, "codigo = ?", new String[]{codigo}, null, null, null);

        if (cursor.moveToFirst())
        {
            return getEntity(cursor);
        }
        cursor.close();
        return null;
    }

    private UniversidadeEntity getEntity(Cursor cursor)
    {
        UniversidadeEntity entity = null;
        entity = new UniversidadeEntity();
        entity.setCodigo(cursor.getLong(0));
        entity.setNome(cursor.getString(1));
        entity.setCidade(cursor.getLong(2));
        return entity;
    }

    public int delete(String codigo)
    {
        return db.delete(TABLE_NAME, "codigo = ?", new String[]{codigo});
    }

    public int update(UniversidadeEntity entity)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMNS_NAMES[0], entity.getCodigo());
        contentValues.put(COLUMNS_NAMES[1], entity.getNome());
        contentValues.put(COLUMNS_NAMES[2], entity.getCidade());
        //
        return db.update(TABLE_NAME, contentValues, "codigo = ?"
                , new String[]{entity.getCodigo().toString()});
    }

    public Long getProximoCodigo()
    {
        Long retorno = null;
        Cursor cursor = db.rawQuery("select ifnull(max(codigo), 0) + 1 from " + TABLE_NAME, null);
        if (cursor.moveToFirst())
        {
            retorno = cursor.getLong(0);
        }
        cursor.close();
        return retorno;
    }
}
