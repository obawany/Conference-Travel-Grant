/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author Ocean
 */
@Entity
public class AdminAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String userId;
    @Lob
    private byte[] password;
    @Lob
    private byte[] salt;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String getUserId() {
        return userId;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    
    public byte[] getPassword(){
        return password;
    }
    
    public void setPassword(byte[] passwd){
        this.password = passwd;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
