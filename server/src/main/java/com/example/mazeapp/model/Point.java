package com.example.mazeapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Point {
    @JsonProperty("xIndex")
    protected int xIndex;
    @JsonProperty("yIndex")
    protected int yIndex;
}