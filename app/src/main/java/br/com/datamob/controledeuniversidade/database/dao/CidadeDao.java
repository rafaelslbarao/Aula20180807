package br.com.datamob.controledeuniversidade.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.datamob.controledeuniversidade.database.Database;
import br.com.datamob.controledeuniversidade.database.entity.CidadeEntity;

public class CidadeDao
{
    public static final String TABLE_NAME = "Cidade";
    public static final String[] COLUMNS_NAMES = new String[]{"codigo", "nome", "estado"};

    private SQLiteDatabase db;

    public CidadeDao(Context context)
    {
        this.db = Database.getInstance(context);
    }

    public boolean insert(CidadeEntity... entities)
    {
        if (entities != null)
        {
            for (CidadeEntity entity : entities)
            {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMNS_NAMES[0], entity.getCodigo());
                contentValues.put(COLUMNS_NAMES[1], entity.getNome());
                contentValues.put(COLUMNS_NAMES[2], entity.getEstado());
                db.insertOrThrow(TABLE_NAME, null, contentValues);
            }
        }

        return true;
    }

    public List<CidadeEntity> selectAll()
    {
        ArrayList<CidadeEntity> retorno = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME, COLUMNS_NAMES, null, null, null, null,
                "upper(nome), upper(estado)");

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

    private CidadeEntity getEntity(Cursor cursor)
    {
        CidadeEntity entity = null;
        entity = new CidadeEntity();
        entity.setCodigo(cursor.getLong(0));
        entity.setNome(cursor.getString(1));
        entity.setEstado(cursor.getString(2));
        return entity;
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
