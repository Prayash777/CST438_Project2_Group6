import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;



@Controller
@RequestMapping("/")
public class AuthController {

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }
}
