package com.example.github.controller.dto;

import com.example.github.model.Branch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepoDTO {
    String name;
    String login;
    List<Branch> branches;
}
