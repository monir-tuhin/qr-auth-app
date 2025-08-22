package com.app.authentiScan.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "full_name", length = 100, nullable = false)
	private String fullName;

	@Column(name = "username", length = 20, nullable = false, unique = true)
	private String username;

	@Column(name = "email", length = 50, nullable = false, unique = true)
	private String email;

	@Column(name = "mobile_number", length = 20, nullable = false, unique = true)
	private String mobileNumber;

	@Column(name = "password", length = 200, nullable = false)
	private String password;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	/*@ManyToOne(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;*/

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

}
