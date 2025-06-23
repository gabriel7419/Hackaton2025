import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/aluno.dart';

class SelectStudentScreen extends StatefulWidget {
  const SelectStudentScreen({super.key});

  @override
  State<SelectStudentScreen> createState() => _SelectStudentScreenState();
}

class _SelectStudentScreenState extends State<SelectStudentScreen> {
  final ApiService _apiService = ApiService();
  List<Aluno> alunos = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadAlunos();
  }

  Future<void> _loadAlunos() async {
    try {
      final result = await _apiService.getAlunos();
      setState(() {
        alunos = result;
        isLoading = false;
      });
    } catch (e) {
      setState(() {
        isLoading = false;
      });
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Erro ao carregar alunos: $e'),
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
        title: const Text('Selecionar Aluno'),
        backgroundColor: const Color(0xFF2C3E50),
        foregroundColor: Colors.white,
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator())
          : ListView.builder(
              padding: const EdgeInsets.all(16),
              itemCount: alunos.length,
              itemBuilder: (context, index) {
                final aluno = alunos[index];
                return Card(
                  margin: const EdgeInsets.only(bottom: 8),
                  child: ListTile(
                    leading: CircleAvatar(
                      backgroundColor: const Color(0xFF3498DB),
                      child: Text(
                        aluno.nome.substring(0, 1).toUpperCase(),
                        style: const TextStyle(color: Colors.white),
                      ),
                    ),
                    title: Text(aluno.nome),
                    subtitle: Text(aluno.username),
                    trailing: const Icon(Icons.arrow_forward_ios),
                    onTap: () {
                      Navigator.pushNamed(
                        context,
                        '/select-prova',
                        arguments: aluno,
                      );
                    },
                  ),
                );
              },
            ),
    );
  }
}
