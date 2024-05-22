var easyMDE = new EasyMDE({
  element: document.getElementById("statement"),
});
let inputs = []
let outputs = []

document.getElementById('addTestCaseButton').addEventListener('click', function() {
  var testCasesContainer = document.getElementById('testCasesContainer');

  var testCaseDiv = document.createElement('div');
  testCaseDiv.classList.add('input-box');

  var inputLabel = document.createElement('label');
  inputLabel.textContent = 'Input:';
  testCaseDiv.appendChild(inputLabel);

  var inputBox = document.createElement('input');
  inputBox.type = 'text';
  inputBox.required = true;
  testCaseDiv.appendChild(inputBox);
  inputs.push(inputBox)

  var outputLabel = document.createElement('label');
  outputLabel.textContent = 'Output:';
  testCaseDiv.appendChild(outputLabel);

  var outputBox = document.createElement('input');
  outputBox.type = 'text';
  outputBox.required = true;
  testCaseDiv.appendChild(outputBox);
  outputs.push(outputBox)

  testCasesContainer.appendChild(testCaseDiv);
});
document.getElementById('problemForm').addEventListener('submit', function(event) {
  event.preventDefault();

  let formData = new FormData(document.getElementById('problemForm'));
  debugger;
  let formObject = {};
  formData.forEach((value, key) => formObject[key] = value);
  formObject.testCases = []
  for(let i = 0; i < inputs.length; i++){
    formObject.testCases.push({input : inputs[i].value || "", output : outputs[i].value || ""})
  }


  debugger
  fetch('/api/problem', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(formObject),
  })
  .then(response => response.json())
  .then(data => {
    console.log('Success:', data);
    // Handle success
  })
  .catch(error => {
    console.error('Error:', error);
    // Handle error
  });
});
 // document
      //   .getElementById("problemForm")
      //   .addEventListener("submit", function (e) {
      //     console.log("form submit");
      //     e.preventDefault();
      //     var formData = new FormData(this);
      //     formData.set("statement", easyMDE.value());

      //     let object = {};
      //     formData.forEach((value, key) => (object[key] = value));
      //     var jsonObject = JSON.stringify(object);

      //     fetch("/api/problem", {
      //       method: "POST",
      //       body: jsonObject,
      //       headers: {
      //         Accept: "application/json",
      //         "Content-Type": "application/json",
      //       },
      //     })
      //       .then((response) => response.json())
      //       .then((data) => {
      //         if (data.success) {
      //           alert("Problem created successfully!");
      //           this.reset();
      //           easyMDE.value("");
      //         } else {
      //           alert("An error occurred. Please try again.");
      //         }
      //       })
      //       .catch((error) => console.error("Error:", error));
      //   });