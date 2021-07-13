package com.cos.blog.repository;

import java.io.Console;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;


public interface BoardRepository extends JpaRepository<Board, Integer>{
	
}
