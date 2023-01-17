<?php

namespace App\Http\Transformers;

use App\Models\User;
use Illuminate\Http\JsonResponse;

class GetUserResponseTransformer extends UserResponseTransformer
{
    function fromUser(User $user): JsonResponse
    {
        return response()->json(
            $this->jsonDataFromUser($user)
        )->withHeaders([
            "Content-Type" => "application/json"
        ])->setStatusCode(200);
    }
}
