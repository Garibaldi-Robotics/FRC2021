cmake_minimum_required(VERSION 3.0.0)
project(JetsonVision VERSION 0.1.0)

#set(CMAKE_SYSTEM_NAME Linux)
#set(CMAKE_SYSTEM_PROCESSOR x86_64)
#set(CMAKE_C_COMPILER gcc)
#set(CMAKE_CXX_COMPILER g++)

set(CMAKE_SYSTEM_NAME Linux)
set(CMAKE_SYSTEM_PROCESSOR arm)
set(CMAKE_C_COMPILER aarch64-linux-gnu-gcc)
set(CMAKE_CXX_COMPILER aarch64-linux-gnu-g++)

#set(CMAKE_SYSTEM_NAME Windows)
#set(CMAKE_SYSTEM_PROCESSOR x86_64)
#set(CMAKE_C_COMPILER i686-w64-mingw32-gcc)
#set(CMAKE_CXX_COMPILER i686-w64-mingw32-g++)

include(CTest)
enable_testing()

find_package( OpenCV REQUIRED )
include_directories( ${OpenCV_INCLUDE_DIRS} )

add_executable(JetsonVision src/main.cpp)

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)
target_link_libraries( JetsonVision ${OpenCV_LIBS} )