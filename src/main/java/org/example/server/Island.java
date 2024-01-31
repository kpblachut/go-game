package org.example.server;

import java.util.HashSet;
import java.util.Set;

public class Island {
    private final Set<Intersection> intersections;
    private final Set<Chain> neighbours;
    private Player owner;

    public Island() {
        intersections = new HashSet<Intersection>();
        neighbours = new HashSet<Chain>();
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }


    public Set<Intersection> getIntersections() {
        return intersections;
    }

    public Player findOwner() {
        Set<Player> neighbourOwners = new HashSet<Player>();
        for (Chain chain : neighbours) {
            neighbourOwners.add(chain.getOwner());
            if (neighbourOwners.size() > 1) {
                break;
            }
        }
        return (neighbourOwners.size() == 1 ? (Player) neighbourOwners.toArray()[0] : null);
    }

    public static Set<Island> islandsBuilder(GameBoard gameBoard, Set<Chain> deadChains) {
        int width = gameBoard.getWidth();
        int height = gameBoard.getHeight();

        Set<Intersection> toBeTreated = new HashSet<Intersection>();
        Set<Intersection> toBeTreatedLocally;
        Set<Island> islands = new HashSet<Island>();
        Intersection intersection;
        Island newIsland;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                intersection = gameBoard.getIntersection(i,j);
                if (intersection.isEmpty()||deadChains.contains(intersection.getChain())) {
                    toBeTreated.add(intersection);
                }
            }
        }

        while (toBeTreated.size()>0) {

            intersection = toBeTreated.iterator().next();
            newIsland = new Island();

            toBeTreatedLocally = new HashSet<Intersection>();
            toBeTreatedLocally.add(intersection);
            int prev_size;
            int curr_size;
            do {
                prev_size= toBeTreatedLocally.size();
                toBeTreatedLocally =setGrowth(toBeTreatedLocally,deadChains);
                curr_size= toBeTreatedLocally.size();
            } while (curr_size>prev_size);

            newIsland.intersections.addAll(toBeTreatedLocally);

            for(Intersection lcross: toBeTreatedLocally) {
                newIsland.neighbours.addAll(lcross.getNeighbours());
            }

            newIsland.neighbours.removeAll(deadChains);
            toBeTreated.removeAll(toBeTreatedLocally);
            islands.add(newIsland);
        }

        for (Island island : islands) {
            island.owner = island.findOwner();
        }
        return islands;
    }

    public static Set<Intersection> setGrowth(Set<Intersection> toBeGrown, Set<Chain> deadStones) {
        Set<Intersection> grown = new HashSet();
        grown.addAll(toBeGrown);
        for(Intersection intersection : toBeGrown) {
            grown.addAll(intersection.getEmptyOrDeadNeighbours(deadStones));
        }
        return grown;
    }
}
