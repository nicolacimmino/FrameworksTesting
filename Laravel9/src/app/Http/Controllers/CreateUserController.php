<?php

namespace App\Http\Controllers;

use App\Http\Transformers\CreateUserResponseTransformer;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class CreateUserController extends Controller
{
    public function __invoke(Request $request, CreateUserResponseTransformer $responseTransformer)
    {
        $user = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => Hash::make($request->password)
        ]);

        return $responseTransformer->fromUser($user);
    }
}
