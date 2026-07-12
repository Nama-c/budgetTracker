<?php

$css="app.css";

require_once __DIR__."/header.partial.php";

?>

<div class="app">

    <?php require_once __DIR__."/sidebar.partial.php"; ?>

    <main class="content">

        <?php require __DIR__."/../" . $view; ?>

    </main>

</div>

<?php require_once __DIR__."/footer.partial.php"; ?>