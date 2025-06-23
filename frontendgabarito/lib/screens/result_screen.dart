import 'package:flutter/material.dart';
import '../models/aluno.dart';
import '../models/prova.dart';

class ResultScreen extends StatelessWidget {
  final Aluno aluno;
  final Prova prova;
  final Map<String, dynamic> resultado;

  const ResultScreen({
    super.key,
    required this.aluno,
    required this.prova,
    required this.resultado,
  });

  @override
  Widget build(BuildContext context) {
    final nota = resultado['nota']?.toDouble() ?? 0.0;
    final acertos = resultado['acertos'] ?? 0;
    final erros = resultado['erros'] ?? 0;
    final aprovado = nota >= 6.0;

    return Scaffold(
      appBar: AppBar(
        title: const Text('Resultado'),
        backgroundColor: const Color(0xFF2C3E50),
        foregroundColor: Colors.white,
        leading: IconButton(
          icon: const Icon(Icons.home),
          onPressed: () {
            Navigator.pushNamedAndRemoveUntil(
              context,
              '/home',
              (route) => false,
            );
          },
        ),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            // Header
            Card(
              color:
                  aprovado ? const Color(0xFF27AE60) : const Color(0xFFE74C3C),
              child: Padding(
                padding: const EdgeInsets.all(20),
                child: Column(
                  children: [
                    Icon(
                      aprovado ? Icons.check_circle : Icons.cancel,
                      size: 64,
                      color: Colors.white,
                    ),
                    const SizedBox(height: 16),
                    Text(
                      aprovado ? 'APROVADO' : 'REPROVADO',
                      style: const TextStyle(
                        fontSize: 24,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                    Text(
                      'Nota: ${nota.toStringAsFixed(1)}',
                      style: const TextStyle(
                        fontSize: 32,
                        fontWeight: FontWeight.bold,
                        color: Colors.white,
                      ),
                    ),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 24),

            // Detalhes
            Card(
              child: Padding(
                padding: const EdgeInsets.all(16),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Detalhes da Prova',
                      style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 16),
                    _buildDetailRow('Aluno:', aluno.nome),
                    _buildDetailRow('Prova:', prova.titulo),
                    _buildDetailRow('Turma:', prova.turma.nome),
                    _buildDetailRow('Disciplina:', prova.turma.disciplina.nome),
                    _buildDetailRow('Data:', prova.data),
                    const Divider(),
                    _buildDetailRow(
                        'Total de Questões:', '${prova.quantidadeQuestoes}'),
                    _buildDetailRow('Acertos:', '$acertos',
                        color: Colors.green),
                    _buildDetailRow('Erros:', '$erros', color: Colors.red),
                    _buildDetailRow('Percentual:',
                        '${((acertos / prova.quantidadeQuestoes) * 100).toStringAsFixed(1)}%'),
                  ],
                ),
              ),
            ),

            const Spacer(),

            // Botões
            Row(
              children: [
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: () {
                      Navigator.pushNamedAndRemoveUntil(
                        context,
                        '/home',
                        (route) => false,
                      );
                    },
                    icon: const Icon(Icons.home),
                    label: const Text('Voltar ao Início'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color(0xFF3498DB),
                      foregroundColor: Colors.white,
                      padding: const EdgeInsets.all(16),
                    ),
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: ElevatedButton.icon(
                    onPressed: () {
                      Navigator.pushNamed(context, '/select-student');
                    },
                    icon: const Icon(Icons.refresh),
                    label: const Text('Nova Correção'),
                    style: ElevatedButton.styleFrom(
                      backgroundColor: const Color(0xFF27AE60),
                      foregroundColor: Colors.white,
                      padding: const EdgeInsets.all(16),
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildDetailRow(String label, String value, {Color? color}) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 120,
            child: Text(
              label,
              style: const TextStyle(fontWeight: FontWeight.bold),
            ),
          ),
          Expanded(
            child: Text(
              value,
              style: TextStyle(color: color),
            ),
          ),
        ],
      ),
    );
  }
}
