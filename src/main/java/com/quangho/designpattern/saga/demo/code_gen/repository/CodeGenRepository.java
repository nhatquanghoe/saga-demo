package com.quangho.designpattern.saga.demo.code_gen.repository;

import com.quangho.designpattern.saga.demo.code_gen.model.CodeGen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeGenRepository extends JpaRepository<CodeGen, Integer> {
    CodeGen findById(int id);
}
