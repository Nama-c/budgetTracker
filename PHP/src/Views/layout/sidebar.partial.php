<?php $page = $_GET["page"] ?? "dashboard"; ?>
<nav class="menu">
    <h1 style="color:white; margin: 20px">BudgetTracker</h1>

    <a class="<?= $page == "dashboard" ? "active" : "" ?>"
        href="?page=dashboard">

        🏠 Dashboard

    </a>

    <a class="<?= $page == "categorie" ? "active" : "" ?>"
        href="?page=categorie">

        📂 Catégories

    </a>

    <a class="<?= $page == "budget" ? "active" : "" ?>"
        href="?page=budget">

        💰 Budgets

    </a>

    <a class="<?= $page == "transaction" ? "active" : "" ?>"
        href="?page=transaction">

        💳 Transactions

    </a>

    <a href="?page=logout">

        🚪 Déconnexion

    </a>

</nav>