package br.com.acsgsystem.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.acsgsystem.todolist.user.InterfaceUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component //é a classe mais genérica de gerenciamento do Spring
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private InterfaceUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/tasks/")) {

            //Pegar a tutenticação (usuário e senha)
            var authorization = request.getHeader("Authorization");
            // System.out.println("Authorization: " + authorization);

            var authEncoded = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            var authString = new String(authDecode);
            //System.out.println("Authorization: " + authString);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];
            System.out.println("Authorization");
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);


            //Validar usuário
            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuário sem autorização");
                System.out.println("Usuário não encontrado");
            }else{
                //Validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified){
                    System.out.println("LOGADO COM SUCESSO!!!");
                    //Segue viagem
                    request.setAttribute("idUser", user.getId());
                    // request o que vem de requisição e reponse o que mandamos para o usuário
                    filterChain.doFilter(request,response);
                }else{
                    response.sendError(401);
                    System.out.println("Senha incorreta");
                }
            }
        }else{
            filterChain.doFilter(request,response);
        }

    }
}
