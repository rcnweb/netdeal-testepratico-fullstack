package br.com.netdeal.backend.services.impl;

import br.com.netdeal.backend.domain.Colaborador;
import br.com.netdeal.backend.domain.Password;
import br.com.netdeal.backend.repositories.ColaboradorRepository;
import br.com.netdeal.backend.services.ColaboradorService;
import br.com.netdeal.backend.services.exception.DataIntegrityException;
import br.com.netdeal.backend.services.exception.ObjectNotFoundException;
import br.com.netdeal.backend.services.exception.ServiceException;
import br.com.netdeal.backend.services.util.SenhaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public List<Colaborador> findAll(){
        return colaboradorRepository.findAll();
    }

    public Colaborador find(Integer id){
        Optional<Colaborador> obj = colaboradorRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! id: " + id + ", Tipo: " + Colaborador.class.getName()));
    }

    public Colaborador insert(Colaborador obj) {
        obj.setId(null);
        gerarScoreEComplexidadeSenha(obj);
        gerarSenha(obj);
        return colaboradorRepository.save(obj);
    }

    public Colaborador update(Colaborador obj) {
        find(obj.getId());
        gerarScoreEComplexidadeSenha(obj);
        gerarSenha(obj);
        return colaboradorRepository.save(obj);
    }

    private void gerarSenha(Colaborador colaborador){
        try{
            colaborador.setSenha(SenhaUtil.md5(colaborador.getSenha()));
        }catch (NoSuchAlgorithmException ex){
            throw new ServiceException("Erro na conversão da senha : "+ colaborador.getNome());
        }
    }

    private  void gerarScoreEComplexidadeSenha(Colaborador colaborador){
        Password password = new Password(colaborador.getSenha());
        colaborador.setScoreSenha(password.getScore());
        colaborador.setComplexidadeSenha(password.getComplexity());
    }

}