package Projekt.TotalWar.App;

import java.util.ArrayList;

public class FactionModel {
    public Long factionId;
    public String factionName;
    public ArrayList<Object> heroes;

    public Long getFactionId() {
        return factionId;
    }

    public void setFactionId(Long factionId) {
        this.factionId = factionId;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public ArrayList<Object> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Object> heroes) {
        this.heroes = heroes;
    }
}
