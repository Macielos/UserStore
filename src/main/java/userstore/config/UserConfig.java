package userstore.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties("users")
@Data
public class UserConfig {

	@Data
	public static class User
	{
		private String name;
		private String password;
	}

	private List<User> users = new ArrayList<>();

}
