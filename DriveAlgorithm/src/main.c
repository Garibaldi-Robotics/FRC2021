#include <stdio.h>
#include <stdbool.h>

#include <raylib.h>

#define RAYGUI_IMPLEMENTATION
#define RAYGUI_SUPPORT_ICONS
#include <raygui.h>

int main() {
    InitWindow(1280, 720, "Window");
    SetTargetFPS(60);

    

    while (!WindowShouldClose()) {

        float x = GetGamepadAxisMovement(0, 0);
        float y = GetGamepadAxisMovement(0, 1);

        BeginDrawing();
            ClearBackground(RAYWHITE);

            DrawRectangle(10, 10, 20, 20, GREEN);
            DrawRectangle(40, 10, 20, 20, GREEN);
            DrawRectangle(10, 40, 20, 20, GREEN);
            DrawRectangle(40, 40, 20, 20, GREEN);

            DrawCircle(120, 120, 60, DARKGRAY);
            DrawLine(120, 120, 120 + x, 120 + y, RED);


        EndDrawing();
    }


}
