package br.com.datamob.controledeuniversidade;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
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

import br.com.datamob.controledeuniversidade.database.dao.CidadeDao;
import br.com.datamob.controledeuniversidade.database.entity.CidadeEntity;
import br.com.datamob.controledeuniversidade.dialogs.PopupInformacao;

public class CadastroDeCidade extends AppCompatActivity
{
    private TextView tvCodigo;
    private TextInputLayout tilNome;
    private TextInputEditText etNome;
    private Spinner spEstado;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_cidade);
        ininicializaComponentes();
        carregaEstados();
    }

    private void ininicializaComponentes()
    {
        tvCodigo = findViewById(R.id.tvCodigo);
        tilNome = findViewById(R.id.tilNome);
        etNome = findViewById(R.id.etNome);
        spEstado = findViewById(R.id.spEstado);
        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
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
        tvCodigo.setText(new CidadeDao(this).getProximoCodigo().toString());
    }

    private void carregaEstados()
    {
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);
    }

    private void confirmaTela()
    {
        if (!validaTela())
            return;

        salvaRegistroFechaTela();
    }

    private void salvaRegistroFechaTela()
    {
        CidadeEntity entity = new CidadeEntity();
        preencheValores(entity);
        try
        {
            if (new CidadeDao(this).insert(entity))
                fechaTelaSucesso();
            else
                PopupInformacao.mostraMensagem(this, "Erro ao inserir");
        }
        catch (SQLiteConstraintException ex)
        {
            PopupInformacao.mostraMensagem(this, "Código já existe");
        }
    }

    private void preencheValores(CidadeEntity entity)
    {
        entity.setCodigo(Long.valueOf(tvCodigo.getText().toString()));
        entity.setNome(etNome.getText().toString().trim());
        entity.setEstado(spEstado.getSelectedItem().toString());
    }

    private boolean validaTela()
    {
        boolean retorno = true;
        //
        if (etNome.getText().toString().trim().length() == 0)
        {
            tilNome.setError("Informe o nome da cidade");
            retorno = false;
        }
        //
        if (spEstado.getSelectedItemPosition() <= 0)
        {
            PopupInformacao.mostraMensagem(this, "Selecione o estado");
            retorno = false;
        }
        //
        return retorno;
    }

    private void fechaTelaSucesso()
    {
        finish();
    }
}
