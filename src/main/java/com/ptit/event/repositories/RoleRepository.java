package com.ptit.event.repositories;

import com.ptit.event.entities.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query(nativeQuery = true, value = "select * from role r where r.name in :permissions")
  List<Role> findByNameIn(List<String> permissions);
}
