function getProblemIdFromPath() {
  const path = window.location.pathname;
  const pathSegments = path.split('/');
  return pathSegments[pathSegments.length - 1];
}

const editor = CodeMirror.fromTextArea(document.getElementById("editor"), {
  mode: "text/x-c++src",
  theme: "dracula",
  lineNumbers: true,
})

editor.setSize("45%")

function sendSubmission() {
  const sourceCode = editor.getValue();
  const language = "CPP";
  const problemId = getProblemIdFromPath();

  let data = {
    sourceCode: {
      content: sourceCode,
      language: language
    },
    problem: {
        id: problemId
    }
  }

  fetch("/api/submission", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
    credentials: 'include' 
  })
    .then((response) => response.text())
    .then((text) => console.log(text));
}