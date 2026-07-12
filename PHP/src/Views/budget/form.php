<h1 class="page-title">

    <?= isset($budget)
        ? "Modifier un budget"
        : "Nouveau budget" ?>

</h1>

<div class="card">

    <form method="POST">

        <div class="form-group">

            <label>Catégorie</label>

            <select name="categorie">

                <?php foreach($categories as $categorie): ?>

                    <option
                        value="<?= $categorie->getIdCategorie() ?>">

                        <?= $categorie->getNom() ?>

                    </option>

                <?php endforeach; ?>

            </select>

        </div>

        <div class="form-group">

            <label>Montant</label>

            <input
                type="number"
                name="montant"
                step="0.01"
                required>

        </div>

        <button class="btn">

            Enregistrer

        </button>

    </form>

</div>