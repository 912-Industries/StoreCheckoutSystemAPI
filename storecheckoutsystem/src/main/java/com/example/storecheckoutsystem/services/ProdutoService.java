package com.example.storecheckoutsystem.services;

import com.example.storecheckoutsystem.model.CategoriaProduto;
import com.example.storecheckoutsystem.model.Markup;
import com.example.storecheckoutsystem.model.PrecoProduto;
import com.example.storecheckoutsystem.model.Produto;
import com.example.storecheckoutsystem.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
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
        Optional<Produto> produtoOptional = produtoRepository.findById(id);
        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            Produto produtoModel = new Produto();
            BeanUtils.copyProperties(produto, produtoModel);

            // Acessar a categoria do produto
            CategoriaProduto categoriaProduto = produto.getCategoriaProduto();
            produtoModel.setCategoriaProduto(categoriaProduto);

            // Acessar o preço do produto
            PrecoProduto precoProduto = produto.getPrecoProduto();
            PrecoProduto precoProdutoModel = new PrecoProduto();
            BeanUtils.copyProperties(precoProduto, precoProdutoModel);
            produtoModel.setPrecoProduto(precoProdutoModel);

            return produtoModel;
        } else {
            throw new IllegalArgumentException("Produto não encontrado");
        }
    }

    public Produto cadastroProduto(Produto produto) {
        Markup lastMarkup = markupService.getLastMarkup();
        PrecoProduto precoProduto = produto.getPrecoProduto();
        if (precoProduto.getPrecoCustoProduto()!= null) {
            float calculatedPrice = (float) markupService.calculateProductPrice(precoProduto.getPrecoCustoProduto(), lastMarkup);
            precoProduto.setPrecoFinalProduto((double) calculatedPrice);
        } else {
            // handle the case where precoCustoProduto is null
            precoProduto.setPrecoFinalProduto(0.0); // or some other default value
        }

        Produto produtoModel = new Produto();
        BeanUtils.copyProperties(produto, produtoModel);

        CategoriaProduto categoriaProduto = produto.getCategoriaProduto();
        produtoModel.setCategoriaProduto(categoriaProduto);

        PrecoProduto precoProdutoModel = new PrecoProduto();
        BeanUtils.copyProperties(precoProduto, precoProdutoModel);
        produtoModel.setPrecoProduto(precoProdutoModel);

        return produtoRepository.save(produtoModel);
    }

    public Produto editarProduto(int id, Produto produto) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (!optionalProduto.isPresent()) {
            throw new RuntimeException("Produto não encontrado");
        }

        Produto produtoAtual = optionalProduto.get();
        Produto produtoModel = new Produto();
        PrecoProduto precoProduto = produto.getPrecoProduto();
        BeanUtils.copyProperties(produtoAtual, produtoModel);

        // Atualizar campos do produto
        produtoModel.setQuantidadeProduto(produto.getQuantidadeProduto());

        // Atualizar preço final do produto
        Double precoCustoProduto = precoProduto.getPrecoCustoProduto();
        Markup lastMarkup = markupService.getLastMarkup();
        float calculatedPrice = (float) markupService.calculateProductPrice(precoCustoProduto, lastMarkup);
        precoProduto.setPrecoFinalProduto((double) calculatedPrice);

        // Atualizar categoria do produto
        CategoriaProduto categoriaProduto = produtoAtual.getCategoriaProduto();
        produtoModel.setCategoriaProduto(categoriaProduto);

        // Atualizar preço do produto
        precoProduto = produtoAtual.getPrecoProduto();
        PrecoProduto precoProdutoModel = new PrecoProduto();
        BeanUtils.copyProperties(precoProduto, precoProdutoModel);
        produtoModel.setPrecoProduto(precoProdutoModel);

        return produtoRepository.save(produtoModel);
    }

    public void excluirProduto(int id) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
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
        Produto produtoModel = new Produto();
        PrecoProduto precoProduto = produtoAtual.getPrecoProduto();
        PrecoProduto precoProdutoModel = new PrecoProduto();
        BeanUtils.copyProperties(produtoAtual, produtoModel);

        precoProduto.setPrecoCustoProduto(precoProduto.getPrecoCustoProduto());
        produtoModel.setQuantidadeProduto(produtoAtual.getQuantidadeProduto() + produto.getQuantidadeProduto());

        Markup lastMarkup = markupService.getLastMarkup();
        Double productPrice = precoProduto.getPrecoCustoProduto();
        float calculatedPrice = (float) markupService.calculateProductPrice(productPrice, lastMarkup);
        precoProdutoModel.setPrecoFinalProduto((double) calculatedPrice);

        // Acessar a categoria do produto
        CategoriaProduto categoriaProduto = produtoAtual.getCategoriaProduto();
        produtoModel.setCategoriaProduto(categoriaProduto);

        // Acessar o preço do produto
        BeanUtils.copyProperties(precoProduto, precoProdutoModel);
        produtoModel.setPrecoProduto(precoProdutoModel);

        return produtoRepository.save(produtoModel);
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
