package com.authValidation.jwttoken.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.authValidation.jwttoken.model.beans.LoginRequest;
import com.authValidation.jwttoken.model.beans.ResponseBean;
import com.authValidation.jwttoken.model.entity.User;
import com.authValidation.jwttoken.service.inf.JwtTokenServiceInf;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private JwtTokenServiceInf jwtTokenServiceInf;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@CrossOrigin("*")
    @PostMapping("/signin")
    @ResponseBody
    public ResponseEntity<ResponseBean> login(@RequestBody LoginRequest loginRequest) {
    	System.out.println(loginRequest.getUsername()+"== and == "+loginRequest.getPassword());
        String token = jwtTokenServiceInf.login(loginRequest.getUsername(),loginRequest.getPassword());
        HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headerlist.add("X-Requested-With");
        headerlist.add("Authorization");
        headers.setAccessControlAllowHeaders(headerlist);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", token);
        return new ResponseEntity<ResponseBean>(new ResponseBean("Token created successfully", 200, token),HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@CrossOrigin("*")
    @PostMapping("/signout")
    @ResponseBody
    public ResponseEntity<ResponseBean> logout (@RequestHeader(value="Authorization") String token) {
        HttpHeaders headers = new HttpHeaders();
      if (jwtTokenServiceInf.logout(token)) {
          headers.remove("Authorization");
          return new ResponseEntity<ResponseBean>(new ResponseBean("signout successfully", 200, token),HttpStatus.OK);
      }
        return new ResponseEntity<ResponseBean>(new ResponseBean("Logout Failed", 200, token),HttpStatus.NOT_FOUND);
    }

   
    @PostMapping("/valid/token")
    @ResponseBody
    public Boolean isValidToken (@RequestHeader(value="Authorization") String token) {
        return true;
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/signin/token")
    @CrossOrigin("*")
    @ResponseBody
    public ResponseEntity<ResponseBean> createNewToken (@RequestHeader(value="Authorization") String token) {
        String newToken = jwtTokenServiceInf.createToken(token);
        HttpHeaders headers = new HttpHeaders();
        List<String> headerList = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerList.add("Content-Type");
        headerList.add(" Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");
        headers.setAccessControlAllowHeaders(headerList);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", newToken);
        return new ResponseEntity<ResponseBean>(new ResponseBean("Get Token successfully", 200, token),HttpStatus.OK);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody User user) {
		User userExists = jwtTokenServiceInf.getUser(user.getEmail());
		if (userExists != null) {
			return new ResponseEntity<ResponseBean>(new ResponseBean("Email id already existed", 200, user) ,HttpStatus.OK);
		}
		jwtTokenServiceInf.save(user);
		 return new ResponseEntity<ResponseBean>(new ResponseBean("User register successfully", 200, user),HttpStatus.OK);
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @CrossOrigin("*")
    @GetMapping("/findById/{id}")
    public ResponseEntity<ResponseBean> finfById (@PathVariable(value="id")long id) {
    	User userExists = jwtTokenServiceInf.findById(id);
		if (userExists != null) {
			return new ResponseEntity<ResponseBean>(new ResponseBean("User find successfully", 200, userExists) ,HttpStatus.OK);
		}
		
		 return new ResponseEntity<ResponseBean>(new ResponseBean("User does not existed by :"+id, 200, id),HttpStatus.OK);
    	
    }
    
}
