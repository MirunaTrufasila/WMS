package com.lts.controller;

import com.lts.config.ApplicationViews;
import com.lts.util.AdminConstants;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.lts.service.MessageService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Controller
@RequestMapping("/")
public class HomeController {

    private final InfoEndpoint infoEndpoint;
    private final Environment environment;
    private final MessageService messageService;
    private final BuildProperties buildProperties;

    @Autowired
    public HomeController(InfoEndpoint infoEndpoint,
                          Environment environment,
                          MessageService messageService,
                                                   BuildProperties buildProperties) {
        this.infoEndpoint = infoEndpoint;
        this.environment = environment;
        this.messageService = messageService;
        this.buildProperties = buildProperties;
    }

    @GetMapping
    public ModelAndView index(ModelMap modelMap, HttpSession session, HttpServletRequest request) {
        modelMap.addAttribute("info", infoEndpoint.info());
        if (environment.getActiveProfiles().length > 0) {
            modelMap.addAttribute("profile", environment.getActiveProfiles()[0]);
        }
        ModelAndView modelAndView = new ModelAndView(ApplicationViews.indexHtml);
        modelAndView.addObject("currentUserEditUrl", session.getAttribute("currentUserEditUrl"));
        // we pass the current context path to be used in index.html, dynamically
        modelAndView.addObject("currentContextPath", request.getContextPath() + "/");
        modelAndView.addObject("buildVersion", buildProperties.getVersion());
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String lang,
                              HttpServletRequest request,
                              HttpSession session) {
        ModelAndView modelAndView = new ModelAndView(ApplicationViews.loginHtml);
        if (lang == null) {
            String acceptLanguageHeader = request.getHeader("Accept-Language");
            if (ArrayUtils.contains(AdminConstants.SUPPORTED_LANGUAGES, acceptLanguageHeader)) {
                lang = acceptLanguageHeader;
            } else {
                lang = AdminConstants.DEFAULT_LANGUAGE;
            }
        }
        modelAndView.addObject("loginUsername", messageService.getMessageForLang("loginUsername", lang));
        modelAndView.addObject("loginPassword", messageService.getMessageForLang("loginPassword", lang));
        modelAndView.addObject("loginRememberMe", messageService.getMessageForLang("loginRememberMe", lang));
        modelAndView.addObject("loginLogin", messageService.getMessageForLang("loginLogin", lang));
        Object loginErrorCode = session.getAttribute("loginErrorCode");
        if (loginErrorCode instanceof String && !(((String) loginErrorCode).contains(" "))) {
            modelAndView.addObject("loginError", messageService.getMessageForLang((String) loginErrorCode, lang));
        } else {
            modelAndView.addObject("loginError", messageService.getMessageForLang("loginError", lang));
        }
        modelAndView.addObject("loginGetQrCodeButton", messageService.getMessageForLang("loginGetQrCodeButton", lang));
        modelAndView.addObject("currentContextPath", request.getContextPath() + "/");
        modelAndView.addObject("loginLogout", messageService.getMessageForLang("loginLogout", lang));
        modelAndView.addObject("loginProblems", messageService.getMessageForLang("loginProblems", lang));
        modelAndView.addObject("loginCallTehnic", messageService.getMessageForLang("loginCallTehnic", lang));
        modelAndView.addObject("currentLanguage", lang);
//        modelAndView.addObject("activeProfile", activeProfile != null && !activeProfile.isEmpty() ? activeProfile : "default");

        modelAndView.addObject("buildName", buildProperties.getName());
        modelAndView.addObject("buildVersion", buildProperties.getVersion());
        modelAndView.addObject("buildDate", DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).
                withLocale(Locale.UK).withZone(ZoneId.systemDefault()).format(buildProperties.getTime()));

        return modelAndView;
    }

    @GetMapping({
            "/administration",
            "/home",

            "/users",
            "/users/edit/{id}",
            "/users/create",

            "/employees",
            "/employees/edit/{id}",
            "/employees/create",

            "/furnizori",
            "/furnizori/edit/{id}",
            "/furnizori/create",

            "/facturi",
            "/facturi/edit/{id}",
            "/facturi/create",

            "/company-details",

            "/errors",
            "/errors/edit/{id}",

            "/activities",
            "/contact",

    })
    public ModelAndView navigation(HttpSession session, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView(ApplicationViews.indexHtml);
        modelAndView.addObject("currentUserEditUrl", session.getAttribute("currentUserEditUrl"));
        // we pass the current context path to be used in index.html, dynamically
        modelAndView.addObject("currentContextPath", request.getContextPath() + "/");
        modelAndView.addObject("buildVersion", buildProperties.getVersion());
        return modelAndView;
    }
}