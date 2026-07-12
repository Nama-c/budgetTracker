<h1 class="page-title">

    Catégories

</h1>

<div class="toolbar">

    <a
        href="?page=categorie-create"
        class="btn">

        ➕ Nouvelle catégorie

    </a>

</div>

<div class="card">

    <table>

        <thead>

            <tr>

                <th>Nom</th>

                <th>Type</th>

                <th>Origine</th>

                <th width="180">Actions</th>

            </tr>

        </thead>

        <tbody>

            <?php if (empty($categories)): ?>

                <tr>

                    <td colspan="4" class="empty">

                        Aucune catégorie trouvée.

                    </td>

                </tr>

            <?php endif; ?>

            <?php foreach ($categories as $categorie): ?>

                <tr>

                    <td>

                        <?= htmlspecialchars($categorie->getNom()) ?>

                    </td>

                    <td>

                        <?= htmlspecialchars($categorie->getType()) ?>

                    </td>

                    <td>

                        <?= $categorie->isParDefaut() ? "Commune" : "Personnelle" ?>

                    </td>

                    <td>

                        <a
                            class="btn-small"
                            href="?page=categorie-edit&id=<?= $categorie->getIdCategorie() ?>">

                            Modifier

                        </a>

                        <a
                            class="btn-small danger"
                            onclick="return confirm('Supprimer cette catégorie ?')"
                            href="?page=categorie-delete&id=<?= $categorie->getIdCategorie() ?>">

                            Supprimer

                        </a>

                    </td>

                </tr>

            <?php endforeach; ?>

        </tbody>

    </table>

</div>