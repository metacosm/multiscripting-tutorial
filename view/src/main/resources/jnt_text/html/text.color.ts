class ColoredText {
    constructor(public text: string) {
        this.text = text.trim();
    }

    color(): string {
        let split = this.text.split(" ");
        if (split.length === 1) {
            return `<span style='color: ${this.text}'>` + this.text + "</span>";
        } else {
            return this.text;
        }
    }
}

let coloredText = new ColoredText(currentNode.getPropertyAsString('text'));
out.print(coloredText.color());

