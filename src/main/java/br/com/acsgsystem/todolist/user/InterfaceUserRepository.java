package br.com.acsgsystem.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

//interface é um modelo um contrato que temos dentro da app, na interface temos métodos mas não tem a implementação
// dos métodos, só defefine
// o primeiro parâmetro é qual a classe esse repositório está representando?
// o segundo parâmetro é qual é o tipo de ID que essa minha entidade tem
public interface InterfaceUserRepository extends JpaRepository<UserModel, UUID> {

    // aqui esta fazendo a busca pelo usename, e o tipo é UserModel
    UserModel findByUsername(String username);


}
