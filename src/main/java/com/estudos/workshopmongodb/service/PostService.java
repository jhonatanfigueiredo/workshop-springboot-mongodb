package com.estudos.workshopmongodb.service;

import com.estudos.workshopmongodb.entity.Post;
import com.estudos.workshopmongodb.repository.PostRepository;
import com.estudos.workshopmongodb.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public Post findById(String id) {
        Optional<Post> post = repository.findById(id);
        return post.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado."));
    }
}
