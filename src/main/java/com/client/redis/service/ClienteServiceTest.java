package com.client.redis.service;

import com.client.redis.dto.ClienteRequestDTO;
import com.client.redis.dto.ClienteResponseDTO;
import com.client.redis.entity.Cliente;
import com.client.redis.repository.ClienteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteServiceTest {
    @MockBean
    private ClienteRepository clienteRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClienteService clienteService;

    private ClienteRequestDTO clienteRequestDTO;
    private Cliente cliente;
    private Cliente clienteSalvo;
    private ClienteResponseDTO clienteResponseDTO;


    @BeforeEach
    void init() {

        clienteRequestDTO = ClienteRequestDTO.builder()
                .nomeCompleto("Mateus Alves")
                .cpf("888.888.888.88")
                .email("ariel@gmail.com")
                .build();

        clienteResponseDTO = ClienteResponseDTO.builder()
                .nomeCompleto("Mateus Alves")
                .cpf("888.888.888.88")
                .enderecoEletronico("ariel@gmail.com")
                .build();


        cliente = Cliente.builder()
                .nome("Mateus")
                .sobrenome("Alves")
                .cpf("888.888.888.88")
                .build();

        clienteSalvo = Cliente.builder()
                .id(1L)
                .nome("Ariel")
                .sobrenome("Tintel")
                .cpf("888.888.888.88")
                .build();

        when(clienteRepository.save(any())).thenReturn(cliente);

    }

    @Test
    public void criarTest() {


        when(modelMapper.<Publisher<?>>map(clienteRequestDTO, Cliente.class)).thenReturn(cliente);
        when((Publisher<?>) clienteRepository.save(cliente)).thenReturn(clienteSalvo);
        when((Publisher<?>) modelMapper.map(clienteSalvo, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);

        ClienteResponseDTO clienteResponseDTOSalvo = clienteService.criar(clienteRequestDTO);
        Assertions.assertNotNull(clienteResponseDTOSalvo);
        Assertions.assertEquals("888.888.888.88", clienteResponseDTOSalvo.getCpf());
        Mockito.verify(clienteRepository, Mockito.times(1)).save(cliente);
    }

    @Test
    public void listarClientesTest() {

        Cliente cliente = Cliente.builder()
                .id(1L)
                .nome("Mateus Alves")
                .cpf("888.888.888.88")
                .build();
        Cliente cliente2 = Cliente.builder()
                .id(2L)
                .nome("Mateus Alves")
                .cpf("888.888.888.88")
                .build();
        Cliente cliente3 = Cliente.builder()
                .id(3L)
                .nome("Mateus Alves")
                .cpf("888.888.888.88")
                .build();
        List<Cliente> clienteList = Arrays.asList(cliente, cliente2, cliente3);

        when(clienteRepository.findAll()).thenReturn(clienteList);


        when(modelMapper.<Publisher<?>>map(cliente, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);
        when((Iterable<? extends Publisher<?>>) modelMapper.map(cliente2, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);
        when((Iterable<? extends Publisher<?>>) modelMapper.map(cliente3, ClienteResponseDTO.class)).thenReturn(clienteResponseDTO);

        List<ClienteResponseDTO> listaClientes = clienteService.listarClientes(null);
        Assertions.assertNotNull(listaClientes);
        Assertions.assertEquals(3, listaClientes.size());
        Mockito.verify(clienteRepository, Mockito.times(1)).findAll();
    }

}
