package com.example.demo.service;

import com.example.demo.dto.VoteDto;
import com.example.demo.exeptions.PostNotFoundException;
import com.example.demo.exeptions.RedditException;
import com.example.demo.model.Post;
import com.example.demo.model.Vote;
import com.example.demo.model.VoteType;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.VoteRepository;
import lombok.AllArgsConstructor;

import java.util.Optional;


@AllArgsConstructor
public class VoteService {


    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not found with id " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new RedditException("you have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (VoteType.UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() -1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);



    }

    private Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }
}
