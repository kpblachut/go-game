package org.example.server;

import java.util.HashSet;
import java.util.Set;

public class Chain {
    private final Set<Intersection> stones;
    private final Set<Intersection> liberties;
    private final Player owner;

    public Chain(Set<Intersection> stones, Set<Intersection> liberties, Player owner) {
        this.stones = stones;
        this.liberties = liberties;
        this.owner = owner;
    }

    public Chain(Intersection intersection, Player owner) {
        stones = new HashSet<Intersection>();
        stones.add(intersection);
        this.owner = owner;
        liberties = new HashSet<Intersection>(intersection.getEmptyNeighbors());
    }

    public Chain(Chain chain) {
        this.stones = new HashSet<Intersection>(chain.stones);
        this.liberties = new HashSet<Intersection>(chain.liberties);
        this.owner = chain.owner;
    }

    public Player getOwner() {
        return owner;
    }

    public Set<Intersection> getLiberties() {
        return liberties;
    }

    public Set<Intersection> getStones() {
        return stones;
    }

    public void add(Chain chain, Intersection playedStone) {
        // Fuse stone sets
        this.stones.addAll(chain.stones);
        // Fuse liberties
        this.liberties.addAll(chain.liberties);
        // remove played stone from liberties
        this.liberties.remove(playedStone);
    }

    public Chain removeLiberty(Intersection playedStone) {
        Chain newChain = new Chain(stones, liberties, owner);
        newChain.liberties.remove(playedStone);
        return newChain;
    }

    public void die() {
        for (Intersection rollingStone : this.stones) {
            rollingStone.setChain(null);
            Set<Chain> neighbouringChains = rollingStone.getNeighbours();
            for (Chain chain : neighbouringChains) {
                chain.liberties.add(rollingStone);
            }
        }
        this.owner.addCapturedStones(this.stones.size());
    }
}
