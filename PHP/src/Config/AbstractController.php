<?php

namespace App\Config;

abstract class AbstractController
{
    protected function render(string $view,array $data = [],string $layout = "app"): void {
        extract($data);
        $view = $view . ".php";
        $layoutFile = __DIR__ . "/../Views/layout/" . $layout . ".php";

        if (file_exists($layoutFile)) {
            require_once $layoutFile;
            return;
        }

        require_once __DIR__ . "/../Views/" . $view . ".php";
    }

    protected function redirect(string $url): void
    {
        header("Location: " . $url);
        exit;
    }
}
