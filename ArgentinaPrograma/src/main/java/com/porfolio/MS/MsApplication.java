package com.porfolio.MS;


import com.porfolio.MS.Files.FileStorageProperties;
import com.porfolio.MS.Files.FileStorageService;
import com.porfolio.MS.User.User;
import com.porfolio.MS.User.UserProfile;
import com.porfolio.MS.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;



@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class MsApplication {
	@Autowired
	private UserRepository userRepository;
	//encript
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MsApplication.class, args);
	}

	@Bean
	CommandLineRunner init(FileStorageService fileStorageService) {
		return (args) -> {
			//fileStorageService.deleteAll();
			fileStorageService.init();
		};
	}

	@Bean
	public CommandLineRunner loadData() {
		return (args) -> {
			// Create a User instance
			if(!userRepository.existsByUsername("manuelsaponaro")){
				User user = new User("manuelsaponaro@gmail.com", passwordEncoder.encode("123"), "Manuel",
						"Saponaro");


				user.setUsername("manuelsaponaro");
				user.setRole("ADMIN");
				user.setId(1L);

				// Create a UserProfile instance
				UserProfile userProfile = new UserProfile();
				userProfile.setCodepen("...");
				userProfile.setGithub("..");
				userProfile.setCodepen("..");
				userProfile.setInstagram("..");
				userProfile.setTitulo("FullStack Developer Jr.");
				userProfile.setInfoPersonal("Soy un developer nuevo");

				userProfile.setExperienciaTitulo1("Ministerio de Salud del GCBA");
				userProfile.setExperienciaTexo1("Jefe del servicio de psicologia");
				userProfile.setExperienciaTitulo2(null);
				userProfile.setExperienciaTexo2(null);
				userProfile.setExperienciaTitulo3(null);
				userProfile.setExperienciaTexo3(null);

				userProfile.setEducacionTitulo1("Ministerio de Salud del GCBA");
				userProfile.setEducacionTexo1("Jefe del servicio de psicologia");
				userProfile.setEducacionTitulo2(null);
				userProfile.setEducacionTexo2(null);
				userProfile.setEducacionTitulo3(null);
				userProfile.setEducacionTexo3(null);

				userProfile.setProyectosTitulo1("Ministerio de Salud del GCBA");
				userProfile.setProyectosTexo1("Jefe del servicio de psicologia");
				userProfile.setProyectosTitulo2(null);
				userProfile.setProyectosTexo2(null);
				userProfile.setProyectosTitulo3(null);
				userProfile.setProyectosTexo3(null);

				userProfile.setCss(5);
				userProfile.setHtml(4);
				userProfile.setJava(9);
				userProfile.setPython(10);
				userProfile.setComunicacion(5);
				userProfile.setRelaciones(2);

				// Set child reference(userProfile) in parent entity(user)
				user.setUserProfile(userProfile);

				// Set parent reference(user) in child entity(userProfile)
				userProfile.setUser(user);

				// Save Parent Reference (which will save the child as well)
				userRepository.save(user);
			}

		};
	}


}

