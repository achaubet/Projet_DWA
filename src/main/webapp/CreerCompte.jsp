    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Inscription</title>
        <!-- Inclure les fichiers CSS de Tailwind -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
    </head>
    <body>
        <div class="container mx-auto">
            <h1 class="text-2xl font-bold text-center mt-6">Inscription</h1>
            <form method="post" action="ServletCreerCompte" class="max-w-md mx-auto mt-6">
                <div class="mb-4">
                    <label for="pseudo" class="block text-gray-700 font-bold mb-2">Pseudo</label>
                    <input type="text" name="pseudo" id="pseudo" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                    <label id="pseudo-error" class="block text-gray-700 font-bold mb-2" hidden></label>
                </div>
                <div class="mb-4">
                    <label for="mdp" class="block text-gray-700 font-bold mb-2">Mot de passe</label>
                    <input type="password" name="mdp" id="mdp" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                </div>
                <div class="mb-4">
                    <label for="age" class="block text-gray-700 font-bold mb-2">Âge</label>
                    <input type="number" name="age" id="age" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                </div>
                <div class="mb-4">
                    <label for="sexe" class="block text-gray-700 font-bold mb-2">Genre</label>
                    <select name="sexe" id="sexe" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                        <option value="">Choisir le genre</option>
                        <option value="H">Homme</option>
                        <option value="F">Femme</option>
                        <option value="A">Autre...</option>
                    </select>
                </div>
                <div class="mb-4">
                    <label for="ville" class="block text-gray-700 font-bold mb-2">Ville</label>
                    <input type="text" name="ville" id="ville" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                </div>
                <div class="flex items-center justify-between">
                <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                    S'inscrire
                </button>
            </div>
        </form>
    </div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script>
    $(document).ready(function() {
        $("#pseudo").on('blur', function() {
            let pseudo = $(this).val().trim();
            $.ajax({
                url: 'ServletVerifierPseudo',
                type: 'POST',
                data: {pseudo: pseudo},
                success: function(reponse) {
                    //console.log(JSON.parse(reponse));
                    console.log(reponse);
                    if(reponse.exist === true){
                        $('#pseudo').addClass('border-red-500');
                        $('#pseudo-error').text('Pseudo déjà existant, veuillez en choisir un autre.').show();
                        $('button[type="submit"]').attr('disabled', true);
                    } else {
                        $('#pseudo').removeClass('border-red-500');
                        $('#pseudo-error').hide();
                        $('button[type="submit"]').attr('disabled', false);
                    }
                }.bind(this)
            });
        });
    });
</script>
    
</html>
