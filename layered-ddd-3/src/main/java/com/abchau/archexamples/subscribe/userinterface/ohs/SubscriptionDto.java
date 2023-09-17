package com.abchau.archexamples.subscribe.userinterface.ohs;

import java.time.LocalDateTime;

import lombok.Builder;

// Published Language
@Builder
record SubscriptionDto(String email, String status) {}
