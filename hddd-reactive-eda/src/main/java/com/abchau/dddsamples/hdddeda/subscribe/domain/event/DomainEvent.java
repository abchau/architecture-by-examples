package com.abchau.archexamples.hdddeda.subscribe.domain.event;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public abstract class DomainEvent {

	@JsonIgnore
	protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

	protected final String specversion = "1.0";

	protected final String type = "TBC";

	protected final String source = "TBC";

	protected final String id;

	protected final String subject = "TBC";

	protected final String time;

	@JsonProperty("datacontenttype")
	protected String dataContentType = "application/json";

	// protected Map<String, Object> data;

	protected Map<String, Object> metadata;

	public DomainEvent() {
		this.id = UUID.randomUUID().toString();
		this.time = DATE_TIME_FORMATTER.format(ZonedDateTime.now(Clock.systemUTC()));
	}

	public Map<String, Object> defaultMetadata() {
		return Map.of(
			"os", System.getProperty("os.name"),
			"arch", System.getProperty("os.arch")
		);
	}
	
	public void addMetadata(final String key, final Object value) {
		this.metadata.put(key, value);
	}

	public void addMetadata(final Map<String, Object> customMetadata) {
		this.metadata = Stream.concat(this.metadata.entrySet().stream(), customMetadata.entrySet().stream())
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
				(value1, value2) -> value2));
	}

}
