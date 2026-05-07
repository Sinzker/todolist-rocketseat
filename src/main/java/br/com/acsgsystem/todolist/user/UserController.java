package br.com.acsgsystem.todolist.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired// vai instanciar, gerenciar esse obejto, vai criar, todo ciclo de vida o spring vai gerenciar
    private InterfaceUserRepository userRepository;// aqui esta passando a interface

    @PostMapping("/")
    //Retornos diferentes dentro de uma mesma requisição ResponseEntity
    public ResponseEntity create(@RequestBody UserModel userModel){
       var user = this.userRepository.findByUsername(userModel.getUsername());

       //verifica se o usuário ja existe
       if(user != null){
           //Retornar uma mensagem de erro e o Status Code no BD, se é 200, 500 etc, da pra passar o número direto dentro do
           //() do status ou se não souber qual tipo pode utilizar o HttpStatus. que vai dar as opções, o body é a mensagem
           // que vai aparecer no BD
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
       }

       //hash da senha, aqui vamos criptografar a senha, para que ninguém entre o BD e descrubra
        //o primeiro elemento do parenteses é o cost q á a "força" pra senha, o 12 é o que está na documentação
        var passwordHashred = BCrypt.withDefaults().hashToString(12,userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(userModel);
        // aqui o status deu sucesso
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

}
