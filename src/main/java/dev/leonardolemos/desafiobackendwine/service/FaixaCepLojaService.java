package dev.leonardolemos.desafiobackendwine.service;

import dev.leonardolemos.desafiobackendwine.exception.FaixaCepNotFoundException;
import dev.leonardolemos.desafiobackendwine.exception.FaixaCepValidationException;
import dev.leonardolemos.desafiobackendwine.exception.LojaNotFoundException;
import dev.leonardolemos.desafiobackendwine.model.FaixaCepLoja;
import dev.leonardolemos.desafiobackendwine.model.dto.LojaResponseDTO;
import dev.leonardolemos.desafiobackendwine.repository.FaixaCepLojaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaixaCepLojaService {

    private final FaixaCepLojaRepository faixaCepLojaRepository;

    public FaixaCepLojaService(FaixaCepLojaRepository faixaCepLojaRepository) {
        this.faixaCepLojaRepository = faixaCepLojaRepository;
    }

    @Transactional
    public FaixaCepLoja save(FaixaCepLoja request) {
        validateFaixa(request);

        return faixaCepLojaRepository.save(request);
    }

    @Transactional
    public void delete(Long id) {
        validateDelete(id);

        faixaCepLojaRepository.deleteById(id);
    }

    public LojaResponseDTO findNearbyLojaByCep(Long cep) {
        LojaResponseDTO lojaResponse = faixaCepLojaRepository.findCodigoLojaByCep(cep);

        validateLojaResponse(lojaResponse, cep);

        return lojaResponse;
    }

    private void validateLojaResponse(LojaResponseDTO lojaResponse, Long cep) {
        if (lojaResponse == null) {
            throw new LojaNotFoundException("Não foi encontrada uma loja próxima ao CEP [" + cep + "].");
        }
    }

    private void validateFaixa(FaixaCepLoja request) {
        if (request.getInicioFaixaCep().compareTo(request.getFimFaixaCep()) > 0) {
            throw new FaixaCepValidationException("O Início da Faixa de CEP precisa ser maior do que o Fim desta.");
        }

        boolean isFaixaAlreadySaved = false;

        if (request.getId() == null) {
            isFaixaAlreadySaved = faixaCepLojaRepository.faixaAlreadyExists(request.getInicioFaixaCep(), request.getFimFaixaCep());
        } else {
            FaixaCepLoja faixaCepLoja = faixaCepLojaRepository.findById(request.getId()).orElse(null);

            if (faixaCepLoja != null) {
                isFaixaAlreadySaved = faixaCepLojaRepository.faixaAlreadyExistsAndIsNotItself(faixaCepLoja.getId(), request.getInicioFaixaCep(), request.getFimFaixaCep());
            } else {
                throw new FaixaCepNotFoundException("O programa não pôde atualizar a faixa de CEP para a loja de código [" + request.getCodigoLoja() + "], a faixa não está cadastrada.");
            }
        }

        if (isFaixaAlreadySaved) {
            throw new FaixaCepValidationException("O programa não pôde salvar a faixa de CEP para a loja de código [" + request.getCodigoLoja() + "], a faixa já está cadastrada para outra loja.");
        }
    }

    public void validateDelete(Long id) {
        faixaCepLojaRepository
                .findById(id)
                .orElseThrow(() -> new FaixaCepNotFoundException("Não foi encontrada uma Faixa de CEP com o ID [" + id + "]."));
    }

}
