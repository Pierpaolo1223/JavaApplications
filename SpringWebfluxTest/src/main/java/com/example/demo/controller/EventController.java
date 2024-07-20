package com.example.demo.controller;

import java.time.Duration;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

@RestController
public class EventController {

	private ConcurrentHashMap<String, Flux<String>> userFluxMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, Disposable> userDisposableMap = new ConcurrentHashMap<>();

	/**
	 * @param userId
	 * @return
	 */

	@GetMapping(value = "/events/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getEvents(@PathVariable String userId) { // method that get events each second.
		return userFluxMap.computeIfAbsent(userId, id -> {
			Flux<String> flux = Flux.interval(Duration.ofSeconds(2))
					.map(sequence -> "Messaggio per l'utente " + id + ": evento numero " + sequence)
					.publish().autoConnect(0, disposable -> userDisposableMap.put(userId, disposable));
			return flux;
		});
	}

	/**
	 * @param userId
	 */
	@GetMapping(value = "/dispose/{userId}") // Metodo che consente di cancellare il flusso di eventi.
	public ResponseEntity<String> disposeUserFlux(@PathVariable String userId) {
		try {
			Disposable disposable = userDisposableMap.get(userId);
			if (disposable != null) {
				disposable.dispose();
				userFluxMap.remove(userId);
				userDisposableMap.remove(userId);
				return ResponseEntity.ok("Flusso annullato con successo per l'utente " + userId);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nessun flusso trovato per l'utente " + userId);
			}
		} catch (CancellationException e) {
			// Log l'eccezione se necessario
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Errore nell'annullamento del flusso per l'utente " + userId);
		}
	}

	// metodo just per ricevere un singolo elemento,metodo fromIterable per
	// riceverne più di uno.

}
