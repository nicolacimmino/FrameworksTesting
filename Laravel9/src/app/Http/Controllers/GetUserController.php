<?php

namespace App\Http\Controllers;

use App\Http\Transformers\GetUserResponseTransformer;
use Illuminate\Http\Request;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class GetUserController extends Controller
{
    public function __invoke(Request $request, GetUserResponseTransformer $responseTransformer)
    {
        if ($request['user_id'] != $request->user()->id) {
            throw new NotFoundHttpException("User not found");
        }

        return $responseTransformer->fromUser($request->user());
    }
}
