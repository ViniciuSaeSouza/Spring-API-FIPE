package br.com.api_fipe.APITabelaFIPE.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface IConverteDados {

    <T> T converteDados(String json, Class<T> classe);

    <T> List<T> obterLista(String json, Class<T> classe);


}
