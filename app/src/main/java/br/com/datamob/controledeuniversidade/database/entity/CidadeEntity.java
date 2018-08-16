package br.com.datamob.controledeuniversidade.database.entity;

public class CidadeEntity
{
    private Long codigo;
    private String nome;
    private String estado;

    public CidadeEntity()
    {
    }

    public CidadeEntity(Long codigo, String nome, String estado)
    {
        this.codigo = codigo;
        this.nome = nome;
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

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getEstado()
    {
        return estado;
    }

    public void setEstado(String estado)
    {
        this.estado = estado;
    }

    @Override
    public String toString()
    {
        return nome.toString() + " - " + estado.toString();
    }
}
