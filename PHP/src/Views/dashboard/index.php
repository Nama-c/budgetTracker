<h1 class="page-title">
    Tableau de bord
</h1>

<p class="welcome">
    Bonjour <strong><?= htmlspecialchars($_SESSION["user"]->getPrenom()) ?></strong> 👋
</p>

<div class="stats">

    <div class="stat-card">

        <h3>💰 Solde</h3>

        <p><?= number_format($dashboard["solde"],0,","," ") ?> FCFA</p>

    </div>

    <div class="stat-card">

        <h3>📈 Revenus</h3>

        <p><?= number_format($dashboard["revenus"],0,","," ") ?> FCFA</p>

    </div>

    <div class="stat-card">

        <h3>📉 Dépenses</h3>

        <p><?= number_format($dashboard["depenses"],0,","," ") ?> FCFA</p>

    </div>

    <div class="stat-card">

        <h3>🎯 Budgets</h3>

        <p><?= $dashboard["budgets"] ?></p>

    </div>

</div>

<div class="card">

    <h2>Bienvenue sur BudgetTracker</h2>

    <p>

        Depuis ce tableau de bord vous pourrez gérer :

    </p>

    <ul class="dashboard-list">

        <li>📂 Vos catégories</li>

        <li>💰 Vos budgets</li>

        <li>💳 Vos transactions</li>

        <li>📊 Vos statistiques</li>

    </ul>

</div>