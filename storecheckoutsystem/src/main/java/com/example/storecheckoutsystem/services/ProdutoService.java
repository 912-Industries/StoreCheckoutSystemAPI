package com.example.storecheckoutsystem.services;

import com.example.storecheckoutsystem.model.Markup;
import com.example.storecheckoutsystem.model.PrecoProduto;
import com.example.storecheckoutsystem.model.Produto;
import com.example.storecheckoutsystem.repository.ProdutoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        if (precoProduto.getPrecoCustoProduto() != null) {
            float calculatedPrice = (float) markupService.calculateProductPrice(precoProduto.getPrecoCustoProduto(), lastMarkup);
            BigDecimal finalPrice = new BigDecimal(calculatedPrice).setScale(2, RoundingMode.HALF_UP);
            precoProduto.setPrecoFinalProduto(finalPrice.doubleValue());
        }

        Produto produtoModel = new Produto();
        BeanUtils.copyProperties(produto, produtoModel);
        produtoModel.setPrecoProduto(precoProduto);
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

        // Obter o preço de custo original
        Double precoCustoOriginal = produto.getPrecoProduto().getPrecoCustoProduto();

        // Atualizar preço final do produto
        Markup lastMarkup = markupService.getLastMarkup();
        float calculatedPrice = (float) markupService.calculateProductPrice(precoCustoOriginal, lastMarkup);
        BigDecimal finalPrice = new BigDecimal(calculatedPrice).setScale(2, RoundingMode.HALF_UP);
        precoProduto.setPrecoFinalProduto(finalPrice.doubleValue());

        // Atualizar preço de custo do produto (use original value)
        precoProduto.setPrecoCustoProduto(precoCustoOriginal);

        // Atualizar preço do produto no modelo
        produtoModel.setPrecoProduto(precoProduto);

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

        // Update the precoCusto_precoProduto field in the produtoAtual object
        precoProduto.setPrecoCustoProduto(produto.getPrecoProduto().getPrecoCustoProduto());

        // Copy the properties from produtoAtual to produtoModel
        BeanUtils.copyProperties(produtoAtual, produtoModel);

        // Update the quantidadeProduto field in the produtoModel object
        produtoModel.setQuantidadeProduto(produtoAtual.getQuantidadeProduto() + produto.getQuantidadeProduto());

        // Calculate the precoFinalProduto field
        Markup lastMarkup = markupService.getLastMarkup();
        Double productPrice = precoProduto.getPrecoCustoProduto();
        float calculatedPrice = (float) markupService.calculateProductPrice(productPrice, lastMarkup);
        BigDecimal finalPrice = new BigDecimal(calculatedPrice).setScale(2, RoundingMode.HALF_UP);
        precoProdutoModel.setPrecoFinalProduto(finalPrice.doubleValue());

        // Copy the updated precoCustoProduto value from produtoAtual to precoProdutoModel
        precoProdutoModel.setPrecoCustoProduto(produtoAtual.getPrecoProduto().getPrecoCustoProduto());

        // Copy the updated precoProdutoModel to the produtoModel object
        produtoModel.setPrecoProduto(precoProdutoModel);

        return produtoRepository.save(produtoModel);
    }

    public void removerProdutos(List<Map<String, Object>> produtos) {
        for (Map<String, Object> produto : produtos) {
            Integer idProduto = (Integer) produto.get("id_produto");
            Integer quantidade = (Integer) produto.get("quantidade");

            Produto produtoEntity = produtoRepository.findById(idProduto).orElseThrow();

            int novaQuantidade = produtoEntity.getQuantidadeProduto() - quantidade;
            if (novaQuantidade < 0) {
                throw new IllegalStateException("Quantidade do produto não pode ser reduzida abaixo de zero");
            }

            produtoEntity.setQuantidadeProduto(novaQuantidade);
            produtoRepository.save(produtoEntity);
        }
    }
}
