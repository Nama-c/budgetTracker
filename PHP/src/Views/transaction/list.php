<h1 class="page-title">

    Transactions

</h1>

<div class="toolbar">

    <a
        href="?page=transaction-create"
        class="btn">

        ➕ Nouvelle transaction

    </a>

</div>

<div class="card">

    <table>

        <thead>

            <tr>

                <th>Date</th>

                <th>Libellé</th>

                <th>Catégorie</th>

                <th>Montant</th>

                <th>Type</th>

                <th width="180">Actions</th>

            </tr>

        </thead>

        <tbody>

            <?php if(empty($transactions)): ?>

                <tr>

                    <td colspan="6" class="empty">

                        Aucune transaction enregistrée.

                    </td>

                </tr>

            <?php endif; ?>

            <?php foreach($transactions as $transaction): ?>

                <tr>

                    <td>

                        <?= date("d/m/Y", strtotime($transaction->getDateTransaction())) ?>

                    </td>

                    <td>

                        <?= htmlspecialchars($transaction->getDescription()) ?>

                    </td>

                    <td>

                        <?= htmlspecialchars($transaction->getNomCategorie()) ?>

                    </td>

                    <td>

                        <?= number_format($transaction->getMontant(),0,","," ") ?>

                        FCFA

                    </td>

                    <td>

                        <span class="<?= $transaction->getType()=="REVENU" ? "badge-success" : "badge-danger" ?>">

                            <?= $transaction->getType() ?>

                        </span>

                    </td>

                    <td>

                        <a
                            class="btn-small"
                            href="?page=transaction-edit&id=<?= $transaction->getIdTransaction() ?>">

                            Modifier

                        </a>

                        <a
                            class="btn-small danger"
                            href="?page=transaction-delete&id=<?= $transaction->getIdTransaction() ?>">

                            Supprimer

                        </a>

                    </td>

                </tr>

            <?php endforeach; ?>

        </tbody>

    </table>

</div>