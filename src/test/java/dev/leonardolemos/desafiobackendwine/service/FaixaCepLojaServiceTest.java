package dev.leonardolemos.desafiobackendwine.service;

import dev.leonardolemos.desafiobackendwine.exception.FaixaCepNotFoundException;
import dev.leonardolemos.desafiobackendwine.exception.FaixaCepValidationException;
import dev.leonardolemos.desafiobackendwine.exception.LojaNotFoundException;
import dev.leonardolemos.desafiobackendwine.model.FaixaCepLoja;
import dev.leonardolemos.desafiobackendwine.model.dto.LojaResponseDTO;
import dev.leonardolemos.desafiobackendwine.repository.FaixaCepLojaRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FaixaCepLojaServiceTest {

    @Mock
    FaixaCepLojaRepository faixaCepLojaRepository;

    @InjectMocks
    FaixaCepLojaService faixaCepLojaService;

    @Test(expected = FaixaCepValidationException.class)
    public void test_saving_with_invalid_faixa_range() {
        FaixaCepLoja faixaCepLojaWithInvalidRange = new FaixaCepLoja("LOJA_CAMPO_GRANDE", 20000000L, 10000000L);

        faixaCepLojaService.save(faixaCepLojaWithInvalidRange);
    }

    @Test(expected = FaixaCepValidationException.class)
    public void test_saving_new_faixa_that_already_exists() {
        when(faixaCepLojaRepository.faixaAlreadyExists(any(), any())).thenReturn(true);

        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_CAMPO_GRANDE", 10000000L, 20000000L);

        faixaCepLojaService.save(faixaCepLoja);
    }

    @Test(expected = FaixaCepValidationException.class)
    public void test_saving_new_faixa_that_already_exists_and_isnt_itself() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_CAMPO_GRANDE", 10000000L, 20000000L);
        faixaCepLoja.setId(1L);

        when(faixaCepLojaRepository.findById(any())).thenReturn(Optional.of(faixaCepLoja));
        when(faixaCepLojaRepository.faixaAlreadyExistsAndIsNotItself(any(), any(), any())).thenReturn(true);

        faixaCepLojaService.save(faixaCepLoja);
    }

    @Test
    public void test_saving_valid_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_CAMPO_GRANDE", 10000000L, 20000000L);
        faixaCepLoja.setId(1L);

        when(faixaCepLojaRepository.save(any(FaixaCepLoja.class))).thenReturn(faixaCepLoja);

        FaixaCepLoja savedFaixa = faixaCepLojaService.save(new FaixaCepLoja("LOJA_CAMPO_GRANDE", 10000000L, 20000000L));

        assertEquals(1L, savedFaixa.getId());
    }

    @Test(expected = FaixaCepNotFoundException.class)
    public void test_not_existing_faixa_deleting() {
        faixaCepLojaService.delete(1L);
    }

    @Test
    public void test_existing_faixa_deleting() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_CAMPO_GRANDE", 10000000L, 20000000L);
        faixaCepLoja.setId(1L);

        when(faixaCepLojaRepository.findById(any())).thenReturn(Optional.of(faixaCepLoja));

        faixaCepLojaService.delete(1L);
    }

    @Test(expected = LojaNotFoundException.class)
    public void test_nearby_loja_not_finding() {
        faixaCepLojaService.findNearbyLojaByCep(10000000L);
    }

    @Test
    public void test_nearby_loja_finding() {
        LojaResponseDTO lojaResponse = new LojaResponseDTO("LOJA_CAMPO_GRANDE");

        when(faixaCepLojaRepository.findCodigoLojaByCep(any())).thenReturn(lojaResponse);

        faixaCepLojaService.findNearbyLojaByCep(10000000L);
    }

}
