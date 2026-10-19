package com.mycompany.questao3_atividade_3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private final String stmtInserir = "INSERT INTO livro(titulo) VALUES(?)";
    private final String stmtInserir2 = "INSERT INTO Livro_Autor(idLivro, idAutor) VALUES(?,?)";
    private final String stmtConsultar = "SELECT * FROM livro WHERE id = ?";
    private final String stmtListaLivroAutor = "SELECT * FROM livro";

    public void inserirLivro(Livro livro) {

        try(Connection con=ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(stmtInserir, PreparedStatement.RETURN_GENERATED_KEYS);
           )
        {
            con.setAutoCommit(false);
            stmt.setString(1, livro.getTitulo());
            stmt.executeUpdate();
            int idLivroGravado = lerIdLivro(stmt);
            livro.setId(idLivroGravado);
            this.gravarAutores(livro, con);

            con.commit();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um livro no banco de dados. Origem="+ex.getMessage());
        } 
        try(Connection con=ConnectionFactory.getConnection();
            PreparedStatement stmt2 = con.prepareStatement(stmtInserir2, PreparedStatement.RETURN_GENERATED_KEYS);
           ){
            List<Autor> autores = new ArrayList<Autor>();
            for(Autor autor: autores){
                stmt2.setInt(1, livro.getId());
                stmt2.setInt(2, autor.getId());
                stmt2.executeUpdate();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException("Erro ao inserir um livro no banco de dados. Origem="+ex.getMessage());
        } 
    }
     private int lerIdLivro(PreparedStatement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        rs.next();
        return rs.getInt(1);
    }   

    public Livro consultarLivro(int id) {
        try(Connection con=ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(stmtConsultar);
           )
        {
            Livro livroLido = null;
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            List<Autor> listaAutores = lerAutores(id,con);
            livroLido = new Livro(rs.getString("titulo"), listaAutores);
            livroLido.setId(rs.getInt("Id"));
            return livroLido;           
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um livro no banco de dados. Origem="+ex.getMessage());            
        }

    }

    private void gravarAutores(Livro livro, Connection con) throws SQLException {
        String sql = "INSERT INTO livro_autor (idLivro, idAutor) VALUES ( ?, ?)";
        PreparedStatement stmt;
        stmt = con.prepareStatement(sql);
        stmt.setInt(1, livro.getId());
        List<Autor> autores = livro.getAutores();
        for (Autor autor : autores) {
            stmt.setLong(2, autor.getId());
            stmt.executeUpdate();
        }
    }

    private List<Autor> lerAutores(long idLivro, Connection con) throws SQLException{
        //Select para pegar os autores de um livro
        String sql = "SELECT autor.id,autor.nome"
                + " FROM autor"
                + " INNER JOIN livro_autor"
                + " 	ON autor.id = livro_autor.idAutor"
                + " WHERE livro_autor.idLivro = ? ";
        PreparedStatement stmt = null;
        List<Autor> autores = null;
        stmt = con.prepareStatement(sql);
        stmt.setLong(1, idLivro);
        ResultSet resultado = stmt.executeQuery();
        autores = new ArrayList<Autor>();
        while (resultado.next()) {
            Autor autorLido = new Autor(resultado.getString("nome"));
            autorLido.setId(resultado.getInt("id"));
            autores.add(autorLido);
        }

        return autores;
    }



    public List<Livro> listarLivroComAutores() {
        try(Connection con=ConnectionFactory.getConnection();
            PreparedStatement stmt = con.prepareStatement(stmtListaLivroAutor);
           )
        {
       
            ResultSet rs = stmt.executeQuery();
            List<Livro> listaLivros = new ArrayList<Livro>();
            while (rs.next()) {
                List<Autor> listAutores = lerAutores(rs.getInt(1),con);
                Livro livro = new Livro(rs.getString(2), listAutores);
                livro.setId(rs.getInt(1));
                listaLivros.add(livro);
            }

            return listaLivros;            
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao listar um livro com autores no banco de dados. Origem="+ex.getMessage());            
        }
        
        

    }
}
