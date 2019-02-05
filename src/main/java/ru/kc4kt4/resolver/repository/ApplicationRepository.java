package ru.kc4kt4.resolver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kc4kt4.resolver.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
