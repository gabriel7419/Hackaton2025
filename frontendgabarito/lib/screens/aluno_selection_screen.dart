import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/aluno.dart';
import '../theme/app_theme.dart';
import 'prova_selection_screen.dart';

class AlunoSelectionScreen extends StatefulWidget {
  @override
  _AlunoSelectionScreenState createState() => _AlunoSelectionScreenState();
}

class _AlunoSelectionScreenState extends State<AlunoSelectionScreen> {
  List<Aluno> _alunos = [];
  bool _isLoading = true;
  String _searchQuery = '';

  @override
  void initState() {
    super.initState();
    _loadAlunos();
  }

  _loadAlunos() async {
    try {
      final apiService = ApiService();
      List<Aluno> alunos = await apiService.getAlunos();
      setState(() {
        _alunos = alunos;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Erro ao carregar alunos: $e'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  List<Aluno> get _filteredAlunos {
    if (_searchQuery.isEmpty) {
      return _alunos;
    }
    return _alunos.where((aluno) {
      return aluno.nome.toLowerCase().contains(_searchQuery.toLowerCase()) ||
          aluno.email.toLowerCase().contains(_searchQuery.toLowerCase());
    }).toList();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Selecionar Aluno')),
      body: Column(
        children: [
          Padding(
            padding: EdgeInsets.all(16),
            child: TextField(
              decoration: InputDecoration(
                hintText: 'Buscar aluno...',
                prefixIcon: Icon(Icons.search),
                border: OutlineInputBorder(
                  borderRadius: BorderRadius.circular(8),
                ),
              ),
              onChanged: (value) {
                setState(() {
                  _searchQuery = value;
                });
              },
            ),
          ),
          Expanded(
            child: _isLoading
                ? Center(child: CircularProgressIndicator())
                : _filteredAlunos.isEmpty
                    ? Center(
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Icon(Icons.search_off,
                                size: 64, color: Colors.grey),
                            SizedBox(height: 16),
                            Text(
                              'Nenhum aluno encontrado',
                              style:
                                  TextStyle(fontSize: 18, color: Colors.grey),
                            ),
                          ],
                        ),
                      )
                    : ListView.builder(
                        padding: EdgeInsets.symmetric(horizontal: 16),
                        itemCount: _filteredAlunos.length,
                        itemBuilder: (context, index) {
                          final aluno = _filteredAlunos[index];
                          return Card(
                            margin: EdgeInsets.only(bottom: 8),
                            child: ListTile(
                              leading: CircleAvatar(
                                backgroundColor: AppTheme.primaryColor,
                                child: Text(
                                  aluno.nome[0].toUpperCase(),
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ),
                              title: Text(
                                aluno.nome,
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                              subtitle: Text(aluno.email),
                              trailing: Icon(Icons.arrow_forward_ios),
                              onTap: () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) =>
                                        ProvaSelectionScreen(aluno: aluno),
                                  ),
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
