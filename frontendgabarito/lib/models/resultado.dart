class Resultado {
  final int id;
  final double nota;
  final int acertos;
  final int erros;

  Resultado({
    required this.id,
    required this.nota,
    required this.acertos,
    required this.erros,
  });

  factory Resultado.fromJson(Map<String, dynamic> json) {
    return Resultado(
      id: json['id'],
      nota: json['nota'].toDouble(),
      acertos: json['acertos'],
      erros: json['erros'],
    );
  }
}
