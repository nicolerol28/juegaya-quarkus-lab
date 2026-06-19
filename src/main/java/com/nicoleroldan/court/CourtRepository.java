package com.nicoleroldan.court;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourtRepository implements PanacheRepository<Court> {
}