const navTab = document.getElementById("nav-tab");

const entities = ["files", "notes", "credentials"]

const anchors = []

entities.map(entity => {

    const anch = document.createElement("a");
    anch.setAttribute("class", "nav-item nav-link")
    anch.setAttribute("id", `nav-${entity}-tab`)
    anch.setAttribute("data-toggle", "tab")
    anch.setAttribute("href", `#nav-${entity}`)
    anch.setAttribute("role", "tab")
    anch.setAttribute("aria-controls", `nav-${entity}`)
    anch.setAttribute("aria-selected", "false")
    anch.setAttribute("name", entity)
    anch.addEventListener("click", () => sessionStorage.setItem("super-drive-last-tab-opened", entity))
    anch.textContent = entity.charAt(0).toUpperCase() + entity.substring(1)
    navTab.append(anch)
    // anchors.append(anch)
    anchors.push(anch)
})

console.log(sessionStorage.getItem("super-drive-last-tab-opened"))

let index;

switch(sessionStorage.getItem("super-drive-last-tab-opened")){
    case "notes":
        index = 1;
        break
    case "credentials":
        index = 2;
        break
    default:
        index = 0;
        break
}

anchors[index].setAttribute("class", `${anchors[index].getAttribute("class")} active`)
anchors[index].setAttribute("aria-selected", "true")
anchors[index].click()
const div = document.getElementById(`nav-${entities[index]}`)
div.setAttribute("class", "tab-pane fade show active")