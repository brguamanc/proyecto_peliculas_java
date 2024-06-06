package com.alura.screenmatch.principal;

import com.alura.screenmatch.exception.ErroEnConversionDeDuracionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class API {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);

        List<Titulo> titulos = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while(true){
            System.out.println("Escriba el nombre de la película");
            var busqueda = sc.nextLine();

            if(busqueda.equalsIgnoreCase("salir")){
                break;
            }

            String direccion =  "http://www.omdbapi.com/?t=" +
                    busqueda.replace(" ", "+")+
                    "&apikey=92e7899";
            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                HttpResponse<String> response = client
                        .send(request,HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);



                TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(miTituloOmdb);

                Titulo miTitulo = new Titulo (miTituloOmdb);
                System.out.println(miTitulo);

                titulos.add(miTitulo);

            }catch (NumberFormatException e){
                System.out.println("Ocurrió un error");
                System.out.println(e.getMessage());
            }catch (IllegalArgumentException e){
                System.out.println("Error en la URI de la dirección");
                System.out.println(e.getMessage());
            }catch (ErroEnConversionDeDuracionException e){
                System.out.println("Ocurrió un error inesperado");
                System.out.println(e.getMessage());
            }
        }

        System.out.println(titulos);

        FileWriter escritura = new FileWriter("titulos.json");
        escritura.write(gson.toJson(titulos));
        escritura.close();

        System.out.println("Finalizó la ejecución del programa");
    }
}