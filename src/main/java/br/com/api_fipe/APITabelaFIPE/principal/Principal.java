package br.com.api_fipe.APITabelaFIPE.principal;

import br.com.api_fipe.APITabelaFIPE.model.Dados;
import br.com.api_fipe.APITabelaFIPE.model.Modelos;
import br.com.api_fipe.APITabelaFIPE.model.Veiculo;
import br.com.api_fipe.APITabelaFIPE.service.ConsumoAPI;
import br.com.api_fipe.APITabelaFIPE.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static Scanner sc = new Scanner(System.in);

    private static ConsumoAPI consumoAPI = new ConsumoAPI();

    private static ConverteDados conversorDeDados = new ConverteDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    private final String URL_MODELOS = "/marcas/" + "/modelos";

    public void ExibeMenu() {
        System.out.println("|| API - FIPE - CONSTRUÍDO POR VINICIUS SAES ||\n");
        System.out.println("Digite o tipo de veículo que você deseja consultar: " +
                "\n- Carros" +
                "\n- Motos" +
                "\n- Caminhoes" +
                "\n\nDigite: ");

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

        // link api: https://parallelum.com.br/fipe/api/v1/{tipoVeiculo}/marcas/{codMarca}/modelos/
        endereco = endereco + codMarca + "/modelos/";

        // Busca a lista de modelos de acordo com o tipo de veículo
        json = consumoAPI.obterDados(endereco);
        System.out.println(json);

        // Aqui não é necessário extrair os modelos com um método igual a linha 43 pois a api retorna um objeto de chave "modelos" que contém uma lista de modelos,
        // podemos modelar uma `Record` que representa um objeto que é uma Lista de modelos (modelos que por sua vez é um objeto do molde da classe `Dados` (codigo && nome))
        Modelos listaModelos = conversorDeDados.converteDados(json, Modelos.class);

        // Ordena a lista de modelos por Nome e imprime no console
        listaModelos.modelos()
                .stream()
                .sorted(Comparator.comparing(Dados::nome))
                .forEach(System.out::println);

        System.out.println("Digite um trecho do modelo desejado: ");
        String trechoModelo = sc.nextLine();

        // Cria uma nova lista com todos os modelos que possuam no seu Nome o `trechoModelo` digitado pelo usuário
        List<Dados> modelosEscolhidos = listaModelos.modelos()
            .stream()
            .filter(m -> m.nome().toLowerCase().contains(trechoModelo.toLowerCase()))
            .collect(Collectors.toList());

        // Imprime e solicita o modelo desejado
        System.out.println("Modelos escolhidos: \n");
        modelosEscolhidos.stream().sorted(Comparator.comparing(Dados::nome)).forEach(System.out::println);
        // Teste para treinar ordenar a lista pelo código de maneira numérica convertendo o código para Integer
        //        modelosEscolhidos.stream().sorted(Comparator.comparing((x -> Integer.parseInt(x.codigo())))).forEach(System.out::println);

        System.out.println("\nDigite o código do modelo: ");

        String codigoModelo = sc.nextLine();

        // Link api:  https://parallelum.com.br/fipe/api/v1/{tipoVeiculo}/marcas/{codigoMarca}/modelos/{codigoModelo}/anos
        endereco =  endereco + codigoModelo + "/anos/";

        json = consumoAPI.obterDados(endereco);
        List<Dados> listaAnos = conversorDeDados.obterLista(json, Dados.class);

        List<Veiculo> listaVeiculos = new ArrayList<>();

        for (Dados a : listaAnos) {
            var enderecoAnos = endereco + a.codigo() + "/";
            json = consumoAPI.obterDados(enderecoAnos);
            var veiculo = conversorDeDados.converteDados(json, Veiculo.class);
            listaVeiculos.add(veiculo);
        }

        //Imprime todos os veículos no console,
        // comparando os valores utilizando regex para remover tudo que não seja dígito ou vírgula, substitui a vírgula por ponto,
        // converte o resultado em Double e usa para comparação (não afeta o valor original)
        // Ex: "R$ 9.160,00" -> 9.160,00
        listaVeiculos.stream()
                .sorted(Comparator.comparing(x-> Double.parseDouble(x.valor().replaceAll("[^\\d,]","").replace(",", "."))))
                .forEach(System.out::println);
    }
}
