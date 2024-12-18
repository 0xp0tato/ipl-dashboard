package com.example.ninadsajwan.ipl_dashboard.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ninadsajwan.ipl_dashboard.model.Match;
import com.example.ninadsajwan.ipl_dashboard.repository.MatchRepository;

@Service
public class MatchService {
    private MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<Match> getMatchesForTeam(String teamName, int year) {

        LocalDate starDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);

        return matchRepository.getMatchesByTeamBetweenDates(teamName, starDate, endDate);

    }

}
