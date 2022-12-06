package com.client.redis.repository;

import com.client.redis.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

    public List<Cliente> findByNomeContainingIgnoreCase(String nome);
    public Cliente findByEmail(String email);
    public Cliente findByCpf(String cpf);
}
