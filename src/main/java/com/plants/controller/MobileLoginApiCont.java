package com.plants.controller;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import com.plants.Dao.MobileApiDao;
import com.plants.Dao.userDao;
import com.plants.Service.OTPService;
import com.plants.Service.SmsService;
import com.plants.entities.AgentMain;
import com.plants.entities.user;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/MobileLoginApi")
public class MobileLoginApiCont {
	
	 @Autowired
	 private OTPService otpService;
	  
	 @Autowired
	 private SmsService smsService;
	 
	 @Autowired
	 userDao userdao;
	 
	 @Autowired
		private MobileApiDao mobileApiDao;
	 
	 @PostMapping("/sendOTP")
	 public ResponseEntity<Map<String, String>> sendOTP(@RequestBody Map<String, String> request) {     
		 Map<String, String> response = new HashMap<>();  
		 String mobileNumber = request.get("mobileNumber");
	        
	     if (mobileNumber == null || mobileNumber.isEmpty()) {
	         return ResponseEntity.badRequest().body(Map.of("error", "Mobile number is required"));
	     }
	     String otp = otpService.generateOTP(mobileNumber);

	     smsService.sendOtp(mobileNumber, otp);
	    
	     response.put("message", "OTP sent successfully!");
	    
	     return ResponseEntity.ok(response);
	 }
	 
	 
	 @PostMapping("/verifyOTP")
		public ResponseEntity<Map<String, Object>> verifyOTP(@RequestBody Map<String, String> request) {
			Map<String, Object> response = new HashMap<>();
			String mobileNumber = request.get("mobileNumber");
			String otp = request.get("otp");
			List<AgentMain> findMob = mobileApiDao.findMobileNumber(mobileNumber);

			if (mobileNumber == null || mobileNumber.isEmpty() || otp == null || otp.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("error", "Mobile number and OTP are required"));
			}

			boolean isOtpValid = otpService.verifyOtp(mobileNumber, otp);

