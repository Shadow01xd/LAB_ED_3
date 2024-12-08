/**
 * Punto de entrada principal del programa.
 * Presenta un menu de opciones y ejecuta la accion correspondiente
 * segun la eleccion del usuario.
 */
fun main() {
    while (true) {
        mostrarMenu()
        val opcion = readLine()?.trim()

        when (opcion) {
            "1" -> ejecutarBubbleSort()
            "2" -> ejecutarQuickSort()
            "3" -> calcularFactorialInteractivo()
            "4" -> resolverTorresHanoiInteractivo()
            "5" -> {
                println("Saliendo del programa...")
                return
            }
            else -> {
                println("Opcion no valida. Intente de nuevo.\n")
            }
        }
    }
}

/**
 * Muestra el menu principal con las opciones disponibles.
 */
fun mostrarMenu() {
    println("Seleccione una opcion:")
    println("1. Ordenar una lista usando Bubble Sort")
    println("2. Ordenar una lista usando Quick Sort")
    println("3. Calcular el factorial de un numero")
    println("4. Resolver las Torres de Hanoi")
    println("5. Salir")
    print("Opcion: ")
}

/**
 * Solicita una lista de numeros al usuario, la ordena utilizando Bubble Sort,
 * y muestra la lista original, la lista ordenada y el tiempo de ejecucion.
 */
fun ejecutarBubbleSort() {
    val listaNumeros = solicitarListaNumeros()
    println("Lista original: $listaNumeros")
    val inicio = System.nanoTime()
    bubbleSort(listaNumeros)
    val fin = System.nanoTime()
    val tiempo = (fin - inicio) / 1_000_000_000.0
    println("Lista ordenada usando Bubble Sort: $listaNumeros")
    println("Tiempo de ejecucion: ${"%.9f".format(tiempo)} segundos\n")
}

/**
 * Solicita una lista de numeros al usuario, la ordena utilizando Quick Sort,
 * y muestra la lista original, la lista ordenada y el tiempo de ejecucion.
 */
fun ejecutarQuickSort() {
    val listaNumeros = solicitarListaNumeros()
    println("Lista original: $listaNumeros")
    val inicio = System.nanoTime()
    quickSort(listaNumeros, 0, listaNumeros.size - 1)
    val fin = System.nanoTime()
    val tiempo = (fin - inicio) / 1_000_000_000.0
    println("Lista ordenada usando Quick Sort: $listaNumeros")
    println("Tiempo de ejecucion: ${"%.9f".format(tiempo)} segundos\n")
}

/**
 * Solicita un numero entero no negativo y muestra su factorial, calculado de manera recursiva.
 */
fun calcularFactorialInteractivo() {
    val numero = solicitarEnteroPositivo("Ingrese un numero entero no negativo (por ejemplo, 5): ")
    val resultado = factorial(numero)
    println("El factorial de $numero es: $resultado\n")
}

/**
 * Solicita el numero de discos y resuelve las Torres de Hanoi,
 * mostrando cada paso de la solucion.
 */
fun resolverTorresHanoiInteractivo() {
    val n = solicitarEnteroPositivo("Ingrese el numero de discos: ")
    println("Resolviendo Torres de Hanoi con $n discos:")
    var paso = 1
    torresHanoi(n, 'A', 'C', 'B') { disco, desde, hasta ->
        println("Paso $paso: Mover disco $disco de Torre $desde a Torre $hasta")
        paso++
    }
    println()
}

/**
 * Solicita repetidamente al usuario una lista de numeros separados por comas hasta obtener una lista valida.
 * Retorna la lista de enteros obtenida.
 *
 * Ejemplo de entrada: "8,3,5,1,9"
 */
fun solicitarListaNumeros(): MutableList<Int> {
    while (true) {
        println("Ingrese una lista de numeros separados por comas (ej: 8,3,5,1,9):")
        val input = readLine()?.trim()
        if (input.isNullOrEmpty()) {
            println("Entrada vacia. Intente de nuevo.")
            continue
        }

        val listaNumeros = parsearLista(input)
        if (listaNumeros.isNotEmpty()) {
            return listaNumeros
        } else {
            println("No se pudo parsear la lista. Asegurese de ingresar solo numeros separados por comas. Intente de nuevo.")
        }
    }
}

