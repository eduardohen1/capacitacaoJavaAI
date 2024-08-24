
function limparDados(){
    document.getElementById("resposta").innerHTML = "-";
}

function enviarDados(){
    limparDados();
    var conteudo = document.getElementById("pergunta").value;

    var msgBody = {
        question : conteudo
    }

    var header = {
        method: 'POST',
        body: JSON.stringify(msgBody),
        headers: {
            'Content-Type': 'application/json'
        }
    }
    console.log(msgBody);
    fetch("http://localhost:8080/chat", header)
        .then(res => res.text())
        .then(resp => exibirTexto(resp))
        .catch(err => {
            console.log(err);
        });
}

function exibirTexto(resp){
    console.log(resp);
    document.getElementById("resposta").innerHTML = resp;
}