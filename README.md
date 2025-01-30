# 🌌 Mandelbrot Set Visualizer


Discover the fascinating world of the Mandelbrot Set with this interactive, Java-powered explorer. Built using Swing and BufferedImage, this tool allows you to smoothly zoom into the fractal's intricate details, offering an engaging mathematical experience.


## 🖼️ Screenshots

![Screenshot](https://github.com/IAnuragMahapatra/MandelbrotSet/blob/39d7c6edd6838d3d10fa2499d7f1df83ec690c11/Screenshots/Screenshot1.png)

## 🚀 Installation

### Prerequisites

- Java Development Kit (JDK 8 or later)
- A Java IDE (optional)

### Steps

1. Clone the repository:
   ```sh
   https://github.com/IAnuragMahapatra/MandelbrotSet.git
   cd MandelbrotSet
   ```
2. Compile and run the program:
   ```sh
   javac MandelbrotSet.java
   java MandelbrotSet
   ```

## 🎮 Controls

| Action            | Key / Mouse Button |
| ----------------- | ------------------ |
| Move Up           | `W`                |
| Move Down         | `S`                |
| Move Left         | `A`                |
| Move Right        | `D`                |
| Zoom In           | Left Mouse Button  |
| Zoom Out          | Right Mouse Button |
| Toggle Fullscreen | `F11`              |

## 🎨 Customizing Colors

Modify the `computeColor` method in `MandelbrotSet.java` to change how the fractal is colored.

### Example Color Methods:

#### 1️⃣ **Smooth Gradient**

```java
private int computeColor(int iterations) {
    if (iterations == MAX_ITERATION) return Color.BLACK.getRGB();
    return Color.getHSBColor(iterations / 256f, 1, iterations / (iterations + 8f)).getRGB();
}
```

#### 2️⃣ **Blue Hue Scaling**

```java
private int computeColor(int iterations) {
    if (iterations == MAX_ITERATION) return Color.BLACK.getRGB();
    return new Color(0, 0, iterations % 256).getRGB();
}
```

#### 3️⃣ **Psychedelic Colors**

```java
private int computeColor(int iterations) {
    if (iterations == MAX_ITERATION) return Color.BLACK.getRGB();
    return Color.HSBtoRGB(iterations / 256f, 1, 1);
}
```

#### 4️⃣ **Fire Theme**

```java
private int computeColor(int iterations) {
    if (iterations == MAX_ITERATION) return Color.BLACK.getRGB();
    return Color.getHSBColor(0.02f * iterations, 1.0f, 1.0f);
}
```

#### 5️⃣ **Green Mystic**

```java
private int computeColor(int iterations) {
    if (iterations == MAX_ITERATION) return Color.BLACK.getRGB();
    return new Color(iterations % 128, 255 - (iterations % 128), 64).getRGB();
}
```

#### 6️⃣ **Pastel Shades**

```java
private int computeColor(int iterations) {
    if (iterations == MAX_ITERATION) return Color.BLACK.getRGB();
    return new Color((iterations * 5) % 255, (iterations * 3) % 255, (iterations * 7) % 255).getRGB();
}
```

🔹 **To apply any of these color schemes, replace the existing ****`computeColor`**** method with one of the above!**

## 🌟 Inspiration

This project was inspired by [William Fiset's Fractal Explorer](https://github.com/williamfiset/FractalExplorer). His work motivated me to create my own interactive Mandelbrot Set visualizer with additional features and customization options.

## 📜 License

This project is licensed under the [MIT License](LICENSE).

## 📧 Contact

- **Author**: Anurag Mahapatra
- **Email**: [anurag2005om@gmail.com](mailto:anurag2005om@gmail.com)

Happy exploring! 🚀🎨

