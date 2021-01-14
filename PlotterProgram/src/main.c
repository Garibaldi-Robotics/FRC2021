#include <stdio.h>
#include <math.h>
#include <stdbool.h>
#include <raylib.h>

#define RAYGUI_IMPLEMENTATION
#define RAYGUI_SUPPORT_ICONS
#include <raygui.h>

typedef struct POI
{
    int x, y, action;
} POI;


int poisLength = 0;
int selectedPOI = 0;
POI pois[128];

void newFile() {
    for (int i = 0; i < 128; i++)
        pois[i] = (POI) {0};
    poisLength = 0;
}

void saveFile() {

    if (poisLength < 2) return;

    char* file = malloc(poisLength * (3 + sizeof(int) * 3));
    int filePos = 0;


    if (poisLength > 100)
        sprintf(file, "%i", poisLength);
    else if (poisLength > 10)
        sprintf(file, "0%i", poisLength);
    else
        sprintf(file, "00%i", poisLength);

    for (int i = 0; i < poisLength; i++)
        sprintf(file, "%s\n%i,%i,%i,", file, pois[i].x, pois[i].y, pois[i].action);
    
    SaveFileText("./route.csv", file);
}

void openFile() {
    newFile();

    char* file = LoadFileText("./route.csv");
    char poiAmmount[3];

    for (int i = 0; i < strlen(file); i++) {
        if (file[i] == '\n') break;
        
        sprintf(poiAmmount, "%c", file[i]);
    }
    poisLength = TextToInteger(poiAmmount);

    int pos = 4;
    for (int i = 0; i < poisLength; i++) {
        char line[12] = "";

        // Seperate first line
        for (int j = pos; j < strlen(file); j++) {
            if (file[j] == '\n') {
                pos++;
                break;
            }
            sprintf(line, "%s%c", line, file[j]);
            pos++;
        }

        char currNum[4] = "";
        int currNumIndex = 0;
        for (int j = 0; j < 12; j++) {
            if (line[j] == ',') {

                if (currNumIndex == 0)
                    pois[i].x = TextToInteger(currNum);
                else if (currNumIndex == 1)
                    pois[i].y = TextToInteger(currNum);
                else
                    pois[i].action = TextToInteger(currNum);
                
                sprintf(currNum, "");
                currNumIndex++;
            } else
                sprintf(currNum, "%s%c", currNum, line[j]);
        }
    }
}

void newPOI() {
    pois[poisLength] = (POI) { pois[selectedPOI].x, pois[selectedPOI].y, 0};
    poisLength++;
}

void removePOI() {

    if (selectedPOI == poisLength -1) {
        poisLength--;
        return;
    }

    for (int i = selectedPOI + 1; i < poisLength; i++)
        pois[i -1] = pois[i];

    poisLength--;
    
}

int main(void) {
    const int screenWidth = 1920;
    const int screenHeight = 1080;

    InitWindow(screenWidth, screenHeight, "Auto Plotter 2");
    SetTargetFPS(60);

    //GuiLoadStyle("resources/cyber/cyber.rgs");

    Vector2 fieldOffset = (Vector2) {248, 28};
    Vector2 mousePosition = { 0 };
    int topButton = -1;

    int dropdownActive = 0;
    bool dropdownEditMode = false;

    while (!WindowShouldClose())
    {
        mousePosition = GetMousePosition();

        

        BeginDrawing();
        ClearBackground(RAYWHITE);

        //#region Field plotting

        // Draw lines between points
        for (int i = 0; i < poisLength; i++)
        {
            if (poisLength > 1 && i > 0)
                DrawLine(pois[i -1].x + fieldOffset.x, pois[i -1].y + fieldOffset.y, pois[i].x + fieldOffset.x, pois[i].y + fieldOffset.y, RED);
        }

        // Draw points
        for (int i = 0; i < poisLength; i++)
        {
            if (i == 0) {

                if (selectedPOI == i)
                    DrawCircle(pois[i].x + fieldOffset.x, pois[i].y + fieldOffset.y, 8, GREEN);
                else
                    DrawCircle(pois[i].x + fieldOffset.x, pois[i].y + fieldOffset.y, 8, BLUE);

            } else {

                if (selectedPOI == i)
                    DrawCircle(pois[i].x + fieldOffset.x, pois[i].y + fieldOffset.y, 8, GREEN);
                else
                    DrawCircle(pois[i].x + fieldOffset.x, pois[i].y + fieldOffset.y, 8, RED);
                
            }
        }
        
        //#endregion

        //#region Sidebar
        GuiPanel((Rectangle) {0, 20, 240, screenHeight - 20});

        
        if (GuiButton((Rectangle) {2, 40, 236, 30}, "New POI")) {
            newPOI();
        }
        if (GuiButton((Rectangle) {2, 72, 236, 30}, "Remove POI")) {
            removePOI();
        }

        GuiPanel((Rectangle) {2, 120, 236, 120});
        GuiLabel((Rectangle) {4, 120, 232, 30}, FormatText("Selected: POI%i", selectedPOI));

        Rectangle xValueBoxRect = (Rectangle) {34, 150, 102, 20};
        if (CheckCollisionPointRec(mousePosition, xValueBoxRect))
            GuiValueBox(xValueBoxRect, "X: ", &pois[selectedPOI].x, 0, screenWidth - 240, true);
        else
            GuiValueBox(xValueBoxRect, "X: ", &pois[selectedPOI].x, 0, screenWidth - 240, false);

        Rectangle yValueBoxRect = (Rectangle) {34, 180, 102, 20};
        if (CheckCollisionPointRec(mousePosition, yValueBoxRect))
            GuiValueBox(yValueBoxRect, "Y: ", &pois[selectedPOI].y, 0, screenHeight - 20, true);
        else
            GuiValueBox(yValueBoxRect, "Y: ", &pois[selectedPOI].y, 0, screenWidth - 20, false);
        
        if (dropdownEditMode) GuiDisable();

        GuiPanel((Rectangle) {2, 244, 236, 720});
        for (int i = 0; i < poisLength; i++)
        {

            if (selectedPOI == i) {
                GuiDisable();
                if (GuiButton((Rectangle) {4, 242 + (32 * i), 232, 30}, TextFormat("POI%i", i))) {
                    selectedPOI = i;
                    dropdownActive = pois[selectedPOI].action;
                }
                GuiEnable();
            } else
            {
               if (GuiButton((Rectangle) {4, 242 + (32 * i), 232, 30}, TextFormat("POI%i", i))) {
                    selectedPOI = i;
                    dropdownActive = pois[selectedPOI].action;
                }
            }
            
        }

        GuiEnable();

        GuiPanel((Rectangle) {0, 964, 240, screenHeight - 964});

        if (GuiDropdownBox((Rectangle) {34, 210, 102, 20}, "None;Load;Launch", &dropdownActive, dropdownEditMode)) {
            dropdownEditMode = !dropdownEditMode;
            TraceLog(LOG_INFO, "%i", dropdownActive);
            pois[selectedPOI].action = dropdownActive;
        }

        
        //#endregion

        //#region TopBar
        GuiPanel((Rectangle) {0, 0, screenWidth, 20});

        if (GuiButton((Rectangle) {0, 0, 40, 20}, "New")) newFile();
        if (GuiButton((Rectangle) {41, 0, 40, 20}, "Save")) saveFile();
        if (GuiButton((Rectangle) {82, 0, 40, 20}, "Open")) openFile();
        if (GuiButton((Rectangle) {123, 0, 85, 20}, "Fullscreen")) ToggleFullscreen();

        //#endregion

        EndDrawing();
    }

    CloseWindow();

    return 0;
}