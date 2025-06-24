package com.unialfa.gabarito_app.service;

import com.unialfa.gabarito_app.entity.Resultado;
import com.unialfa.gabarito_app.entity.User;
import com.unialfa.gabarito_app.repository.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ResultadoService {

    @Autowired
    private ResultadoRepository resultadoRepository;

    public Resultado save(Resultado resultado) {
        return resultadoRepository.save(resultado);
    }

    public List<Resultado> findAll() {
        return resultadoRepository.findAll();
    }

    public Optional<Resultado> findById(Long id) {
        return resultadoRepository.findById(id);
    }

    public List<Resultado> findByAluno(User aluno) {
        return resultadoRepository.findByAluno(aluno);
    }

    public void delete(Long id) {
        resultadoRepository.deleteById(id);
    }
}