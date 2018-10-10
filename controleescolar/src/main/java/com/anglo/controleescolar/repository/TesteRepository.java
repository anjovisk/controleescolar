package com.anglo.controleescolar.repository;

import org.springframework.stereotype.Repository;

import com.anglo.controleescolar.repository.entity.Teste;

@Repository
public interface TesteRepository extends PagingAndSortingExtendedRepository<Teste, Integer> {

}
