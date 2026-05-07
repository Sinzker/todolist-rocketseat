package br.com.acsgsystem.todolist.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data //Lombok - getters e setters
@Entity(name="tb_users")
public class UserModel {

    @Id // chave primária
    @GeneratedValue(generator = "UUID") //gera o id automaticamente
    private UUID id;//identificado único, é mais seguro do que o sequencial

    @Column(unique = true)//vai ser uma coluna com uma restrição(constraint), vai definir que vai ser um atributo único//valida username
    private String username;
    private String name;
    private String password;

    @CreationTimestamp // para saber quando foi criado
    private LocalDateTime createdAt;

}
