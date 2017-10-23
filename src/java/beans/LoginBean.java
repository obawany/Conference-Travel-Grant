/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import beans.AdminAccount;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ocean
 */
@Named(value = "loginBean")
@RequestScoped
public class LoginBean {
    
    private String userId;
    private String password;
    private String status;
    @PersistenceContext(unitName = "BookStore-warPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    
    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    public void login() {
         AdminAccount acc = em.find(AdminAccount.class, userId);
         if (acc != null) {
             try {
                 // check password
                 byte[] salt = acc.getSalt();
                 String saltString = new String(salt, "UTF-8");
                 String checkPass = saltString+password;
                 MessageDigest digest = MessageDigest.getInstance("SHA-256");
                 byte[] checkPassHash = digest.digest(checkPass.getBytes("UTF-8"));
                 if (Arrays.equals(checkPassHash, acc.getPassword())) {
                     //login ok - set user in session context
                     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
                     session.setAttribute("User", acc);
                     status="Login Successful - " + "Welcome Admin";
                 } else {
                    status="Invalid Login, Please Try again"; 
                 }
             } catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                 Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
             }
         } else {
             status="Invalid Login, Please Try again";
         }
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
}

