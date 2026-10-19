/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kauan
 */
public class DaoFactory {
    private DaoFactory(){
    }
    public static ContatoDao getContatoDao(){
        return new ContatoDaoSql();
    }
}
