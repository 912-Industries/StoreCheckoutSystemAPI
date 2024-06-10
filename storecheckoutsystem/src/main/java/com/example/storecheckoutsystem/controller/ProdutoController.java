package com.example.storecheckoutsystem.controller;

import com.example.storecheckoutsystem.model.CategoriaProduto;
import com.example.storecheckoutsystem.model.PrecoProduto;
import com.example.storecheckoutsystem.model.Produto;
import com.example.storecheckoutsystem.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<Iterable<Produto>> pesquisaProduto() {
        Iterable<Produto> produtos = produtoService.pesquisaProdutos();
        return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Integer id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Produto> cadastroProduto(@Validated @RequestBody Produto produto) {
        Produto novoProduto = produtoService.cadastroProduto(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable int id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.editarProduto(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable int id) {
        produtoService.excluirProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quantidade/{id}")
    public ResponseEntity<Integer> buscarQuantidadeProduto(@PathVariable int id) {
        int quantidade = produtoService.buscarQuantidadeProduto(id);
        return ResponseEntity.ok(quantidade);
    }

    @PutMapping("/compra/{id}")
    public ResponseEntity<Produto> pedidoCompra(@PathVariable int id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.pedidoCompra(id, produto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @PostMapping("/finalizar-compra")
    public ResponseEntity<Void> removerProdutos(@RequestBody List<Map<String, Object>> produtos) {
        produtoService.removerProdutos(produtos);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/precoProduto")
    public ResponseEntity<PrecoProduto> buscarPrecoProdutoPorId(@PathVariable Integer id) {
        PrecoProduto precoProduto = precoProdutoService.buscarPrecoProdutoPorId(id);
        return ResponseEntity.ok(precoProduto);
    }

    @GetMapping("/{id}/categoria")
    public ResponseEntity<CategoriaProduto> buscarCategoriaPorId(@PathVariable Integer id) {
        CategoriaProduto categoria = categoriaService.buscarCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }
}
