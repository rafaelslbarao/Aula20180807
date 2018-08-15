package br.com.datamob.controledeuniversidade;

import android.app.Activity;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import br.com.datamob.controledeuniversidade.database.dao.UniversidadeDao;
import br.com.datamob.controledeuniversidade.database.entity.UniversidadeEntity;
import br.com.datamob.controledeuniversidade.dialogs.PopupInformacao;

public class CadastroDeUniversidade extends AppCompatActivity
{
    public static final String EXTRA_CODIGO = "br.com.datamob.controledeuniversidade.codigo";
    //
    private TextInputLayout tilCodigo;
    private TextInputLayout tilNome;
    private TextInputLayout tilCidade;
    private TextInputEditText etCodigo;
    private TextInputEditText etNome;
    private TextInputEditText etCidade;
    //
    private UniversidadeEntity universidadeEntity;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_universidade);
        Long codigo = getIntent().getLongExtra(EXTRA_CODIGO, -1);
        universidadeEntity = new UniversidadeDao(this).selectByCodigo(codigo.toString());
        ininicializaComponentes();
        if (universidadeEntity != null)
            carregaValores();
    }

    private void ininicializaComponentes()
    {
        tilCodigo = findViewById(R.id.tilCodigo);
        tilNome = findViewById(R.id.tilNome);
        tilCidade = findViewById(R.id.tilCidade);
        etCodigo = findViewById(R.id.etCodigo);
        etNome = findViewById(R.id.etNome);
        etCidade = findViewById(R.id.etCidade);
        FloatingActionButton fabConfirmar = findViewById(R.id.fabConfirmar);
        FloatingActionButton fabDeletar = findViewById(R.id.fabDeletar);
        //
        etCodigo.addTextChangedListener(new TextWatcher()
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
                tilCodigo.setError(null);
            }
        });
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
        etCidade.addTextChangedListener(new TextWatcher()
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
                tilCidade.setError(null);
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
        if (etCodigo.getText().toString().trim().length() == 0)
        {
            tilCodigo.setError("Informe o código da universidade");
            retorno = false;
        }
        //
        if (etNome.getText().toString().trim().length() == 0)
        {
            tilNome.setError("Informe o nome da universidade");
            retorno = false;
        }
        //
        if (etCidade.getText().toString().trim().length() == 0)
        {
            tilCidade.setError("Informe a cidade da universidade");
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
        universidadeEntity.setCodigo(Long.valueOf(etCodigo.getText().toString()));
        universidadeEntity.setCidade(etCidade.getText().toString().trim());
        universidadeEntity.setNome(etNome.getText().toString().trim());
    }

    private void fechaTelaSucesso()
    {
        setResult(Activity.RESULT_OK);
        finish();
    }

    private void carregaValores()
    {
        etCodigo.setText(universidadeEntity.getCodigo().toString());
        etCidade.setText(universidadeEntity.getCidade());
        etNome.setText(universidadeEntity.getNome());
    }
}
