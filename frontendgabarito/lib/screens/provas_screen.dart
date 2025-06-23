import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/prova.dart';

class ProvasScreen extends StatefulWidget {
  const ProvasScreen({super.key});

  @override
  State<ProvasScreen> createState() => _ProvasScreenState();
}

class _ProvasScreenState extends State<ProvasScreen> {
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
        title: const Text('Provas Disponíveis'),
        backgroundColor: const Color(0xFF2C3E50),
        foregroundColor: Colors.white,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : RefreshIndicator(
              onRefresh: _loadProvas,
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
                      title: Text(
                        prova.titulo,
                        style: const TextStyle(fontWeight: FontWeight.bold),
                      ),
                      subtitle: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          const SizedBox(height: 4),
                          Text('Turma: ${prova.turma.nome}'),
                          Text('Disciplina: ${prova.turma.disciplina.nome}'),
                          Text('Data: ${prova.data}'),
                          Text('Questões: ${prova.quantidadeQuestoes}'),
                        ],
                      ),
                      isThreeLine: true,
                    ),
                  );
                },
              ),
            ),
    );
  }
}
