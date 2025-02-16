package br.com.api_fipe.APITabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true) //Adicionado para caso seja implementado novos atributos no retorno da API

public record Dados(String codigo, String nome) {

}
