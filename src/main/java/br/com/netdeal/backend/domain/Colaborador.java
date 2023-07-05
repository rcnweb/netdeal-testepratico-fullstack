package br.com.netdeal.backend.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Colaborador implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String senha;

    private String complexidadeSenha;

    private Integer scoreSenha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getComplexidadeSenha() {
        return complexidadeSenha;
    }

    public void setComplexidadeSenha(String complexidadeSenha) {
        this.complexidadeSenha = complexidadeSenha;
    }

    public Integer getScoreSenha() {
        return scoreSenha;
    }

    public void setScoreSenha(Integer scoreSenha) {
        this.scoreSenha = scoreSenha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Colaborador)) return false;
        Colaborador that = (Colaborador) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
