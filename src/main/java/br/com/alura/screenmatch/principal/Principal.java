package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO="https://www.omdbapi.com/?apikey=4b2df4f0&t=";
    public void exibeMenu(){
        System.out.println("Digite o nome de sua série a ser pesquisada: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados( ENDERECO + nomeSerie.replace(" ", "+"));
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i=1; i<=dados.totalTemporadas(); i++){
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+ "&season="+ i);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        for (int i=0; i< dados.totalTemporadas();i++){
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for(int j= 0; j< episodiosTemporada.size();j++){
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }
    }
}
