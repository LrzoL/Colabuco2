package com.colabuco.model.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Random;

@Entity
@Table(name = "carrinho_de_compra")
public class CarrinhoDeComprasModel {
    Random random = new Random();

    @Id
    @Column(name = "id_de_carrinho")
    private String id;

    @OneToOne(mappedBy = "carrinho")
    private PedidoModel pedido;

    @Column(name = "valor")
    private double valor;
    private ArrayList<ProdutoModel> produtos;

    private String cpfCliente;

    @OneToOne
    @JoinColumn(name = "id_do_cliente")
    private ClienteModel cliente;

    //construtor
    public CarrinhoDeComprasModel(String cpfCliente) {
        this.id = Integer.toString(random.nextInt());
        this.valor = 0.0;
        this.produtos = new ArrayList<ProdutoModel>();
        this.cpfCliente = cpfCliente;
    }

    //getters
    public String getId() {
        return id;
    }
    public double getValor() {
        return valor;
    }
    public ArrayList<ProdutoModel> getProdutos() {
        return produtos;
    }
    public String getCpfCliente() {
        return cpfCliente;
    }

    //metódos
    public void adicionarProduto(ProdutoModel produto){
        this.produtos.add(produto);
        this.valor += produto.getPreco();
    }

    public void removerProduto(String idProduto){
        for (int i = 0; i < this.produtos.size(); i++){
            if (this.produtos.get(i).getId() == idProduto){
                this.produtos.remove(this.produtos.get(i));
                this.valor -= this.produtos.get(i).getPreco();
            }
        }
    }

    public void limpar(){
        this.produtos.clear();
        this.valor = 0.0;
    }

    public void fazerPedido(String endereco){
        pedido = new PedidoModel(this.id, this.cpfCliente, endereco, this.valor);
        this.cliente.registrarPedido(pedido);
    }
}
