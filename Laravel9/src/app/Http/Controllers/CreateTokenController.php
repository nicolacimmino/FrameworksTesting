<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class CreateTokenController extends Controller
{
    public function __invoke(Request $request)
    {
        if (!Auth::attempt($request->only(['email', 'password']))) {
            return response("invalid credentials", 401);
        }

        $user = User::where('email', $request['email'])->first();

        $token = $user->createToken("JWT");

        return response()->json([
            'token' => $token->plainTextToken,
            'user_id' => $user->id
        ])->setStatusCode(201);
    }
}
