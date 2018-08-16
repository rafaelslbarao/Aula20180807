package br.com.datamob.controledeuniversidade.database.view_entity;

public class UniversidadeCidadeEntity
{
    private Long codigo;
    private String nome_universidade;
    private Long cidade;
    private String nome_cidade;
    private String estado;

    public UniversidadeCidadeEntity()
    {
    }

    public UniversidadeCidadeEntity(Long codigo, String nome_universidade, Long cidade, String nome_cidade, String estado)
    {
        this.codigo = codigo;
        this.nome_universidade = nome_universidade;
        this.cidade = cidade;
        this.nome_cidade = nome_cidade;
        this.estado = estado;
    }

    public Long getCodigo()
    {
        return codigo;
    }

    public void setCodigo(Long codigo)
    {
        this.codigo = codigo;
    }

    public String getNome_universidade()
    {
        return nome_universidade;
    }

    public void setNome_universidade(String nome_universidade)
    {
        this.nome_universidade = nome_universidade;
    }

    public Long getCidade()
    {
        return cidade;
    }

    public void setCidade(Long cidade)
    {
        this.cidade = cidade;
    }

    public String getNome_cidade()
    {
        return nome_cidade;
    }

    public void setNome_cidade(String nome_cidade)
    {
        this.nome_cidade = nome_cidade;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }
}
