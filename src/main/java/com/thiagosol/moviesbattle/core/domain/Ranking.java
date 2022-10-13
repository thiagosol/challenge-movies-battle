package com.thiagosol.moviesbattle.core.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ranking {

    private int position;
    private Player player;
    private BigDecimal points;

    private Ranking(int position, Player player, BigDecimal points) {
        this.position = position;
        this.player = player;
        this.points = points;
    }

    public static List<Ranking> getRanking(HashMap<Player, BigDecimal> mapPlayerPoints){
        LinkedHashMap<Player, BigDecimal> orderedMap = mapPlayerPoints.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        List<Ranking> ranking = new ArrayList<>();
        int position = 1;
        for(var player : orderedMap.keySet()) {
            ranking.add(new Ranking(position, player, orderedMap.get(player)));
            position++;
        }
        return ranking;
    }

    public int getPosition() {
        return position;
    }

    public Player getPlayer() {
        return player;
    }

    public BigDecimal getPoints() {
        return points;
    }
}
