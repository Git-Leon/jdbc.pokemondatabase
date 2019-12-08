package com.github.git_leon.pokemondatabase.model;

public class PokemonTrainer {
    private final long id;
    private final int spdef;
    private final int maxHp;
    private final int pokelevel;
    private final int spatk;
    private final int defense;
    private final int attack;
    private final int pokemon_id;
    private final int hp;
    private final int speed;

    public PokemonTrainer(long id, int spdef, int maxHp, int pokelevel, int spatk, int defense, int attack, int pokemon_id, int hp, int speed) {
        this.id = id;
        this.spdef = spdef;
        this.maxHp = maxHp;
        this.pokelevel = pokelevel;
        this.spatk = spatk;
        this.defense = defense;
        this.attack = attack;
        this.pokemon_id = pokemon_id;
        this.hp = hp;
        this.speed = speed;
    }

    public long getId() {
        return id;
    }

    public int getSpdef() {
        return spdef;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getPokelevel() {
        return pokelevel;
    }

    public int getSpatk() {
        return spatk;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public int getPokemon_id() {
        return pokemon_id;
    }

    public int getHp() {
        return hp;
    }

    public int getSpeed() {
        return speed;
    }
}
