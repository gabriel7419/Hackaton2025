import 'package:gabarito_app/models/disciplina.dart';

class Turma {
  final int id;
  final String nome;
  final String periodo;
  final Disciplina disciplina;

  Turma({
    required this.id,
    required this.nome,
    required this.periodo,
    required this.disciplina,
  });

  factory Turma.fromJson(Map<String, dynamic> json) {
    return Turma(
      id: json['id'],
      nome: json['nome'],
      periodo: json['periodo'],
      disciplina: Disciplina.fromJson(json['disciplina']),
    );
  }
}
