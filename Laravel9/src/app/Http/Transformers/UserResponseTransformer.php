<?php

namespace App\Http\Transformers;

use App\Models\User;
use Illuminate\Http\JsonResponse;

abstract class UserResponseTransformer
{
    protected function jsonDataFromUser(User $user): array
    {
        return [
            'id' => $user->id,
            'name' => $user->name,
            'email' => $user->email,
        ];
    }
}
