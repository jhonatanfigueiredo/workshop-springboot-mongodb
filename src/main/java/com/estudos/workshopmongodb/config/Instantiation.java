package com.estudos.workshopmongodb.config;

import com.estudos.workshopmongodb.dto.AuthorDto;
import com.estudos.workshopmongodb.dto.CommentDto;
import com.estudos.workshopmongodb.entity.Post;
import com.estudos.workshopmongodb.entity.User;
import com.estudos.workshopmongodb.repository.PostRepository;
import com.estudos.workshopmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

@Configuration
public class Instantiation implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public Instantiation(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(String... arg0) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        userRepository.deleteAll();
        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, alex, bob));

        postRepository.deleteAll();
        Post post1 = new Post(null, sdf.parse("17/05/2022"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDto(maria));
        Post post2 = new Post(null, sdf.parse("18/05/2022"), "Bom dia", "Acordei feliz hoje!", new AuthorDto(maria));

        CommentDto c1 = new CommentDto("Boa viagem, mano!", post1.getDate(), new AuthorDto(alex));
        CommentDto c2 = new CommentDto("Aproveite!", post1.getDate(), new AuthorDto(bob));
        CommentDto c3 = new CommentDto("Tenha um ótima dia!", post2.getDate(), new AuthorDto(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().add(c3);
        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}
