<?php

namespace App\Http\Controllers;

use App\Http\Requests\CreateUserRequest;
use App\Http\Transformers\CreateUserResponseTransformer;
use App\Models\User;
use Illuminate\Support\Facades\Hash;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;

class CreateUserController extends Controller
{
    public function __invoke(CreateUserRequest $request, CreateUserResponseTransformer $responseTransformer)
    {
        if (User::where('email', $request->input('email'))->exists()) {
            throw new BadRequestHttpException("email already in use.");
        }

        $user = User::create([
            'name' => $request->input('name'),
            'email' => $request->input('email'),
            'password' => Hash::make($request->input('password'))
        ]);

        return $responseTransformer->fromUser($user);
    }
}
