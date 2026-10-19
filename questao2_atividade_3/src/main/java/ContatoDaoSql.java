
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kauan
 */
public class ContatoDaoSql implements ContatoDao{
    private final String insert = "insert into Contato (nome, telefone, email) values (?,?,?)";
    private final String selectAll = "select * from Contato";
    private final String selectById = "select id, nome, telefone, email from Contato where id = ?";
    private final String update = "update Contato set nome = ?, telefone = ?, email = ? where id = ?";
    private final String deleteAll = "truncate table Contato";
    private final String deleteById = "delete from Contato where id = ?";
    
    public ContatoDaoSql(){    
    }
    @Override
    public void add(Contato contato){
        try(Connection conexao = ConnectionFactory.getConnection();
               PreparedStatement ps = conexao.prepareStatement(insert)){
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setString(3, contato.getEmail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                contato.setId(rs.getLong(1)); 
            }
            }   
            ps.close();
            conexao.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public List<Contato> getAll(){
        List<Contato> contatos = new ArrayList<Contato>();
        try(Connection co = ConnectionFactory.getConnection();
                PreparedStatement ps = co.prepareStatement(selectAll)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Contato contato = new Contato(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email")
                );
                contatos.add(contato);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return contatos;
    }
    @Override
    public Contato getById(long id){
        Contato contato = null;
        try(Connection co = ConnectionFactory.getConnection();
            PreparedStatement ps = co.prepareStatement(selectById)){
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();  
            if(rs.next()){
                contato = new Contato(
                rs.getLong("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email")
            );
            }
            rs.close();
            ps.close();
            co.close();
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return contato;
    }
    @Override
    public void update (Contato contato){
            try(Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement ps = conexao.prepareStatement(update)){
            ps.setString(1, contato.getNome());
            ps.setString(2, contato.getTelefone());
            ps.setString(3, contato.getEmail());
            ps.setLong(4, contato.getId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                contato.setId(rs.getLong(1)); 
            }
            }   
            ps.close();
            conexao.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void delete(long id){
        try(Connection co = ConnectionFactory.getConnection();
            PreparedStatement ps = co.prepareStatement(deleteById)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void deleteAll(){
        try(Connection co = ConnectionFactory.getConnection();
            PreparedStatement ps = co.prepareStatement(deleteAll)){
            ps.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
