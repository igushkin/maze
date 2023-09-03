let board;
let context;
let maze;

const renderingSpeed = 10;
const renderingDelay = 10;
const searchDelay = 50;
const mazePassageColor = '#f7f7f7';
const mazeWallColor = '#3b71ca';
const canvasColor = mazeWallColor;
const textColor = "#000";
const playerColor = '#e96a6c';
const mazeSize = 51;
const canvasHeight = mazeSize;
const canvasWidth = mazeSize;
const canvasBlockSize = 7;

let generateMazeBtn;
let solveMazeBtn;
let isDrawingInProgress = false;
let interruptDrawing = false;
let solutionRequestCounter = 0;
let isExitFound = false;


$(document).ready(function () {
    board = document.getElementById("board");
    generateMazeBtn = document.getElementById("generate-maze-btn");
    solveMazeBtn = document.getElementById("solve-maze-btn")
    $(solveMazeBtn).attr("disabled", true);
    board.height = canvasHeight * canvasBlockSize;
    board.width = canvasWidth * canvasBlockSize;
    context = board.getContext("2d");
    generateMaze();
});

const timer = ms => new Promise(res => setTimeout(res, ms));

async function generateMaze() {
    if (isDrawingInProgress) {
        interruptDrawing = true;
        return;
    }

    this.maze = await requestMaze(mazeSize, mazeSize);
    context.fillStyle = mazeWallColor;
    context.fillRect(0, 0, mazeSize * canvasBlockSize, mazeSize * canvasBlockSize);

    isDrawingInProgress = true;
    $(solveMazeBtn).attr("disabled", true);
    const isInterrupted = !(await drawMaze(this.maze.creationPath));
    isDrawingInProgress = false;
    if (isInterrupted) {
        interruptDrawing = false;
        generateMaze();
    } else {
        $(solveMazeBtn).attr("disabled", false);
    }
}

async function solveMaze() {
    let solution = await requestMazeSolution(this.maze);
    $(generateMazeBtn).attr("disabled", true);
    solutionRequestCounter++;
    await drawSolution(solution);
    solutionRequestCounter--;
    if (solutionRequestCounter == 0) {
        $(generateMazeBtn).attr("disabled", false);
        $(solveMazeBtn).attr("disabled", true);
        isExitFound = false;
    }
}

async function drawMaze(data) {
    context.fillStyle = mazePassageColor;
    for (var index = 0; index < data.length; index += renderingSpeed) {
        await timer(renderingDelay);
        for (var s = 0; s + index < data.length && s < renderingSpeed; s++) {
            if (interruptDrawing) {
                return false;
            }
            var cell = data[s + index];
            context.fillStyle = mazePassageColor;
            context.fillRect(cell.xIndex * canvasBlockSize, cell.yIndex * canvasBlockSize, canvasBlockSize, canvasBlockSize);
        }
    }
    return true;
}

async function drawSolution(data) {

    for (var index = 1; index < data.length && !isExitFound; index++) {
        await timer(searchDelay);

        var prevCell = data[index - 1];
        var curCell = data[index];

        context.fillStyle = playerColor;
        context.fillRect(curCell.xIndex * canvasBlockSize, curCell.yIndex * canvasBlockSize, canvasBlockSize, canvasBlockSize);

        context.fillStyle = mazePassageColor;
        context.fillRect(prevCell.xIndex * canvasBlockSize, prevCell.yIndex * canvasBlockSize, canvasBlockSize, canvasBlockSize);
    }
    isExitFound = true;
    printMessage("Solved!");
}

function printMessage(message) {
    context.font = "30px Arial";
    context.fillStyle = textColor;
    var textWidth = context.measureText(message).width;
    context.fillText(message, (board.width / 2) - (textWidth / 2), board.height / 2);
}


function requestMaze(height, width) {
    return new Promise(function (resolve, reject) {
        $.get("http://localhost:8080/?height=" + height + "&width=" + width, function (data, status) {
            resolve(data);
        });
    })
}

function requestMazeSolution(maze) {
    return new Promise(function (resolve, reject) {
        jQuery.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'type': 'POST',
            'url': 'http://localhost:8080/',
            'data': JSON.stringify(maze),
            'dataType': 'json',
            'success': function (data) {
                resolve(data)
            }
        });
    })
}