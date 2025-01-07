package com.tech.blog;

import com.tech.blog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Launcher implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//		System.out.println(this.passwordEncoder.encode("string"));

		//		try {
		//			Role role = new Role();
		//			role.setId(AppConstants.ADMIN_USER);
		//			role.setName("ADMIN_USER");
		//
		//			Role role1 = new Role();
		//			role1.setId(AppConstants.NORMAL_USER);
		//			role1.setName("NORMAL_USER");
		//
		//			List<Role> roles = List.of(role, role1);
		//
		//			List<Role> saveRoles = this.roleRepository.saveAll(roles);
		//
		//			saveRoles.forEach(r -> {
		//				System.out.println(r.getName());
		//			});
		//
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}
}
