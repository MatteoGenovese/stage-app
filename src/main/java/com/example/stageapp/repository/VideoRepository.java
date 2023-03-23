package com.example.stageapp.repository;

import com.example.stageapp.pojo.Video;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;


//utilizzata per indicare che una determinata interfaccia non dovrebbe essere considerata un repository
@NoRepositoryBean
public interface VideoRepository <E extends Video, String> extends MongoRepository<E , String> {

}
