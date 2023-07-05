package br.com.netdeal.backend.services;

import br.com.netdeal.backend.domain.Colaborador;

import java.util.List;

public interface ColaboradorService {

    public List<Colaborador> findAll();
    public Colaborador find(Integer id);

    public Colaborador insert(Colaborador obj);

    public Colaborador update(Colaborador obj);
}
