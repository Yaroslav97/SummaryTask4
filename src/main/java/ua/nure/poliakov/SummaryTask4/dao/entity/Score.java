package ua.nure.poliakov.SummaryTask4.dao.entity;

/**
 * Score entity
 */

public class Score {

    private String login;
    private double score;
    private String operation;

    public Score(String login, double score, String operation) {
        this.login = login;
        this.score = score;
        this.operation = operation;
    }

    public String getLogin() {
        return login;
    }

    public double getScore() {
        return score;
    }

    public String getOperation() {
        return operation;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
