package com.example.ninadsajwan.ipl_dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ninadsajwan.ipl_dashboard.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByTeamName(String teamName);
}
