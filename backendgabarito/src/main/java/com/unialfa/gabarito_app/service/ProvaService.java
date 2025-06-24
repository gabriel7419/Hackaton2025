package com.unialfa.gabarito_app.service;


import com.unialfa.gabarito_app.entity.Prova;
import com.unialfa.gabarito_app.entity.Resultado;
import com.unialfa.gabarito_app.entity.User;
import com.unialfa.gabarito_app.repository.ProvaRepository;
import com.unialfa.gabarito_app.repository.ResultadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@Service
public class ProvaService {

    @Autowired
    private ProvaRepository provaRepository;

    @Autowired
    private ResultadoRepository resultadoRepository;

    public Prova save(Prova prova) {
        return provaRepository.save(prova);
    }

    public List<Prova> findAll() {
        return provaRepository.findAll();
    }

    public Optional<Prova> findById(Long id) {
        return provaRepository.findById(id);
    }

    public List<Prova> findByProfessor(User professor) {
        return provaRepository.findByProfessor(professor);
    }

    public void delete(Long id) {
        provaRepository.deleteById(id);
    }

    public Map<String, Object> getEstatisticas(Long provaId) {
        Optional<Prova> prova = provaRepository.findById(provaId);
        if (prova.isEmpty()) {
            throw new RuntimeException("Prova não encontrada");
        }

        Map<String, Object> stats = new HashMap<>();
        List<Resultado> resultados = resultadoRepository.findByProva(prova.get());

        stats.put("totalAlunos", resultados.size());
        Double media = resultadoRepository.findMediaByProva(prova.get());
        stats.put("media", media != null ? media : 0.0);
        stats.put("aprovados", resultadoRepository.countByProvaAndNotaGreaterThanEqual(prova.get(), 6.0));
        stats.put("reprovados", resultados.size() - resultadoRepository.countByProvaAndNotaGreaterThanEqual(prova.get(), 6.0));

        return stats;
    }
}