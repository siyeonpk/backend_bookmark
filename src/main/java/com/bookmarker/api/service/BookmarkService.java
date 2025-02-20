package com.bookmarker.api.service;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.domain.BookmarkRepository;
import com.bookmarker.api.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository repository;
    private final BookmarkMapper mapper;

//    public BookmarkService(BookmarkRepository repository) {
//        this.repository = repository;
//    }

    @Transactional(readOnly = true)
    public BookmarksDTO getBookmarks(Integer page) {
        int pageNo = page < 1 ? 0 : page - 1;//JPA 페이지가 0부터 시작하기 때문
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "id");
//        Page<Bookmark> bookmarkPage = repository.findAll(pageable);//page<T>
//        Page<BookmarkDTO> bookmarkPage = repository.findAll(pageable)
//                //.map(bookmark -> mapper.toDTO(bookmark));
//                .map(mapper::toDTO);
        //custom query method 호출
        Page<BookmarkDTO> bookmarkPage = repository.findBookmarks(pageable);

        return new BookmarksDTO(bookmarkPage);
    }

    @Transactional(readOnly = true)
    public BookmarksDTO<?> searchBookmarks(String query, Integer page) {
        int pageNo = page < 1 ? 0 : page - 1 ;
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.Direction.DESC, "createdAt");
        //Page<BookmarkDTO> bookmarkPage = repository.searchBookmarks(query, pageable);
        Page<BookmarkVM> bookmarkPage = repository.findByTitleContainingIgnoreCase(query, pageable);
        return new BookmarksDTO<>(bookmarkPage);
    }

    public BookmarkDTO createBookmark(CreateBookmarkRequest request) {
        Bookmark bookmark = new Bookmark(request.getTitle(), request.getUrl(), Instant.now());
        Bookmark savedBookmark = repository.save(bookmark);
        return mapper.toDTO(savedBookmark);
    }
    
}