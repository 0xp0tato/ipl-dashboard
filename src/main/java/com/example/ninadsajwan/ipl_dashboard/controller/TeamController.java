package com.example.ninadsajwan.ipl_dashboard.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ninadsajwan.ipl_dashboard.model.Match;
import com.example.ninadsajwan.ipl_dashboard.model.Team;
import com.example.ninadsajwan.ipl_dashboard.service.MatchService;
import com.example.ninadsajwan.ipl_dashboard.service.TeamService;

@RestController
@CrossOrigin
public class TeamController {

    private TeamService teamService;
    private MatchService matchService;

    public TeamController(TeamService teamService, MatchService matchService) {
        this.teamService = teamService;
        this.matchService = matchService;
    }

    @GetMapping("/team")
    public Iterable<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {

        return teamService.findLatestMatchesByTeam(teamName);
    }

    @GetMapping("/team/{teamName}/matches")
    public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
        return matchService.getMatchesForTeam(teamName, year);
    }
}
