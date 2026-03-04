import java.time.LocalDate

// 1. Criar a Classe Requisito (Data Class)
data class Requisito(val mensagemErro: String, val validacao: (String) -> Boolean)

fun main() {
    // 1. Definição da lista de requisitos
    val listaDeRegras = listOf(
        Requisito("A senha deve ter pelo menos 5 caracteres.") { it.length >= 5 },
        Requisito("A senha deve conter pelo menos uma letra maiúscula.") { it.any { c -> c.isUpperCase() } },
        Requisito("A senha deve conter pelo menos um número.") { it.any { c -> c.isDigit() } },
        Requisito("A senha deve conter a palavra 'SATC' (sem distinguir maiúsculas/minúsculas).") { it.contains("SATC", ignoreCase = true) },
        Requisito("A senha deve conter o ano do Hexa (2026).") { it.contains("2026") },
        Requisito("A senha deve conter o Emoji de floco de neve (❄).") { it.contains("❄") },

        // Criatividade 1: Contém o número de caracteres
        Requisito("A senha deve conter o número de caracteres que ela possui (ex: se tem 10 chars, deve ter '10').") { 
            it.contains(it.length.toString()) 
        },

        // Criatividade 2: Soma dos dígitos
        Requisito("A soma de todos os números na senha deve ser igual a 25.") {
            it.filter { c -> c.isDigit() }.sumOf { c -> c.digitToInt() } == 25
        },

        // Criatividade 3: Dia atual em Romanos
        Requisito("A senha deve conter o dia atual em algarismos romanos (ex: hoje é dia ${LocalDate.now().dayOfMonth}, então '${toRoman(LocalDate.now().dayOfMonth)}').") {
            it.contains(toRoman(LocalDate.now().dayOfMonth), ignoreCase = true)
        }
    )

    var senhaAprovada = false

    println("=== VALIDADOR DE SENHA ===")
    println("Crie sua senha absoluta.")

    // 2. Loop de tentativa e erro
    do {
        print("\nDigite sua senha: ")
        val entrada = readLine() ?: ""

        var erroEncontrado: String? = null

        // 3. Engine de Validação
        for (regra in listaDeRegras) {
            if (!regra.validacao(entrada)) {
                erroEncontrado = regra.mensagemErro
                break
            }
        }

        if (erroEncontrado != null) {
            println("❌ ERRO: $erroEncontrado")
        } else {
            println("✅ SUCESSO! Senha aceite pelo Overlord.")
            senhaAprovada = true
        }
    } while (!senhaAprovada)
}

// Função auxiliar para converter número para romano (1 a 31)
fun toRoman(number: Int): String {
    val values = listOf(10, 9, 5, 4, 1)
    val symbols = listOf("X", "IX", "V", "IV", "I")
    
    var num = number
    val sb = StringBuilder()

    for (i in values.indices) {
        while (num >= values[i]) {
            num -= values[i]
            sb.append(symbols[i])
        }
    }
    return sb.toString()
}
