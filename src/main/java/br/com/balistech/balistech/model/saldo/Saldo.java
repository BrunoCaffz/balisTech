package br.com.balistech.balistech.model.saldo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Saldo {
    private Long id;
    private Long idUsuario;
    private double valor;
    private LocalDate ultimaAtualizacao;
}