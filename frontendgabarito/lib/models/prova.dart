class Prova {
  final int id;
  final String titulo;
  final String disciplinaNome;
  final String turmaNome;
  final String dataProva;
  final int totalQuestoes;

  Prova({
    required this.id,
    required this.titulo,
    required this.disciplinaNome,
    required this.turmaNome,
    required this.dataProva,
    required this.totalQuestoes,
  });

  factory Prova.fromJson(Map<String, dynamic> json) {
    return Prova(
      id: json['id'],
      titulo: json['titulo'],
      disciplinaNome: json['disciplina']['nome'],
      turmaNome: json['turma']['nome'],
      dataProva: json['dataProva'],
      totalQuestoes: json['totalQuestoes'],
    );
  }
}
