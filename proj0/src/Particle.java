import edu.princeton.cs.algs4.StdRandom;
import net.sf.saxon.expr.GeneralComparison;

import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        lifespan = -1; // All human created particles have no lifespan
    }

    public Color color() {
        return switch(this.flavor) {
            case ParticleFlavor.EMPTY -> Color.BLACK;
            case ParticleFlavor.SAND -> Color.YELLOW;
            case ParticleFlavor.BARRIER -> Color.GRAY;
            case ParticleFlavor.WATER -> Color.BLUE;
            case ParticleFlavor.FOUNTAIN -> Color.CYAN;
            case ParticleFlavor.PLANT -> new Color(0, 255, 0);
            case ParticleFlavor.FIRE -> new Color(255, 0, 0);
            case ParticleFlavor.FLOWER -> new Color(255, 141, 161);
        };
    }

    public void moveInto(Particle other) {
        other.flavor = this.flavor;
        other.lifespan = this.lifespan;
        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        Particle p = neighbors.get(Direction.DOWN);
        if (p.flavor == ParticleFlavor.EMPTY) {
            this.moveInto(p);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int random_number = StdRandom.uniformInt(3);
        switch(random_number) {
            case 0 -> {
                return;
            }
            case 1 -> {
                Particle left_neighbor = neighbors.get(Direction.LEFT);
                if (left_neighbor.flavor == ParticleFlavor.EMPTY) {
                    moveInto(left_neighbor);
                }
            }
            case 2 -> {
                Particle right_neighbor = neighbors.get(Direction.RIGHT);
                if (right_neighbor.flavor == ParticleFlavor.EMPTY) {
                    moveInto(right_neighbor);
                }
            }
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        assert(this.flavor == ParticleFlavor.PLANT || this.flavor == ParticleFlavor.FLOWER);
        //Only plants and flowers can grow.

        int random_number = StdRandom.uniformInt(10);
        switch(random_number) {
            case 0 -> {
                Particle up_neighbor = neighbors.get(Direction.UP);
                if (up_neighbor.flavor == ParticleFlavor.EMPTY) {
                    up_neighbor.flavor = this.flavor;
                    up_neighbor.lifespan = LIFESPANS.get(this.flavor); // Not secure!
                }
            }
            case 1 -> {
                Particle left_neighbor = neighbors.get(Direction.LEFT);
                if (left_neighbor.flavor == ParticleFlavor.EMPTY) {
                    left_neighbor.flavor = this.flavor;
                    left_neighbor.lifespan = LIFESPANS.get(this.flavor);
                }
            }
            case 2 -> {
                Particle right_neighbor = neighbors.get(Direction.RIGHT);
                if (right_neighbor.flavor == ParticleFlavor.EMPTY) {
                    right_neighbor.flavor = this.flavor;
                    right_neighbor.lifespan = LIFESPANS.get(this.flavor);
                }
            }
            //default: do nothing
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (this.flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (this.flavor != ParticleFlavor.BARRIER) {
            this.fall(neighbors);
        }
        if (this.flavor == ParticleFlavor.WATER) {
            this.flow(neighbors);
        }
        if (this.flavor == ParticleFlavor.PLANT || this.flavor == ParticleFlavor.FLOWER) {
            this.grow(neighbors);
        }
    }
}