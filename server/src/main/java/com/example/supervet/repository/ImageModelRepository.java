package com.example.supervet.repository;

import com.example.supervet.model.entity.ImageModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageModelRepository extends CrudRepository<ImageModel, Long> {


    ImageModel findByName(String name);
}
