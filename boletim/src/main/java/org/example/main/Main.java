package org.example.main;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.dao.AlunoDAO;
import org.example.modelo.Aluno;
import org.example.util.JPAUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/*
MAURICIO BRITO TEIXEIRA   SC3033988
VINICIUS DA SILVA GONÇALVES OLIVEIRA   SC3033406
 */

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
        EntityManager em = JPAUtil.getEntityManager();
        AlunoDAO alunoDAO = new AlunoDAO(em);
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu de Opções:");
            System.out.println("1. Cadastrar aluno");
            System.out.println("2. Excluir aluno");
            System.out.println("3. Alterar aluno");
            System.out.println("4. Buscar aluno pelo nome");
            System.out.println("5. Listar alunos (com status aprovação)");
            System.out.println("6. Fim");
            System.out.print("Escolha uma opção (1-6): ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do aluno: ");
                    String nome = scanner.nextLine();
                    System.out.println("Digite o RA do aluno: ");
                    String RA = scanner.nextLine();
                    System.out.println("Digite o E-mail do aluno: ");
                    String email = scanner.nextLine();
                    System.out.println("Digite a nota 1 do aluno: ");
                    BigDecimal nota1 = scanner.nextBigDecimal();
                    System.out.println("Digite a nota 2 do aluno: ");
                    BigDecimal nota2 = scanner.nextBigDecimal();
                    System.out.println("Digite a nota 3 do aluno: ");
                    BigDecimal nota3 = scanner.nextBigDecimal();
                    Aluno aluno = new Aluno(nome,RA,email,nota1,nota2,nota3);
                    alunoDAO.cadastrar(aluno);
                    break;
                case 2:
                    System.out.println("Digite o nome do aluno: ");
                    String nomeDlt = scanner.nextLine();
                    Aluno alunoRemove = alunoDAO.removerPorNome(nomeDlt);
                    if (alunoRemove!=null){
                        String out = "O aluno "+alunoRemove.getNome()+" foi removido.";
                        System.out.println(out);
                    }
                    break;
                case 3:
                    System.out.println("Digite o nome do aluno a ser alterado: ");
                    nome = scanner.nextLine();
                    System.out.println("Digite o novo nome do aluno: ");
                    String newNome = scanner.nextLine();
                    System.out.println("Digite o novo RA do aluno: ");
                    RA = scanner.nextLine();
                    System.out.println("Digite o novo E-mail do aluno: ");
                    email = scanner.nextLine();
                    System.out.println("Digite a nova nota 1 do aluno: ");
                    nota1 = scanner.nextBigDecimal();
                    System.out.println("Digite a nova nota 2 do aluno: ");
                    nota2 = scanner.nextBigDecimal();
                    System.out.println("Digite a nova nota 3 do aluno: ");
                    nota3 = scanner.nextBigDecimal();
                    Aluno newAluno = new Aluno(newNome, RA, email, nota1, nota2, nota3);
                    alunoDAO.alterarAluno(newAluno, nome);
                    break;
                case 4:
                    System.out.println("Digite o nome do aluno: ");
                    String nomeBusc = scanner.nextLine();
                    List<Aluno> listaP = alunoDAO.buscarPorNome(nomeBusc);
                    if(!listaP.isEmpty()){
                        for (Aluno alunos : listaP) {
                            System.out.println(alunos.toString());
                        }
                    }
                    else {
                        System.out.println("Nenhum aluno encontrado para o nome informado!");
                    }
                    break;
                case 5:
                    List<Aluno> lista = alunoDAO.buscarTodos();
                    for (Aluno alunos : lista) {
                        System.out.println(alunos.toString());
                    }
                    break;
                case 6:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
        scanner.close();
        em.close();
        System.exit(0);
    }
}