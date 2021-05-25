package dev.leonardolemos.desafiobackendwine.controller;

import dev.leonardolemos.desafiobackendwine.exception.FaixaCepValidationException;
import dev.leonardolemos.desafiobackendwine.model.FaixaCepLoja;
import dev.leonardolemos.desafiobackendwine.model.dto.LojaResponseDTO;
import dev.leonardolemos.desafiobackendwine.service.FaixaCepLojaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@Tag(name = "Faixa CEP Loja", description = "API de Faixas de CEPs para Lojas")
@RestController
@Slf4j
public class FaixaCepLojaController {

    private final FaixaCepLojaService faixaCepLojaService;

    public FaixaCepLojaController(FaixaCepLojaService faixaCepLojaService) {
        this.faixaCepLojaService = faixaCepLojaService;
    }

    @Operation(operationId = "saveFaixaCepLoja", summary = "Salva uma Faixa de CEP para uma Loja especificada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Salvou uma faixa de CEP com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FaixaCepLoja.class)
                    )
                    }),
            @ApiResponse(responseCode = "400",
                    description = "A faixa enviada para ser salva não é válida"
            )
    })
    @PostMapping("/faixas")
    FaixaCepLoja save(@Valid @RequestBody FaixaCepLoja faixaCepLoja) {
        try {
            return faixaCepLojaService.save(faixaCepLoja);
        } catch (FaixaCepValidationException e) {
            log.error("", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Operation(operationId = "updateFaixaCepLoja", summary = "Atualiza uma Faixa de CEP de uma Loja")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Atualizou uma faixa de CEP com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FaixaCepLoja.class)
                    )
                    }),
            @ApiResponse(responseCode = "400",
                    description = "A faixa enviada para ser atualizada não é válida"
            )
    })
    @PutMapping("/faixas")
    FaixaCepLoja update(@Valid @RequestBody FaixaCepLoja faixaCepLoja) {
        try {
            return faixaCepLojaService.save(faixaCepLoja);
        } catch (FaixaCepValidationException e) {
            log.error("", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @Operation(operationId = "deleteFaixaCepLoja", summary = "Exclui uma Faixa de CEP de Loja de acordo com um ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Excluiu uma faixa de CEP com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FaixaCepLoja.class)
                    )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Não existe uma Faixa de CEP com o ID especificado"
            )
    })
    @DeleteMapping("/faixas/{id}")
    void delete(@PathVariable Long id) {
        try {
            faixaCepLojaService.delete(id);
        } catch (FaixaCepValidationException e) {
            log.error("", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(operationId = "findCepFaixaCepLoja", summary = "Busca uma loja cuja Faixa de CEP inclui um CEP informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Achou loja cuja Faixa de CEP inclui um CEP informado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FaixaCepLoja.class)
                    )
                    }),
            @ApiResponse(responseCode = "404",
                    description = "Não existe Loja dentro da faixa do CEP informado"
            )
    })
    @GetMapping("/faixas/{cep}")
    LojaResponseDTO findNearbyLoja(@PathVariable Long cep) {
        try {
            return faixaCepLojaService.findNearbyLojaByCep(cep);
        } catch (FaixaCepValidationException e) {
            log.error("", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
