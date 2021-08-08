package hovanvydut.apiblog.api.v1.vote;

import hovanvydut.apiblog.api.v1.vote.dto.ArticleVoteReq;
import hovanvydut.apiblog.core.vote.VoteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author hovanvydut
 * Created on 8/6/21
 */

@RestController
@RequestMapping("/api/v1")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/articles/{slug}/vote")
    public void voteArticle(@PathVariable String slug, Principal principal,
                            @Valid ArticleVoteReq params) {
        this.voteService.voteArticle(slug, principal.getName(), params.getType());
    }

}
