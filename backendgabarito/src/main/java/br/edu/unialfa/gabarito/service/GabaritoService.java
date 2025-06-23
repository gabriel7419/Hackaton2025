package br.edu.unialfa.gabarito.service;

import br.edu.unialfa.gabarito.model.RespostaAluno;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class GabaritoService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RespostaAluno corrigirProva(RespostaAluno respostaAluno) {
        try {
            List<String> gabaritoOficial = objectMapper.readValue(
                    respostaAluno.getProva().getGabaritoOficial(),
                    new TypeReference<List<String>>() {}
            );

            List<String> respostasAluno = objectMapper.readValue(
                    respostaAluno.getRespostas(),
                    new TypeReference<List<String>>() {}
            );

            int acertos = 0;
            int totalQuestoes = gabaritoOficial.size();

            for (int i = 0; i < totalQuestoes && i < respostasAluno.size(); i++) {
                if (gabaritoOficial.get(i).equalsIgnoreCase(respostasAluno.get(i))) {
                    acertos++;
                }
            }

            respostaAluno.setTotalAcertos(acertos);

            // Calcula a nota (0 a 10)
            BigDecimal nota = BigDecimal.valueOf(acertos)
                    .multiply(BigDecimal.valueOf(10))
                    .divide(BigDecimal.valueOf(totalQuestoes), 2, RoundingMode.HALF_UP);

            respostaAluno.setNota(nota);

            return respostaAluno;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao corrigir prova: " + e.getMessage());
        }
    }
}
