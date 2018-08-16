package br.com.datamob.controledeuniversidade.database.view_dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.datamob.controledeuniversidade.database.Database;
import br.com.datamob.controledeuniversidade.database.view_entity.UniversidadeCidadeEntity;

public class UniversidadeCidadeDao
{
    public static final String[] COLUMNS_NAMES = new String[]{"codigo", "nome_universidade", "cidade", "nome_cidade", "estado"};

    private SQLiteDatabase db;

    public UniversidadeCidadeDao(Context context)
    {
        this.db = Database.getInstance(context);
    }


    public List<UniversidadeCidadeEntity> selectAll()
    {
        ArrayList<UniversidadeCidadeEntity> retorno = new ArrayList<>();

        Cursor cursor = db.rawQuery(
                " select u.codigo, u.nome as nome_universidade, u.cidade, c.nome as nome_cidade, c.estado" +
                        " from universidade u, cidade c" +
                        " where u.cidade = c.codigo" +
                        " order by upper(u.nome), upper(c.nome), upper(c.estado)", null);

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

    private UniversidadeCidadeEntity getEntity(Cursor cursor)
    {
        UniversidadeCidadeEntity entity = null;
        entity = new UniversidadeCidadeEntity();
        entity.setCodigo(cursor.getLong(0));
        entity.setNome_universidade(cursor.getString(1));
        entity.setCidade(cursor.getLong(2));
        entity.setNome_cidade(cursor.getString(3));
        entity.setEstado(cursor.getString(4));
        return entity;
    }

}
