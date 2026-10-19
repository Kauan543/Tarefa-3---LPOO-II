package com.mycompany.questao3_atividade_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class MainLivroAutor {
    
    private AutorDAO autorDAO;
    private LivroDAO livroDAO;

    public MainLivroAutor() throws Exception{
        autorDAO = new AutorDAO();
        livroDAO = new LivroDAO();
    }

    public static void main(String args[]) throws Exception{
        MainLivroAutor main = new MainLivroAutor();
        String opcao = "";
        while (true) {
            try{
            System.out.println("Escolha uma das opções e tecle <ENTER>: ");
            System.out.println("  1 - Incluir Autor");
            System.out.println("  2 - Incluir Livro");
            System.out.println("  3 - Listar Autores");
            System.out.println("  4 - Listar Livro com autores");
            System.out.println("  5 - Listar Autores de um livro");
            System.out.println("  6 - Listar Livros de um autor");
            System.out.println("  7 - Sair");
            Scanner sc = new Scanner(System.in,"ISO-8859-1");
            opcao = sc.nextLine();
            switch(opcao){
                case "1":
                    main.incluirAutor();
                    break;
                case "2":
                    main.incluirLivro();
                    break;
                case "3":
                    main.listarAutores();
                    break;
                case "4":
                    main.listarLivroComAutores();
                    break;
                case "5":
                    main.listarAutoresLivro();
                    break;
                case "6":
                    main.listarLivrosAutor();
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
            if(opcao.equals("7")){
                break;
            }
            }catch(Exception ex){
                System.out.println("Falha na operação. Mensagem="+ ex.getMessage());
                //ex.printStackTrace();
            }
        }
    }
    public void incluirAutor() throws Exception{
        System.out.print("Digite o nome do autor:");
        Scanner sc = new Scanner(System.in,"ISO-8859-1");
        String nome = sc.nextLine();
        Autor autor = new Autor(nome);
        autorDAO.inserirAutor(autor);
    }

    public void incluirLivro() {
        System.out.print("Digite o título do livro:");
        Scanner sc = new Scanner(System.in,"ISO-8859-1");
        String titulo = sc.nextLine();
        int numAutores=1;
        List<Autor> listaAutores = new ArrayList();
        int idAutor=0;
        do{
            try{
                Scanner sc2 = new Scanner(System.in,"ISO-8859-1");
                System.out.print("ID Autor "+numAutores+":");
                idAutor = sc2.nextInt();
                if(idAutor<=-1)
                    break;
                Autor autor = autorDAO.consultarAutor(idAutor);
                if(autor != null){
                    listaAutores.add(autor);
                    numAutores++;
                }else{
                    System.out.println("Autor não existe!");
                }
            }
            catch(Exception ex){
                System.out.println("ID autor não é inteiro ou inválido!");
            }
        }while(true);


        Livro livro = new Livro(titulo,listaAutores);
        livroDAO.inserirLivro(livro);
    }

    public void listarAutores() throws Exception{
        List<Autor> listaAutores = autorDAO.listarAutores();
        
        Collections.sort(listaAutores, new Comparator<Autor>() {
           @Override
           public int compare(Autor arg0, Autor arg1) {
               String nomeAutor1 = arg0.getNome();
               String nomeAutor2 = arg1.getNome();
               int retorno = nomeAutor1.compareToIgnoreCase(nomeAutor2);
               return retorno;
               /*int idAutor1 = arg0.getId();
               int idAutor2 = arg1.getId();
               if(idAutor1 > idAutor2)
                   return 1;
               else if(idAutor1 == idAutor2)
                   return 0;
               else
                   return -1;*/
             }
        });
        System.out.println("ID\tNOME");
        for(Autor autor:listaAutores){
            System.out.println(autor.getId()+" \t"+autor.getNome());
        }
    }

    public void listarLivroComAutores() throws Exception{
        List<Livro> listaLivros = livroDAO.listarLivroComAutores();
        Collections.sort(listaLivros, new Comparator<Livro>() {
           public int compare(Livro arg0, Livro arg1) {
               String titulo = arg0.getTitulo();
               int i = titulo.compareToIgnoreCase(arg1.getTitulo());
               return i;
             }
        });
        System.out.println("ID\tTitulo do Livro\tAutores");
        for(Livro livro:listaLivros){
            System.out.print(livro.getId()+"\t"+livro.getTitulo()+"\t");
            for(Autor autor:livro.getAutores()){
                System.out.print(autor.getNome()+";");
            }
            System.out.print("\n");
        }

    }
    public void listarAutoresLivro() throws Exception{
        System.out.print("Digite o ID do livro:");
        Scanner sc = new Scanner(System.in,"ISO-8859-1");
        int id = sc.nextInt();
        Livro livro = livroDAO.consultarLivro(id);
        List<Autor> listaAutor = livro.getAutores();
        System.out.printf("Autor(es) do Livro %s\n", livro.getTitulo());
        for(Autor autor : listaAutor){
            System.out.printf("ID: %d Nome: %s\n", autor.getId(), autor.getNome());
        }
    }
        public void listarLivrosAutor() throws Exception{
        System.out.print("Digite o ID do autor:");
        Scanner sc = new Scanner(System.in,"ISO-8859-1");
        int id = sc.nextInt();
        Autor autor = autorDAO.consultarAutor(id);
        List<Livro> listaLivros = autorDAO.lerLivros(id);
        System.out.printf("Livro(s) do Autor %s\n", autor.getNome());
        for(Livro livro : listaLivros){
            System.out.printf("ID: %d Nome: %s\n", livro.getId(), livro.getTitulo());
        }
    }
}
