package com.espacos_academicos.usuariosservice.init;

import com.espacos_academicos.usuariosservice.entity.Usuario;
import com.espacos_academicos.usuariosservice.entity.Professores; // Importe Professores
import com.espacos_academicos.usuariosservice.repository.UsuarioRepository;
import com.espacos_academicos.usuariosservice.repository.ProfessoresRepository; // Importe ProfessoresRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfessoresRepository professoresRepository; // Injete ProfessoresRepository

    @Override
    public void run(String... args) throws Exception {
        seedUsuarios();
        seedProfessores(); // Chame o método para popular professores
    }

    private void seedUsuarios() {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@ucsal.br");
            admin.setSenha("admin123"); // Lembre-se: senhas em texto plano para demonstração
            admin.setFuncao("admin");
            usuarioRepository.save(admin);

            Usuario professorUser = new Usuario();
            professorUser.setEmail("fernando.borges@pro.ucsal.br");
            professorUser.setSenha("1234");
            professorUser.setFuncao("professor");
            usuarioRepository.save(professorUser);

            System.out.println("Usuários semeados com sucesso no usuarios-service!");
        }
    }

    private void seedProfessores() {
        if (professoresRepository.count() == 0) {
            Professores p1 = new Professores();
            p1.setNome("Ana Silva");
            p1.setEmail("ana@pro.ucsal.br");
            p1.setCurso("Análise e Desenvolvimento de Sistemas");
            professoresRepository.save(p1);

            Professores p2 = new Professores();
            p2.setNome("Bruno Costa");
            p2.setEmail("bruno@pro.ucsal.br");
            p2.setCurso("Engenharia Civil");
            professoresRepository.save(p2);

            // Adicione os outros professores do seu script SQL original
            // ...

            System.out.println("Professores semeados com sucesso no usuarios-service!");
        }
    }
}