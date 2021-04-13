package com.example.crud_react_js.respository;

import com.example.crud_react_js.beans.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActorRepository extends JpaRepository<Actor, Long> {
}
