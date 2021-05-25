package dev.leonardolemos.desafiobackendwine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.leonardolemos.desafiobackendwine.exception.FaixaCepNotFoundException;
import dev.leonardolemos.desafiobackendwine.exception.FaixaCepValidationException;
import dev.leonardolemos.desafiobackendwine.exception.LojaNotFoundException;
import dev.leonardolemos.desafiobackendwine.model.FaixaCepLoja;
import dev.leonardolemos.desafiobackendwine.model.dto.LojaResponseDTO;
import dev.leonardolemos.desafiobackendwine.service.FaixaCepLojaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FaixaCepLojaController.class)
public class FaixaCepLojaControllerTest {

    @MockBean
    FaixaCepLojaService faixaCepLojaService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_faixa_saving_with_success() throws Exception {
        FaixaCepLoja faixaRequest = setup_loja_vitoria();
        FaixaCepLoja faixaResult = new FaixaCepLoja(faixaRequest.getCodigoLoja(), faixaRequest.getInicioFaixaCep(), faixaRequest.getFimFaixaCep());
        faixaResult.setId(1L);

        when(faixaCepLojaService.save(any(FaixaCepLoja.class))).thenReturn(faixaResult);

        mockMvc.perform(post("/faixas")
                .content(objectMapper.writeValueAsString(faixaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo_loja").value("LOJA_VITORIA"));
    }

    @Test
    public void test_faixa_saving_with_invalid_faixa() throws Exception {
        FaixaCepLoja faixaRequest = setup_loja_vitoria();

        when(faixaCepLojaService.save(any(FaixaCepLoja.class))).thenThrow(FaixaCepValidationException.class);

        mockMvc.perform(post("/faixas")
                .content(objectMapper.writeValueAsString(faixaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_faixa_updating_with_invalid_faixa() throws Exception {
        FaixaCepLoja faixaRequest = setup_loja_vitoria();

        when(faixaCepLojaService.save(any(FaixaCepLoja.class))).thenThrow(FaixaCepValidationException.class);

        mockMvc.perform(put("/faixas")
                .content(objectMapper.writeValueAsString(faixaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_faixa_updating_with_not_existing_faixa() throws Exception {
        FaixaCepLoja faixaRequest = setup_loja_vitoria();

        when(faixaCepLojaService.save(any(FaixaCepLoja.class))).thenThrow(FaixaCepNotFoundException.class);

        mockMvc.perform(put("/faixas")
                .content(objectMapper.writeValueAsString(faixaRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_faixa_delete_with_not_existing_faixa() throws Exception {

        doThrow(FaixaCepNotFoundException.class).when(faixaCepLojaService).delete(isA(Long.class));

        mockMvc.perform(delete("/faixas/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void test_faixa_delete() throws Exception {

        doNothing().when(faixaCepLojaService).delete(isA(Long.class));

        mockMvc.perform(delete("/faixas/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void test_faixa_find_nearby_loja() throws Exception {
        LojaResponseDTO lojaResponse = setup_loja_vitoria_response();

        when(faixaCepLojaService.findNearbyLojaByCep(anyLong())).thenReturn(lojaResponse);

        mockMvc.perform(get("/faixas/nearbylojabycep/{cep}", 10000000L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loja").value("LOJA_VITORIA"));
    }

    @Test
    public void test_faixa_find_nearby_loja_that_not_exists() throws Exception {
        LojaResponseDTO lojaResponse = setup_loja_vitoria_response();

        when(faixaCepLojaService.findNearbyLojaByCep(anyLong())).thenThrow(LojaNotFoundException.class);

        mockMvc.perform(get("/faixas/nearbylojabycep/{cep}", 10000000L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private FaixaCepLoja setup_loja_vitoria() {
        return new FaixaCepLoja("LOJA_VITORIA", 10000000L, 20000000L);
    }

    private LojaResponseDTO setup_loja_vitoria_response() {
        return new LojaResponseDTO("LOJA_VITORIA");
    }
}
