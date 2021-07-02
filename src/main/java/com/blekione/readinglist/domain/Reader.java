package com.blekione.readinglist.domain;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "readers")
public class Reader implements UserDetails {
   private static final long serialVersionUID = 192454217283646403L;

   @Id
   @Column(name = "reader_username")
   private String            username;
   @Column(name = "reader_full_name")
   private String            fullName;
   @Column(name = "reader_password")
   private String            password;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of(new SimpleGrantedAuthority("ROLE_bob"));
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public String getPassword() {
      return password;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   @Override
   // auto-generated
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
      result = prime * result + ((password == null) ? 0 : password.hashCode());
      result = prime * result + ((username == null) ? 0 : username.hashCode());
      return result;
   }

   @Override
   // auto-generated
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
      final Reader other = (Reader) obj;
      if (fullName == null) {
         if (other.fullName != null) {
            return false;
         }
      } else if (!fullName.equals(other.fullName)) {
         return false;
      }
      if (password == null) {
         if (other.password != null) {
            return false;
         }
      } else if (!password.equals(other.password)) {
         return false;
      }
      if (username == null) {
         if (other.username != null) {
            return false;
         }
      } else if (!username.equals(other.username)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      final var sb = new StringBuilder();
      sb.append("Reader: {");
      sb.append("\n   username: ").append(username);
      sb.append("\n   fullName: ").append(fullName);
      sb.append("\n   password: ").append(password);
      sb.append("}\n");
      return sb.toString();
   }

}
