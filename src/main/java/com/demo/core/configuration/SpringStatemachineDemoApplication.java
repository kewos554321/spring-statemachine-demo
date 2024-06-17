package com.demo.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;

import com.demo.core.enumerate.Events;
import com.demo.core.enumerate.States;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
public class SpringStatemachineDemoApplication implements CommandLineRunner {

	@Autowired
	private StateMachine<States, Events> stateMachine;

	public static void main(String[] args) {
		SpringApplication.run(SpringStatemachineDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// stateMachine.sendEvent(Mono.just(MessageBuilder
		// 		.withPayload(Events.COIN).build()));
    	// stateMachine.sendEvent(Events.COIN);
		stateMachine.startReactively().subscribe(); 
		stateMachine
			.sendEvent(Mono.just(MessageBuilder
				.withPayload(Events.COIN).build()))
			.subscribe();
		stateMachine
			.sendEvent(Mono.just(MessageBuilder
				.withPayload(Events.PUSH).build()))
			.subscribe();
		stateMachine
			.sendEvent(Mono.just(MessageBuilder
				.withPayload(Events.COIN).build()))
			.subscribe();
		stateMachine
			.sendEvent(Mono.just(MessageBuilder
				.withPayload(Events.COIN).build()))
			.subscribe();
		log.info("stateMachine={}", stateMachine.getId());
	}

}
