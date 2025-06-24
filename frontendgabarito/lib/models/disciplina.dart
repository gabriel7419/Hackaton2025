class Disciplina {
  final int id;
  final String nome;
  final String codigo;

  Disciplina({
    required this.id,
    required this.nome,
    required this.codigo,
  });

  factory Disciplina.fromJson(Map<String, dynamic> json) {
    return Disciplina(
      id: json['id'],
      nome: json['nome'],
      codigo: json['codigo'],
    );
  }
}
