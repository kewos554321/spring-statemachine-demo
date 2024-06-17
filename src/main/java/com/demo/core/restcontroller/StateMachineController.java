package com.demo.core.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.StateMachineEventResult.ResultType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.core.enumerate.Events;
import com.demo.core.enumerate.States;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class StateMachineController {

	//tag::snippetA[]
	@Autowired
	private StateMachine<States, Events> stateMachine;
	//end::snippetA[]

	//tag::snippetB[]
	@GetMapping("/state")
	public Mono<States> state() {
		return Mono.defer(() -> Mono.justOrEmpty(stateMachine.getState().getId()));
	}
	//end::snippetB[]

	//tag::snippetC[]
	@PostMapping("/events")
	public Flux<EventResult> events(@RequestBody Flux<EventData> eventData) {
		return eventData
			.filter(ed -> ed.getEvent() != null)
			.map(ed -> MessageBuilder.withPayload(ed.getEvent()).build())
			.flatMap(m -> stateMachine.sendEvent(Mono.just(m)))
			.map(EventResult::new);
	}
	//end::snippetC[]

	public static class EventData {
		private Events event;

		public Events getEvent() {
			return event;
		}

		public void setEvent(Events event) {
			this.event = event;
		}
	}

	public static class EventResult {

		private final StateMachineEventResult<States, Events> result;
		
		EventResult(StateMachineEventResult<States, Events> result) {
			this.result = result;
		}

		public ResultType getResultType() {
			return result.getResultType();
		}

		public Events getEvent() {
			return result.getMessage().getPayload();
		}
	}
}