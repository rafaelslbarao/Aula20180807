package br.com.datamob.controledeuniversidade.database.entity;

public class UniversidadeEntity
{
    private Long codigo;
    private String nome;
    private String cidade;

    public UniversidadeEntity()
    {
    }

    public UniversidadeEntity(Long codigo, String nome, String cidade)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.cidade = cidade;
    }

    public Long getCodigo()
    {
        return codigo;
    }

    public void setCodigo(Long codigo)
    {
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getCidade()
    {
        return cidade;
    }

    public void setCidade(String cidade)
    {
        this.cidade = cidade;
    }
}
