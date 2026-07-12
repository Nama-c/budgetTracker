<?php

namespace App\Config;

class Validator
{
    public static function required(string $value): bool
    {
        return trim($value) !== "";
    }

    public static function email(string $email): bool
    {
        return filter_var(
            $email,
            FILTER_VALIDATE_EMAIL
        );
    }

    public static function minLength(string $value,int $length): bool {
        return strlen($value) >= $length;
    }
}