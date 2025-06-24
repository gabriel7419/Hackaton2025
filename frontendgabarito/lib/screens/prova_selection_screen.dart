import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/aluno.dart';
import '../models/prova.dart';
import '../theme/app_theme.dart';
import 'resposta_screen.dart';

class ProvaSelectionScreen extends StatefulWidget {
  final Aluno aluno;

  ProvaSelectionScreen({required this.aluno});

  @override
  _ProvaSelectionScreenState createState() => _ProvaSelectionScreenState();
}

class _ProvaSelectionScreenState extends State<ProvaSelectionScreen> {
  List<Prova> _provas = [];
  bool _isLoading = true;

  @override
  void initState() {
    super.initState();
    _loadProvas();
  }

  _loadProvas() async {
    try {
      final apiService = ApiService();
      List<Prova> provas = await apiService.getProvas();
      setState(() {
        _provas = provas;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Erro ao carregar provas: $e'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Selecionar Prova')),
      body: Column(
        children: [
          Card(
            margin: EdgeInsets.all(16),
            child: Padding(
              padding: EdgeInsets.all(16),
              child: Row(
                children: [
                  CircleAvatar(
                    backgroundColor: AppTheme.primaryColor,
                    child: Text(
                      widget.aluno.nome[0].toUpperCase(),
                      style: TextStyle(
                        color: Colors.white,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                  ),
                  SizedBox(width: 16),
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          'Aluno Selecionado:',
                          style: TextStyle(
                            fontSize: 14,
                            color: Colors.grey[600],
                          ),
                        ),
                        Text(
                          widget.aluno.nome,
                          style: TextStyle(
                            fontSize: 18,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: _isLoading
                ? Center(child: CircularProgressIndicator())
                : _provas.isEmpty
                    ? Center(
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Icon(Icons.assignment,
                                size: 64, color: Colors.grey),
                            SizedBox(height: 16),
                            Text(
                              'Nenhuma prova disponível',
                              style:
                                  TextStyle(fontSize: 18, color: Colors.grey),
                            ),
                          ],
                        ),
                      )
                    : ListView.builder(
                        padding: EdgeInsets.symmetric(horizontal: 16),
                        itemCount: _provas.length,
                        itemBuilder: (context, index) {
                          final prova = _provas[index];
                          return Card(
                            margin: EdgeInsets.only(bottom: 8),
                            child: ListTile(
                              leading: Container(
                                width: 50,
                                height: 50,
                                decoration: BoxDecoration(
                                  color: AppTheme.accentColor,
                                  borderRadius: BorderRadius.circular(8),
                                ),
                                child: Icon(
                                  Icons.assignment,
                                  color: Colors.white,
                                ),
                              ),
                              title: Text(
                                prova.titulo,
                                style: TextStyle(fontWeight: FontWeight.bold),
                              ),
                              subtitle: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  Text('${prova.disciplinaNome}'),
                                  Text('${prova.turmaNome}'),
                                  Text('${prova.totalQuestoes} questões'),
                                ],
                              ),
                              trailing: Column(
                                mainAxisAlignment: MainAxisAlignment.center,
                                children: [
                                  Icon(Icons.arrow_forward_ios),
                                  SizedBox(height: 4),
                                  Text(
                                    prova.dataProva,
                                    style: TextStyle(
                                      fontSize: 12,
                                      color: Colors.grey[600],
                                    ),
                                  ),
                                ],
                              ),
                              onTap: () {
                                Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                    builder: (context) => RespostaScreen(
                                      aluno: widget.aluno,
                                      prova: prova,
                                    ),
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
