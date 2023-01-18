<?php

namespace App\Exceptions;

use Illuminate\Foundation\Exceptions\Handler as ExceptionHandler;
use Illuminate\Validation\UnauthorizedException;
use Illuminate\Validation\ValidationException;
use Symfony\Component\HttpKernel\Exception\BadRequestHttpException;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class Handler extends ExceptionHandler
{
    public function register()
    {
        $this->renderable(function (NotFoundHttpException $e) {
            return response()->json([
                "error" => $e->getMessage(),
                "error_code" => "NOT_FOUND",
            ])->setStatusCode(404);
        });

        $this->renderable(function (BadRequestHttpException $e) {
            return response()->json([
                "error" => $e->getMessage(),
                "error_code" => "BAD_REQUEST",
            ])->setStatusCode(400);
        });

        $this->renderable(function (ValidationException $e) {
            return response()->json([
                "error" => $e->getMessage(),
                "error_code" => "BAD_REQUEST",
            ])->setStatusCode(400);
        });

        $this->renderable(function (UnauthorizedException $e) {
            return response()->json([
                "error" => $e->getMessage(),
                "error_code" => "UNAUTHORIZED",
            ])->setStatusCode(401);
        });

    }

}
