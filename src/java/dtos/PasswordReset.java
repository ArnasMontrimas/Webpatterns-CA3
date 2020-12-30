/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Arnas
 */
public class PasswordReset {
    private int id;
    private String ipAddress;
    private int attempts;
    private LocalDateTime createdAt;
    private LocalDateTime timeout;
    
    public PasswordReset(int id, String ipAddress, int attempts, LocalDateTime createdAt, LocalDateTime timeout) {
        this.id = id;
        this.ipAddress = ipAddress;
        this.attempts = attempts;
        this.createdAt = createdAt;
        this.timeout = timeout;
    }

    public int getId() {
        return id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getAttempts() {
        return attempts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getTimeout() {
        return timeout;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setTimeout(LocalDateTime timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "PasswordReset{" + "id=" + id + ", ipAddress=" + ipAddress + ", attempts=" + attempts + ", createdAt=" + createdAt + ", timeout=" + timeout + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PasswordReset other = (PasswordReset) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
