import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/aluno.dart';
import '../models/prova.dart';

class SelectProvaScreen extends StatefulWidget {
  final Aluno aluno;

  const SelectProvaScreen({super.key, required this.aluno});

  @override
  State<SelectProvaScreen> createState() => _SelectProvaScreenState();
}

class _SelectProvaScreenState extends State<SelectProvaScreen> {
  final ApiService _apiService = ApiService();
  List<Prova> provas = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadProvas();
  }

  Future<void> _loadProvas() async {
    try {
      final result = await _apiService.getProvas();
      setState(() {
        provas = result;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Erro ao carregar provas: $e'),
            backgroundColor: Colors.red,
          ),
        );
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Provas - ${widget.aluno.nome}'),
        backgroundColor: const Color(0xFF2C3E50),
        foregroundColor: Colors.white,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : Column(
              children: [
                Container(
                  width: double.infinity,
                  padding: const EdgeInsets.all(16),
                  color: const Color(0xFFE8F4FD),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      const Text(
                        'Aluno Selecionado:',
                        style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: Color(0xFF2C3E50),
                        ),
                      ),
                      Text(
                        widget.aluno.nome,
                        style: const TextStyle(fontSize: 16),
                      ),
                      Text(
                        'Username: ${widget.aluno.username}',
                        style: const TextStyle(color: Colors.grey),
                      ),
                    ],
                  ),
                ),
                Expanded(
                  child: ListView.builder(
                    padding: const EdgeInsets.all(16),
                    itemCount: provas.length,
                    itemBuilder: (context, index) {
                      final prova = provas[index];
                      return Card(
                        margin: const EdgeInsets.only(bottom: 12),
                        child: ListTile(
                          leading: const CircleAvatar(
                            backgroundColor: Color(0xFF27AE60),
                            child: Icon(Icons.assignment, color: Colors.white),
                          ),
                          title: Text(prova.titulo),
                          subtitle: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              Text('Turma: ${prova.turma.nome}'),
                              Text(
                                  'Disciplina: ${prova.turma.disciplina.nome}'),
                              Text('Data: ${prova.data}'),
                              Text('Questões: ${prova.quantidadeQuestoes}'),
                            ],
                          ),
                          trailing: const Icon(Icons.arrow_forward_ios),
                          onTap: () {
                            Navigator.pushNamed(
                              context,
                              '/answer-sheet',
                              arguments: {
                                'aluno': widget.aluno,
                                'prova': prova,
                              },
                            );
                          },
                        ),
                      );
                    },
                  ),
                ),
              ],
            ),
    );
  }
}
