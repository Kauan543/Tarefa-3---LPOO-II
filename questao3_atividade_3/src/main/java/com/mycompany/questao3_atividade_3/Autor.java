package com.mycompany.questao3_atividade_3;
import java.util.ArrayList;
import java.util.List;

public final class Autor {

    private int id;
    private String nome;
    private List<Livro> livros;

    public Autor(String nome) {
        this.nome = nome;
        this.livros = new ArrayList();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setLivros(List<Livro> livros) {
         for(Livro livro: livros){
             this.adicionarLivro(livro);
        }
    }

    public List<Livro> getLivros() {
        return this.livros;
    }

    public void adicionarLivro(Livro livro) {
        if (!this.getLivros().contains(livro)) {
            this.livros.add(livro);
            livro.adicionarAutor(this);
        }
    }

    public void removerLivro(Livro livro) {
        if (this.getLivros().contains(livro)) {
            this.livros.remove(livro);
            livro.removerAutor(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    /**
     * Método que produz um hashCode igual para autores iguais.
     * @return Inteiro que representa o hashCode do objeto
     */
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.id;
        return hash;
    }
    /**
     * Método que compara dois autores pelo seu id
     * @return booleano: Verndadeiro se igual, falso, caso contrário.
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(!obj.getClass().equals(this.getClass()) )
            return false;

        Autor objAutor = (Autor)obj;

        if(objAutor.getId()!=this.getId())
            return false;

        return true;
    }
}
