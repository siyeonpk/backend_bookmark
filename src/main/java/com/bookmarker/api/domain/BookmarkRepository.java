package com.bookmarker.api.domain;

import com.bookmarker.api.dto.BookmarkDTO;
import com.bookmarker.api.dto.BookmarkVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    //class based JPA projection
    @Query("""
    select new BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
    """)
//    @Query("""
//    select new com.bookmarker.api.dto.BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
//    """)
    Page<BookmarkDTO> findBookmarks(Pageable pageable);

    @Query("""
    select new BookmarkDTO(b.id, b.title, b.url, b.createdAt) from Bookmark b
    where lower(b.title) like lower(concat('%', :query, '%'))
    """)
    Page<BookmarkDTO> searchBookmarks(String query, Pageable pageable);


    //method명의 naming 규칙을 보고 query문을 자동으로 생성해주는 query method
    Page<BookmarkDTO> findByTitleContainsIgnoreCase(String query, Pageable pageable);

    Page<BookmarkVM> findByTitleContainingIgnoreCase(String query, Pageable pageable);
}