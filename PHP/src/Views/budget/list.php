<h1 class="page-title">

    Budgets

</h1>

<div class="toolbar">

    <a
        href="?page=budget-create"
        class="btn">

        ➕ Nouveau budget

    </a>

</div>

<div class="card">

    <table>

        <thead>

            <tr>

                <th>Catégorie</th>
                <th>Maximum</th>
                <th>Consommé</th>
                <th>Restant</th>
                <th>Actions</th>

            </tr>

        </thead>

        <tbody>

            <?php if (empty($budgets)): ?>

                <tr>

                    <td colspan="5" class="empty">

                        Aucun budget enregistré.

                    </td>

                </tr>

            <?php endif; ?>

            <?php foreach ($budgets as $budget): ?>

                <tr>

                    <td>

                        <?= htmlspecialchars($budget->getNomCategorie()) ?>

                    </td>

                    <td>

                        <?= number_format($budget->getMontantMaximum(),0,","," ") ?> FCFA

                    </td>

                    <td>

                        <?= number_format($budget->getMontantConsomme(),0,","," ") ?> FCFA

                    </td>

                    <td>

                        <?= number_format($budget->getMontantRestant(),0,","," ") ?> FCFA

                    </td>

                    <td>

                        <a
                            class="btn-small"
                            href="?page=budget-edit&id=<?= $budget->getIdBudget() ?>">

                            Modifier

                        </a>

                        <a
                            class="btn-small danger"
                            href="?page=budget-delete&id=<?= $budget->getIdBudget() ?>">

                            Supprimer

                        </a>

                    </td>

                </tr>

            <?php endforeach; ?>

        </tbody>

    </table>

</div>