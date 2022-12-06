package com.client.redis.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@Builder
@RedisHash("Cliente")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClienteRedis {
    @Id
    private String cpf;

    private String nome;

    private String sobrenome;

    private String email;

    private String ddd;


    private String telefone;
}
