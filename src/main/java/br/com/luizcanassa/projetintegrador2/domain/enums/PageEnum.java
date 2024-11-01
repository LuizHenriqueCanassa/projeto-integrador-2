package br.com.luizcanassa.projetintegrador2.domain.enums;


import lombok.Getter;

@Getter
public enum PageEnum {
    DASHBOARD("dashboard", "Página Inicial"),
    ORDERS_TABLE("orders-table", "Realizar pedido"),
    LOGIN("login", "Login"),
    USERS("users", "Usuários"),
    CREATE_USERS("users", "Cadastrar Usuário"),
    EDIT_USERS("users", "Editar Usuário"),
    CATEGORIES("categories", "Categorias"),
    CREATE_CATEGORIES("categories", "Cadastrar Categoria"),
    EDIT_CATEGORIES("categories", "Editar Categoria"),
    PRODUCTS("products", "Produtos"),
    CREATE_PRODUCTS("products", "Cadastrar Produto"),
    EDIT_PRODUCTS("products", "Editar Produto"),
    CUSTOMERS("customers", "Clientes"),
    CREATE_CUSTOMERS("customers", "Cadastrar Cliente"),
    EDIT_CUSTOMERS("customers", "Editar Cliente"),
    ORDERS_LOCAL("orders-local", "Pedidos no estabelecimento"),
    ORDERS_LOCAL_CREATE("orders-local", "Criar pedido no estabelecimento"),
    ORDERS_LOCAL_DETAILS("orders-local", "Detalhes do pedido"),
    ORDERS_DELIVERY("orders-delivery", "Pedidos para delivery"),
    ORDERS_DELIVERY_CREATE("orders-delivery", "Criar pedido para delivery"),
    ORDERS_DELIVERY_DETAILS("orders-delivery", "Detalhes do pedido"),
    UNAUTHORIZED("403", "Não Autorizado"),
    NOT_FOUND("404", "Não Encontrado");

    private final String currentPage;

    private final String pageName;

    PageEnum(String currentPage, String pageName) {
        this.currentPage = currentPage;
        this.pageName = pageName;
    }

}
