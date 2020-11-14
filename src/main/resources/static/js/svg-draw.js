// const myVivus = 
new Vivus("my-svg-div",
    {duration: 200, file: "../images/file-cabinet.svg"}, myVivus => {
        // myVivus.play(myVivus.getStatus() === "end" ? -1 : 1)
        if(myVivus.getStatus() === "end") {
            // paint after line animations are complete
            paint(true)
            // after wating a second
            setTimeout(() => {
                // setTimeout(() => myVivus.reset(), 3000)
                myVivus.reset()
                paint(false)
                myVivus.play()
                
            }, 3000)
            // myVivus.reset().play().destroy()
        } 
    });


function paint(fill){
    fillPath(fill, "color-1", "url(#paint0_linear)");
    fillPath(fill, "color-2", "black");
    fillPath(fill, "color-3", "url(#paint1_linear)");
    fillPath(fill, "color-4", "url(#paint2_linear)");
    fillPath(fill, "color-5", "url(#paint3_linear)");
    fillPath(fill, "color-6", "url(#paint4_linear)");
    fillPath(fill, "color-7", "url(#paint5_linear)");
    fillPath(fill, "color-8", "url(#paint6_linear)");
    fillPath(fill, "color-9", "url(#paint7_linear)");
    fillPath(fill, "color-10", "url(#paint8_linear)");
    fillPath(fill, "color-11", "url(#paint9_linear)");
    fillPath(fill, "color-12", "url(#paint10_linear)");
    fillPath(fill, "color-13", "url(#paint11_linear)");
    fillPath(fill, "color-14", "url(#paint12_linear)");
    fillPath(fill, "color-15", "url(#paint13_linear)");
    fillPath(fill, "color-16", "url(#paint14_linear)");
    fillPath(fill, "color-17", "url(#paint15_linear)");
    fillPath(fill, "color-18", "url(#paint16_linear)");
    fillPath(fill, "color-19", "white");
    fillPath(fill, "color-20", "url(#paint17_linear)");
    fillPath(fill, "color-21", "url(#paint18_linear)");
    fillPath(fill, "color-22", "url(#paint19_linear)");
    fillPath(fill, "color-23", "url(#paint20_linear)");
    fillPath(fill, "color-24", "url(#paint21_linear)");
    fillPath(fill, "color-25", "url(#paint22_linear)");
    fillPath(fill, "color-26", "url(#paint23_linear)");
    fillPath(fill, "color-27", "url(#paint24_linear)");
    fillPath(fill, "color-28", "url(#paint25_linear)");
    fillPath(fill, "color-29", "url(#paint26_linear)");
}

function fillPath(fill, classname, color){
    const paths = document.querySelectorAll(`#my-svg-div .${classname}`);
    if(fill) {
        for(path of paths){
            path.style.fill = `${color}`;
        }
    } else {
        for(path of paths){
            path.style.fill = "none";
        }
    }
}
