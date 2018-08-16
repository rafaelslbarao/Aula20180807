package br.com.datamob.controledeuniversidade;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.datamob.controledeuniversidade.database.view_dao.UniversidadeCidadeDao;
import br.com.datamob.controledeuniversidade.database.view_entity.UniversidadeCidadeEntity;

public class ListaDeUniversidades extends AppCompatActivity
{
    private ListView lvInformacoes;
    private FloatingActionButton fabAdicionar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_universidades);
        inicializaComponentes();
    }

    private void inicializaComponentes()
    {
        lvInformacoes = findViewById(R.id.lvInformacoes);
        fabAdicionar = findViewById(R.id.fabAdicionar);
        //
        fabAdicionar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ListaDeUniversidades.this, CadastroDeUniversidade.class));
            }
        });
        //
        lvInformacoes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                UniversidadeCidadeEntity item = (UniversidadeCidadeEntity) lvInformacoes.getAdapter().getItem(position);
                Intent intent = new Intent(ListaDeUniversidades.this, CadastroDeUniversidade.class);
                intent.putExtra(CadastroDeUniversidade.EXTRA_CODIGO, item.getCodigo());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        carregaLista();
    }

    private void carregaLista()
    {
        lvInformacoes.setAdapter(new ListaDeUniversidadesAdapter(this, new UniversidadeCidadeDao(this).selectAll()));
    }
}
