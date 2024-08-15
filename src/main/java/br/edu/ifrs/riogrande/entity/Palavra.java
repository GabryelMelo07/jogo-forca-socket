package br.edu.ifrs.riogrande.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Palavra {
    private Integer id;
    private String conteudo;
}
