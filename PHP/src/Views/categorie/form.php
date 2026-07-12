<h1 class="page-title">

    <?= isset($categorie) ? "Modifier une catégorie" : "Nouvelle catégorie" ?>

</h1>

<div class="card">

    <form method="POST">

        <div class="form-group">

            <label>Nom</label>

            <input
                type="text"
                name="nom"
                value="<?= $categorie->getNom() ?? "" ?>"
                required>

        </div>

        <div class="form-group">

            <label>Type</label>

            <select name="type">

                <option value="DEPENSE">

                    Dépense

                </option>

                <option value="REVENU">

                    Revenu

                </option>

            </select>

        </div>

        <button class="btn">

            Enregistrer

        </button>

    </form>

</div>