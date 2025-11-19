package by.tms.tmsc35p3.repository;

import by.tms.tmsc35p3.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    boolean existsById(Long id);

    List<Post> findAllByAuthor_Id(Long authorId);
}
