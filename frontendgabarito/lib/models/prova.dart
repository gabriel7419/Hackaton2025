import 'package:gabarito_app/models/turma.dart';

class Prova {
  final int id;
  final String titulo;
  final String data;
  final int quantidadeQuestoes;
  final String? gabarito;
  final Turma turma;

  Prova({
    required this.id,
    required this.titulo,
    required this.data,
    required this.quantidadeQuestoes,
    this.gabarito,
    required this.turma,
  });

  factory Prova.fromJson(Map<String, dynamic> json) {
    return Prova(
      id: json['id'],
      titulo: json['titulo'],
      data: json['data'],
      quantidadeQuestoes: json['quantidadeQuestoes'],
      gabarito: json['gabarito'],
      turma: Turma.fromJson(json['turma']),
    );
  }

  get disciplinaNome => null;
}
