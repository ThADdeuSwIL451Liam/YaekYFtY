// 代码生成时间: 2025-09-09 14:21:37
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling theme switch functionality.
 */
@Controller
@SessionAttributes("currentTheme")
public class ThemeSwitchController {

    // Default themes available
    private static final String[] THEMES = {"theme-dark", "theme-light", "theme-blue"};

    @GetMapping("/changeTheme")
    public String changeTheme(@RequestParam(name = "theme", required = false) String theme,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              SessionStatus status) {
        // Check if the provided theme is valid
        if (theme != null && isThemeAvailable(theme)) {
            model.addAttribute("currentTheme", theme);
        } else {
            // Set to default theme if not valid or not provided
            model.addAttribute("currentTheme", "theme-dark");
            redirectAttributes.addFlashAttribute("error", "Invalid theme selected. Default theme applied.");
        }

        // Invalidate session to clear other attributes
        status.setComplete();

        return "redirect:/"; // Redirect to home page or relevant page
    }

    /**
     * Check if the provided theme is available.
     *
     * @param theme the theme to check
     * @return true if the theme is available, false otherwise
     */
    private boolean isThemeAvailable(String theme) {
        for (String availableTheme : THEMES) {
            if (availableTheme.equals(theme)) {
                return true;
            }
        }
        return false;
    }
}