			if (isOtpValid) {
				if (findMob.isEmpty()) {
					response.put("AgentExit", "false");
					response.put("message", "OTP Verified Successfully");
				} else {
					response.put("Object", findMob);
					response.put("AgentExit", "true");
					response.put("message", "OTP Verified Successfully");
				}
				return ResponseEntity.ok(response);
			} else {
				response.put("message", "Invalid or Expired OTP");
				return ResponseEntity.ok(response);
			}
		}
	 
	 @PostMapping("/getPersonalDetailProfileAgent")
		public ResponseEntity<Map<String, String>> getPersonalDetailProfileAgent(@RequestBody AgentMain agentMain) {
		    Map<String, String> response = new HashMap<>();
		    System.out.println(" agent ---- " + agentMain.toString());
		    
		    AgentMain findEmailAndMobile = this.userdao.findEmailAndMobileAg(agentMain.getEmailId(), agentMain.getMobileNumber());
		    if(findEmailAndMobile != null) {
		    	if(agentMain.getEmailId().equals(findEmailAndMobile.getEmailId())) {
			    	response.put("message", "Email Already Exit !!");
			    	return ResponseEntity.ok(response);
			    }
		    }
		    if(findEmailAndMobile != null) {
		    	 if(agentMain.getMobileNumber().equals(findEmailAndMobile.getMobileNumber())) {
		 	    	response.put("message", "Mobile Number Exit !!");
		 	    	return ResponseEntity.ok(response);
		 	    }
		    }
		    agentMain.setAgentVerified(false);
		    agentMain.setActiveAgent(false);
			AgentMain savedAgent = this.userdao.save(agentMain);
			String agentID = String.valueOf(savedAgent.getAgentIDPk()); 
			response.put("agentID",agentID );
		    return ResponseEntity.ok(response);
		}
		
	 @PostMapping("/getselfiImage")
	 public ResponseEntity<Map<String, String>> getselfiImage(@RequestParam("selfiImage") MultipartFile file, @RequestParam("agentID") String agentID) {
	     Map<String, String> response = new HashMap<>();

	     if (file.isEmpty()) {
	         response.put("message", "Please select your selfie image.");
	         return ResponseEntity.badRequest().body(response);
	     }

	     try {
	         // Specify the external directory to save the uploaded file
	         String externalDirectory = "src/main/resources/static/uploadImages";
	         File directory = new File(externalDirectory);
	         
	         // Create the directory if it does not exist
	         if (!directory.exists()) {
	             directory.mkdirs();
	         }

	         // Save the file in the specified external directory
	         Path filePath = Paths.get(externalDirectory, file.getOriginalFilename());
	         Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	         file.getInputStream().close();

	         // Manually construct the image URL using the correct path
	         String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
	                             .path("/uploadImages/")
	                             .path(file.getOriginalFilename())
	                             .toUriString();

	         // Debug log to verify the generated image URL
	         System.out.println("imageUrl -------" + imageUrl );

	         // Update the database record with the image name or URL (optional)
	         this.userdao.updateSelfiImage(file.getOriginalFilename(), agentID);

	         // Prepare the response
	         response.put("agentID", agentID);
	         response.put("message", "Image uploaded successfully.");
	         response.put("imageUrl", imageUrl);

	     } catch (IOException e) {
	         e.printStackTrace();
	         response.put("message", "Something went wrong while uploading the image.");
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	     }

	     return ResponseEntity.ok(response);
	 }
		@PostMapping("/getaddressDetail")
		public ResponseEntity<Map<String, String>> getaddressDetail(@RequestBody Map<String, String> request) {
			 Map<String, String> response = new HashMap<>();
			 String AgentIDPk = request.get("AgentIDPk");
			 String state = request.get("state");
			 String city = request.get("city");
			 String address = request.get("address");
			 String pincode = request.get("pincode");
			 String latitude = request.get("latitude");
			 String longitude = request.get("longitude");
			 
			 this.userdao.updateAddress(state,city,address, pincode,latitude, longitude, AgentIDPk);
			 response.put("agentID",AgentIDPk );
			 response.put("message", "Address Addedd successfully");
			 return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getaddharImage")
		public ResponseEntity<Map<String, String>> getaddharImage(@RequestParam("aadharImg") MultipartFile file, @RequestParam("agentID") String agentID , @RequestParam("aadhaarNumber") String aadhaarNumber) {
		    Map<String, String> response = new HashMap<>();

		    if (file.isEmpty()) {
		        response.put("message", "Please select your Aadhar image.");
		        return ResponseEntity.badRequest().body(response);
		    }

		    try {
		    	String externalDirectory = "src/main/resources/static/uploadImages";
		         File directory = new File(externalDirectory);
		         
		         // Create the directory if it does not exist
		         if (!directory.exists()) {
		             directory.mkdirs();
		         }

		         // Save the file in the specified external directory
		         Path filePath = Paths.get(externalDirectory, file.getOriginalFilename());
		        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        file.getInputStream().close();
		        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploadImages/")
                        .path(file.getOriginalFilename())
                        .toUriString();
		       
		        this.userdao.updateaddharImage(file.getOriginalFilename(), aadhaarNumber, agentID);
		       
		        response.put("agentID", agentID);
		        response.put("message", "Image Aadhar uploaded successfully.");
		        response.put("imageUrl", imageUrl);
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		        response.put("message", "Something went wrong while uploading the image.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }

		    return ResponseEntity.ok(response);
		}
		

		@PostMapping("/getbankAccount")
		public ResponseEntity<Map<String, String>> getbankAccount(@RequestBody Map<String, String> request) {
			 Map<String, String> response = new HashMap<>();
			 String AgentIDPk = request.get("AgentIDPk");
			 String accHolderName = request.get("accHolderName");
			 String accNumber = request.get("accNumber");
			 String bankName = request.get("bankName");
			 String IFSCCode = request.get("IFSCCode");
			 String AccMobNumber = request.get("AccMobNumber");
			
			 this.userdao.updateBankAccount(accHolderName,accNumber,bankName, IFSCCode,AccMobNumber, AgentIDPk);
			 response.put("agentID",AgentIDPk );
			 response.put("message", "Bank Account Addedd successfully");
			 return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getBankAccPassBookImage")
		public ResponseEntity<Map<String, Object>> getBankAccPassBookImage(@RequestParam("BankAccPassBookImage") MultipartFile file, @RequestParam("agentID") String agentID) {
		    Map<String, Object> response = new HashMap<>();
		    if (file.isEmpty()) {
		        response.put("message", "Please select your Bank Pass book image.");
		        return ResponseEntity.badRequest().body(response);
		    }
		    try {
		    	String externalDirectory = "src/main/resources/static/uploadImages";
		         File directory = new File(externalDirectory);
		         
		         // Create the directory if it does not exist
		         if (!directory.exists()) {
		             directory.mkdirs();
		         }

		         // Save the file in the specified external directory
		         Path filePath = Paths.get(externalDirectory, file.getOriginalFilename());
		        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		        file.getInputStream().close();

		        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/uploadImages/")
                        .path(file.getOriginalFilename())
                        .toUriString();

		        this.userdao.updateBankPassBookImg(file.getOriginalFilename(), agentID);

		        AgentMain allRecordFind = this.userdao.findAgentID(agentID);
		        response.put("Object", allRecordFind); 
		        response.put("agentID", agentID);
		        response.put("message", "Bank Pass book uploaded successfully.");
		        response.put("imageUrl", imageUrl);
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		        response.put("message", "Something went wrong while uploading the image.");
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		    }

		    return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getliveLocationLatiAndLong")
		public ResponseEntity<Map<String, String>> getliveLocationLatiAndLong(@RequestBody Map<String, String> request) {
			 Map<String, String> response = new HashMap<>();
			 String AgentIDPk = request.get("AgentIDPk");
			 String Agentlatitude = request.get("Agentlatitude");
			 String AgentLongtitude = request.get("AgentLongtitude");
			
			 AgentMain getActiveRecord = this.userdao.findAgentID(AgentIDPk);
			 
			 if(getActiveRecord != null) {
				 if(getActiveRecord.isActiveAgent() == true ) {
					 System.out.println("---AgentIDPk--- " + AgentIDPk);
					 System.out.println("---Agentlatitude--- " + Agentlatitude);
					 System.out.println("---AgentLongtitude--- " + AgentLongtitude);
					 this.userdao.updateliveLocation(Agentlatitude,AgentLongtitude, AgentIDPk);
					 response.put("message", "Location Updated.");
				}else {
					 response.put("message", "Agent is Not Active");
				}
				 
			 }else {
				 response.put("message", "No Record Found Agent");
			 }
			
			 return ResponseEntity.ok(response);
		}
		
		@PostMapping("/getActiveAgentToggle")
		public ResponseEntity<Map<String, String>> getActiveAgentToggle(@RequestBody Map<String, String> request) {
			 Map<String, String> response = new HashMap<>();
			 String AgentIDPk = request.get("AgentIDPk");
			 boolean isActiveAgent = request.get("isActiveAgent") != null;
			
			 AgentMain getagentRecord = this.userdao.findAgentID(AgentIDPk);
			 
			 if(getagentRecord != null) {
				 System.out.println("---AgentIDPk--- " + AgentIDPk);
			     System.out.println("---isActiveAgent--- " + isActiveAgent);
			     
			     this.userdao.UpdateagentActive(isActiveAgent, getagentRecord.getAgentIDPk());
				 response.put("message", "Agent Active.");
			 }else {
				 response.put("message", "No Record Found Agent");
			 }
		    	
			 return ResponseEntity.ok(response);
		}
		
}
