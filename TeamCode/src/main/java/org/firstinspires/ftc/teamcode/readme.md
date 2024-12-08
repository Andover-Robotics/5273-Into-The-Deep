
```mermaid
---
title: 5273 Thunder Code Organization
---
classDiagram
class Bot{
    - HardwareMap map
    + void teleOpTick()
}
class Movement{
    - DcMotor leftFront
    - DcMotor rightFront
    - DcMotor leftBack
    - DcMotor rightBack
    - ThreeDeadWheelLocalizer localizer
    + void teleOpTick()
}
class Slides{
    -DcMotor slidesLeft
    -DcMotor slidesRight
    
}
Bot *-- Movement
Bot *--Slides
```