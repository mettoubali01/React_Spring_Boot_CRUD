package com.example.crud_react_js.respository;

import com.example.crud_react_js.beans.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Long> {
}
