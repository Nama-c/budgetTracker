<?php require_once __DIR__ . "/../layout/header.partial.php"; ?>

<div class="container">

    <div class="card">

        <h1>Créer un compte</h1>

        <?php if (isset($erreur)) : ?>

            <div class="alert">
                <?= htmlspecialchars($erreur) ?>
            </div>

        <?php endif; ?>

        <form action="?page=register" method="POST">

            <div class="form-group">
                <input
                    type="text"
                    name="nom"
                    placeholder="Nom"
                    required>
            </div>

            <div class="form-group">
                <input
                    type="text"
                    name="prenom"
                    placeholder="Prénom"
                    required>
            </div>

            <div class="form-group">
                <input
                    type="email"
                    name="email"
                    placeholder="Adresse email"
                    required>
            </div>

            <div class="form-group">
                <input
                    type="password"
                    name="motDePasse"
                    placeholder="Mot de passe"
                    required>
            </div>

            <div class="form-group">
                <input
                    type="password"
                    name="confirmMotDePasse"
                    placeholder="Confirmer le mot de passe"
                    required>
            </div>

            <button
                class="btn-primary"
                type="submit">

                Créer un compte

            </button>

        </form>

        <div class="text-center">

            <p>Vous avez déjà un compte ?</p>

            <a href="?page=login">

                Se connecter

            </a>

        </div>

    </div>

</div>

<?php require_once __DIR__ . "/../layout/footer.partial.php"; ?>