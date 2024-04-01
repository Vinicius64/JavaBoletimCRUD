package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.modelo.Aluno;

import java.util.List;

public class AlunoDAO {
    private EntityManager em;

    public AlunoDAO(EntityManager em){this.em=em;}

    public void cadastrar(Aluno aluno){
        em.getTransaction().begin();
        this.em.persist(aluno);
        em.getTransaction().commit();
    }

    public List<Aluno> buscarTodos(){
        String jpql = "SELECT p FROM Aluno p";
        return em.createQuery(jpql,Aluno.class).getResultList();
    }
    public List<Aluno> buscarPorNome(String nome) throws NoResultException{
            String jpql = "SELECT p FROM Aluno p WHERE p.nome = ?1";
            return em.createQuery(jpql,Aluno.class).setParameter(1,nome).getResultList();
    }
    public Aluno buscarUnicoPorNome(String nome) throws NoResultException {
        try {
            String jpql = "SELECT p FROM Aluno p WHERE p.nome = :n";
            return em.createQuery(jpql, Aluno.class)
                    .setMaxResults(1)
                    .setParameter("n", nome)
                    .getSingleResult();
        }catch (NoResultException e){
            System.out.println("Nenhum aluno encontrado para o nome informado!");
            return null;
        }
    }

    public Aluno buscarUnicoPorId(Long id) throws NoResultException {
        String jpql = "SELECT p FROM Aluno p WHERE p.id = :id";
        return em.createQuery(jpql, Aluno.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Aluno removerPorNome(String nome){
        Aluno aluno = buscarUnicoPorNome(nome);
        em.getTransaction().begin();
        if(aluno!=null){
            em.remove(aluno);
        }
        em.getTransaction().commit();
        return aluno;
    }

    public Aluno alterarAluno(Aluno aluno, String nome){
        em.getTransaction().begin();
        Aluno oldAluno;
        try{
            oldAluno = buscarUnicoPorNome(nome);
            oldAluno.setNome(oldAluno.getNome().equals(aluno.getNome()) ? oldAluno.getNome() : aluno.getNome());
            oldAluno.setEmail(oldAluno.getEmail().equals(aluno.getEmail()) ? oldAluno.getEmail() : aluno.getEmail());
            oldAluno.setRa(oldAluno.getRa().equals(aluno.getRa()) ? oldAluno.getRa() : aluno.getRa());
            oldAluno.setNota1(oldAluno.getNota1().equals(aluno.getNota1()) ? oldAluno.getNota1() : aluno.getNota1());
            oldAluno.setNota2(oldAluno.getNota2().equals(aluno.getNota2()) ? oldAluno.getNota2() : aluno.getNota2());
            oldAluno.setNota3(oldAluno.getNota3().equals(aluno.getNota3()) ? oldAluno.getNota3() : aluno.getNota3());
            em.merge(oldAluno);
        } catch (NoResultException e) {
            System.out.println("Aluno não encontrado!");
        }
        em.getTransaction().commit();
        return null;
    }
}
