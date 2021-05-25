package dev.leonardolemos.desafiobackendwine.repository;

import dev.leonardolemos.desafiobackendwine.model.FaixaCepLoja;
import dev.leonardolemos.desafiobackendwine.model.dto.LojaResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FaixaCepLojaRepository extends CrudRepository<FaixaCepLoja, Long> {

    @Query("SELECT count(*) > 0 FROM FaixaCepLoja f WHERE (?1 BETWEEN f.inicioFaixaCep AND f.fimFaixaCep) OR (?2 BETWEEN f.inicioFaixaCep AND f.fimFaixaCep)")
    boolean faixaAlreadyExists(Long startValue, Long endValue);

    @Query("SELECT count(*) > 0 FROM FaixaCepLoja f WHERE f.id != ?1 AND ((?2 BETWEEN f.inicioFaixaCep AND f.fimFaixaCep) OR (?3 BETWEEN f.inicioFaixaCep AND f.fimFaixaCep))")
    boolean faixaAlreadyExistsAndIsNotItself(Long idFaixa, Long startValue, Long endValue);

    @Query("SELECT new dev.leonardolemos.desafiobackendwine.model.dto.LojaResponseDTO(f.codigoLoja) FROM FaixaCepLoja f WHERE (?1 BETWEEN f.inicioFaixaCep AND f.fimFaixaCep)")
    LojaResponseDTO findCodigoLojaByCep(Long cep);

}
