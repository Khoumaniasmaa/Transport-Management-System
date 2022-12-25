package ma.tms.tms.web;

import lombok.AllArgsConstructor;
import ma.tms.tms.entities.Conducteurs;
import ma.tms.tms.entities.Commande;
import ma.tms.tms.entities.Vehicules;
import ma.tms.tms.entities.Zone;
import ma.tms.tms.repositories.ConducteursRepository;
import ma.tms.tms.repositories.CommandeRepository;
import ma.tms.tms.repositories.VehiculesRepository;
import ma.tms.tms.repositories.ZoneRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class TMSController {
    private CommandeRepository commandeRepository;
    private ZoneRepository zoneRepository;
    private ConducteursRepository conducteursRepository;
    private VehiculesRepository vehiculesRepository;

    /***********************************Gestion les commandes ************************************************************/
    @GetMapping(path = "/index") /*nom de pages*/
    public String intervention(Model model,
                               @RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "5") int size,
                               @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Commande> pagecommande = commandeRepository.findByNomContains((keyword), PageRequest.of(page, size));
        model.addAttribute("listcommande", pagecommande.getContent());
        model.addAttribute("pages", new int[pagecommande.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "ListeCommande";
    }

    @GetMapping("/deleteCommande")
    public String delete(Long id, String keyword, int page) {
        commandeRepository.deleteById(id);
        return "redirect:/index?page" + page + "&keyword" + keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/accueil";
    }

    @GetMapping("/ListeCommande")
    @ResponseBody
    public List<Commande> listcommande() {
        return commandeRepository.findAll();
    }

    @GetMapping("/AjouterCommande")
    public String AjouterCommande(Model model) {
        model.addAttribute("commande", new Commande());
        return "AjouterCommande";
    }

    @PostMapping(path = "/save")
    public String save(Model model, @Valid Commande commande, BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) return "AjouterCommande";
        commandeRepository.save(commande);
        return "redirect:/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping(path = "/editCommande")
    public String editIntervention(Model model, long id, String keyword, int page) {
        Commande intervention = commandeRepository.findById(id).orElse(null);
        if (intervention == null) throw new RuntimeException("Intervention introuvable");
        model.addAttribute("intervention", intervention);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editCommande";
    }

    @GetMapping(path = "/detailCommande")
    public String det(Model model, long id, String keyword, int page) {
        Commande intervention = commandeRepository.findById(id).orElse(null);
        if (intervention == null) throw new RuntimeException("Intervention introuvable");
        model.addAttribute("intervention", intervention);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "detail";
    }

    /******************************************************************/
    @GetMapping("/Dashboard")
    public String Dashboard() {
        return "Dashboard";
    }

    @GetMapping("/Login")
    public String Login() {
        return "Login";
    }

    @GetMapping("/accueil")
    public String accueil() {
        return "accueil";
    }

    @GetMapping("/acc")
    public String acc() {
        return "accu";
    }

    @GetMapping("/Profil")
    public String Profil() {
        return "Profil";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/forgot-password")
    public String Forgot_Password() {
        return "forgot-password";
    }

    /************************************conducteur ***************************************************/

    @GetMapping(path = "/Listeconducteurs") /*nom de pages*/
    public String conducteurs(Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Conducteurs> pageconducteurs = conducteursRepository.findByNomContains((keyword), PageRequest.of(page, size));
        model.addAttribute("listconducteurs", pageconducteurs.getContent());
        model.addAttribute("pages", new int[pageconducteurs.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "Listeconducteurs";
    }
    @GetMapping("/deleteconducteur")
    public String deleteconducteur(Long id, String keyword, int page) {
        conducteursRepository.deleteById(id);
        return "redirect:/Listeconducteurs?page=" + page + "&keyword=" + keyword;
    }
    @GetMapping("/Ajouterconducteurs")
    public String Ajouterconducteurs(Model model) {
        model.addAttribute("conducteurs", new Conducteurs());
        return "Ajouterconducteurs";
    }

    @GetMapping("/Conducteurs")
    @ResponseBody
    public List<Conducteurs> listconducteurs() {
        return conducteursRepository.findAll();
    }

    @PostMapping(path = "/saveconducteurs")
    public String saveconducteurs(Model model, @Valid Conducteurs conducteurs, BindingResult bindingResult,
                                  @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) return "Ajouterconducteurs";
        conducteursRepository.save(conducteurs);
        return "redirect:/Listeconducteurs?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping(path = "/editconducteur")
    public String editConducteur(Model model, long id, String keyword, int page) {
        Conducteurs conducteurs = conducteursRepository.findById(id).orElse(null);
        if (conducteurs == null) throw new RuntimeException("conducteur introuvable");
        model.addAttribute("conducteurs", conducteurs);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editconducteur";
    }

    /************************************vehicule ***************************************************/

    @GetMapping(path = "/ListeVehicules")
    public String vehicules (Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Vehicules> pagevehicules = vehiculesRepository.findByNomContains((keyword), PageRequest.of(page, size));
        model.addAttribute("listvehicule", pagevehicules.getContent());
        model.addAttribute("pages", new int[pagevehicules.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "ListeVehicules";
    }

    @GetMapping("/Ajoutervehicules")
    public String Ajoutervehicules(Model model) {
        model.addAttribute("vehicules", new Vehicules());
        return "Ajoutervehicules";
    }

    @GetMapping("/Vehicules")
    @ResponseBody
    public List<Vehicules> listvehicule() {
        return vehiculesRepository.findAll();
    }
    @GetMapping(path = "/editvehicules")
    public String editvehicules(Model model, long id, String keyword, int page) {
        Vehicules vehicules = vehiculesRepository.findById(id).orElse(null);
        if (vehicules == null) throw new RuntimeException("zone introuvable");
        model.addAttribute("vehicules", vehicules);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editvehicules";
    }

    @PostMapping(path = "/savevehicules")
    public String savevehicules(Model model, @Valid Vehicules vehicules, BindingResult bindingResult,
                                @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) return "Ajoutervehicules";
        vehiculesRepository.save(vehicules);
        return "redirect:/ListeVehicules?page=" + page + "&keyword=" + keyword;
    }
    @GetMapping("/deletevehicules")
    public String deletevehicules (Long id, String keyword, int page) {
        vehiculesRepository.deleteById(id);
        return "redirect:/ListeVehicules?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping(path = "/detailVehicules")
    public String detailVehicules(Model model, long id, String keyword, int page) {
        Vehicules vehicules = vehiculesRepository.findById(id).orElse(null);
        if (vehicules == null) throw new RuntimeException("vehicules introuvable");
        model.addAttribute("vehicules", vehicules);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "detailVehicules";
    }
    /***************************************Zone**********************************************/
    @GetMapping(path = "/Listezone") /*nom de pages*/
    public String zone (Model model,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "size", defaultValue = "5") int size,
                              @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Zone> pagezone = zoneRepository.findByNomContains((keyword), PageRequest.of(page, size));
        model.addAttribute("listzone", pagezone.getContent());
        model.addAttribute("pages", new int[pagezone.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "Listezone";
    }

    @GetMapping("/Ajouterzone")
    public String Ajouterzone(Model model) {
        model.addAttribute("zone", new Zone());
        return "Ajouterzone";
    }


    @GetMapping("/Zone")
    @ResponseBody
    public List<Zone> listzone() {
        return zoneRepository.findAll();
    }

    @GetMapping(path = "/editZone")
    public String editZone(Model model, long id, String keyword, int page) {
        Zone zone = zoneRepository.findById(id).orElse(null);
        if (zone == null) throw new RuntimeException("zone introuvable");
        model.addAttribute("zone", zone);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editZone";
    }
    @GetMapping("/deleteZone")
    public String deleteZone(Long id, String keyword, int page) {
        zoneRepository.deleteById(id);
        return "redirect:/Listezone?page=" + page + "&keyword=" + keyword;
    }
    @PostMapping(path = "/save1")
    public String save1(Model model, @Valid Zone zone, BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keyword, @RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) return "Ajouterzone";
        zoneRepository.save(zone);
        return "redirect:/Listezone?page=" + page + "&keyword=" + keyword;
    }
}
