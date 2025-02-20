package com.bookmarker.api.controller;

import com.bookmarker.api.domain.Bookmark;
import com.bookmarker.api.dto.BookmarkDTO;
import com.bookmarker.api.dto.BookmarksDTO;
import com.bookmarker.api.dto.CreateBookmarkRequest;
import com.bookmarker.api.service.BookmarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;


    @GetMapping
    public BookmarksDTO getBookmarks(@RequestParam(name = "page", defaultValue = "1") Integer page
                                        ,@RequestParam(name = "query", defaultValue = "") String query) {
//        if(query == null || query.trim().isEmpty()) {
        if(query == null || query.isBlank()) { //isEmpty + trim
            return bookmarkService.getBookmarks(page);
        }
        return bookmarkService.searchBookmarks(query, page);

        //return bookmarkService.getBookmarks(page);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//201
    //@Valid는 Data Binding을 체크하는 validator를 호출하는 역할
    public BookmarkDTO createBookmark(@RequestBody @Valid CreateBookmarkRequest request) {
        return bookmarkService.createBookmark(request);
    }

}