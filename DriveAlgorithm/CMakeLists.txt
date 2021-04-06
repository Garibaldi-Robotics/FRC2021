cmake_minimum_required(VERSION 3.0.0)
project(DriveAlgorithm VERSION 0.1.0)

include(CTest)
enable_testing()

include(${CMAKE_BINARY_DIR}/conanbuildinfo.cmake)
conan_basic_setup()

file(GLOB_RECURSE sources src/*.c src/*.cpp src/*.h)

add_executable(DriveAlgorithm ${sources})

target_link_libraries(DriveAlgorithm ${CONAN_LIBS})

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})
include(CPack)
