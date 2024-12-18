package com.example.ninadsajwan.ipl_dashboard.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ninadsajwan.ipl_dashboard.model.Team;
import com.example.ninadsajwan.ipl_dashboard.repository.MatchRepository;
import com.example.ninadsajwan.ipl_dashboard.repository.TeamRepository;

@Service
public class TeamService {

    private TeamRepository teamRepository;
    private MatchRepository matchRepository;

    public TeamService(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    public Team findLatestMatchesByTeam(String teamName) {
        Team team = teamRepository.findByTeamName(teamName);

        Pageable pageable = PageRequest.of(0, 4);

        team.setMatches(this.matchRepository.getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable));

        return team;
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

}
