import 'package:flutter/foundation.dart';
import '../models/user.dart';
import 'api_service.dart';
import 'storage_service.dart';

class AuthService extends ChangeNotifier {
  final ApiService _apiService = ApiService();
  final StorageService _storageService = StorageService();

  User? _currentUser;
  bool _isLoading = false;

  User? get currentUser => _currentUser;
  bool get isLoading => _isLoading;
  bool get isLoggedIn => _currentUser != null;

  Future<void> checkLoginStatus() async {
    _isLoading = true;
    notifyListeners();

    if (await _storageService.isLoggedIn()) {
      _currentUser = await _storageService.getUser();
    }

    _isLoading = false;
    notifyListeners();
  }

  Future<Map<String, dynamic>> login(String username, String password) async {
    _isLoading = true;
    notifyListeners();

    try {
      final result = await _apiService.login(username, password);

      if (result['success'] == true) {
        _currentUser = User.fromJson(result['user']);
        await _storageService.saveUser(_currentUser!);
      }

      _isLoading = false;
      notifyListeners();

      return result;
    } catch (e) {
      _isLoading = false;
      notifyListeners();
      return {'success': false, 'message': 'Erro: $e'};
    }
  }

  Future<void> logout() async {
    _currentUser = null;
    await _storageService.logout();
    notifyListeners();
  }
}
