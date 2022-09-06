# HexagonalDDDCodingChallenge

Existem vários problemas com esse exercício no ponto de vista 
da arquitetura de software.
1) o wiremock deveria possuir uma query para
diversos produtos para não gastar com uma query para cada produto.
2) não existe uma relação entre as promoções para que
se possa criar um mecanismo comum a promoções.
3) um produto, podendo conter mais de uma promoção,
poderá ter conflitos.
4) fazer requisições para cada produto irá gerar a necessidade de 
configurar um cache para não fazer requisições repetidas.
5) algumas exigências como a possibilidade de se escanear os
produtos em qualquer ordem não fazem sentido do ponto de vista
do software uma vez que irá gerar queries para cada produto.
(imagine um carrinho com 200 produtos)
6) a adição de novos tipos de promoção irá gerar necessariamente
a demanda por manutenção do software (ou pelo menos de alguma função
de SQL)
7) É muito difícil de pensar que esses items devessem ficar
em cache ao invés de serem controlados pelo front-end
uma vez que seria necessário um controle de usuários em tempo real
e isso iria dar trabalho
8) a aplicação das regras aqui é a parte mais fácil mas a performance
está morta.