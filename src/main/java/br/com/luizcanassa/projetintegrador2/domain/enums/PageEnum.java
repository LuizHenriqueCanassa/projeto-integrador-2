package br.com.luizcanassa.projetintegrador2.domain.enums;


import lombok.Getter;

@Getter
public enum PageEnum {
    DASHBOARD("dashboard", "Página Inicial"),
    LOGIN("login", "Login"),
    USERS("users", "Usuários"),
    CREATE_USERS("users", "Cadastrar Usuário"),
    EDIT_USERS("users", "Editar Usuário"),
    PRODUCTS("products", "Produtos"),
    UNAUTHORIZED("403", "Não Autorizado"),
    NOT_FOUND("404", "Não Encontrado"),;

    private final String currentPage;

    private final String pageName;

    PageEnum(String currentPage, String pageName) {
        this.currentPage = currentPage;
        this.pageName = pageName;
    }

}
