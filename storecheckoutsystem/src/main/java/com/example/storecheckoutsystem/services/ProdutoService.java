package com.example.storecheckoutsystem.services;

import com.example.storecheckoutsystem.model.Markup;
import com.example.storecheckoutsystem.model.Produto;
import com.example.storecheckoutsystem.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MarkupService markupService;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, MarkupService markupService) {
        this.produtoRepository = produtoRepository;
        this.markupService = markupService;
    }

    public Iterable<Produto> pesquisaProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Integer id) {
        Produto produto = produtoRepository.findById(id).orElseThrow();
        Produto produtoDTO = new Produto();
        produtoDTO.setIdProduto(produto.getIdProduto());
        produtoDTO.setNomeProduto(produto.getNomeProduto());
        produtoDTO.setPrecoFinalProduto(produto.getPrecoFinalProduto());
        return produtoDTO;
    }

    public Produto cadastroProduto(Produto produto) {
        Markup lastMarkup = markupService.getLastMarkup();
        float productPrice = produto.getPrecoCustoProduto();
        float calculatedPrice = (float) markupService.calculateProductPrice(productPrice, lastMarkup);
        produto.setPrecoFinalProduto(calculatedPrice);
        return produtoRepository.save(produto);
    }

    public Produto editarProduto(int id, Produto produto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (!optionalProduto.isPresent()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produtoAtual = optionalProduto.get();
        produtoAtual.setPrecoCustoProduto(produto.getPrecoCustoProduto());
        produtoAtual.setQuantidadeProduto(produto.getQuantidadeProduto());
        Markup lastMarkup = markupService.getLastMarkup();
        float productPrice = produto.getPrecoCustoProduto();
        float calculatedPrice = (float) markupService.calculateProductPrice(productPrice, lastMarkup);
        produtoAtual.setPrecoFinalProduto(calculatedPrice);
        return produtoRepository.save(produtoAtual);
    }

    public void excluirProduto(int id) {
        produtoRepository.deleteById(id);
    }

    public int buscarQuantidadeProduto(int id) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (!optionalProduto.isPresent()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produto = optionalProduto.get();
        return produto.getQuantidadeProduto();
    }

    public Produto pedidoCompra(int id, Produto produto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (!optionalProduto.isPresent()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produtoAtual = optionalProduto.get();
        produtoAtual.setPrecoCustoProduto(produto.getPrecoCustoProduto());
        produtoAtual.setQuantidadeProduto(produtoAtual.getQuantidadeProduto() + produto.getQuantidadeProduto());
        Markup lastMarkup = markupService.getLastMarkup();
        float productPrice = produto.getPrecoCustoProduto();
        float calculatedPrice = (float) markupService.calculateProductPrice(productPrice, lastMarkup);
        produtoAtual.setPrecoFinalProduto(calculatedPrice);
        return produtoRepository.save(produtoAtual);
    }

    public void removerProdutos(List<Map<String, Object>> produtos) {
        for (Map<String, Object> produto : produtos) {
            Integer idProduto = (Integer) produto.get("id_produto");
            Integer quantidade = (Integer) produto.get("quantidade");

            Produto produtoEntity = produtoRepository.findById(idProduto).orElseThrow();

            int novaQuantidade = produtoEntity.getQuantidadeProduto() - quantidade;
            if (novaQuantidade <= 0) {
                novaQuantidade = 0;
                throw new IllegalStateException("Quantidade do produto não pode ser reduzida abaixo de zero");
            }

            produtoEntity.setQuantidadeProduto(novaQuantidade);
            produtoRepository.save(produtoEntity);
        }
    }
}
