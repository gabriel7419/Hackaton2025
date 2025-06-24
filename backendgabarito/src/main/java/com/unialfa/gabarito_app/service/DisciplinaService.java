package com.unialfa.gabarito_app.service;


import com.unialfa.gabarito_app.entity.Disciplina;
import com.unialfa.gabarito_app.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    public Disciplina save(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }

    public Optional<Disciplina> findById(Long id) {
        return disciplinaRepository.findById(id);
    }

    public Optional<Disciplina> findByCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo);
    }

    public boolean existsByCodigo(String codigo) {
        return disciplinaRepository.existsByCodigo(codigo);
    }

    public void delete(Long id) {
        disciplinaRepository.deleteById(id);
    }
}