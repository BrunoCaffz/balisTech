package br.com.balistech.balistech.model.metaFinanceira;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetaFinanceira {
    private Long id;
    private Long idUsuario;
    private String descricao;
    private double valorMeta;
    private LocalDate dataLimite;
    private double valorAtual;
}