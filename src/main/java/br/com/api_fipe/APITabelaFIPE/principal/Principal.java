package br.com.api_fipe.APITabelaFIPE.principal;

import br.com.api_fipe.APITabelaFIPE.model.Dados;
import br.com.api_fipe.APITabelaFIPE.model.Modelos;
import br.com.api_fipe.APITabelaFIPE.service.ConsumoAPI;
import br.com.api_fipe.APITabelaFIPE.service.ConverteDados;

import java.util.*;

public class Principal {
    private static Scanner sc = new Scanner(System.in);

    private static ConsumoAPI consumoAPI = new ConsumoAPI();

    private static ConverteDados conversorDeDados = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    private final String URL_MODELOS = "/marcas/" + "/modelos";

    public void ExibeMenu() {
        System.out.println("|| API - FIPE - CONSTRUÍDO POR VINICIUS SAES ||\n");
        System.out.println("Digite o número do tipo de veículo que você deseja consultar: " +
                "\n1 - Carros" +
                "\n2 - Motos" +
                "\n3 - Caminhoes" +
                "\nEscolha: ");

        String opcao = sc.nextLine();
        String endereco;
        // link GET marcas do tipo veiculo: // link api: https://parallelum.com.br/fipe/api/v1/{tipoVeiculo}/marcas/
        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas/";
        } else if (opcao.toLowerCase().contains("moto")) {
            endereco = URL_BASE + "motos/marcas/";
        } else {
            endereco = URL_BASE + "caminhoes/marcas/";
        }

        String json = consumoAPI.obterDados(endereco);
        System.out.println(json);

        //Dado que a api retorna uma lista de objetos, é necessário extrair essa lista com um método.
        List<Dados> dadosFipe = conversorDeDados.obterLista(json, Dados.class);
        dadosFipe.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o código da marca: ");
        String codMarca = sc.nextLine();

        // link api: https://parallelum.com.br/fipe/api/v1/carros/marcas/{codMarca}/modelos
        endereco = endereco + codMarca + "/modelos";

        String json2 = consumoAPI.obterDados(endereco);
        System.out.println(json2);

        // Aqui não é necessário extrair os modelos com um método igual a linha 43 pois a api retorna um objeto de chave "modelos" que contém uma lista de modelos,
        // podemos modelar uma `Record` que representa um objeto que é uma Lista de modelos (modelos que por sua vez é um tipo da classe `Dados`)
        var listaModelos = conversorDeDados.converteDados(json2, Modelos.class);
        listaModelos.modelos()
                .stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);
        //TEste
    }
}
