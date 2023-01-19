<?php

namespace App\Http\Controllers;

use App\Http\Transformers\GetUserResponseTransformer;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class GetUserController extends Controller
{
    public function __invoke(User $user, Request $request, GetUserResponseTransformer $responseTransformer)
    {
        if (Auth::id() !== $user->id) {
            // Don't spill the beans, it's unauthorized, but don't let the caller know if
            //  the user exists or not.
            throw new NotFoundHttpException();
        }

        return $responseTransformer->fromUser($request->user());
    }
}
