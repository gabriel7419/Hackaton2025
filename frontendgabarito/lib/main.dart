import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'services/auth_service.dart';
import 'screens/login_screen.dart';
import 'screens/home_screen.dart';
import 'screens/select_student_screen.dart';
import 'screens/select_prova_screen.dart';
import 'screens/answer_sheet_screen.dart';
import 'screens/result_screen.dart';
import 'screens/provas_screen.dart';
import 'theme/app_theme.dart';
import 'models/aluno.dart';
import 'models/prova.dart';

void main() {
  runApp(const GabaritoApp());
}

class GabaritoApp extends StatelessWidget {
  const GabaritoApp({super.key});

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => AuthService(),
      child: MaterialApp(
        title: 'Sistema de Gabarito - UniALFA',
        theme: AppTheme.lightTheme,
        debugShowCheckedModeBanner: false,
        home: const SplashScreen(),
        routes: {
          '/login': (context) => const LoginScreen(),
          '/home': (context) => const HomeScreen(),
          '/select-student': (context) => const SelectStudentScreen(),
          '/provas': (context) => const ProvasScreen(),
        },
        onGenerateRoute: (settings) {
          switch (settings.name) {
            case '/select-prova':
              final aluno = settings.arguments as Aluno;
              return MaterialPageRoute(
                builder: (context) => SelectProvaScreen(aluno: aluno),
              );
            case '/answer-sheet':
              final args = settings.arguments as Map<String, dynamic>;
              return MaterialPageRoute(
                builder: (context) => AnswerSheetScreen(
                  aluno: args['aluno'] as Aluno,
                  prova: args['prova'] as Prova,
                ),
              );
            case '/result':
              final args = settings.arguments as Map<String, dynamic>;
              return MaterialPageRoute(
                builder: (context) => ResultScreen(
                  aluno: args['aluno'] as Aluno,
                  prova: args['prova'] as Prova,
                  resultado: args['resultado'] as Map<String, dynamic>,
                ),
              );
            default:
              return MaterialPageRoute(
                builder: (context) => const NotFoundScreen(),
              );
          }
        },
      ),
    );
  }
}

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen> {
  @override
  void initState() {
    super.initState();
    _checkLoginStatus();
  }

  Future<void> _checkLoginStatus() async {
    final authService = Provider.of<AuthService>(context, listen: false);
    await authService.checkLoginStatus();

    if (mounted) {
      if (authService.isLoggedIn) {
        Navigator.pushReplacementNamed(context, '/home');
      } else {
        Navigator.pushReplacementNamed(context, '/login');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppTheme.primaryColor,
      body: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.school,
              size: 100,
              color: Colors.white,
            ),
            SizedBox(height: 24),
            Text(
              'Sistema de Gabarito',
              style: TextStyle(
                fontSize: 32,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
            Text(
              'UniALFA',
              style: TextStyle(
                fontSize: 18,
                color: Colors.white70,
                letterSpacing: 2,
              ),
            ),
            SizedBox(height: 48),
            CircularProgressIndicator(
              valueColor: AlwaysStoppedAnimation<Color>(Colors.white),
            ),
            SizedBox(height: 16),
            Text(
              'Carregando...',
              style: TextStyle(
                color: Colors.white70,
                fontSize: 16,
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class NotFoundScreen extends StatelessWidget {
  const NotFoundScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Página não encontrada'),
        backgroundColor: AppTheme.primaryColor,
        foregroundColor: Colors.white,
      ),
      body: const Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Icon(
              Icons.error_outline,
              size: 100,
              color: AppTheme.errorColor,
            ),
            SizedBox(height: 24),
            Text(
              '404',
              style: TextStyle(
                fontSize: 48,
                fontWeight: FontWeight.bold,
                color: AppTheme.textPrimaryColor,
              ),
            ),
            Text(
              'Página não encontrada',
              style: TextStyle(
                fontSize: 18,
                color: AppTheme.textSecondaryColor,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
