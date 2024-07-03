package bestdeals.backend.dto;

import bestdeals.backend.entities.Role;
import lombok.Data;

import java.util.List;

@Data
public class SignUpRequest {

	private String name;
	private String email;
	private String password;
	private String address;
	private List<Role> roles;
	
}
