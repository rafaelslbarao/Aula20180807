package br.com.datamob.controledeuniversidade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.datamob.controledeuniversidade.database.entity.UniversidadeEntity;

public class ListaDeUniversidadesAdapter extends ArrayAdapter<UniversidadeEntity>
{
    private LayoutInflater inflater;

    public ListaDeUniversidadesAdapter(@NonNull Context context, @NonNull List<UniversidadeEntity> objects)
    {
        super(context, R.layout.item_lista_universidades, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Holder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_lista_universidades, null);
            holder = new Holder();
            convertView.setTag(holder);
            holder.tvCodigo = convertView.findViewById(R.id.tvCodigo);
            holder.tvNome = convertView.findViewById(R.id.tvNome);
            holder.tvCidade = convertView.findViewById(R.id.tvCidade);
        }
        else
            holder = (Holder) convertView.getTag();
        //
        UniversidadeEntity item = getItem(position);
        holder.tvCodigo.setText(item.getCodigo().toString());
        holder.tvNome.setText(item.getNome());
        holder.tvCidade.setText(item.getCidade());
        //
        return convertView;
    }

    private class Holder
    {
        public TextView tvCodigo;
        public TextView tvNome;
        public TextView tvCidade;
    }
}
