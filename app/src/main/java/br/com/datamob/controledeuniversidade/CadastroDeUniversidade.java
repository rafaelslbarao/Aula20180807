package br.com.datamob.controledeuniversidade;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import br.com.datamob.controledeuniversidade.database.dao.CidadeDao;
import br.com.datamob.controledeuniversidade.database.dao.UniversidadeDao;
import br.com.datamob.controledeuniversidade.database.entity.CidadeEntity;
import br.com.datamob.controledeuniversidade.database.entity.UniversidadeEntity;
import br.com.datamob.controledeuniversidade.dialogs.PopupInformacao;

public class CadastroDeUniversidade extends AppCompatActivity
{
    public static final String EXTRA_CODIGO = "br.com.datamob.controledeuniversidade.codigo";
    private static final int CADASTRO_CIDADE = 1;
    //
    private TextView tvCodigo;
    private TextInputLayout tilNome;
    private TextInputEditText etNome;
    private Spinner spCidade;
    //
    private UniversidadeEntity universidadeEntity;
    private List<CidadeEntity> cidades;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_universidade);
        Long codigo = getIntent().getLongExtra(EXTRA_CODIGO, -1);
        universidadeEntity = new UniversidadeDao(this).selectByCodigo(codigo.toString());
        ininicializaComponentes();
        carregaCidades();
        if (universidadeEntity != null)
            carregaValores();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CADASTRO_CIDADE:
                carregaCidades();
                break;
        }
    }

    private void ininicializaComponentes()
    {
        tvCodigo = findViewById(R.id.tvCodigo);
        tilNome = findViewById(R.id.tilNome);
        etNome = findViewById(R.id.etNome);
        spCidade = findViewById(R.id.spCidade);
        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        FloatingActionButton fabDeletar = findViewById(R.id.fabDeletar);
        FloatingActionButton fabAdicionarCidade = findViewById(R.id.fabAdicionarCidade);
        //
        etNome.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                tilNome.setError(null);
            }
        });
        //
        fabConfirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                confirmaTela();
            }
        });
        //
        fabAdicionarCidade.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(CadastroDeUniversidade.this, CadastroDeCidade.class), CADASTRO_CIDADE);
            }
        });
        //
        if (universidadeEntity == null)
            fabDeletar.setEnabled(false);
        else
        {
            fabDeletar.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    deleteRegistroFechaTela();
                }
            });
        }
        //
        tvCodigo.setText(new UniversidadeDao(this).getProximoCodigo().toString());
    }

    private void carregaCidades()
    {
        List<CidadeEntity> cidadeEntities = new CidadeDao(this).selectAll();
        cidadeEntities.add(0, new CidadeEntity(0l, "Cidade", "Estado"));
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cidadeEntities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCidade.setAdapter(adapter);
    }

    private void confirmaTela()
    {
        if (!validaTela())
            return;

        salvaRegistroFechaTela();
    }

    private boolean validaTela()
    {
        boolean retorno = true;
        //
        if (etNome.getText().toString().trim().length() == 0)
        {
            tilNome.setError("Informe o nome da universidade");
            retorno = false;
        }
        //
        if (spCidade.getSelectedItemPosition() <= 0)
        {
            PopupInformacao.mostraMensagem(this, "Selecione a cidade e estado");
            retorno = false;
        }
        //
        return retorno;
    }

    private void salvaRegistroFechaTela()
    {
        if (universidadeEntity == null)
        {
            UniversidadeEntity universidadeEntity = new UniversidadeEntity();
            preencheValores(universidadeEntity);
            try
            {
                if (new UniversidadeDao(this).insert(universidadeEntity))
                    fechaTelaSucesso();
                else
                    PopupInformacao.mostraMensagem(this, "Erro ao inserir");
            }
            catch (SQLiteConstraintException ex)
            {
                PopupInformacao.mostraMensagem(this, "Código já existe");
            }
        }
        else
        {
            preencheValores(universidadeEntity);
            if (new UniversidadeDao(this).update(universidadeEntity) > 0)
                fechaTelaSucesso();
            else
                PopupInformacao.mostraMensagem(this, "Erro ao inserir");
        }
    }

    private void deleteRegistroFechaTela()
    {
        if (new UniversidadeDao(this).delete(universidadeEntity.getCodigo().toString()) > 0)
            fechaTelaSucesso();
        else
            PopupInformacao.mostraMensagem(this, "Erro ao remover");
    }

    private void preencheValores(UniversidadeEntity universidadeEntity)
    {
        universidadeEntity.setCodigo(Long.valueOf(tvCodigo.getText().toString()));
        universidadeEntity.setCidade(((CidadeEntity) spCidade.getSelectedItem()).getCodigo());
        universidadeEntity.setNome(etNome.getText().toString().trim());
    }

    private void fechaTelaSucesso()
    {
        finish();
    }

    private void carregaValores()
    {
        tvCodigo.setText(universidadeEntity.getCodigo().toString());
        etNome.setText(universidadeEntity.getNome());
        for (int i = 1; i < spCidade.getCount(); i++)
        {
            if (((CidadeEntity) spCidade.getItemAtPosition(i)).getCodigo().equals(universidadeEntity.getCidade()))
            {
                spCidade.setSelection(i, true);
            }
        }
    }
}
