document.addEventListener("DOMContentLoaded", function() {
  fetch("/api/submission/list")
      .then(response => response.json())
      .then(submissions => {
          const submissionsTableBody = document.querySelector("#submissions-list tbody");
          submissions.forEach(submission => {
              const submissionRow = document.createElement("tr");
              submissionRow.classList.add("table-row");
              submissionRow.setAttribute("data-id", submission.id);
             
              submissionRow.innerHTML = `
                  <td>${submission.problem.title}</td>
                  <td class="${submission.status.toLowerCase()}">${submission.status}</td>
              `;
              submissionsTableBody.appendChild(submissionRow);
          });
      })
      .catch(error => console.error("Error fetching submissions:", error))
       .then(() => fetch("/api/auth/user", {
            credentials: 'same-origin'
          }))
          .then(response => response.text())
          .then(textResponse => console.log(textResponse))

});