package ru.penza.controllers;


import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.config.WebSocketMessageBrokerStats;
import ru.penza.forms.EngineForm;
import ru.penza.forms.PeriodForm;
import ru.penza.forms.ToolForm;
import ru.penza.forms.UserForm;
import ru.penza.models.Engine;
import ru.penza.services.EngineService;
import ru.penza.services.ToolService;
import ru.penza.services.UserService;
import ru.penza.services.ValueService;

import java.util.Optional;

//@CrossOrigin
@Controller
public class TildaControllerDB {

    @Autowired
    private EngineService engineService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private ValueService valueService;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private WebSocketMessageBrokerStats webSocketMessageBrokerStats;


    @GetMapping("/")                                                     // EMPTY PATH //
    public String getFirst() {
        return "redirect:/home";
    }


    @GetMapping("/home")                                                 // HOME PAGE //
    public String getHome(Authentication auth) {
        if (auth != null) {
            return "redirect:/main";
        }
        return "home_page";
    }


    @GetMapping("/main")                                                // MAIN PAGE //
    public String getDB(Authentication auth) {
        if (authHasRole("ADMIN", auth)) {
            return "testdb_admin";
        }
        return "testdb_user";
    }



    private boolean authHasRole(String role, Authentication auth) {          // CHECK AUTHORITY //
        for (GrantedAuthority authority : auth.getAuthorities()) {
            if (role.equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }





    @PostMapping("/add_user")                            /// ADD USER TO DB ///
    @ResponseBody
    public String addUser(UserForm userForm) {
        return userService.addUser(userForm);
    }


    @PostMapping("/delete_user")                         /// DELETE USER FROM DB ///
    @ResponseBody
    public String deleteUser(UserForm userForm) {
        Integer result = userService.deleteUserByLogin(userForm.getLogin());
        if (result > 0) {
            return userForm.getLogin() + " successfully removed from DB";
        } else {
            return "There is no such user in the DB";
        }
    }


    @GetMapping("/users")                              /// GET USER'S LOGINS FROM DB ///
    @ResponseBody
    public String getUsersLogins() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", userService.getLogins());
        return jsonObject.toString();
    }


    @GetMapping("/motors")                              /// GET ENGINES FROM DB ///
    @ResponseBody
    public String getAllMotors() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("engines", engineService.getAllEnginesNames());
        return jsonObject.toString();
    }


    @GetMapping("/tools")                               /// GET TOOLS FROM DB ///
    @ResponseBody
    public String getAllTools() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("critical_values", toolService.getAllCriticalsMap());
        return jsonObject.toString();
    }


    @GetMapping("/all_values")                         /// GET ALL VALUES FROM DB ///
    @ResponseBody
    public String getAllVals() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("critical_values", toolService.getAllCriticalsMap());
        jsonObject.put("engines", engineService.getAllEnginesNames());
        jsonObject.put("values", valueService.getAllValuesMap());
        return jsonObject.toString();
    }


//    @GetMapping("/all")                               /// GET ALL VALUES FROM DB ------- WEBSOCKET///
//    @ResponseBody
//    public void getAll() {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("critical_values", toolService.getAllCriticalsMap());
//        jsonObject.put("engines", engineService.getAllEnginesNames());
//        jsonObject.put("values", valueService.getAllValuesMap());
//        messagingTemplate.convertAndSend("/topic/public", jsonObject.toString());
//        System.out.println("Stats:  " + webSocketMessageBrokerStats.toString());
//    }


    @PostMapping("/add_motor")                        /// ADD ENGINE TO DB ///
    @ResponseBody
    public String addEngineToDB(EngineForm engineForm) {
        return engineService.addEngine(engineForm);
    }


    @PostMapping("/delete_motor")                     /// DELETE ENGINE FROM DB ///
    @ResponseBody
    public String deleteCascadeEngine(EngineForm engineForm) {
        Optional<Engine> engine = engineService.findEngineByName(engineForm.getName());
        if (engine.isPresent()) {
            valueService.delValueByEngineId(engine.get().getId());
            engineService.delEngineById(engine.get().getId());
            return engineForm.getName() + " successfully removed from DB";
        } else {
            return "There is no such engine in the DB";
        }
    }


    @PostMapping("/change_tool")                      /// CHANGE CRITICAL VALUE IN DB ///
    @ResponseBody
    public String changeTool(ToolForm toolForm) {
        return toolService.updateTool(toolForm.getName(), toolForm.getValue());
    }


    @PostMapping("/last_values")                      /// GET LAST VALUES FROM DB ///
    @ResponseBody
    public String getLV(PeriodForm periodForm) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("critical_values", toolService.getAllCriticalsMap());
        jsonObject.put("last_values", valueService.getLastVals(periodForm.getTime_then(),
                periodForm.getTime_now(), periodForm.getEngine(), periodForm.getTool()));
        System.out.println(jsonObject);
        return jsonObject.toString();
    }


}
