package com.demo.core.configuration;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import com.demo.core.enumerate.Events;
import com.demo.core.enumerate.States;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

	@Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
            .withConfiguration()
                .autoStartup(true)
                .listener(listener());
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
            .withStates()
                .initial(States.LOCKED)
                    .states(EnumSet.allOf(States.class));
    }

	@Override
	public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
			throws Exception {
		transitions
				.withExternal()
						.source(States.LOCKED)
						.target(States.UNLOCKED)
						.event(Events.COIN)
						.and()
				.withExternal()
						.source(States.UNLOCKED)
						.target(States.LOCKED)
						.event(Events.PUSH);
	}

    @Bean
    public StateMachineListener<States, Events> listener() {
        return new StateMachineListenerAdapter<States, Events>() {
            @Override
            public void stateChanged(State<States, Events> from, State<States, Events> to) {
				if (from != null) {
					log.info("State change from {} to {}", from, to.getId());
				} else {
					log.info("State change to {}", to.getId());
				}
            }
        };
    }
}
