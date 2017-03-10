package org.mygrpc.controller;

import java.util.List;

import org.mygrpc.grpc.services.Gender;
import org.mygrpc.grpc.services.UserDetail;
import org.mygrpc.grpc.services.UserServiceGrpc;
import org.mygrpc.grpc.services.UserServiceGrpc.UserServiceBlockingStub;
import org.mygrpc.grpc.services.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@RestController
@RequestMapping("/grcp")
public class UserSender {
	
	@Autowired
    private DiscoveryClient discoveryClient;
 
    @RequestMapping(method = RequestMethod.GET, value = "/senduser")
    public ResponseEntity<?> api() {
    	user u=null;
    	List<ServiceInstance> server=discoveryClient.getInstances("grpc-server");
    	for (ServiceInstance serviceInstance : server) {
    		
			String hostName=serviceInstance.getHost();
			int gRpcPort=Integer.parseInt(serviceInstance.getMetadata().get("grpc.port"));
			
			ManagedChannel channel=ManagedChannelBuilder.forAddress(hostName,gRpcPort).usePlaintext(true).build();
	        UserServiceBlockingStub stub=UserServiceGrpc.newBlockingStub(channel);
	        
	        UserDetail user=UserDetail.newBuilder()
	        			.setName("Thamira")
	        			.setEmail("Thamira1005@gmail.com")
	        			.setAge(24).setGender(Gender.Male)
	        			.setPassword("password").build();
	        
	        u=stub.createUser(user);
		}
    	
        return ResponseEntity.ok("User "+u);
    }
	
	
}
