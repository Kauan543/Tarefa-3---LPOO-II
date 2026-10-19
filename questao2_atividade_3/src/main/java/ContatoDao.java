
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author Kauan
 */
public interface ContatoDao {
    public void add(Contato contato);
    public List<Contato> getAll();
    public Contato getById(long id);
    public void update (Contato contato);
    public void delete(long id);
    public void deleteAll();
}
