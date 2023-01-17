<?php

use App\Http\Controllers\CreateTokenController;
use App\Http\Controllers\CreateUserController;
use App\Http\Controllers\GetUserController;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware(['throttle:api_anon'])->group(function () {
    Route::post('/users/', CreateUserController::class);
    Route::post('/tokens/', CreateTokenController::class);
});

Route::middleware(['auth:sanctum'])->group(function () {
    Route::get('/users/{user_id}', GetUserController::class)->middleware("auth:sanctum");
});


