package net.enderboy500.enderlib.helper;

public enum TraderLevel {
    NOVICE(1),
    APPRENTICE(2),
    JOURNEYMAN(3),
    EXPERT(4),
    MASTER(5)
    ;

    public final int level;

    TraderLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}
