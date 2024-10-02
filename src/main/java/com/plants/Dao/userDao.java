package com.plants.Dao;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.plants.entities.AgentMain;
import com.plants.entities.Plans;
import com.plants.entities.user;

import jakarta.transaction.Transactional;


public interface userDao extends CrudRepository<user, Integer>{
	
	@Query("select e FROM user e WHERE e.username = :username AND e.password = :password")
	public user finduserNameAndPassword(@Param("username") String username , @Param("password") String password);
	
	public AgentMain save(AgentMain agentMain);
	
	public Plans save(Plans plans);
	
	@Query("select e FROM AgentMain e WHERE e.emailId = :emailId OR e.mobileNumber = :mobileNumber")
	public AgentMain findEmailAndMobileAg(@Param("emailId") String emailId , @Param("mobileNumber") String mobileNumber);
	
	@Query("select e FROM AgentMain e WHERE e.AgentIDPk = :AgentIDPk ")
	public AgentMain findAgentID(@Param("AgentIDPk") String AgentIDPk);
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET a.selfiImage = :selfiImage WHERE a.AgentIDPk = :AgentIDPk")
    public void updateSelfiImage(@Param("selfiImage") String selfiImage , @Param("AgentIDPk") String AgentIDPk);
	
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET a.state = :state ,a.city = :city, a.address = :address, a.pincode = :pincode, a.latitude = :latitude,a.longitude = :longitude  WHERE a.AgentIDPk = :AgentIDPk")
    public void updateAddress(@Param("state") String state ,@Param("city") String city ,@Param("address") String address ,@Param("pincode") String pincode ,@Param("latitude") String latitude ,@Param("longitude") String longitude , @Param("AgentIDPk") String AgentIDPk);
	
	
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET  a.latitude = :latitude,a.longitude = :longitude  WHERE a.AgentIDPk = :AgentIDPk")
    public void updateliveLocation(@Param("latitude") String latitude ,@Param("longitude") String longitude , @Param("AgentIDPk") String AgentIDPk);
	
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET  a.isActiveAgent = :isActiveAgent  WHERE a.AgentIDPk = :AgentIDPk")
    public void UpdateagentActive(@Param("isActiveAgent") boolean isActiveAgent , @Param("AgentIDPk") int AgentIDPk);
	
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET a.aadharImg = :aadharImg , a.aadhaarNumber =:aadhaarNumber WHERE a.AgentIDPk = :AgentIDPk")
    public void updateaddharImage(@Param("aadharImg") String aadharImg,@Param("aadhaarNumber") String aadhaarNumber , @Param("AgentIDPk") String AgentIDPk);
	
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET a.accHolderName = :accHolderName ,a.accNumber = :accNumber, a.bankName = :bankName, a.IFSCCode = :IFSCCode, a.AccMobNumber = :AccMobNumber  WHERE a.AgentIDPk = :AgentIDPk")
    public void updateBankAccount(@Param("accHolderName") String accHolderName ,@Param("accNumber") String accNumber ,@Param("bankName") String bankName ,@Param("IFSCCode") String IFSCCode ,@Param("AccMobNumber") String AccMobNumber , @Param("AgentIDPk") String AgentIDPk);
	
	@Modifying
	@Transactional
    @Query("UPDATE AgentMain a SET a.BankAccPassBookImage = :BankAccPassBookImage  WHERE a.AgentIDPk = :AgentIDPk")
    public void updateBankPassBookImg(@Param("BankAccPassBookImage") String BankAccPassBookImage, @Param("AgentIDPk") String AgentIDPk);
	
	
	@Query("select e FROM AgentMain e where e.AgentVerified = false")
	public ArrayList<AgentMain> getpendingVerif();
	
	@Query("select e FROM AgentMain e where e.AgentVerified = true")
	public ArrayList<AgentMain> getVerified();
	
	@Query("select e FROM AgentMain e where e.isActiveAgent = true")
	public ArrayList<AgentMain> activeAgent();
}
