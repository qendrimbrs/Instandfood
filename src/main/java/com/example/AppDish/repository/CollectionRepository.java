package com.example.AppDish.repository;

import com.example.AppDish.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
    Collection findCollectionByCollectionId(Long collectionId);
}