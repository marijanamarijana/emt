package com.example.emtlab.repo;

import com.example.emtlab.model.views.GoodBooksView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GoodBooksViewRepository extends JpaRepository<GoodBooksView, Long> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "REFRESH MATERIALIZED VIEW public.good_books", nativeQuery = true)
    void refreshMaterializedView();
}
