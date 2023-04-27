<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <!-- Inclure les fichiers CSS de Tailwind -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>
<body>
    <div class="container mx-auto">
        <h1 class="text-2xl font-bold text-center mt-6">Connexion</h1>
        <form method="post" action="ServletConnexion" class="max-w-md mx-auto mt-6">
            <div class="mb-4">
                <label for="pseudo" class="block text-gray-700 font-bold mb-2">Pseudo</label>
                <input type="text" name="pseudo" id="pseudo" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="mb-4">
                <label for="mdp" class="block text-gray-700 font-bold mb-2">Mot de passe</label>
                <input type="password" name="mdp" id="mdp" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="flex items-center justify-between">
                <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                    Se connecter
                </button>
            </div>
        </form>
    </div>
</body>
</html>
