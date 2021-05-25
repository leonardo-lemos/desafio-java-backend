package dev.leonardolemos.desafiobackendwine.repository;

import dev.leonardolemos.desafiobackendwine.model.FaixaCepLoja;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FaixaCepLojaRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    FaixaCepLojaRepository faixaCepLojaRepository;

    @Test
    public void test_entity_saving() {
        FaixaCepLoja faixaCepLoja = setup_loja_sao_paulo();

        assertEquals(1L, faixaCepLoja.getId());
    }

    @Test
    public void test_if_faixa_already_exists() {
        setup_loja_sao_paulo();

        boolean faixaAlreadyExistsFirstCase = faixaCepLojaRepository.faixaAlreadyExists(10000000L, 20000000L);
        boolean faixaAlreadyExistsSecondCase = faixaCepLojaRepository.faixaAlreadyExists(10000001L, 20000000L);
        boolean faixaAlreadyExistsThirdCase = faixaCepLojaRepository.faixaAlreadyExists(10008000L, 20000001L);
        boolean faixaAlreadyExistsFourthCase = faixaCepLojaRepository.faixaAlreadyExists(10008000L, 10000900L);
        boolean faixaAlreadyExistsFifthCase = faixaCepLojaRepository.faixaAlreadyExists(20000001L, 30000000L);

        assertTrue(faixaAlreadyExistsFirstCase);
        assertTrue(faixaAlreadyExistsSecondCase);
        assertTrue(faixaAlreadyExistsThirdCase);
        assertTrue(faixaAlreadyExistsFourthCase);
        assertFalse(faixaAlreadyExistsFifthCase);
    }

    @Test
    public void test_if_faixa_exists_and_isnt_itself() {
        FaixaCepLoja faixaLojaSaoPaulo = setup_loja_sao_paulo();
        FaixaCepLoja faixaLojaVitoria = setup_loja_vitoria();

        assertNotNull(faixaLojaSaoPaulo.getId());
        assertNotNull(faixaLojaVitoria.getId());

        assertNotEquals(0, faixaLojaSaoPaulo.getId());
        assertNotEquals(0, faixaLojaVitoria.getId());

        boolean faixaExistsButIsItself = faixaCepLojaRepository.faixaAlreadyExistsAndIsNotItself(
                faixaLojaSaoPaulo.getId(),
                faixaLojaSaoPaulo.getInicioFaixaCep(),
                faixaLojaSaoPaulo.getFimFaixaCep()
        );

        boolean faixaExistsAndIsNotItself = faixaCepLojaRepository.faixaAlreadyExistsAndIsNotItself(
                faixaLojaSaoPaulo.getId(),
                faixaLojaVitoria.getInicioFaixaCep(),
                faixaLojaVitoria.getFimFaixaCep()
        );

        assertFalse(faixaExistsButIsItself);
        assertTrue(faixaExistsAndIsNotItself);
    }

    private FaixaCepLoja setup_loja_sao_paulo() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_SAO_PAULO", 10000000L, 20000000L);

        return faixaCepLojaRepository.save(faixaCepLoja);
    }

    private FaixaCepLoja setup_loja_vitoria() {
        FaixaCepLoja faixaCepLoja = new FaixaCepLoja("LOJA_VITORIA", 20000001L, 30000000L);

        return faixaCepLojaRepository.save(faixaCepLoja);
    }

}
