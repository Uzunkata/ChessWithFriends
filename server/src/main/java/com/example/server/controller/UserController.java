package com.example.server.controller;

import com.example.server.dto.PasswordRequestModel;
import com.example.server.dto.RecipientConfirmation;
import com.example.server.exception.CustomException;
import com.example.server.exception.UserAlreadyExists;
import com.example.server.model.PasswordReset;
import com.example.server.model.User;
import com.example.server.model.Verified;
import com.example.server.repository.PasswordResetRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.VerifiedRepository;
import com.example.server.security.JwtProvider;
import com.example.server.service.EmailService;
import com.github.tsohr.JSONObject;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;

//import com.example.server.util.Provider;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerifiedRepository verifiedRepository;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private EmailService emailService;

    @Value("${oc.email.template.confirmation}")
    private String emailVerificationTemplate;

    @Value("${oc.email.template.password.reset}")
    private String passwordResetTemplate;

    public void processOAuthPostLogin(String email) {
        User existUser = userRepository.findByEmail(email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(email);
            userRepository.save(newUser);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody User user) throws MessagingException, TemplateException, IOException, UserAlreadyExists, CustomException {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            if(userRepository.findByUsername(user.getUsername()) == null) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setVerified(false);
//            user.setProvider(Provider.LOCAL);
                userRepository.save(user);

                String hash = jwtProvider.generateEmailVerificationHash();
                saveEmailVerification(hash, user);

                RecipientConfirmation recipient = getRecipient(hash, user.getEmail(), "Confirm your email at Chess With Friends", "bonus text!");
                emailService.sendConfirmationMail(recipient, emailVerificationTemplate);

                return ResponseEntity.ok("user has been created");
            }else {
                throw new UserAlreadyExists("User with this username already exists!");
            }
        } else {
            throw new UserAlreadyExists("User with this email already exists!");
        }
    }

    private void saveEmailVerification(String hash, User user) {
        Verified emailVerification = new Verified();
        emailVerification.setHash(hash);
        emailVerification.setUser(user);
        verifiedRepository.save(emailVerification);
    }

    private RecipientConfirmation getRecipient(String hash, String email, String subject, String bonusText) {
        RecipientConfirmation recipient = new RecipientConfirmation();
        recipient.setEmail(email);
        recipient.setHash(hash);
        recipient.setSubject(subject);
        recipient.setText(bonusText);
        return  recipient;
    }

    @PostMapping(value = "/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "hash") String hash) throws CustomException {

        Verified emailVerification = verifiedRepository.findByHash(hash);

        if (emailVerification != null) {
            User user = emailVerification.getUser();
            LocalDateTime date = emailVerification.getDateCreated();

            if(date.plusMinutes(30L).isBefore(LocalDateTime.now())){
                verifiedRepository.delete(emailVerification);
                userRepository.delete(user);

                throw new CustomException("Expired, try again");
            }

            user.setVerified(true);// verify user

            verifiedRepository.delete(emailVerification);

            return ResponseEntity.ok("user validated");
        }
        throw new CustomException("Already confirmed or wrong url");
    }

    //send email for password reset
    @PostMapping(value = "/send-password-reset")
    public ResponseEntity<?> sendPasswordReset(@RequestBody String email) throws MessagingException, IOException, TemplateException, CustomException {
        User user = getUserFromEmailJson(email);
        if (user != null) {
            String hash = jwtProvider.generateEmailVerificationHash();

            savePasswordReset(hash, user);//saves password reset hash with the user's id in the db

            RecipientConfirmation recipient = getRecipient(hash, user.getEmail(), "Reset your password at Chess with friends", "bonus text");

            emailService.sendConfirmationMail(recipient, passwordResetTemplate);

            return ResponseEntity.ok("email send");
        } else {
            throw new CustomException("No such email is registered!");
        }
    }

    private User getUserFromEmailJson(String emailJson) {
        JSONObject jsonObject = new JSONObject(emailJson);
        String emailString = jsonObject.getString("email");
        return userRepository.findByEmail(emailString);
    }

    public void savePasswordReset(String hash, User user) {
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setHash(hash);
        passwordReset.setUser(user);
        passwordResetRepository.save(passwordReset);
    }

    @PostMapping(value = "/reset-password-request")
    public ResponseEntity<?> passwordReset(@RequestBody PasswordRequestModel passwordRequestModel) {
        String hash = passwordRequestModel.getHash();
        PasswordReset passwordReset = passwordResetRepository.findByHash(hash);
        LocalDateTime date = passwordReset.getDateCreated();

        if (passwordReset != null) {
            if (date.plusMinutes(30L).isBefore(LocalDateTime.now())) {
                passwordResetRepository.delete(passwordReset);
                return ResponseEntity.ok("Expired");
            }
            String password = passwordRequestModel.getPassword();

            User user = passwordReset.getUser();
            user.setPassword(passwordEncoder.encode(password));

            passwordResetRepository.delete(passwordReset);

            return ResponseEntity.ok("Password changed");
        } else {
            return ResponseEntity.ok("hash not valid or expired");
        }
    }
}

