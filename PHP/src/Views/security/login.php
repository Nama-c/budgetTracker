<?php require_once __DIR__ . "/../layout/header.partial.php"; ?>

<div class="container">

    <div class="card">

        <h1>BudgetTracker</h1>

        <?php if (isset($erreur)) : ?>

            <div class="alert">
                <?= htmlspecialchars($erreur) ?>
            </div>

        <?php endif; ?>

        <form method="POST" action="?page=login">

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

            <button
                class="btn-primary"
                type="submit">

                Se connecter

            </button>

        </form>

        <div class="text-center">

            <p>Vous n'avez pas encore de compte ?</p>

            <a href="?page=register">

                Créer un compte

            </a>

        </div>

    </div>

</div>

<?php require_once __DIR__ . "/../layout/footer.partial.php"; ?>