#include <stdio.h>
#include <math.h>
#include <raylib.h>

#define RAYGUI_IMPLEMENTATION
#define RAYGUI_SUPPORT_ICONS
#include <raygui.h>

#define GUI_FILE_DIALOG_IMPLEMENTATION
#include "gui_file_dialog.h"

int main(void) {
    
    const int screenWidth = 1920;
    const int screenHeight = 1080;

    InitWindow(screenWidth, screenHeight, "Auto Plotter 2.0");
    SetTargetFPS(30);


    Vector2 mousePosition = { 0 };


    while (!WindowShouldClose())
    {
        mousePosition = GetMousePosition();





        BeginDrawing();
        ClearBackground(RAYWHITE);

        GuiPanel((Rectangle) {0, 0, screenWidth, 30});
        GuiButton((Rectangle) {0, 0, 60, 30}, "test");

        EndDrawing();
    }

    CloseWindow();

    return 0;
}
