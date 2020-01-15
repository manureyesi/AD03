# Tarefa AD03

Nesta tarefa vamos poñer en práctica o visto nesta unidade intentando facer un caso práctico e relativamente funcional. Imos partir da tarefa AD02 modificando certas cousas e engadindo novas funcionalidades.

## Descripción do problema

A situación é a seguinte: necesitamos un programa para xestionar as tendas dunha franquicia de venta de equipos informáticos.
Necesitamos gardar a seguinte información:

* Temos que gardar as **provincias** de España. Esta telas no arquivo provincias.json. Deberase gardar o id e o nome da provincia.
* As **tendas** co seu nome, a provincia e a súa cidade.
* Os **productos** co seu ***nome***, ***descripción*** e ***prezo***.
* Cada tenda terá unha selección de productos. Para cada tenda temos que gardar o ***stock*** que ten dese productos.
* Cada tenda terá unha serie de **empregados**. Cada empregado pode traballar en unha ou varias tendas. Necesitamos gardar o número de ***horas semanais*** que traballa en cada tenda. Ademais necesitamos gardar o se nome e apelidos.
* Os clientes da franquicia. Estes non son clientes de cada tenda, senón da franquicia en xeral. De cada cliente debemos gardar o seu ***nome***, ***apelidos*** e ***email***.

A aplicación deberá poder facer as seguintes accións aos seus usuarios:

* Engadir unha nova tenda.
* Mostrar as tendas.
* Eliminar unha tenda.
* Engadir un producto.
* Mostrar os productos da franquicia.
* Mostrar os productos dunha tenda.
* Engadir un producto a unha tenda.
* Actualizar o stock dun producto nunha determinada tenda.
* Mostrar o stock dun producto dunha tenda.
* Eliminar un producto dunha determinada tenda.
* Eliminar un producto.
* Engadir un cliente.
* Mostrar os clientes
* Eliminar un cliente.
* Ler os titulares do periódico ***El Pais***. (Explícase máis abaixo)
* Sair da aplicación.

Non é necesario realizar unha interface gráfica. Pódese facer un menú que pida os datos pola consola.
A persistencia debe de facerse do seguinte xeito:

* Deberás realizar un diagrama entidade-relación do problema a resolver. (Isto non é necesario entregalo)
* A continuación deberás transformalo ao modelo relacional para obter as táboas necesarias. (Isto non é necesario entregalo)
* Débese gardar os datos da aplicación utilizando a base de datos SQLLite.
* Cando se inicie o programa comprobará se existe a base de datos. Se non existe creará a nova base de datos así como todas as súas táboas. tamén se deberán de cargar tódalas provincias de España.
* Cada vez que se produza un cambio nos datos teremos que actualizar a base de datos.

Para realizar a carga de datos das provincias débese de utilizar a liberías **GSON**.

En canto a lectura dos titulares de “El País” a aplicación tera que ler un RSS. Estes están en formato XML. Tan só se deberán mostrar por pantalla os titulares. Para iso utiliza **SAX** para parsear o documento XML. O arquivo XML telo debaixo da tarefa. Como ampliación e optativo sería interesante en lugar de ler o arquivo descargado, poder ler o arquivo online. Así sempre teriamos os titulares actualizados. O enlace dese XML é o seguinte: ***RSS de El País***[Contribution guidelines for this project](http://ep00.epimg.net/rss/elpais/portada.xml).
