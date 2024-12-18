package com.example.ninadsajwan.ipl_dashboard.model;

import java.util.List;

import org.springframework.data.annotation.Transient;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String teamName;

    private long totalMatches;

    private long totalWins;

    @Transient
    @OneToMany
    private List<Match> matches;

    public Team(String teamName, long totalMatches) {
        // this.id = UUID.randomUUID().toString();
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }
}
