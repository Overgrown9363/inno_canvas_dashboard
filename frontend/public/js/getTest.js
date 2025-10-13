async function callBackend(){
    let getCourse = await fetch("http://localhost:8080/dashboard/1")
    console.log(getCourse)
}