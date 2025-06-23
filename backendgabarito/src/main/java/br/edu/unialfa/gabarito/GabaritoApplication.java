package br.edu.unialfa.gabarito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GabaritoApplication {
    public static void main(String[] args) {
//         Geração de hashes (execute uma vez e depois remova)
//        if (args.length > 0 && args[0].equals("--generate-hashes")) {
//            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//            System.out.println("Admin: " + encoder.encode("123456"));
//            System.out.println("Professor: " + encoder.encode("123456"));
//            System.out.println("Aluno: " + encoder.encode("123456"));
//            System.exit(0);
//        }

        SpringApplication.run(GabaritoApplication.class, args);
    }
}