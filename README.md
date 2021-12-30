*Languages: [en](README.en.md)*

# Paged Users App

App de listagem de usuários via API remota desenvolvido com a finalidade de estudos e focada no uso da biblioteca **Paging 3** para implementação de paginação.

O projeto foi desenvolvido usando a estratégia de paginação simples somente de fonte remota (webservice) e de fonte remota + local (network + database) com `RemoteMediator`.

As estratégias implementadas foram separadas em 2 *branches*: `paging_source` e `remote_mediator`.

## Paginação Simples

Ao paginar diretamente da rede, a `PagingSource` carrega os dados e retorna um objeto `LoadResult`. A implementação da `PagingSource` é transmitida ao `Pager` usando o parâmetro `pagingSourceFactory`.

Conforme novos dados são exigidos pela IU, o `Pager` chama o método `load()` da `PagingSource` e retorna um fluxo de objetos `PagingData` que encapsulam os novos dados. Normalmente, cada objeto `PagingData` é armazenado em cache no `ViewModel` antes de ser enviado para a IU para exibição.

<img src="https://developer.android.com/topic/libraries/architecture/images/paging3-base-lifecycle.png" width="700">

## Remote Mediator

Uma implementação de `RemoteMediator` ajuda a carregar dados paginados da rede no banco de dados, mas não carrega dados diretamente na IU. Em vez disso, o app usa o banco de dados como ***source of truth***, ou seja, o app só mostra dados que foram armazenados em cache no banco de dados. Uma implementação de `PagingSource` (gerada pelo `Room`) processa o carregamento de dados armazenados em cache do banco de dados na IU.

<img src="https://developer.android.com/topic/libraries/architecture/images/paging3-layered-architecture.svg" width="700">

[*Fonte (Android Developers)*](https://developer.android.com/topic/libraries/architecture/paging/v3-network-db)
