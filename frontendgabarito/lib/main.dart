import 'package:flutter/material.dart';
import 'pages/login_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Corretor de Gabaritos',
      theme: ThemeData(
        primaryColor: const Color(0xFF0055A5), // Azul original
        scaffoldBackgroundColor: const Color(0xFFF3E5F5), // Roxo claro de fundo
        colorScheme: ColorScheme.fromSwatch().copyWith(
          primary: const Color(0xFF0055A5), // Azul original
          secondary: const Color(0xFF6C757D), // Cinza original
          surface: Colors.white, // Superfície de cards/inputs
        ),
        inputDecorationTheme: InputDecorationTheme(
          filled: true,
          fillColor: Colors.white,
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: const BorderSide(color: Color(0xFF0055A5)),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(12),
            borderSide: const BorderSide(color: Color(0xFF0055A5), width: 2),
          ),
          hintStyle: const TextStyle(color: Color(0xFF6C757D)),
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: const Color(0xFF0055A5), // Azul original
            foregroundColor: Colors.white,
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(16),
            ),
            textStyle: const TextStyle(fontSize: 16),
            elevation: 4,
            padding: const EdgeInsets.symmetric(vertical: 14),
          ),
        ),
        textTheme: const TextTheme(
          bodyMedium: TextStyle(color: Color(0xFF6C757D)), // Cinza original
          titleLarge: TextStyle(
            color: Color(0xFF0055A5), // Azul para títulos
            fontWeight: FontWeight.bold,
          ),
        ),
        cardTheme: CardTheme(
          elevation: 2,
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(12),
          ),
          color: Colors.white, // Fundo branco para cards
        ),
      ),
      home: const LoginPage(),
    );
  }
}
