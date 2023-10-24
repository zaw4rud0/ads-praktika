package ch.zhaw.ads;

public class SnowflakeServer implements CommandExecutor {

    private Turtle turtle;

    @Override
    public String execute(String command) {
        int level = Integer.parseInt(command);

        turtle = new Turtle(0.1, 0.7);

        double dist = 0.75;

        snowflake(level, dist);
        turtle.turn(-120);
        snowflake(level, dist);
        turtle.turn(-120);
        snowflake(level, dist);

        return turtle.getTrace();
    }

    private void snowflake(int level, double dist) {
        if (level == 0) {
            turtle.move(dist);
        } else {
            level--;
            dist = dist / 3;
            snowflake(level, dist);
            turtle.turn(60);
            snowflake(level, dist);
            turtle.turn(-120);
            snowflake(level, dist);
            turtle.turn(60);
            snowflake(level, dist);
        }
    }
}