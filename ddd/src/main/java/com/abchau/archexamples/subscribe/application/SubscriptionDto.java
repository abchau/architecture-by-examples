package com.abchau.archexamples.subscribe.application;

import java.time.ZonedDateTime;

import lombok.Builder;
@Builder
public record SubscriptionDto(Long id, String email, String status, ZonedDateTime subscribedAt) {}
