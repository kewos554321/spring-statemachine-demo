package com.demo.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import com.demo.core.enumerate.Events;
import com.demo.core.enumerate.States;

@SpringBootApplication
public class SpringStatemachineDemoApplication implements CommandLineRunner {

	@Autowired
	private StateMachine<States, Events> stateMachine;

	public static void main(String[] args) {
		SpringApplication.run(SpringStatemachineDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		stateMachine.sendEvent(Events.E1);
    	stateMachine.sendEvent(Events.E2);
	}

}
