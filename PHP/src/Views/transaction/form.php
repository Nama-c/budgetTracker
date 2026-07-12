<h1 class="page-title">

    <?= isset($transaction)
        ? "Modifier une transaction"
        : "Nouvelle transaction" ?>

</h1>

<div class="card">

    <form method="POST">

        <div class="form-group">

            <label>Libellé</label>

            <input
                type="text"
                name="description"
                value="<?= isset($transaction) ? htmlspecialchars($transaction->getDescription()) : "" ?>"
                required>

        </div>

        <div class="form-group">

            <label>Montant</label>

            <input
                type="number"
                name="montant"
                step="0.01"
                value="<?= isset($transaction) ? $transaction->getMontant() : "" ?>"
                required>

        </div>

        <div class="form-group">

            <label>Type</label>

            <select name="type">

                <option
                    value="DEPENSE"
                    <?= isset($transaction) && $transaction->getType() == "DEPENSE" ? "selected" : "" ?>>

                    Dépense

                </option>

                <option
                    value="REVENU"
                    <?= isset($transaction) && $transaction->getType() == "REVENU" ? "selected" : "" ?>>

                    Revenu

                </option>

            </select>

        </div>

        <div class="form-group">

            <label>Catégorie</label>

            <select name="idCategorie">
                <?php foreach ($categories as $categorie): ?>
                    <option
                        value="<?= $categorie->getIdCategorie() ?>"
                        <?= isset($transaction) && $transaction->getIdCategorie() == $categorie->getIdCategorie() ? "selected" : "" ?>>
                        <?= htmlspecialchars($categorie->getNom()) ?>
                    </option>
                <?php endforeach; ?>
            </select>

        </div>

        <div class="form-group">

            <label>Date</label>

            <input
                type="date"
                name="dateTransaction"
                value="<?= isset($transaction)
                            ? $transaction->getDateTransaction()
                            : date('Y-m-d') ?>">

        </div>

        <button class="btn">

            <?= isset($transaction)
                ? "Modifier"
                : "Enregistrer" ?>

        </button>

    </form>

</div>