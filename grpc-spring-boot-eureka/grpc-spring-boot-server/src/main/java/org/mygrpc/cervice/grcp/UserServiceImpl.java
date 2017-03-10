package org.mygrpc.cervice.grcp;

import org.lognet.springboot.grpc.GRpcService;
import org.mygrpc.grpc.services.UserDetail;
import org.mygrpc.grpc.services.UserServiceGrpc;
import org.mygrpc.grpc.services.user;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.stub.StreamObserver;

@GRpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
	
	private Logger logger = LoggerFactory.getLogger(UserServiceGrpc.class);
	
	@Override
	public void createUser(UserDetail request, StreamObserver<user> responseObserver) {
		logger.debug(request.toString());
		System.err.println(request);
		user.Builder builder = user.newBuilder();
		user response = builder.setName(request.getName()).build();
		logger.debug(response.toString());
		responseObserver.onNext(response);
		responseObserver.onCompleted();

	}
}
