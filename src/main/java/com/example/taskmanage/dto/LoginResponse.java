package com.example.taskmanage.dto;

public class LoginResponse {
    private final Long id;
    private final String fullName;
    private final String login;
    private final String role;
    private final String redirectUrl;
    private final Long departmentId;
    private final Long teamId;

    public LoginResponse(Long id, String fullName, String login, String role, String redirectUrl) {
        this(id, fullName, login, role, redirectUrl, null, null);
    }

    public LoginResponse(Long id, String fullName, String login, String role, String redirectUrl, Long departmentId) {
        this(id, fullName, login, role, redirectUrl, departmentId, null);
    }

    public LoginResponse(Long id, String fullName, String login, String role, String redirectUrl, Long departmentId, Long teamId) {
        this.id = id;
        this.fullName = fullName;
        this.login = login;
        this.role = role;
        this.redirectUrl = redirectUrl;
        this.departmentId = departmentId;
        this.teamId = teamId;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLogin() {
        return login;
    }

    public String getRole() {
        return role;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public Long getTeamId() {
        return teamId;
    }

    // toString(), equals(), hashCode() — опционально
    @Override
    public String toString() {
        return "LoginResponse{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", login='" + login + '\'' +
                ", role='" + role + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", departmentId=" + departmentId +
                ", teamId=" + teamId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginResponse)) return false;

        LoginResponse that = (LoginResponse) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getFullName() != null ? !getFullName().equals(that.getFullName()) : that.getFullName() != null)
            return false;
        if (getLogin() != null ? !getLogin().equals(that.getLogin()) : that.getLogin() != null) return false;
        if (getRole() != null ? !getRole().equals(that.getRole()) : that.getRole() != null) return false;
        if (getRedirectUrl() != null ? !getRedirectUrl().equals(that.getRedirectUrl()) : that.getRedirectUrl() != null)
            return false;
        if (getDepartmentId() != null ? !getDepartmentId().equals(that.getDepartmentId()) : that.getDepartmentId() != null)
            return false;
        return getTeamId() != null ? getTeamId().equals(that.getTeamId()) : that.getTeamId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFullName() != null ? getFullName().hashCode() : 0);
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getRedirectUrl() != null ? getRedirectUrl().hashCode() : 0);
        result = 31 * result + (getDepartmentId() != null ? getDepartmentId().hashCode() : 0);
        result = 31 * result + (getTeamId() != null ? getTeamId().hashCode() : 0);
        return result;
    }
}