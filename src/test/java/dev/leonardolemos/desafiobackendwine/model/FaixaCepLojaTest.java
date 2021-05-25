package dev.leonardolemos.desafiobackendwine.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaixaCepLojaTest {

    @Test
    public void test_valid_inicio_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", 10000000L, 20000000L);

        boolean result = faixaCepLoja.isFaixaInicioValid();

        assertTrue(result);
    }

    @Test
    public void test_invalid_inicio_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", 1000L, 20000000L);

        boolean result = faixaCepLoja.isFaixaInicioValid();

        assertFalse(result);
    }

    @Test
    public void test_valid_fim_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", 10000000L, 20000000L);

        boolean result = faixaCepLoja.isFaixaFimValid();

        assertTrue(result);
    }

    @Test
    public void test_invalid_fim_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", 10000000L, 2000L);

        boolean result = faixaCepLoja.isFaixaFimValid();

        assertFalse(result);
    }

    @Test
    public void test_null_inicio_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", null, 20000000L);

        boolean result = faixaCepLoja.isFaixaInicioValid();

        assertFalse(result);
    }

    @Test
    public void test_null_fim_faixa() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", 10000000L, null);

        boolean result = faixaCepLoja.isFaixaFimValid();

        assertFalse(result);
    }
}
