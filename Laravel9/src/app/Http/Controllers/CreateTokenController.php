<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Validation\UnauthorizedException;

class CreateTokenController extends Controller
{
    public function __invoke(Request $request)
    {
        if (!Auth::attempt($request->only(['email', 'password']))) {
            throw new UnauthorizedException();
        }

        $user = User::where('email', $request['email'])->first();

        $token = $user->createToken("JWT");

        return response()->json([
            'token' => $token->plainTextToken,
            'user_id' => $user->id
        ])->setStatusCode(201);
    }
}
