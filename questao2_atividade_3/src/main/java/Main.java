import java.util.List;

public class Main {
    public static void main(String[] args) {

        ContatoDao contatoDao = DaoFactory.getContatoDao();
        
        System.out.println("=== TESTE 1: Inserir novo contacto ===");
        Contato novoContato = new Contato(0, "João Silva", "912345678", "joao.silva@email.pt");
        contatoDao.add(novoContato);
        System.out.println("Contacto inserido com sucesso! ID gerado: " + novoContato.getId());
        
        System.out.println("\n=== TESTE 2: Listar todos os contactos ===");
        List<Contato> contatos = contatoDao.getAll();
        for (Contato c : contatos) {
            System.out.println("ID: " + c.getId() + " | Nome: " + c.getNome() + " | Telefone: " + c.getTelefone());
        }
        
        if (!contatos.isEmpty()) {
            long idParaTestar = contatos.get(0).getId();
            
            System.out.println("\n=== TESTE 3: Buscar contacto por ID (" + idParaTestar + ") ===");
            Contato contatoEncontrado = contatoDao.getById(idParaTestar);
            if (contatoEncontrado != null) {
                System.out.println("Encontrado: " + contatoEncontrado.getNome() + " - " + contatoEncontrado.getEmail());
                
                System.out.println("\n=== TESTE 4: Atualizar contacto ===");
                contatoEncontrado.setNome("João Silva Atualizado");
                contatoDao.update(contatoEncontrado);
                System.out.println("Contacto atualizado. Verifica a base de dados!");
            }
            
            System.out.println("\n=== TESTE 5: Eliminar contacto por ID (" + idParaTestar + ") ===");
            contatoDao.delete(idParaTestar);
            System.out.println("Contacto eliminado.");
            
            // Teste comentado para testar somente no fim
            // System.out.println("\n=== TESTE 6: Eliminar TODOS os contactos ===");
            // contatoDao.deleteAll();
            // System.out.println("Todos os registos foram eliminados.");
        }
    }
}