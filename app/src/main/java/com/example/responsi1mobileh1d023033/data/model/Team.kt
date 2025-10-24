package com.example.responsi1mobileh1d023033.data.model

data class Team(
    val id: Int,
    val name: String,
    val crest: String,
    val area: Area,
    val coach: Coach,
    val squad: List<Player>
)

data class Area(val name: String)
data class Coach(
    val name: String,
    val nationality: String
)