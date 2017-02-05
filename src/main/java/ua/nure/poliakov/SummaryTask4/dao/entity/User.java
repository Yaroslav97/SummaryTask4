package ua.nure.poliakov.SummaryTask4.dao.entity;

/**
 * User Entity.
 */

public class User {

    private int id;
    private String fullName ;
    private String login;
    private String email;
    private Double score;
    private String role;
    private Boolean isBan;
    private String password;
    private int subscriptions;

    public User() {
    }

    public User(String fullName, String login, String email, String password) {
        this.fullName = fullName;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public User(String fullName, String login, String email, Boolean isBan) {
        this.fullName = fullName;
        this.login = login;
        this.email = email;
        this.score = score;
        this.role = role;
        this.isBan = isBan;
        this.password = password;
    }

    public User(String fullName, String login, String email, Double score, String role, Boolean isBan, String password) {
        this.fullName = fullName;
        this.login = login;
        this.email = email;
        this.score = score;
        this.role = role;
        this.isBan = isBan;
        this.password = password;
    }

    public User(String fullName, String login, String email, Double score, String role, Boolean isBan, String password, int subscriptions) {
        this(fullName, login, email, score, role, isBan, password);
        this.subscriptions = subscriptions;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Double getScore() {
        return score;
    }

    public String getRole() {
        return role;
    }

    public Boolean getBan() {
        return isBan;
    }

    public String getPassword() {
        return password;
    }

    public int getSubscriptions() {
        return subscriptions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBan(Boolean ban) {
        isBan = ban;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSubscriptions(int subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return "UsersList{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", role='" + role + '\'' +
                ", isBan=" + isBan +
                ", password='" + password + '\'' +
                ", subscriptions=" + subscriptions +
                '}';
    }
}
