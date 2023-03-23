package com.example.stageapp.repository;

import com.example.stageapp.pojo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;


//utilizzata per indicare che una determinata interfaccia non dovrebbe essere considerata un repository
@NoRepositoryBean
public interface VideoRepository <E extends Video, String> extends MongoRepository<E , String> {
//	List<Optional<E>> findByTitleIgnoreCaseContainingAndGenreListIgnoreCaseContaining(String title, String genre);
//	Page<E> findByTitleIgnoreCaseContainingAndGenreListIgnoreCaseContaining(String title, String genre, Pageable page);

	//mockare il repository e fatti tornare i
}
