package br.com.api_fipe.APITabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Utilizado para ignorar o retorno dos 'anos' da api
public record Modelos(List<Dados> modelos) {
}