/**
 * Solicita repetidamente un numero entero no negativo hasta que el usuario ingrese uno valido.
 * Retorna el numero ingresado.
 *
 * @param mensaje Mensaje que se muestra al usuario solicitando el numero.
 */
fun solicitarEnteroPositivo(mensaje: String): Int {
    while (true) {
        print(mensaje)
        val input = readLine()?.trim()
        val numero = input?.toIntOrNull()
        if (numero != null && numero >= 0) {
            return numero
        } else {
            println("Entrada no valida. Debe ser un numero entero no negativo. Intente de nuevo.")
        }
    }
}

/**
 * Convierte una cadena de texto con numeros separados por comas en una lista de enteros.
 * Si alguno de los elementos no es un numero valido, sera ignorado.
 *
 * @param input Cadena con numeros separados por comas.
 * @return Lista de enteros parseados. Si no se puede parsear ninguno, retorna una lista vacia.
 */
fun parsearLista(input: String): MutableList<Int> {
    return input.split(",")
        .mapNotNull { it.trim().toIntOrNull() }
        .toMutableList()
}

/**
 * Ordena la lista dada usando el algoritmo Bubble Sort.
 * Complejidad: O(n^2)
 *
 * @param lista Lista de enteros a ordenar (modificada in-place).
 */
fun bubbleSort(lista: MutableList<Int>) {
    val n = lista.size
    for (i in 0 until n) {
        for (j in 0 until n - i - 1) {
            if (lista[j] > lista[j + 1]) {
                val temp = lista[j]
                lista[j] = lista[j + 1]
                lista[j + 1] = temp
            }
        }
    }
}

/**
 * Ordena la lista usando el algoritmo Quick Sort (en su lugar).
 * Complejidad promedio: O(n log n)
 *
 * @param lista Lista a ordenar.
 * @param low Indice inicial.
 * @param high Indice final.
 */
fun quickSort(lista: MutableList<Int>, low: Int, high: Int) {
    if (low < high) {
        val pi = partition(lista, low, high)
        quickSort(lista, low, pi - 1)
        quickSort(lista, pi + 1, high)
    }
}

/**
 * Funcion auxiliar para Quick Sort: particiona el arreglo alrededor de un pivote.
 * Retorna la posicion final del pivote.
 *
 * @param lista Lista a particionar.
 * @param low Indice inicial.
 * @param high Indice final.
 * @return La posicion del pivote despues de la particion.
 */
fun partition(lista: MutableList<Int>, low: Int, high: Int): Int {
    val pivot = lista[high]
    var i = low - 1
    for (j in low until high) {
        if (lista[j] <= pivot) {
            i++
            val temp = lista[i]
            lista[i] = lista[j]
            lista[j] = temp
        }
    }
    val temp = lista[i + 1]
    lista[i + 1] = lista[high]
    lista[high] = temp
    return i + 1
}

/**
 * Calcula el factorial de un numero de forma recursiva.
 * 
 * @param n Numero no negativo.
 * @return El factorial de n. factorial(0) = 1.
 */
fun factorial(n: Int): Long {
    return if (n == 0 || n == 1) 1 else n * factorial(n - 1)
}

/**
 * Resuelve las Torres de Hanoi de forma recursiva.
 *
 * @param n Numero de discos.
 * @param desde Torre origen.
 * @param hasta Torre destino.
 * @param aux Torre auxiliar.
 * @param accion Lambda que representa la accion a realizar en cada movimiento.
 */
fun torresHanoi(n: Int, desde: Char, hasta: Char, aux: Char, accion: (Int, Char, Char) -> Unit) {
    if (n == 1) {
        accion(1, desde, hasta)
        return
    }
    torresHanoi(n - 1, desde, aux, hasta, accion)
    accion(n, desde, hasta)
    torresHanoi(n - 1, aux, hasta, desde, accion)
}
